package org.example.Config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Producer.WikimediaChangeProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
