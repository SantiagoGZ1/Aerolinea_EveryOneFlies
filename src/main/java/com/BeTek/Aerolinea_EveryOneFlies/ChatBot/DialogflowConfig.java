package com.BeTek.Aerolinea_EveryOneFlies.ChatBot;


import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;



@Configuration
public class DialogflowConfig {

    private static final String CREDENTIALS_PATH = "src/main/java/com/BeTek/Aerolinea_EveryOneFlies/ChatBot/everyoneflies-ewlv-bb71cf9941cb.json";

    @Bean
    public SessionsClient sessionsClient() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH));
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        return SessionsClient.create(sessionsSettings);
    }

}
