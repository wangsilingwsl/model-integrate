package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色", notes = "说明: user-用户, assistant-助手", example = "user")
    private String role;

    @ApiModelProperty(value = "消息内容", notes = "说明: 消息内容", example = "你好")
    private String content;

}
