package com.BeTek.Aerolinea_EveryOneFlies.ChatBot;


import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Configuration
public class DialogflowConfig {

    @Bean
    public SessionsClient sessionsClient() throws IOException {

        InputStream inputStream = new FileInputStream("src/main/java/com/BeTek/Aerolinea_EveryOneFlies/ChatBot/everyoneflies-ewlv-bb71cf9941cb.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        return SessionsClient.create(SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build());
    }

}
