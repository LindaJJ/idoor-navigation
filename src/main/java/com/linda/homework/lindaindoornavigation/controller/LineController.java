package com.linda.homework.lindaindoornavigation.controller;

import com.linda.homework.lindaindoornavigation.controller.dto.NodeDTO;
import com.linda.homework.lindaindoornavigation.controller.dto.SimilarNodeDTO;
import com.linda.homework.lindaindoornavigation.controller.service.LineService;
import com.linda.homework.lindaindoornavigation.controller.service.PictureService;
import com.linda.homework.lindaindoornavigation.controller.util.TransUtils;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.util.Base64Util;
import com.linda.homework.lindaindoornavigation.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @PostMapping("/getSimilarPic")
    public List<SimilarNodeDTO> rankSimilarPics(@RequestParam("imgStr") String imgStr){
        List<SimilarNodeDTO> similarNodeDTOS = new ArrayList<>();
        try {
            similarNodeDTOS = pictureService.rankSimilarPics(Base64Util.decode(Base64Util.deleteJPEGBase64Prefix(imgStr)));
        } catch (Throwable e) {
            logger.error("LineController#rankSimilarPics", e);
        }
        return similarNodeDTOS;
    }

    @GetMapping("/getShortestPath")
    public List<NodeDTO> getShortestPath(@RequestParam("startName") String startName, @RequestParam("endName") String endName){

        try {
            String startNodeId = IDGenerator.generateNodeId(java.net.URLDecoder.decode(startName , "UTF-8"));
            String endNodeId = IDGenerator.generateNodeId(java.net.URLDecoder.decode(endName , "UTF-8"));
            return lineService.getShortestPath(startNodeId, endNodeId);
        } catch (Throwable e) {
            logger.error("LineController#getShortestPath", e);
        }
        return new ArrayList<>();
    }

}
