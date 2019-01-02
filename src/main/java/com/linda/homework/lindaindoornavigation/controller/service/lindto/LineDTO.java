package com.linda.homework.lindaindoornavigation.controller.service.lindto;

import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;

import java.util.LinkedList;
import java.util.List;

/**
 * 路线传输对象 本质上是一个双向链表
 */
public class LineDTO {
    private List<NodeDO> line = new LinkedList<>();

    public List<NodeDO> getLine() {
        return line;
    }

    public void setLine(List<NodeDO> line) {
        this.line = line;
    }
}
