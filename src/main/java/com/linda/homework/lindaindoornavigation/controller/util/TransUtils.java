package com.linda.homework.lindaindoornavigation.controller.util;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.linda.homework.lindaindoornavigation.controller.dto.NodeDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.model.lindo.RelevantNodeDO;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO and DO transfer utils
 */
public class TransUtils {

    public static NodeDTO transferDO2DTO(NodeDO nodeDO){
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setNodeId(nodeDO.getNodeId());
        nodeDTO.setNodeName(nodeDO.getNodeName());
        nodeDTO.setpHashCode(nodeDO.getpHashCode());
        nodeDTO.setPic(nodeDO.getNodePic());
        if(!StringUtils.isEmpty(nodeDO.getRelevantNode())){
            nodeDTO.setRelevantNode(JSONArray.parseArray(nodeDO.getRelevantNode(), RelevantNodeDO.class));
        }
        return nodeDTO;
    }

    public static NodeDO transferDTO2DO(NodeDTO nodeDTO){
        NodeDO nodeDO = new NodeDO();
        nodeDO.setNodeId(nodeDTO.getNodeId());
        nodeDO.setNodeName(nodeDTO.getNodeName());
        nodeDO.setpHashCode(nodeDTO.getpHashCode());
        nodeDO.setNodePic(nodeDTO.getPic());
        if(nodeDTO.getRelevantNode() != null) {
            nodeDO.setRelevantNode(JSON.toJSONString(nodeDTO.getRelevantNode()));
        }
        return nodeDO;
    }

    public static List<NodeDO> transferDTOList2DOList(List<NodeDTO> nodeDTOS){
        List<NodeDO> nodeDOS = new ArrayList<>();
        nodeDTOS.forEach(nodeDTO -> {
            nodeDOS.add(transferDTO2DO(nodeDTO));
        });
        return nodeDOS;
    }

    public static List<NodeDTO> transferDOList2DTOList(List<NodeDO> nodeDOS){
        List<NodeDTO> nodeDTOS = new ArrayList<>();
        nodeDOS.forEach(nodeDO -> {
            nodeDTOS.add(transferDO2DTO(nodeDO));
        });
        return nodeDTOS;
    }
}
