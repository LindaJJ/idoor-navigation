package com.linda.homework.lindaindoornavigation.controller.dto;

/**
 * 新建节点时的DTO
 */
public class NewNodeDTO {

    /**
     * base64 of the image
     */
    private String imgStr;

    /**
     * the name of this node
     */
    private String nodeName;

    private String nextNodeName;

    /**
     * distance between this node and the next node
     */
    private Integer nextNodeDistance;

    /**
     * angle between this node and the next node
     */
    private Integer nextNodeAngle;

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public Integer getNextNodeDistance() {
        return nextNodeDistance;
    }

    public void setNextNodeDistance(Integer nextNodeDistance) {
        this.nextNodeDistance = nextNodeDistance;
    }

    public Integer getNextNodeAngle() {
        return nextNodeAngle;
    }

    public void setNextNodeAngle(Integer nextNodeAngle) {
        this.nextNodeAngle = nextNodeAngle;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNextNodeName() {
        return nextNodeName;
    }

    public void setNextNodeName(String nextNodeName) {
        this.nextNodeName = nextNodeName;
    }
}
