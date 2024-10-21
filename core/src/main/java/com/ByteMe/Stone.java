package com.ByteMe;

import com.badlogic.gdx.math.Vector2;

public class Stone extends Obstacle{
    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public Stone(Vector2 position, Wood.Orientation orientation) {
        super(orientation == Wood.Orientation.HORIZONTAL ? "stone_horizontal.png" : "stone_vertical.png",
            position,
            orientation == Wood.Orientation.HORIZONTAL ? 50 : 10,
            orientation == Wood.Orientation.HORIZONTAL ? 10 : 50);
    }
}
