package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.graphic.HealthBarVisibility;

public class ToggleHealthBarsAction implements AbstractAction {

    private final HealthBarVisibility visibility;

    public ToggleHealthBarsAction(HealthBarVisibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public void execute() {
        visibility.toggle();
    }
}
