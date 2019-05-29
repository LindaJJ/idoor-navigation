package com.linda.homework.lindaindoornavigation.controller.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linda.homework.lindaindoornavigation.controller.dto.NodeDTO;
import com.linda.homework.lindaindoornavigation.controller.service.LineService;
import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.controller.util.TransUtils;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.model.lindo.RelevantNodeDO;
import com.linda.homework.lindaindoornavigation.util.Tuple;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;

@Component("lineService")
public class LineServiceImpl implements LineService {
    @Resource
    private NodeService nodeService;

    /**
     * 多源最短路径距离保存map 表示节点id之间的距离
     */
    private Map<Tuple<String, String>, Long> shortestDistance = new ConcurrentHashMap<>();

    /**
     * 多源最短路径保存map 表示节点id之间的路径
     */
    private Map<Tuple<String, String>, String> shortestPath = new ConcurrentHashMap<>();

    /**
     * 定时调度 更新最短路径
     */
    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    /**
     * 所有节点
     */
    private List<NodeDO> nodeDOS;

    /**
     * 根据开始地点id和结束地点id获取最短路径
     * @param startNodeId
     * @param endNodeId
     * @return
     */
    @Override
    public List<NodeDTO> getShortestPath(String startNodeId, String endNodeId){
        Tuple<String, String> pathTuple = new Tuple<>(startNodeId, endNodeId);
        if(CollectionUtils.isEmpty(nodeDOS)){
            return new ArrayList<>();
        }
        Map<String, NodeDO> idNodeDOMap = nodeDOS.stream().collect(Collectors.toMap(NodeDO :: getNodeId, nodeDO -> nodeDO));
        String nextNodeId = shortestPath.get(pathTuple);
        List<NodeDO> result = new ArrayList<>();
        result.add(idNodeDOMap.get(startNodeId));
        while(nextNodeId != null && !"null".equals(nextNodeId)){
            result.add(idNodeDOMap.get(nextNodeId));
            pathTuple = new Tuple<>(nextNodeId, endNodeId);
            nextNodeId = shortestPath.get(pathTuple);
        }
        return TransUtils.transferDOList2DTOList(result);
    }

    @Override
    public void refreshPath() {
        refresh();
    }

    private void refresh(){
        ResponseDTO<List<NodeDO>> responseDTO = nodeService.getAllNodes();
        if(!responseDTO.getSuccess()){
            // do nothing about log
            return;
        }
        refresh(responseDTO.getData());
    }

    /**
     * 更新最短路径关系和最短距离关系
     * @param nodeDOS
     * @return
     */
    private void refresh(List<NodeDO> nodeDOS) {

        this.nodeDOS = nodeDOS;

        Tuple<Map<Tuple<String, String>, String>, Map<Tuple<String, String>, Long>> tuple = initGraph(nodeDOS);
        Map<Tuple<String, String>, Long> shortestDistance = tuple.getRight();
        Map<Tuple<String, String>, String> shortestPath = tuple.getLeft();
        for(int k=0; k<nodeDOS.size(); k++){
            for(int i=0; i<nodeDOS.size(); i++){
                for(int j=0; j<nodeDOS.size(); j++){
                    Tuple<String, String> tupleIK = new Tuple<>(nodeDOS.get(i).getNodeId(), nodeDOS.get(k).getNodeId());
                    Tuple<String, String> tupleKJ = new Tuple<>(nodeDOS.get(k).getNodeId(), nodeDOS.get(j).getNodeId());
                    Tuple<String, String> tupleIJ = new Tuple<>(nodeDOS.get(i).getNodeId(), nodeDOS.get(j).getNodeId());
                    Long ikDistance = shortestDistance.get(tupleIK);
                    Long kjDistance = shortestDistance.get(tupleKJ);
                    Long ijDistance = shortestDistance.get(tupleIJ);
                    if(ikDistance != Long.MAX_VALUE && kjDistance != Long.MAX_VALUE && ikDistance + kjDistance < ijDistance){
                        shortestDistance.put(tupleIJ, ikDistance + kjDistance);
                        shortestPath.put(tupleIJ, nodeDOS.get(k).getNodeId());
                    }
                }
            }
        }
        this.shortestPath = shortestPath;
        this.shortestDistance = shortestDistance;
    }

    /**
     * @param nodeDOS
     * 初始化图关系
     */
    private Tuple<Map<Tuple<String, String>, String>, Map<Tuple<String, String>, Long>> initGraph(List<NodeDO> nodeDOS){
        Map<Tuple<String, String>, String> shortestPath = new ConcurrentHashMap<>();
        Map<Tuple<String, String>, Long> shortestDistance = new ConcurrentHashMap<>();
        shortestPath.clear();
        shortestDistance.clear();
        for(int i = 0; i < nodeDOS.size(); i++){
            for (int j = 0; j < nodeDOS.size(); j++) {
                Tuple<String, String> tuple = new Tuple<>(nodeDOS.get(i).getNodeId(), nodeDOS.get(j).getNodeId());
                shortestPath.put(tuple, "null");
                shortestDistance.put(tuple, Long.MAX_VALUE);
            }
        }

        for (NodeDO nodeDO : nodeDOS) {
            String nodeId = nodeDO.getNodeId();
            if (nodeDO.getRelevantNode() != null) {
                JSONArray jsonArray = JSONArray.parseArray(nodeDO.getRelevantNode());
                for (Object object : jsonArray) {
                    JSONObject jsonObject = (JSONObject) object;
                    RelevantNodeDO relevantNodeDO = jsonObject.toJavaObject(RelevantNodeDO.class);
                    initSingleRelevantNode(nodeId, relevantNodeDO.getNodeId(), relevantNodeDO.getDistance(), shortestPath, shortestDistance);
                }
            }
        }
        return new Tuple<>(shortestPath, shortestDistance);
    }

    /**
     * 初始化单个相关节点
     * @param nodeDOId
     * @param relevantNodeDOId
     * @param distance
     * @param shortestPath
     * @param shortestDistance
     */
    private void initSingleRelevantNode(String nodeDOId, String relevantNodeDOId, Long distance, Map<Tuple<String, String>, String> shortestPath, Map<Tuple<String, String>, Long> shortestDistance){
        if(nodeDOId != null && relevantNodeDOId != null && distance != null){
            Tuple<String, String> nodeTuple = new Tuple<>(nodeDOId, relevantNodeDOId);
            shortestDistance.put(nodeTuple, distance);
            shortestPath.put(nodeTuple, relevantNodeDOId);
            shortestDistance.put(nodeTuple, distance);
        }
    }
}
