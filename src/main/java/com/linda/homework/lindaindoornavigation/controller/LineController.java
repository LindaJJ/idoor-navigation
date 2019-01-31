package com.linda.homework.lindaindoornavigation.controller;

import com.linda.homework.lindaindoornavigation.controller.service.LineService;
import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.PictureService;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 线路控制器 将线路解析成节点关系
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/line")
public class LineController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LineController.class);

    @Resource
    private LineService lineService;

    @Resource
    private PictureService pictureService;

    @RequestMapping("/getSimilarPic")
    public List<NodeDO> rankSimilarPics(MultipartFile file){
        List<NodeDO> nodeDOS = new ArrayList<>();
        try {
            nodeDOS = pictureService.rankSamilarPics(file.getBytes());
        } catch (Throwable e) {
            logger.error("LineController#rankSimilarPics", e);
        }
        return nodeDOS;
    }

    @RequestMapping("/getShortestPath")
    public List<NodeDO> getShortestPath(String startNodeId, String endNodeId){
        List<NodeDO> nodeDOS = new ArrayList<>();
        try {
            nodeDOS = lineService.getShortestPath(startNodeId, endNodeId);
        } catch (Throwable e) {
            logger.error("LineController#getShortestPath", e);
        }
        return nodeDOS;
    }

}
