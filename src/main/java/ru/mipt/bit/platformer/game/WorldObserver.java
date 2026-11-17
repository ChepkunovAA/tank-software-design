package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.objects.FieldObject;

public interface WorldObserver {

    void onObjectAdded(FieldObject object);

    void onObjectRemoved(FieldObject object);
}
