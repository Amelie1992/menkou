package com.wzlue.sys.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 2018-05-08 List数据转化Tree数据类型 帮助类
 * step1 List<?> 的源数据类 extends TreeNode
 * step2 新增构造 重写父类构造
 * step3 把 子类 => 父类
 * 调用 buildByRecursive(list);
 *
 */
public class TreeTransformUtil {

    /**
     * 使用递归方法建树
     * @param treeNodes
     * @return
     */
    public static List<TreeNode> buildByRecursive(List<? extends TreeNode> treeNodes) {
        List<TreeNode> trees = new ArrayList<TreeNode>();

        for (TreeNode item : treeNodes) {
            if (item.getParentId()!= null && 0 == item.getParentId()) {
                trees.add(findChildren(item,treeNodes));
            }
        }

//        for(int i=0; i<trees.size();i++){
//            if (i==0){
//                continue;
//            }else {
//                if (trees.get(i).getId()< trees.get(i-1).getId()){
//                    TreeNode temp = trees.get(i);
//                    trees.set(i,trees.get(i-1));
//                    trees.set(i-1,temp);
//                }
//            }
//        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static TreeNode findChildren(TreeNode treeNode,List<? extends TreeNode> treeNodes) {
        for (TreeNode it : treeNodes) {
            if(it.getParentId()!= null && treeNode!=null && treeNode.getId().longValue()==it.getParentId().longValue()) {
                if (treeNode.getList() == null) {
                    treeNode.setList(new ArrayList<TreeNode>());
                }
                treeNode.getList().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
    public static List<Map<String,Object>> buildByRecursiveMap(List<? extends Map> treeNodes) {
        List<Map<String,Object>> trees = new ArrayList<>();
        for (Map<String,Object> map : treeNodes) {
            if (map.get("parentId")!= null && 0 == (Integer) map.get("parentId")) {
                trees.add(findChildrenMap(map,treeNodes));
            }
        }
//        for(int i=0; i<trees.size();i++){
//            if (i==0){
//                continue;
//            }else {
//                if (trees.get(i).getId()< trees.get(i-1).getId()){
//                    TreeNode temp = trees.get(i);
//                    trees.set(i,trees.get(i-1));
//                    trees.set(i-1,temp);
//                }
//            }
//        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static Map<String, Object> findChildrenMap(Map<String,Object> treeNode,List<? extends Map> treeNodes) {
        for (Map<String,Object> it : treeNodes) {
            if(it.get("parentId")!= null && treeNode!=null && treeNode.get("id").equals(it.get("parentId"))) {
                if(treeNode.get("parList") != null){
                    it.put("parList",treeNode.get("parList"));
                }
                List<Map<String,Object>> listMap = new ArrayList<>();
                if (treeNode.get("children") != null) {
                    listMap= (List<Map<String, Object>>) treeNode.get("children");
                }
                List<Integer> parList= (List<Integer>) treeNode.get("parList");
                if(parList ==null){
                    parList=new ArrayList<>(16);
                }
                parList.add((Integer) treeNode.get("id"));
                parList.add((Integer) it.get("id"));
                it.put("parList",parList);
                listMap.add(findChildrenMap(it,treeNodes));
                treeNode.put("children",listMap);
            }
        }
        return treeNode;
    }
}
