package com.ByteMe;

import com.badlogic.gdx.math.Vector2;

public class Stone extends Obstacle{
    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
        H_BOX
    }

    public Stone(Vector2 position, Orientation orientation) {
        super(orientation == Stone.Orientation.HORIZONTAL ? "stone_horizontal.png" : (orientation == Stone.Orientation.VERTICAL ? "stone_vertical.png" : "stone_hbox.png"),
            position,
            orientation == Stone.Orientation.HORIZONTAL ? 50 : (orientation == Stone.Orientation.VERTICAL ? 10 : 100),
            orientation == Stone.Orientation.HORIZONTAL ? 10 : 50);
    }
}
