package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 智谱清言 请求 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class ChatGlmDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模型编码", notes = "模型编码", example = "glm-4")
    private String model;

    @ApiModelProperty(value = "聊天上下文信息", notes = "说明：\n" +
            "（1）messages成员不能为空，1个成员表示单轮对话，多个成员表示多轮对话；例如：\n" +
            "· 1个成员示例，\"messages\": [ {\"role\": \"user\",\"content\": \"你好\"}]\n" +
            "· 3个成员示例，\"messages\": [ {\"role\": \"user\",\"content\": \"你好\"},{\"role\":\"assistant\",\"content\":\"需要什么帮助\"},{\"role\":\"user\",\"content\":\"自我介绍下\"}]\n" +
            "（2）最后一个message为当前请求的信息，前面的message为历史对话信息\n" +
            "（3）成员数目必须为奇数，成员中message的role值说明如下：奇数位messsage的role值必须为user或function，偶数位message的role值为assistant，第一个message的role不能是function。例如：\n" +
            "示例中message中的role值分别为user、assistant、user、assistant、user；奇数位（红框）message中的role值为user，即第1、3、5个message中的role值为user；偶数位（蓝框）值为assistant，即第2、4个message中的role值为assistant",
            example = "[{\"role\":\"system\",\"content\":\"您好，我是智谱清言，我可以帮您查询天气，您可以输入：查询{{destination}}的天气，查询{{destination}}未来{{num_day}}天的天气\"},{\"role\":\"user\",\"content\":\"查询三亚未来5天的天气\"},{\"role\":\"assistant\",\"content\":\"正在查询三亚未来5天的天气\"},{\"role\":\"assistant\",\"content\":\"三亚未来5天的天气是晴天\"}]")
    @NotNull(message = "聊天上下文信息不能为空")
    private List<MessageDTO> messages;

}
