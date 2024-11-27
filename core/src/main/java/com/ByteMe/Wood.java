package com.ByteMe;

import com.badlogic.gdx.math.Vector2;

public class Wood extends Obstacle {
    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        BOX
    }

    public Wood(Vector2 position, Orientation orientation) {
        super(
            orientation == Orientation.HORIZONTAL ? "wood_horizontal.png" :
                (orientation == Orientation.VERTICAL ? "wood_vertical.png" :
                    (orientation == Orientation.DIAGONAL ? "wood_diagonal.png" : "wood_box.png")),
            position,
            orientation == Orientation.VERTICAL ? 10 : 50,
            orientation == Orientation.HORIZONTAL ? 10 : 50,
            2 // 2 health
        );
        this.woodOrientation = orientation;
    }
}
