package com.ByteMe;

import com.badlogic.gdx.math.Vector2;

public class Stone extends Obstacle{
    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
        H_BOX
    }

    public Stone(Vector2 position, Orientation orientation) {
        super(
            orientation == Orientation.HORIZONTAL ? "stone_horizontal.png" :
                (orientation == Orientation.VERTICAL ? "stone_vertical.png" : "stone_hbox.png"),
            position,
            orientation == Orientation.HORIZONTAL ? 50 : (orientation == Orientation.VERTICAL ? 10 : 100),
            orientation == Orientation.HORIZONTAL ? 10 : 50,
            6 // 6 health
        );
    }
}
