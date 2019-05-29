package com.linda.homework.lindaindoornavigation.controller.dto;

/**
 * 形似节点传输对象
 */
public class SimilarNodeDTO {
    /**
     * nodeId
     */
    private String nodeId;

    /**
     * nodeName
     */
    private String nodeName;

    /**
     * base64
     */
    private String imgStr;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }
}
