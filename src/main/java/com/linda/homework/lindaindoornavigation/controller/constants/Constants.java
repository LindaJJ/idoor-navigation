package com.linda.homework.lindaindoornavigation.controller.constants;

import com.linda.homework.lindaindoornavigation.model.lindo.RelevantNodeDO;

/**
 * 常量
 */
public class Constants {

    public enum Direction {
        /**
         * 左
         */
        LEFT,

        /**
         * 右
         */
        RIGHT,

        /**
         * 后
         */
        BACK,

        /**
         * 前
         */
        FRONT;

        /**
         * 判断nodeId在relevantNodeDO中是哪个方向
         * @param nodeId
         * @param relevantNodeDO
         * @return
         */
        public static Direction getDirection(String nodeId ,RelevantNodeDO relevantNodeDO){
            if(nodeId.equals(relevantNodeDO.getLeftNodeId())){
                return LEFT;
            }
            if(nodeId.equals(relevantNodeDO.getRightNodeId())){
                return RIGHT;
            }
            if(nodeId.equals(relevantNodeDO.getFrontNodeId())){
                return FRONT;
            }
            if(nodeId.equals(relevantNodeDO.getBackNodeId())){
                return BACK;
            }
            return null;
        }
    }
}
