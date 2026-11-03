package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.models.GameModel;
import ru.mipt.bit.platformer.models.Tank;
import ru.mipt.bit.platformer.models.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.config.ConfigureConstants.LEVEL_FILE;

public class FileLevelFactory implements LevelFactory {

    private static final char EMPTY_CELL = '_';
    private static final char TREE_CELL = 'T';
    private static final char PLAYER_CELL = 'X';
    private static final char NPC_CELL = 'N';

    private final String resourcePath;

    public FileLevelFactory() {
        this(LEVEL_FILE);
    }

    public FileLevelFactory(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public GameModel generate() {
        List<String> rows = readRows();
        if (rows.isEmpty()) {
            throw new IllegalStateException("Level file is empty: " + resourcePath);
        }

        int height = rows.size();
        int width = rows.get(0).length();
        validateRectangle(rows, width);

        Tank playerTank = null;
        List<Tank> npcTanks = new ArrayList<>();
        List<Tree> trees = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char symbol = rows.get(row).charAt(col);
                GridPoint2 coordinates = new GridPoint2(col, height - 1 - row);
                switch (symbol) {
                    case EMPTY_CELL:
                        break;
                    case TREE_CELL:
                        trees.add(new Tree(coordinates));
                        break;
                    case PLAYER_CELL:
                        if (playerTank != null) {
                            throw new IllegalStateException("Level contains more than one player: " + resourcePath);
                        }
                        playerTank = new Tank(coordinates);
                        break;
                    case NPC_CELL:
                        npcTanks.add(new Tank(coordinates));
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unknown symbol '" + symbol + "' in level file: " + resourcePath);
                }
            }
        }

        if (playerTank == null) {
            throw new IllegalStateException("Level does not contain a player: " + resourcePath);
        }
        return new GameModel(playerTank, npcTanks, trees, width, height);
    }

    private List<String> readRows() {
        try (InputStream input = FileLevelFactory.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("Resource not found: " + resourcePath);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            List<String> rows = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }
            return rows;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read level file: " + resourcePath, e);
        }
    }

    private void validateRectangle(List<String> rows, int width) {
        for (String row : rows) {
            if (row.length() != width) {
                throw new IllegalStateException("Level file is not rectangular: " + resourcePath);
            }
        }
    }
}