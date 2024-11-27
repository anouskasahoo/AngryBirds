package com.ByteMe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class checkOverlapTest {
    static class Vector2 {
        float x, y;

        public Vector2(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    // isOverlapping method to check overlap
    private boolean isOverlapping(Vector2 newPos, Vector2 existingPos, float width, float height) {
        return !(newPos.x + width < existingPos.x ||
            newPos.x > existingPos.x + width ||
            newPos.y + height < existingPos.y ||
            newPos.y > existingPos.y + height);
    }

    @Test
    void testIsOverlapping() {
        Vector2 newPos = new Vector2(0, 0);
        Vector2 existingPos = new Vector2(5, 5);
        float width = 10, height = 10;

        assertTrue(isOverlapping(newPos, existingPos, width, height), "Rectangles should overlap");

        newPos = new Vector2(16, 0);
        assertFalse(isOverlapping(newPos, existingPos, width, height), "Rectangles should not overlap");

        newPos = new Vector2(10, 5);
        assertTrue(isOverlapping(newPos, existingPos, width, height), "Rectangles should touch and overlap");

        newPos = new Vector2(0, -10);
        assertFalse(isOverlapping(newPos, existingPos, width, height), "Rectangles should not overlap");


        newPos = new Vector2(0, 20);
        assertFalse(isOverlapping(newPos, existingPos, width, height), "Rectangles should not overlap");
    }
}
