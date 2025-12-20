package org.example.Producer;


import ch.qos.logback.core.net.server.Client;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.EventHandler;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WikimediaChangeProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "wikimedia.raw";


    public void start(){
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void onOpen() throws Exception {
                System.out.println("Starting Wikimedia Change Producer");
            }

            @Override
            public void onClosed() throws Exception {
                System.out.println("Finished Wikimedia Change Producer");
            }

            @Override
            public void onMessage(String event, MessageEvent messageEvent){
                kafkaTemplate.send(TOPIC,messageEvent.getData().toString());
            }

            @Override
            public void onComment(String s) throws Exception {

            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error in stream: " + t.getMessage());
            }
        };

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource eventSource =
                new EventSource.Builder(eventHandler, URI.create(url))
                        .headers(Headers.of("User-Agent", "Parmod-Kafka-SpringBoot/1.0 (mrtechviewer@gmail.com)"))
                        .build();

        eventSource.start();
    }
}
