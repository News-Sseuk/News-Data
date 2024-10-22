package com.example.newsdata;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGptPromptGenerator {
    private final String DEFAULT_MODEL = "gpt-4o";
    private final double DEFAULT_TEMPERATURE = 0.1;

    public ChatCompletionRequest generatePrompt(String content) {
        List<ChatMessage> promptMessage = createPromptMessage(content);

        return ChatCompletionRequest.builder()
                .model(DEFAULT_MODEL)
                .messages(promptMessage)
                .temperature(DEFAULT_TEMPERATURE)
                .build();
    }

    public List<ChatMessage> createPromptMessage(String content) {
        List<ChatMessage> systemPrompts;

        final String SYSTEM = ChatMessageRole.SYSTEM.value();
        systemPrompts = List.of(
                new ChatMessage(SYSTEM, "다음 본문의 핵심을 10자 내외로 알려줘" + content)
        );

        // 반환
        List<ChatMessage> result = new ArrayList<>();
        result.addAll(systemPrompts);
        return result;
    }
}
