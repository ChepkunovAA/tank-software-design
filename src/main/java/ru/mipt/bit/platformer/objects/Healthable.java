package ru.mipt.bit.platformer.objects;

/**
 * Object that has hitpoints and can therefore display a health bar.
 */
public interface Healthable {

    int getHitpoints();

    int getMaxHitpoints();
}
