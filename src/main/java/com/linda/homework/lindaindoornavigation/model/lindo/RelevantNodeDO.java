package com.linda.homework.lindaindoornavigation.model.lindo;

/**
 * 相关节点信息数据对象
 */
public class RelevantNodeDO {

   private Integer angle;

   private String nodeId;

   private Long distance;

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

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
