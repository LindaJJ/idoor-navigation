package com.linda.homework.lindaindoornavigation.controller;

import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 线路控制器 将线路解析成节点关系
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/line")
public class LineController extends BaseController {

    @Resource
    private NodeService nodeService;

}
