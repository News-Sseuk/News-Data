package com.example.newsdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvProcessingService {

    private final ManagingFile managingFile;

    public void processCsv() {
        try {
            List<String> contents = managingFile.readCsvFile();
            System.out.println("읽기 완");
            List<String> answers = managingFile.processContents(contents);
            System.out.println("처리 완");
            managingFile.writeAnswers(answers);
            System.out.println("끗!!!!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
