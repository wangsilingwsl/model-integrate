package com.wsl.model.llm.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 讯飞星火 有效载荷 消息 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class SparkDeskPayloadMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "聊天上下文信息不能为空")
    private List<MessageDTO> text;

}
