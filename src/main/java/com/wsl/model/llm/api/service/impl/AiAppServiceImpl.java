package com.wsl.model.llm.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.wsl.model.llm.api.constant.enums.ModelTypeEnum;
import com.wsl.model.llm.api.dto.ChatRequestDTO;
import com.wsl.model.llm.api.dto.MessageDTO;
import com.wsl.model.llm.api.service.IAiAppService;
import com.wsl.model.llm.api.service.ModelService;
import com.wsl.model.llm.api.vo.ChatResponseVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI应用ServiceImpl
 *
 * @author wsl
 * @date 2024/02/19
 */
@Service("aiAppService")
public class AiAppServiceImpl implements IAiAppService {

    @Override
    public ChatResponseVO chatMessage(String modelType, ChatRequestDTO dto) throws Exception {
        this.checkMessages(dto.getMessages());
        // 根据枚举类ModelTypeEnum中的枚举值判断模型类型，并调用对应的模型实现类的方法
        ModelService modelService = getModelService(modelType);
        return modelService.chatMessage(dto);
    }

    /**
     * 检查消息参数是否符合规范
     *
     * @param messages 消息参数
     */
    private void checkMessages(List<MessageDTO> messages) {
        if (CollUtil.isNotEmpty(messages)) {
            // messages参数个数必须为奇数并且奇数个数的消息role必须为user，偶数个数的消息role必须为assistant
            if (messages.size() % 2 == 0) {
                throw new RuntimeException("messages参数个数必须为奇数");
            }
            for (int i = 0; i < messages.size(); i++) {
                if (i % 2 == 0) {
                    if (!"user".equals(messages.get(i).getRole())) {
                        throw new RuntimeException("messages奇数参数的role必须为user");
                    }
                } else {
                    if (!"assistant".equals(messages.get(i).getRole())) {
                        throw new RuntimeException("messages偶数参数的role必须为assistant");
                    }
                }
            }
        }
    }


    /**
     * 根据模型类型获取对应的模型服务
     *
     * @param modelType 模型类型
     * @return 模型服务
     */
    private ModelService getModelService(String modelType) {
        try {
            // 将模型类型字符串转换为枚举值
            ModelTypeEnum modelTypeEnum = ModelTypeEnum.valueOf(modelType);
            // 根据枚举值获取对应的实现类Bean的名称
            String beanName = modelTypeEnum.name();
            beanName = StrUtil.toCamelCase(beanName) + "Service";
            return SpringUtil.getBean(beanName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("模型类型错误");
        }
    }

}