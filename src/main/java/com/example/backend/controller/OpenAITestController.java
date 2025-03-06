package com.example.backend.controller;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class OpenAITestController {
    private final OpenAiService openAiService;

    public OpenAITestController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @GetMapping("/openai")
    public ResponseEntity<Map<String, Object>> testOpenAI() {
        Map<String, Object> response = new HashMap<>();
        try {
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo") // より応答の速いモデルを使用
                .messages(Arrays.asList(
                    new ChatMessage("user", "Hello, this is a test message. Please respond with 'OK'.")
                ))
                .maxTokens(10)
                .build();

            String result = openAiService.createChatCompletion(request)
                .getChoices().get(0).getMessage().getContent();

            response.put("success", true);
            response.put("message", "OpenAI API connection successful");
            response.put("response", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "OpenAI API connection failed");
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
} 