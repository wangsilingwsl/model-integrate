package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 讯飞星火 Header DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SparkDeskHeaderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用appid", notes = "从开放平台控制台创建的应用中获取")
    private String app_id;

}
