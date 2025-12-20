package org.example;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.Producer.WikimediaChangeProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Main {

    private final WikimediaChangeProducer wikimediaChangeProducer;

    @PostConstruct
    public void init(){
        wikimediaChangeProducer.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}