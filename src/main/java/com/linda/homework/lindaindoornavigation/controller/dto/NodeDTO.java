package com.linda.homework.lindaindoornavigation.controller.dto;

import com.linda.homework.lindaindoornavigation.model.lindo.RelevantNodeDO;

import java.util.List;

/**
 * 节点数据对象
 */
public class NodeDTO {

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 相关节点信息
     */
    private List<RelevantNodeDO> relevantNode;

    /**
     * 图片对应的base64
     */
    private String pic;

    /**
     * 感知哈希码
     */
    private String pHashCode;


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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getpHashCode() {
        return pHashCode;
    }

    public void setpHashCode(String pHashCode) {
        this.pHashCode = pHashCode;
    }

    public List<RelevantNodeDO> getRelevantNode() {
        return relevantNode;
    }

    public void setRelevantNode(List<RelevantNodeDO> relevantNode) {
        this.relevantNode = relevantNode;
    }
}
