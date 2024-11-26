package com.ByteMe;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
/*
public abstract class Bird {
    public Texture texture;
    public ArrayList<Integer> position;
    public ArrayList<Integer> size;

    public Bird(String t) {
        texture = new Texture(t);
        position = new ArrayList<>(2);
        size = new ArrayList<>(2);
    }
}
*/

public abstract class Bird {
    public Texture texture; // Texture of the bird
    public ArrayList<Integer> position; // Position of the bird (X, Y)
    public ArrayList<Integer> size; // Size of the bird (width, height)
    public Vector2 velocity; // Velocity of the bird (using Vector2 for simplicity)

    // Constructor
    public Bird(String textureFile) {
        texture = new Texture(textureFile); // Load the bird texture
        position = new ArrayList<>(2); // X, Y coordinates
        size = new ArrayList<>(2); // Width, Height
        velocity = new Vector2(0, 0); // Initial velocity (0, 0)
    }

    // Method to update the bird's position based on velocity
    public void update(float deltaTime) {
        position.set(0, (int)(position.get(0) + velocity.x * deltaTime)); // Update X
        position.set(1, (int)(position.get(1) + velocity.y * deltaTime)); // Update Y
    }

    // Method to apply a force to the bird
    public void applyForce(Vector2 force) {
        velocity.add(force); // Add force to the current velocity
    }

    // Optional: method to reset velocity (e.g., when bird is launched or reset)
    public void resetVelocity() {
        velocity.set(0, 0); // Reset velocity to zero
    }
}
