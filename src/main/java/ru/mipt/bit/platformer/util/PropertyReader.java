package ru.mipt.bit.platformer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static ru.mipt.bit.platformer.config.ConfigureConstants.LEVEL_PROPERTIES;

public class PropertyReader {
    public static Properties readLevelProperties() {
        Properties properties = new Properties();
        try (InputStream input = PropertyReader.class.getClassLoader()
                .getResourceAsStream(LEVEL_PROPERTIES)) {
            if (input == null) {
                throw new IllegalStateException("Resource not found: " + LEVEL_PROPERTIES);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read resource: " + LEVEL_PROPERTIES, e);
        }
        return properties;
    }
}
