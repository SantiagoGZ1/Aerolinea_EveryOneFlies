package com.BeTek.Aerolinea_EveryOneFlies.ChatBot;

import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@RequestMapping("api/v1/chatbot")
public class ChatbotController {
    @Autowired
    private SessionsClient sessionsClient;


    @PostMapping("/message")
    public String sendMessage(@RequestParam String message) {
        String projectId = "everyoneflies-ewlv";
        String sessionId = UUID.randomUUID().toString();
        SessionName session = SessionName.of(projectId, sessionId);

        TextInput.Builder textInput = TextInput.newBuilder().setText(message).setLanguageCode("en-US");

        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
        DetectIntentRequest request = DetectIntentRequest.newBuilder()
                .setSession(session.toString())
                .setQueryInput(queryInput)
                .build();

        DetectIntentResponse response = sessionsClient.detectIntent(request);
        QueryResult queryResult = response.getQueryResult();

        return queryResult.getFulfillmentText();
    }
}
