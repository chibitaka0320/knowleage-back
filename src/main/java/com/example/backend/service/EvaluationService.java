package com.example.backend.service;

import com.example.backend.dto.EvaluationRequest;
import com.example.backend.dto.EvaluationResponse;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;

@Service
public class EvaluationService {
    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.max-tokens}")
    private Integer maxTokens;

    @Value("${openai.temperature}")
    private Double temperature;

    public EvaluationService(OpenAiService openAiService, ObjectMapper objectMapper) {
        this.openAiService = openAiService;
        this.objectMapper = objectMapper;
    }

    public EvaluationResponse evaluateAnswer(EvaluationRequest request) {
        String prompt = createPrompt(request);
        
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
            .model(model)
            .messages(Arrays.asList(
                new ChatMessage("system", "あなたは技術面接官です。回答を評価し、JSONフォーマットで結果を返してください。"),
                new ChatMessage("user", prompt)
            ))
            .maxTokens(maxTokens)
            .temperature(temperature)
            .build();

        try {
            String response = openAiService.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
            
            return objectMapper.readValue(response, EvaluationResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("回答の評価中にエラーが発生しました", e);
        }
    }

    private String createPrompt(EvaluationRequest request) {
        return String.format("""
            以下の技術面接の質問について、応募者の回答を評価してください。
            模範回答はあくまで例なので、全体的に応募者の回答を評価してください。

            質問：
            %s

            模範解答：
            %s

            応募者の回答：
            %s

            以下のJSON形式で評価結果を返してください：
            {
                "accuracy": 0-100の数値で正確性を評価,
                "feedback": "全体的なフィードバック",
                "goodPoints": ["良い点1", "良い点2", ...],
                "improvementPoints": ["改善点1", "改善点2", ...],
                "detailedAdvice": "具体的なアドバイス"
            }
            """,
            request.getQuestionContent(),
            request.getExampleAnswer(),
            request.getUserAnswer()
        );
    }
} 