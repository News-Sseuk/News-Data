package com.example.newsdata;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
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

        try (Reader reader = new InputStreamReader(resource.getInputStream()); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            int cnt = 0;
            for (CSVRecord csvRecord : csvParser) {
                if(cnt < 500){
                    cnt++;
                    continue;
                }
                else if (cnt >= 1000) break;
                String line = String.join(";", csvRecord);
                /*System.out.println(line);
                System.out.println("\n");
                System.out.println("\n");
                System.out.println("\n");*/
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
                bw.write(";");
                bw.newLine();
            }
        }
    }
}
