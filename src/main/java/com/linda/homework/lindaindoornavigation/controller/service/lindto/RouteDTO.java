package com.linda.homework.lindaindoornavigation.controller.service.lindto;

import java.util.Objects;

/**
 * 路由传输对象
 */
public class RouteDTO {
    /**
     * 当前路由下一个节点
     */
    private String nextNode;

    /**
     * 到下一个节点的方式
     */
    private String routeType;

    /**
     * 到下一个节点的距离
     */
    private Long distance;

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteDTO routeDTO = (RouteDTO) o;
        return Objects.equals(nextNode, routeDTO.nextNode) &&
                Objects.equals(routeType, routeDTO.routeType) &&
                Objects.equals(distance, routeDTO.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextNode, routeType, distance);
    }
}
