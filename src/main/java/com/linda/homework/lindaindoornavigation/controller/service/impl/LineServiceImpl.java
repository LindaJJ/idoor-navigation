package com.linda.homework.lindaindoornavigation.controller.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.linda.homework.lindaindoornavigation.controller.constants.Constants;
import com.linda.homework.lindaindoornavigation.controller.service.LineService;
import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.model.lindo.RelevantNodeDO;
import com.linda.homework.lindaindoornavigation.util.Tuple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("lineService")
public class LineServiceImpl implements LineService {
    @Resource
    private NodeService nodeService;

    /**
     * 多源最短路径距离保存map 表示节点id之间的距离
     */
    private final Map<Tuple<String, String>, Long> shortestDistance = new ConcurrentHashMap<>();

    /**
     * 多源最短路径保存map 表示节点id之间的路径
     */
    private final Map<Tuple<String, String>, String> shortestPath = new ConcurrentHashMap<>();

    public LineServiceImpl() throws Exception {
        ResponseDTO<List<NodeDO>> responseDTO = nodeService.getAllNodes();
        if(!responseDTO.getSuccess()){
            throw new Exception("初始化节点关系失败");
        }
        List<NodeDO> nodeDOS = responseDTO.getData();

        refreshGraph( shortestPath, shortestDistance, nodeDOS);
    }

    /**
     * 更新最短路径关系和最短距离关系
     * @param shortestPath
     * @param shortestDistance
     * @param nodeDOS
     * @return
     */
    private void refreshGraph(Map<Tuple<String, String>, String> shortestPath, Map<Tuple<String, String>, Long> shortestDistance, List<NodeDO> nodeDOS) {

        initGraph(shortestPath, shortestDistance, nodeDOS);

        for(int k=1; k<=nodeDOS.size(); k++){
            for(int i=1; i<=nodeDOS.size(); i++){
                for(int j=1; j<=nodeDOS.size(); j++){
                    Tuple<String, String> tupleIK = new Tuple<>(nodeDOS.get(i).getNodeId(), nodeDOS.get(k).getNodeId());
                    Tuple<String, String> tupleKJ = new Tuple<>(nodeDOS.get(k).getNodeId(), nodeDOS.get(j).getNodeId());
                    Tuple<String, String> tupleIJ = new Tuple<>(nodeDOS.get(i).getNodeId(), nodeDOS.get(j).getNodeId());
                    Long ikDistance = shortestDistance.get(tupleIK);
                    Long kjDistance = shortestDistance.get(tupleKJ);
                    Long ijDistance = shortestDistance.get(tupleIJ);
                    if(ikDistance + kjDistance < ijDistance){
                        shortestDistance.put(tupleIJ, ikDistance + kjDistance);
                        shortestPath.put(tupleIJ, nodeDOS.get(k).getNodeId());
                    }
                }
            }
        }
    }

    /**
     * @param shortestPath
     * @param shortestDistance
     * @param nodeDOS
     * 初始化图关系
     */
    private void initGraph(Map<Tuple<String, String>, String> shortestPath, Map<Tuple<String, String>, Long> shortestDistance, List<NodeDO> nodeDOS){
        shortestPath.clear();
        shortestDistance.clear();
        for(int i = 0; i < nodeDOS.size(); i++){
            for(int j = 0; j < nodeDOS.size(); j++){
                Tuple<String, String> tuple = new Tuple<>(nodeDOS.get(i).getNodeId(), nodeDOS.get(j).getNodeId());
                shortestPath.put(tuple, null);
                shortestDistance.put(tuple, Long.MAX_VALUE);
            }
        }

        for(int i = 0; i < nodeDOS.size(); i++){
            NodeDO nodeDO = nodeDOS.get(i);
            String nodeId = nodeDO.getNodeId();
            if(nodeDO.getRelevantNode() != null) {
                RelevantNodeDO relevantNodeDO = JSONObject.parseObject(nodeDO.getRelevantNode(), RelevantNodeDO.class);
                initSingleRelevantNode(nodeId, relevantNodeDO.getBackNodeId(), Constants.Direction.BACK, relevantNodeDO.getBackNodeDistance(), shortestPath, shortestDistance);
                initSingleRelevantNode(nodeId, relevantNodeDO.getFrontNodeId(), Constants.Direction.FRONT, relevantNodeDO.getFrontNodeDistance(), shortestPath, shortestDistance);
                initSingleRelevantNode(nodeId, relevantNodeDO.getLeftNodeId(), Constants.Direction.LEFT, relevantNodeDO.getLeftNodeDistance(), shortestPath, shortestDistance);
                initSingleRelevantNode(nodeId, relevantNodeDO.getRightNodeId(), Constants.Direction.RIGHT, relevantNodeDO.getRightNodeDistance(), shortestPath, shortestDistance);
            }
        }
    }

    /**
     * 初始化单个相关节点
     * @param nodeDOId
     * @param relevantNodeDOId
     * @param distance
     * @param shortestPath
     * @param shortestDistance
     */
    private void initSingleRelevantNode(String nodeDOId, String relevantNodeDOId, Constants.Direction direction, Long distance, Map<Tuple<String, String>, String> shortestPath, Map<Tuple<String, String>, Long> shortestDistance){
        if(nodeDOId != null && relevantNodeDOId != null){
            Tuple<String, String> nodeTuple = new Tuple<>(nodeDOId, relevantNodeDOId);
            shortestDistance.put(nodeTuple, distance);
            shortestPath.put(nodeTuple, relevantNodeDOId);
            shortestDistance.put(nodeTuple, Long.MAX_VALUE);
        }
    }
}
