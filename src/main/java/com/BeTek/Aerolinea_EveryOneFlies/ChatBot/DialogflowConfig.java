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
        String credentialsJson = System.getenv("GOOGLE_CREDENTIALS");
        if (credentialsJson == null) {
            throw new IllegalStateException("GOOGLE_CREDENTIALS environment variable is not set");
        }
        InputStream inputStream = new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        return SessionsClient.create(SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build());
    }

}
