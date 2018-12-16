package com.linda.homework.lindaindoornavigation.model.lindo;

/**
 * 节点数据对象
 */
public class NodeDO {

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
    private String relevantNode;

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

    public String getRelevantNode() {
        return relevantNode;
    }

    public void setRelevantNode(String relevantNode) {
        this.relevantNode = relevantNode;
    }
}
