package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通义法睿 输入 请求 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
@ApiModel(description = "通义法睿 输入 请求 DTO")
public class FaRuiInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前提问的问题")
    private String prompt;

    @ApiModelProperty(value = "会话的历史消息")
    private List<FaRuiHistoryDTO> history;

}
