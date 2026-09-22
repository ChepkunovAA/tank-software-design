package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.util.Action;

import java.util.HashMap;
import java.util.Map;

public class KeyboardInputHandler {

    private final Map<Integer, Action> actionMap = new HashMap<>();

    public KeyboardInputHandler() {
        actionMap.put(Input.Keys.W, Action.UP);
        actionMap.put(Input.Keys.UP, Action.UP);
        actionMap.put(Input.Keys.S, Action.DOWN);
        actionMap.put(Input.Keys.DOWN, Action.DOWN);
        actionMap.put(Input.Keys.A, Action.LEFT);
        actionMap.put(Input.Keys.LEFT, Action.LEFT);
        actionMap.put(Input.Keys.D, Action.RIGHT);
        actionMap.put(Input.Keys.RIGHT, Action.RIGHT);
    }

    public Action getActionTriggered() {
        for (Map.Entry<Integer, Action> entry: actionMap.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue();
            }
        }
        return Action.NONE;
    }
}