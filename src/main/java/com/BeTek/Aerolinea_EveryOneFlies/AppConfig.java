package com.BeTek.Aerolinea_EveryOneFlies;

import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public void someMethod() {
        String credentialsPath = dotenv.get("GOOGLE_APPLICATION_CREDENTIALS");
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", credentialsPath);

    }
}
