package com.wsl.model.llm.api.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 聊天请求 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class ChatRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "聊天上下文信息", notes = "（1）最后一个message为当前请求的信息，前面的message为历史对话信息\n" +
            "（2）成员数目必须为奇数\n" +
            "（3）示例中message中的role值分别为user、assistant；奇数位message中的role值为user；偶数位值为assistant",
            example = "[{\"role\":\"user\",\"content\":\"你好\"},{\"role\":\"assistant\",\"content\":\"需要什么帮助\"},{\"role\":\"user\",\"content\":\"自我介绍下\"}]")
    @NotNull(message = "聊天上下文信息不能为空")
    private List<MessageDTO> messages;

    @ApiModelProperty(value = "模型人设", notes = "主要用于人设设定,例如，你是xxx公司制作的AI助手，最大20000字符", example = "你是一名天气助手，需要提供天气查询服务")
    private String system;

    @ApiModelProperty(value = "请求参数", notes = "请求参数", example = "{\"key\":\"value\"}")
    private JSONObject params;
}
