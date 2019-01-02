package com.linda.homework.lindaindoornavigation.controller;

import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 节点Controller
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/node")
public class NodeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Resource
    private NodeService nodeService;

    @RequestMapping("addNode")
    public ResponseDTO<Boolean> addNode(NodeDO nodeDO){
        return nodeService.addNode(nodeDO);
    }

    @RequestMapping("deleteNode")
    public ResponseDTO<Boolean> delete(String nodeId){
        return nodeService.deleteNode(nodeId);
    }

    @RequestMapping("getAllNodes")
    public ResponseDTO<List<NodeDO>> getAllNodes(){
        return nodeService.getAllNodes();
    }
}
