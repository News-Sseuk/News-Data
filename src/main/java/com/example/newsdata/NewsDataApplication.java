package com.example.newsdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsDataApplication implements CommandLineRunner {

    @Autowired
    private CsvProcessingService csvProcessingService;

    public static void main(String[] args) {
        SpringApplication.run(NewsDataApplication.class, args);
    }

    @Override
    public void run(String... args) {
        csvProcessingService.processCsv();
    }
}
