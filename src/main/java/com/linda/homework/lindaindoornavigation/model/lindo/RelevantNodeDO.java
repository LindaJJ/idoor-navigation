package com.linda.homework.lindaindoornavigation.model.lindo;

/**
 * 相关节点信息数据对象
 */
public class RelevantNodeDO {

    /**
     * 相邻节点的id
     */
    private String nodeId;

    /**
     * 与相邻节点的距离 单位cm
     */
    private Long distance;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
