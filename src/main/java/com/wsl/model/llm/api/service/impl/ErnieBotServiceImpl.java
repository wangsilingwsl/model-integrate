package com.wsl.model.llm.api.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wsl.model.llm.api.convert.ChatRequestConvert;
import com.wsl.model.llm.api.dto.ChatRequestDTO;
import com.wsl.model.llm.api.service.ModelService;
import com.wsl.model.llm.api.vo.ChatResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文心一言 大模型服务
 *
 * @author wsl
 * @link https://console.bce.baidu.com/tools/?_=1708497709522&u=qfdc#/api?product=AI&project=%E5%8D%83%E5%B8%86%E5%A4%A7%E6%A8%A1%E5%9E%8B%E5%B9%B3%E5%8F%B0&parent=ERNIE-Bot&api=rpc%2F2.0%2Fai_custom%2Fv1%2Fwenxinworkshop%2Fchat%2Fcompletions&method=post
 * @date 2024/2/19
 */
@Service("ErnieBotService")
@Slf4j
public class ErnieBotServiceImpl implements ModelService {

    private String appSecret = "?";

    private String apiKey = "?";

    private static final String TOKEN_URL_TEMPLATE = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s";

    private static final String CHAT_URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token=%s";


    @Override
    public ChatResponseVO chatMessage(ChatRequestDTO dto) {
        JSONObject ernieBot = ChatRequestConvert.INSTANCE.convertErnieBot(dto);
        String requestBody = JSONUtil.toJsonStr(ernieBot);
        log.info("文心一言请求参数 ernieBot request:{}", requestBody);

        HttpResponse response = HttpUtil.createPost(String.format(CHAT_URL, getAccessToken(apiKey, appSecret)))
                .body(requestBody)
                .header("Content-Type", "application/json")
                .execute();

        if (response == null) {
            throw new RuntimeException("HTTP response is null");
        }

        log.info("文心一言返回结果 ernieBot response:{}", response.body());
        if (response.body() == null || response.body().trim().isEmpty()) {
            throw new RuntimeException("HTTP response body is null or empty");
        }
        JSONObject jsonObject = JSON.parseObject(response.body());
        if (!jsonObject.containsKey("result")) {
            throw new RuntimeException(JSONObject.toJSONString(jsonObject));
        }
        ChatResponseVO vo = new ChatResponseVO();
        vo.setResult(jsonObject.getString("result"));
        return vo;
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @param appId     应用ID
     * @param appSecret 应用密钥
     * @return token
     */
    public String getAccessToken(String appId, String appSecret) {
        String url = String.format(TOKEN_URL_TEMPLATE, apiKey, appSecret);

        try (HttpResponse response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .execute()) {
            JSONObject jsonObject = JSON.parseObject(response.body());
            String accessToken = jsonObject.getString("access_token");
            return accessToken;
        }
    }

}
