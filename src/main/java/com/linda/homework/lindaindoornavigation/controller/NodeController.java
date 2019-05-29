package com.linda.homework.lindaindoornavigation.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linda.homework.lindaindoornavigation.controller.dto.NewNodeDTO;
import com.linda.homework.lindaindoornavigation.controller.dto.NodeDTO;
import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.controller.util.TransUtils;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.util.Base64Util;
import com.linda.homework.lindaindoornavigation.util.IDGenerator;
import com.linda.homework.lindaindoornavigation.util.ImageHelper;
import com.linda.homework.lindaindoornavigation.util.SimilarImageSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 节点Controller
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/node")
public class NodeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Resource
    private NodeService nodeService;

    @RequestMapping("addNode")
    public ResponseDTO<Boolean> addNode(NodeDTO nodeDTO){
        return nodeService.addNode(TransUtils.transferDTO2DO(nodeDTO));
    }

    @RequestMapping("deleteNode")
    public ResponseDTO<Boolean> delete(String nodeId){
        return nodeService.deleteNode(nodeId);
    }

    @RequestMapping("getAllNodes")
    public ResponseDTO<List<NodeDO>> getAllNodes(){
        return nodeService.getAllNodes();
    }

    @PostMapping("newNode")
    public ResponseDTO<Boolean> newNode(NewNodeDTO newNodeDTO) {
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        String nodeId = IDGenerator.generateNodeId(newNodeDTO.getNodeName());
        try {
            ResponseDTO<NodeDO> nodeDOResponseDTO = nodeService.getNodeById(nodeId);
            if(!nodeDOResponseDTO.getSuccess()){
                return buildErrorResult(nodeDOResponseDTO.getErrorMessage());
            }
            if(nodeDOResponseDTO.getData() == null){
                NodeDO nodeDO = new NodeDO();
                nodeDO.setNodeName(newNodeDTO.getNodeName());
                nodeDO.setNodeId(nodeId);
                byte[] pics = ImageHelper.base64tobyteArr(Base64Util.deleteJPEGBase64Prefix(newNodeDTO.getImgStr()));
                nodeDO.setNodePic(newNodeDTO.getImgStr());
                if(pics != null) {
                    nodeDO.setpHashCode(SimilarImageSearch.produceFingerPrint(pics));
                }
                JSONArray jsonArray = new JSONArray();
                JSONObject json = new JSONObject();
                if(!StringUtils.isEmpty(newNodeDTO.getNextNodeName())) {
                    String nextNodeId = IDGenerator.generateNodeId(newNodeDTO.getNextNodeName());
                    json.put("nodeId", nextNodeId);
                }
                if(newNodeDTO.getNextNodeDistance() != null) {
                    json.put("distance", newNodeDTO.getNextNodeDistance());
                }
                if(newNodeDTO.getNextNodeAngle() != null) {
                    json.put("angle", newNodeDTO.getNextNodeAngle());
                }
                jsonArray.add(json);
                nodeDO.setRelevantNode(jsonArray.toJSONString());
                nodeService.addNode(nodeDO);
            } else{
                NodeDO nodeDO = nodeDOResponseDTO.getData();
                JSONArray jsonArray = JSONArray.parseArray(nodeDO.getRelevantNode());
                String nextNodeId = IDGenerator.generateNodeId(newNodeDTO.getNextNodeName());
                refreshJsonArray(nextNodeId, newNodeDTO.getNextNodeDistance(), newNodeDTO.getNextNodeAngle(), jsonArray);
                nodeService.updateRelevantNode(nodeId, jsonArray.toJSONString());
            }
        } catch (IOException e) {
            buildErrorResult(e.getMessage());
            logger.error("NodeController#newNode", e);
        }
        return responseDTO;
    }

    /**
     * refresh jsonarry
     * @param nodeId
     * @param distance
     * @param angle
     * @param jsonArray
     */
    private void refreshJsonArray(String nodeId, Integer distance, Integer angle, JSONArray jsonArray){
        for(Object object : jsonArray){
            JSONObject jsonObject = (JSONObject)object;
            if(jsonObject.get("nodeId").equals(nodeId)){
                jsonObject.put("distance", distance);
                jsonObject.put("angle", angle);
                return;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodeId", nodeId);
        jsonObject.put("distance", distance);
        jsonObject.put("angle", angle);
        jsonArray.add(jsonObject);
    }
}
