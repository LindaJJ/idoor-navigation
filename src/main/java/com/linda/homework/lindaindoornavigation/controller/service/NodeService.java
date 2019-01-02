package com.linda.homework.lindaindoornavigation.controller.service;

import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;

import java.util.List;

/**
 * 节点服务
 */
public interface NodeService {

    /**
     * 添加节点
     * @param nodeDO
     * @return
     */
    ResponseDTO<Boolean> addNode(NodeDO nodeDO);

    /**
     * 删除节点
     * @param nodeId
     * @return
     */
    ResponseDTO<Boolean> deleteNode(String nodeId);

    /**
     * 获取所有节点
     * @return
     */
    ResponseDTO<List<NodeDO>> getAllNodes();
}
