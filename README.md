##室内导航Spring boot
###版本号
***
0.0.1

###已实现接口
####添加导航节点
*** 
```/node/addNode + 参数```

###技术思路
***
本项目使用mysql作为数据库，mybatis+spring boot为项目所依赖框架。<br/>
数据库使用节点信息表存所有节点的信息，每一行记录表示当前节点的id、name、相关节点。<br/>
这样所有节点构成一个图，使用弗洛伊德算法获取当前图的多源最短路径，在内存中保存。
####数据模型设计
#####建表语句
***
```
DROP TABLE IF EXISTS `node_info`;
CREATE TABLE `node_info` (
  `node_id` varchar(255) NOT NULL COMMENT '节点id',
  `node_name` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `relevant_node` json DEFAULT NULL COMMENT '该节点相邻的节点及其距离',
  PRIMARY KEY (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

```
####表详细信息
***
node_info表表示节点信息，node_id为当前节点的唯一标识，node_name为当前节点的名称，relevant_node为当前节点相邻节点。