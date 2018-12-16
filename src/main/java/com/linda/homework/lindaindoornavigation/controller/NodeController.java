package com.linda.homework.lindaindoornavigation.controller;

import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.model.mapper.NodeMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 节点Controller
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/node")
public class NodeController extends BaseController {

    @Resource
    private NodeMapper nodeMapper;

    @RequestMapping("addNode")
    public ResponseDTO<Boolean> addNode(NodeDO nodeDO){
        try {
            nodeMapper.addNode(nodeDO);
        }
        catch(Throwable throwable){
            // todo 暂时输出 后面改成日志
            System.out.println(throwable);
            return buildSystemErrorResult();
        }
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(true);
        return responseDTO;
    }
}
