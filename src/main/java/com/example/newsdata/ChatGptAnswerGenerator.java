package com.example.newsdata;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ChatGptAnswerGenerator {
    @Value("${open-ai.secret-key}")
    private String SECRET_KEY;
    private OpenAiService openAiService;

    @PostConstruct
    public void init() {
        openAiService = new OpenAiService(SECRET_KEY, Duration.ofSeconds(30));
    }

    public String generateAnswer(ChatCompletionRequest request) {
        ChatCompletionResult result = null;
        result = openAiService.createChatCompletion(request);
        ChatCompletionChoice targetChoice = result.getChoices().get(0);
        return targetChoice.getMessage().getContent();
    }
}
