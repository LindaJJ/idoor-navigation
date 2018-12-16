package com.linda.homework.lindaindoornavigation.model.mapper;

import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 节点mapper
 */
@Mapper
public interface NodeMapper {

    /**
     * 添加节点
     */
    void addNode(NodeDO nodeDO);
}
