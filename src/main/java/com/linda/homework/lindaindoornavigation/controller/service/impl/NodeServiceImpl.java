package com.linda.homework.lindaindoornavigation.controller.service.impl;

import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.model.mapper.NodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 节点服务实现类
 */
@Component
public class NodeServiceImpl implements NodeService {

    private static Logger logger = LoggerFactory.getLogger(NodeServiceImpl.class);

    @Resource
    private NodeMapper nodeMapper;

    @Override
    public ResponseDTO<Boolean> addNode(NodeDO nodeDO) {
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        try {
            nodeMapper.addNode(nodeDO);
            responseDTO.setSuccess(true);
            responseDTO.setData(true);
        }
        catch (Throwable throwable){
            responseDTO.setSuccess(false);
            responseDTO.setData(false);
            logger.error("NodeServiceImpl#addNode", throwable);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<Boolean> deleteNode(String nodeId) {
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        try {
            nodeMapper.deleteNode(nodeId);
            responseDTO.setSuccess(true);
            responseDTO.setData(true);
        }
        catch (Throwable throwable){
            responseDTO.setSuccess(false);
            responseDTO.setData(false);
            logger.error("NodeServiceImpl#deleteNode", throwable);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<List<NodeDO>> getAllNodes(){
        ResponseDTO<List<NodeDO>> responseDTO = new ResponseDTO<>();
        try {
            List<NodeDO> data = nodeMapper.getAllNodes();
            responseDTO.setSuccess(true);
            responseDTO.setData(data);
        }
        catch (Throwable throwable){
            responseDTO.setSuccess(false);
            logger.error("NodeServiceImpl#getAllNodes", throwable);
        }
        return responseDTO;
    }
}
