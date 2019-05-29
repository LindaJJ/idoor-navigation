package com.linda.homework.lindaindoornavigation.controller.service;

import com.linda.homework.lindaindoornavigation.controller.dto.NodeDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 路线服务
 */
@Component
public interface LineService {

    /**
     * 根据开始节点id和结束节点id获取最短路径
     * @param startNodeId
     * @param endNodeId
     * @return
     */
    List<NodeDTO> getShortestPath(String startNodeId, String endNodeId);

    void refreshPath();
}
