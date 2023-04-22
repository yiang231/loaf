package com.xjdl.study.exception.globalException;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常响应
 */
@Data
@Slf4j
@NoArgsConstructor
public class ResultResponse {
    private String code;
    private String msg;
    /**
     * 响应结果
     */
    private Object result;

    public ResultResponse(BaseErrorInfoInterface errorInfoInterface) {
        this.msg = errorInfoInterface.getResultMsg();
        this.code = errorInfoInterface.getResultCode();
    }

    public static ResultResponse success(Object result) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(ExceptionEnum.SUCCESS.getResultCode());
        resultResponse.setMsg(ExceptionEnum.SUCCESS.getResultMsg());
        resultResponse.setResult(result);
        return resultResponse;
    }

    public static ResultResponse success() {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(ExceptionEnum.SUCCESS.getResultCode());
        resultResponse.setMsg(ExceptionEnum.SUCCESS.getResultMsg());
        resultResponse.setResult(null);
        return resultResponse;
    }

    public static ResultResponse error(BaseErrorInfoInterface errorInfoInterface) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(errorInfoInterface.getResultCode());
        resultResponse.setMsg(errorInfoInterface.getResultMsg());
        resultResponse.setResult(null);
        return resultResponse;
    }

    public static ResultResponse error(String code, String msg) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(code);
        resultResponse.setMsg(msg);
        resultResponse.setResult(null);
        return resultResponse;
    }

    public static ResultResponse error(String msg) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode("-1");
        resultResponse.setMsg(msg);
        resultResponse.setResult(null);
        return resultResponse;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
