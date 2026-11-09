package ru.mipt.bit.platformer.actions;

import org.junit.Test;
import ru.mipt.bit.platformer.graphic.HealthBarVisibility;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ToggleHealthableBarsActionTest {

    @Test
    public void actionTurnsHealthBarsOn() {
        HealthBarVisibility visibility = new HealthBarVisibility();

        new ToggleHealthBarsAction(visibility).execute();

        assertTrue(visibility.isVisible());
    }

    @Test
    public void secondExecutionTurnsHealthBarsOffAgain() {
        HealthBarVisibility visibility = new HealthBarVisibility();
        ToggleHealthBarsAction action = new ToggleHealthBarsAction(visibility);

        action.execute();
        action.execute();

        assertFalse(visibility.isVisible());
    }

    @Test
    public void toggleRespectsInitialVisibility() {
        HealthBarVisibility visibility = new HealthBarVisibility(true);

        new ToggleHealthBarsAction(visibility).execute();

        assertFalse(visibility.isVisible());
    }
}
