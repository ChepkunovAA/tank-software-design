package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.models.GameModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static ru.mipt.bit.platformer.config.ConfigureConstants.LEVEL_FACTORY;
import static ru.mipt.bit.platformer.config.ConfigureConstants.LEVEL_PROPERTIES;

public class ConfiguredLevelFactory implements LevelFactory {

    private final LevelFactory delegate;

    public ConfiguredLevelFactory() {
        this(readFactoryName());
    }

    ConfiguredLevelFactory(String factoryName) {
        switch (factoryName) {
            case "file":
                this.delegate = new FileLevelFactory();
                break;
            case "random":
                this.delegate = new RandomLevelFactory();
                break;
            default:
                throw new IllegalStateException("Unknown generation method: " + factoryName);
        }
    }

    @Override
    public GameModel generate() {
        return delegate.generate();
    }

    private static String readFactoryName() {
        Properties properties = new Properties();
        try (InputStream input = ConfiguredLevelFactory.class.getClassLoader()
                .getResourceAsStream(LEVEL_PROPERTIES)) {
            if (input == null) {
                throw new IllegalStateException("Resource not found: " + LEVEL_PROPERTIES);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read resource: " + LEVEL_PROPERTIES, e);
        }
        return properties.getProperty(LEVEL_FACTORY);
    }
}