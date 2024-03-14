package com.wsl.model.llm.api.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wsl.model.llm.api.convert.ChatRequestConvert;
import com.wsl.model.llm.api.dto.ChatRequestDTO;
import com.wsl.model.llm.api.service.ModelService;
import com.wsl.model.llm.api.vo.ChatResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 智谱清言 大模型服务
 *
 * @author wsl
 * @link https://open.bigmodel.cn/dev/api#glm-4
 * @date 2024/2/19
 */
@Slf4j
@Service("ChatGlmService")
public class ChatGlmServiceImpl implements ModelService {

    private String apiKey = "?";

    @Override
    public ChatResponseVO chatMessage(ChatRequestDTO dto) throws Exception {
        JSONObject chatGlm = ChatRequestConvert.INSTANCE.convertChatGlm(dto);
        String url = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
        String requestBody = JSONUtil.toJsonStr(chatGlm);
        log.info("智谱清言请求参数 chatGlm request:{}", requestBody);

        HttpResponse response = HttpUtil.createPost(url).body(requestBody).header("Content-Type", "application/json").header("Authorization", "Bearer " + generateToken(apiKey, 3600)).execute();

        log.info("智谱清言返回结果 chatGlm response:{}", Optional.ofNullable(response).map(HttpResponse::body).orElse(""));

        ChatResponseVO vo = new ChatResponseVO();
        Optional<JSONObject> jsonObject = Optional.ofNullable(JSON.parseObject(response.body()));
        jsonObject.ifPresent(json -> {
            Optional<JSONArray> choices = Optional.ofNullable(json.getJSONArray("choices"));
            choices.ifPresent(choiceArray -> {
                if (!choiceArray.isEmpty()) {
                    Optional<JSONObject> firstChoiceMessage = Optional.ofNullable(choiceArray.getJSONObject(0).getJSONObject("message"));
                    firstChoiceMessage.ifPresent(message -> {
                        String content = message.getString("content");
                        if (content != null) {
                            vo.setResult(content);
                        } else {
                            throw new RuntimeException(response.body());
                        }
                    });
                }
            });
            throw new RuntimeException(response.body());
        });
        return vo;
    }

    /**
     * 生成token
     *
     * @param apikey     apikey
     * @param expSeconds 过期时间
     * @return token
     * @throws Exception 异常
     */
    public static String generateToken(String apikey, int expSeconds) throws Exception {
        String[] parts = apikey.split("\\.");
        if (parts.length != 2) {
            throw new Exception("Invalid apikey");
        }

        String id = parts[0];
        String secret = parts[1];

        Map<String, Object> payload = new HashMap<>(16);
        payload.put("api_key", id);
        payload.put("exp", new Date(System.currentTimeMillis() + expSeconds * 1000));
        payload.put("timestamp", new Date(System.currentTimeMillis()));

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withHeader(new HashMap<String, Object>(16) {{
            put("alg", "HS256");
            put("sign_type", "SIGN");
        }}).withPayload(payload).sign(algorithm);
    }

}
