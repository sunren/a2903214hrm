package com.hr.profile.domain.base;

public class TreeNode {
    private String nodeId;
    private String nodeName;
    private String nodeParentId;
    private int nodeType;
    private int nodeStatus;
    private int isEmpty = 0;
    private int isParent;
    private String empId;

    public int getIsEmpty() {
        return this.isEmpty;
    }

    public void setIsEmpty(int isEmpty) {
        this.isEmpty = isEmpty;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public TreeNode() {
    }

    public TreeNode(String nodeId, String nodeName, String nodeParentId, int nodeType) {
        setNodeId(nodeId);
        setNodeName(nodeName);
        setNodeParentId(nodeParentId);
        setNodeType(nodeType);
    }

    public TreeNode(String nodeId, String nodeName, String nodeParentId, int nodeType,
            int nodeStatus) {
        setNodeId(nodeId);
        setNodeName(nodeName);
        setNodeParentId(nodeParentId);
        setNodeType(nodeType);
        setNodeStatus(nodeStatus);
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeParentId() {
        return this.nodeParentId;
    }

    public void setNodeParentId(String nodeParentId) {
        this.nodeParentId = nodeParentId;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getNodeType() {
        return this.nodeType;
    }

    public int getIsParent() {
        return this.isParent;
    }

    public void setIsParent(int isParent) {
        this.isParent = isParent;
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getNodeStatus() {
        return this.nodeStatus;
    }

    public void setNodeStatus(int nodeStatus) {
        this.nodeStatus = nodeStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.domain.base.TreeNode JD-Core Version: 0.5.4
 */