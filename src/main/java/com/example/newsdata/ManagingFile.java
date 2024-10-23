package com.example.newsdata;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ManagingFile {

    private final ResourceLoader resourceLoader;
    private final ChatGptPromptGenerator promptGenerator;
    private final ChatGptAnswerGenerator answerGenerator;

    //final_article 읽기
    public List<String> readCsvFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:news.csv");
        List<String> contents = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null && cnt < 100) {
                contents.add(line);
                cnt ++;
            }
        }
        return contents;
    }

    //데이터 처리
    public List<String> processContents(List<String> contents) {
        List<String> answers = new ArrayList<>();
        for(String content : contents) {
            String script = "";
            try {
                ChatCompletionRequest prompt = promptGenerator.generatePrompt(content);
                script = answerGenerator.generateAnswer(prompt);
            } catch (Exception e) {
                System.out.println("Exception class : " + e.getClass());
                System.out.println("Exception cause : " + e.getCause());
                throw e;
            }
            answers.add(script);
        }
        return answers;
    }

    //파일 쓰기
    public void writeAnswers(List<String> answers) throws IOException{
        Path path = Paths.get("src/main/resources/answerFromGPT.csv");

        try(BufferedWriter bw = Files.newBufferedWriter(path)) {
            for(String answer : answers) {
                bw.write(answer);
                bw.newLine();
            }
        }
    }
}
