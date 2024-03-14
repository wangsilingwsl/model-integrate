package com.wsl.model.llm.api.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通义千问 请求 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class QianWenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模型名", notes = "模型名", example = "qwen-turbo")
    private String model;

    @ApiModelProperty(value = "输入", notes = "输入")
    private QianWenInputDTO input;

    @ApiModelProperty(value = "参数", notes = "参数")
    private JSONObject parameters;

}
