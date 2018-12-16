package com.linda.homework.lindaindoornavigation.controller.service.lindto;

/**
 * Controller返回DTO
 */
public class ResponseDTO<T> {
    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 当请求失败时的错误信息
     */
    private String errorMessage;

    /**
     * 返回的具体数据
     */
    private T data;

    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess(){
        return success == null ? false : success;
    }

    /**
     * 请求是否失败
     * @return
     */
    public boolean isFailure(){
        return !isSuccess();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
