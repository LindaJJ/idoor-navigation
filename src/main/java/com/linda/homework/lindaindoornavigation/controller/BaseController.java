package com.linda.homework.lindaindoornavigation.controller;

import com.linda.homework.lindaindoornavigation.controller.service.lindto.ResponseDTO;

/**
 * 基础Controller
 */
public class BaseController {

    protected static final String SYSTEM_ERROR = "SYSTEM ERROR";

    /**
     * 构建错误返回结果
     * @param errorMessage
     * @return
     */
    protected ResponseDTO<Boolean> buildErrorResult(String errorMessage){
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(false);
        responseDTO.setErrorMessage(errorMessage);
        return responseDTO;
    }

    /**
     * 构建系统错误返回结果
     * @return
     */
    protected ResponseDTO<Boolean> buildSystemErrorResult(){
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(false);
        responseDTO.setErrorMessage(SYSTEM_ERROR);
        return responseDTO;
    }
}
