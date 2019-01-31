package com.linda.homework.lindaindoornavigation.controller.service.impl;

import com.linda.homework.lindaindoornavigation.controller.service.NodeService;
import com.linda.homework.lindaindoornavigation.controller.service.PictureService;
import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;
import com.linda.homework.lindaindoornavigation.model.lindo.NodeDO;
import com.linda.homework.lindaindoornavigation.util.ImageHelper;
import com.linda.homework.lindaindoornavigation.util.SimilarImageSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Component
public class PictureServiceImpl implements PictureService {

    private static final int PIC_COUNT = 8;

    @Autowired
    private NodeService nodeService;

    @Override
    public List<NodeDO> rankSamilarPics(final byte[] picData) {
        ResponseDTO<List<NodeDO>> responseDTO = nodeService.getAllNodes();
        if(responseDTO.isFailure()){
            return new ArrayList<>();
        }
        final String hashCode = SimilarImageSearch.produceFingerPrint(ImageHelper.readImage(picData));
        List<NodeDO> nodeDOS = responseDTO.getData();
        // 按照图片相似度排序的treeSet
        TreeSet<NodeDO>  treeSet = new TreeSet<>((o1, o2) -> {
            String hashCode1 = SimilarImageSearch.produceFingerPrint(ImageHelper.readImage(o1.getPic()));
            String hashCode2 = SimilarImageSearch.produceFingerPrint(ImageHelper.readImage(o2.getPic()));
            int diff1 = SimilarImageSearch.hammingDistance(hashCode, hashCode1);
            int diff2 = SimilarImageSearch.hammingDistance(hashCode, hashCode2);
            if(diff1 == diff2){
                return 0;
            }
            return diff1 < diff2 ? 1 : -1;
        });
        treeSet.addAll(nodeDOS);
        int i = 0;
        List<NodeDO> result = new ArrayList<>(PIC_COUNT);
        while(!treeSet.isEmpty() && i < PIC_COUNT){
            NodeDO nodeDO = treeSet.pollFirst();
            result.add(nodeDO);
        }
        return result;
    }
}
