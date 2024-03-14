package com.wsl.model.llm.api.service;

import com.wsl.model.llm.api.dto.ChatRequestDTO;
import com.wsl.model.llm.api.vo.ChatResponseVO;

/**
 * AI应用Service
 *
 * @author wsl
 * @date 2024/02/19
 */
public interface IAiAppService {

    /**
     * 向大模型发起对话请求-根据模型编码、用户ID
     *
     * @param modelType 模型类型
     * @param dto       消息参数
     * @return ChatResponseVO
     * @throws Exception 异常
     */
    ChatResponseVO chatMessage(String modelType, ChatRequestDTO dto) throws Exception;

}