package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 有效载荷 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class SparkDeskPayloadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息", notes = "消息")
    private SparkDeskPayloadMessageDTO message;

}
