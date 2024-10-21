package com.ByteMe;

import com.badlogic.gdx.math.Vector2;

public class Wood extends Obstacle {
    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public Wood(Vector2 position, Orientation orientation) {
        super(orientation == Orientation.HORIZONTAL ? "wood_horizontal.png" : "wood_vertical.png",
            position,
            orientation == Orientation.HORIZONTAL ? 50 : 10,
            orientation == Orientation.HORIZONTAL ? 10 : 50);
    }
}
