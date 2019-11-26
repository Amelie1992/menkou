package com.wzlue.sys.util;

import java.util.List;

public class TreeNode {

    /**
     * 节点key
     */
    private Long id;

    /**
     * 父节点key
     */
    private Long parentId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 父节点名称
     */
    private String parentName;

    private Object node;
    /**
     * 子节点数据
     */
    private List<TreeNode> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getList() {
        return list;
    }

    public void setList(List<TreeNode> list) {
        this.list = list;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Object getNode() {
        return node;
    }

    public void setNode(Object node) {
        this.node = node;
    }


    public TreeNode(){

    }

    public TreeNode(Long id, String name, Long parentId, String parentName){
        this.setId(id);
        this.setName(name);
        this.setParentId(parentId);
        this.setParentName(parentName);
    }

    public TreeNode(Long id, String name, Long parentId, String parentName, Object node){
        this.setId(id);
        this.setName(name);
        this.setParentId(parentId);
        this.setParentName(parentName);
        this.setNode(node);
    }

    public TreeNode(Long id, String name, TreeNode parent, Object node){
        this.setId(id);
        this.setName(name);
        this.setParentId(parent.getId());
        this.setParentName(parent.getParentName());
        this.setNode(node);
    }

    public TreeNode(Long id, String name, TreeNode parent){
        this.setId(id);
        this.setName(name);
        this.setParentId(parent.getId());
        this.setParentName(parent.getParentName());
    }
}
