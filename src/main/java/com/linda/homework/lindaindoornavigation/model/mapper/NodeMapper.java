package com.linda.homework.lindaindoornavigation.model.mapper;

import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 节点mapper
 */
@Mapper
public interface NodeMapper {

    /**
     * 添加节点
     */
    void addNode(NodeDO nodeDO);

    /**
     * 获取所有的节点
     * @return
     */
    List<NodeDO> getAllNodes();

    /**
     * 删除指定id节点
     * @param nodeId
     */
    void deleteNode(@Param("nodeId")String nodeId);

    /**
     * 获取指定节点
     * @param nodeId
     * @return
     */
    NodeDO getNodeById(@Param("nodeId")String nodeId);

    /**
     * 更新节点中的相关节点
     * @param nodeId
     * @param relevantNode
     */
    void updaterelevantNode(@Param("nodeId")String nodeId, @Param("relevantNode")String relevantNode);
}
