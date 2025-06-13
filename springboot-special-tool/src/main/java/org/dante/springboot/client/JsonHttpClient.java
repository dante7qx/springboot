package org.dante.springboot.client;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class JsonHttpClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    /** 请求超时时间（秒）*/
    private static final int TIMEOUT_SECONDS = 10;

    /**
     * 发送 GET 请求
     *
     * @param url 请求地址
     * @param responseType 响应类型
     * @return 响应结果
     * @throws IOException IO 异常
     * @throws InterruptedException 线程中断异常
     */
     public <T> T get(String url, Class<T> responseType) throws IOException, InterruptedException {
         if(StrUtil.isEmpty(url)) {
             throw new IllegalArgumentException("URL 不能为空");
         }
         HttpRequest request = HttpRequest.newBuilder()
                 .uri(URI.create(url))
                 .GET()
                 .header("Accept", "application/json")
                 .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                 .build();
         HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
         if (response.statusCode() == 200) {
             try {
                 return objectMapper.readValue(response.body(), responseType);
             } catch (JsonProcessingException e) {
                 throw new RuntimeException("JSON 反序列化失败: " + e.getMessage(), e);
             }
         } else {
             throw new RuntimeException("HTTP 请求失败: " + response.statusCode());
         }
    }

    /**
     * 发送 POST 请求
     *
     * @param url 请求地址
     * @param body 请求体（将自动序列化为 JSON）
     * @param responseType 返回类型
     * @return 响应结果
     * @throws IOException IO 异常
     * @throws InterruptedException 线程中断异常
     */
    public <T> T post(String url, Object body, Class<T> responseType) throws IOException, InterruptedException {
        if (StrUtil.isEmpty(url)) {
            throw new IllegalArgumentException("URL 不能为空");
        }
        if (body == null) {
            throw new IllegalArgumentException("请求体不能为空");
        }

        // 构建请求体 JSON 字符串
        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 序列化失败: " + e.getMessage(), e);
        }

        // 构建 HTTP 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            try {
                return objectMapper.readValue(response.body(), responseType);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON 反序列化失败: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("HTTP 请求失败: " + response.statusCode());
        }
    }
}
