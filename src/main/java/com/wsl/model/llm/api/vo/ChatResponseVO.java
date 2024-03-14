package com.wsl.model.llm.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 聊天响应 VO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class ChatResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "结果", notes = "结果")
    private String result;

}
