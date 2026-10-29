package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.AbstractAction;
import ru.mipt.bit.platformer.util.MoveAction;

import java.util.HashMap;
import java.util.Map;

public class KeyboardInputHandler implements InputHandler {

    private final Map<Integer, AbstractAction> actionMap = new HashMap<>();

    public KeyboardInputHandler addKey(Integer key, MoveAction action) {
        actionMap.put(key, action);
        return this;
    }

    @Override
    public AbstractAction getActionTriggered() {
        for (Map.Entry<Integer, AbstractAction> entry: actionMap.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}