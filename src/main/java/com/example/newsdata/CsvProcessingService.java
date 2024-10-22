package com.example.newsdata;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
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
            List<String> answers = managingFile.processContents(contents);
            managingFile.writeAnswers(answers);
            System.out.println("끗!!!!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
