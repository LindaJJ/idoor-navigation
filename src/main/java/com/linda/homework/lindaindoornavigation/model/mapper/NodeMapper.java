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
}
