package com.timezerg.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xnx on 2018/6/1.
 */
public class NodeDetailApiBean implements Serializable,Comparable<NodeDetailApiBean>{

    Date cdate;
    Integer AD;
    List<Node> nodes;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Integer getAD() {
        return AD;
    }

    public void setAD(Integer AD) {
        this.AD = AD;
    }

    /**
     *  增加单个结点
     */
    public void addNode(Node node){
        if (nodes == null)
            nodes = new ArrayList<>();
        nodes.add(node);
    }

    public void addNode(List<Node> nodeList){
        if (nodes == null)
            nodes = new ArrayList<>();
        nodes.addAll(nodeList);
    }


    public void initBlackBean(){
        setNodes(null);
    }




    @Override
    public int compareTo(NodeDetailApiBean bean) {
        if (bean.getAD() == getAD()){
            if (bean.getAD() == Node.AD_VALUE){
                if (getCdate().getTime() < bean.getCdate().getTime()){
                    return -1;
                }else if (getCdate().getTime() == bean.getCdate().getTime()){
                    return 0;
                }else {
                    return 1;
                }
            }else {
                if (getCdate().getTime() > bean.getCdate().getTime()){
                    return -1;
                }else if (getCdate().getTime() == bean.getCdate().getTime()){
                    return 0;
                }else {
                    return 1;
                }
            }
        }else {
            if (getAD() < bean.getAD()){
                return -1;
            }else {
                return 1;
            }
        }
    }


    @Override
    public String toString() {
        if (getNodes() == null)
            return "[AD: " + AD + " ] " + cdate + " - 无结点。";

        else {
            String str = "[AD: " + AD + " ] " + cdate + " - ";

            for (Node node : getNodes()){
                str += node.getTitle();
                str += " / ";
            }
            return str;
        }

    }
}
