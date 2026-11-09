package ru.mipt.bit.platformer.graphic;

public class HealthBarVisibility {

    private boolean visible;

    public HealthBarVisibility() {
        this(false);
    }

    public HealthBarVisibility(boolean visible) {
        this.visible = visible;
    }

    public void toggle() {
        visible = !visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
