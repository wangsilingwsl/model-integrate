package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 请求 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class SparkDeskDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "头部", notes = "头部")
    private SparkDeskHeaderDTO header;

    @ApiModelProperty(value = "参数", notes = "参数")
    private SparkDeskParameterDTO parameter;

    @ApiModelProperty(value = "有效载荷", notes = "有效载荷")
    private SparkDeskPayloadDTO payload;

}
