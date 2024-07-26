package com.BeTek.Aerolinea_EveryOneFlies.ChatBot;


import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;


@Configuration

public class DialogflowConfig {

    @Value("${dialogflow.credentials.path}")
    private String jsonPath;

    @Bean
    public SessionsClient sessionsClient() throws IOException {
        // Ruta al archivo JSON de las credenciales

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        return SessionsClient.create(sessionsSettings);
    }
}
