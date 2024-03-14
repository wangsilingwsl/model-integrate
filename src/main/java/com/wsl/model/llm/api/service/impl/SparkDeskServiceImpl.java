package com.wsl.model.llm.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wsl.model.llm.api.convert.ChatRequestConvert;
import com.wsl.model.llm.api.dto.ChatRequestDTO;
import com.wsl.model.llm.api.dto.SparkDeskDTO;
import com.wsl.model.llm.api.dto.SparkDeskHeaderDTO;
import com.wsl.model.llm.api.service.ModelService;
import com.wsl.model.llm.api.vo.ChatResponseVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 讯飞星火 大模型服务
 *
 * @author wsl
 * @link https://www.xfyun.cn/doc/spark/Web.html
 * @date 2024/2/19
 */
@Service("SparkDeskService")
@Slf4j
public class SparkDeskServiceImpl implements ModelService {

    private String appId = "?";

    private String appSecret = "?";

    private String appKey = "?";

    public static final String HOST_URL = "https://spark-api.xf-yun.com/v3.5/chat";

    @Override
    public ChatResponseVO chatMessage(ChatRequestDTO dto) throws Exception {
        ChatResponseVO vo = new ChatResponseVO();
        SparkDeskDTO sparkDeskDTO = ChatRequestConvert.INSTANCE.convertSparkDesk(dto);
        sparkDeskDTO.setHeader(new SparkDeskHeaderDTO(appId));

        String authUrl = getAuthUrl(HOST_URL, appKey, appSecret).replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(authUrl).build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        StringBuilder sb = new StringBuilder();
        CompletableFuture<String> messageReceived = new CompletableFuture<>();
        String body = JSON.toJSONString(sparkDeskDTO);
        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                //发送消息
                log.info("讯飞星火请求参数 sparkDesk request:{}", body);
                webSocket.send(body);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                try {
                    JSONObject obj = JSON.parseObject(text);

                    // 使用Optional来避免空指针异常，并在内容不存在时抛出异常
                    Optional<String> contentOptional = Optional.ofNullable(obj)
                            .map(jsonObject -> jsonObject.getJSONObject("payload"))
                            .map(payload -> payload.getJSONObject("choices"))
                            .map(choices -> choices.getJSONArray("text"))
                            .map(jsonArray -> jsonArray.getJSONObject(0))
                            .map(jsonObject -> jsonObject.getString("content"));
                    String str = contentOptional.orElseThrow(() -> new RuntimeException(JSONObject.toJSONString(obj)));

                    sb.append(str);

                    // 检查header和status字段
                    Optional<Long> statusOptional = Optional.ofNullable(obj)
                            .map(jsonObject -> jsonObject.getJSONObject("header"))
                            .map(header -> header.getLong("status"));

                    // 如果status为2，则关闭WebSocket并完成CompletableFuture
                    if (statusOptional.isPresent() && statusOptional.get() == 2) {
                        webSocket.close(1000, "Closing WebSocket connection");
                        messageReceived.complete(text);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        messageReceived.get(60, TimeUnit.SECONDS);
        webSocket.close(1000, "Closing WebSocket connection");
        log.info("讯飞星火返回结果 sparkDesk response:{}", sb);
        vo.setResult(sb.toString());
        return vo;
    }

    /**
     * 鉴权方法
     *
     * @param hostUrl   服务地址
     * @param apiKey    apiKey
     * @param apiSecret apiSecret
     * @return 返回鉴权url
     * @throws Exception 异常
     */

    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).
                addQueryParameter("date", date).
                addQueryParameter("host", url.getHost()).
                build();
        return httpUrl.toString();
    }
}
