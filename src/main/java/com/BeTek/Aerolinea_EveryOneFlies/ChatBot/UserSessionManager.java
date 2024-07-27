package com.BeTek.Aerolinea_EveryOneFlies.ChatBot;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserSessionManager {
    private final Map<String, String> userSessionMap = new HashMap<>();

    public String getSessionIdForUser(String userId) {
        return userSessionMap.get(userId);
    }

    public void setSessionIdForUser(String userId, String sessionId) {
        userSessionMap.put(userId, sessionId);
    }

    public void removeSessionForUser(String userId) {
        userSessionMap.remove(userId);
    }
}