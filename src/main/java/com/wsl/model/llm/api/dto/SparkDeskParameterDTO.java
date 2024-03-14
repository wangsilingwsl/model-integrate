package com.wsl.model.llm.api.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 参数 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class SparkDeskParameterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "聊天参数", notes = "聊天参数")
    private JSONObject chat;

}
