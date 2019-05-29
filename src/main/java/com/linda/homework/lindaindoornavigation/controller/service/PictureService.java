package com.linda.homework.lindaindoornavigation.controller.service;

import com.linda.homework.lindaindoornavigation.controller.dto.SimilarNodeDTO;

import java.util.List;

/**
 * 对比图片服务
 */
public interface PictureService {

    /**
     * 获取与输入图片最相似的8张图片对应的节点
     * @param picData 图片对应二进制字节流
     * @return
     */
    List<SimilarNodeDTO> rankSimilarPics(byte[] picData);
}
