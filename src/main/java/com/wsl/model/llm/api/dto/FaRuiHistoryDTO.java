package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通义法睿 会话的历史消息 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@ApiModel(description = "通义法睿 会话的历史消息 DTO")
@Data
public class FaRuiHistoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户输入的问题")
    private String user;

    @ApiModelProperty(value = "机器人的回答")
    private String bot;

}
