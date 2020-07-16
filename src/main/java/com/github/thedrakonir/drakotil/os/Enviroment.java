package com.github.thedrakonir.drakotil.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.thedrakonir.drakotil.logging.LoggerFactory;

public class Enviroment {
    private static Map<String, String> cachedVariables;

    public static Optional<String> getValue(String key) {
        updateCache();

        return Optional.ofNullable(cachedVariables.get(key));
    }

    public static String getValueOrElse(String key, String defaultValue) {
        Optional<String> queryResult = getValue(key);

        return queryResult.isPresent() ? queryResult.get() : defaultValue;
    }

    public static Map<String, String> getValues() {
        updateCache();

        return cachedVariables;
    }

    private static void updateCache() {
        cachedVariables = new HashMap<String, String>(System.getenv());

        Path envFilePath = Paths.get("", ".env");
        try {
            if (Files.notExists(envFilePath)) {
                LoggerFactory.getLogger().info("No .env file found. Only using enviroment variables.");
                return;
            }

            Files.readAllLines(envFilePath).forEach(line -> {
                String[] values = line.split("=");
                try {
                    cachedVariables.put(values[0], values[1]);
                } catch (IndexOutOfBoundsException ex) {
                    throw new IllegalArgumentException(
                            "Corrupted data in .env file. Check for errors and rerun the programm.");
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}