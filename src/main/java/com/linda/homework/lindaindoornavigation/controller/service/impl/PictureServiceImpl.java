package com.linda.homework.lindaindoornavigation.controller.service.impl;

import com.linda.homework.lindaindoornavigation.controller.dto.SimilarNodeDTO;
import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.PictureService;
import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.util.Base64Util;
import com.linda.homework.lindaindoornavigation.util.ImageHelper;
import com.linda.homework.lindaindoornavigation.util.SimilarImageSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Component
public class PictureServiceImpl implements PictureService {

    private static final int PIC_COUNT = 8;

    @Autowired
    private NodeService nodeService;

    @Override
    public List<SimilarNodeDTO> rankSimilarPics(final byte[] picData) {
        ResponseDTO<List<NodeDO>> responseDTO = nodeService.getAllNodes();
        if(responseDTO.isFailure()){
            return new ArrayList<>();
        }
        final String hashCode;
        try {
            hashCode = SimilarImageSearch.produceFingerPrint(ImageHelper.readImage(picData));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        List<NodeDO> nodeDOS = responseDTO.getData();
        // 按照图片相似度排序的treeSet
        TreeSet<NodeDO>  treeSet = new TreeSet<>((o1, o2) -> {
            try {
                String hashCode1 = SimilarImageSearch.produceFingerPrint(ImageHelper.readImage(Base64Util.decode(Base64Util.deleteBase64Prefix(o1.getNodePic()))));
                String hashCode2 = SimilarImageSearch.produceFingerPrint(ImageHelper.readImage(Base64Util.decode(Base64Util.deleteBase64Prefix(o2.getNodePic()))));
                int diff1 = SimilarImageSearch.hammingDistance(hashCode, hashCode1);
                int diff2 = SimilarImageSearch.hammingDistance(hashCode, hashCode2);
                if (diff1 == diff2) {
                    return 0;
                }
                return diff1 > diff2 ? 1 : -1;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException();
            }
        });
        treeSet.addAll(nodeDOS);
        int i = 0;
        List<SimilarNodeDTO> result = new ArrayList<>(PIC_COUNT);
        while(!treeSet.isEmpty() && i < PIC_COUNT){
            NodeDO nodeDO = treeSet.pollFirst();
            SimilarNodeDTO similarNodeDTO = new SimilarNodeDTO();
            similarNodeDTO.setNodeId(nodeDO.getNodeId());
            similarNodeDTO.setNodeName(nodeDO.getNodeName());
            similarNodeDTO.setImgStr(nodeDO.getNodePic());
            result.add(similarNodeDTO);
            i++;
        }
        return result;
    }
}
