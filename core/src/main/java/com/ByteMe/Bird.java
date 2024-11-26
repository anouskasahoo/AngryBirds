package com.ByteMe;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
/*
public abstract class Bird {
    public Texture texture;
    public ArrayList<Integer> size;
    public Vector2 position;
    Vector2 velocity;
    boolean isFlying;
    public ArrayList<Vector2> trajectoryPoints;
    public int damage;
    public float launchVelocityX;
    public float launchVelocityY;

    public Bird(String t) {
        texture = new Texture(t);
        size = new ArrayList<>(2);
        this.position = new Vector2();
        this.velocity = new Vector2(0, 0);
        this.isFlying = false;
        this.trajectoryPoints = new ArrayList<>();
        this.damage = damage;
    }

    public void setPosition(int screenX, int screenY) {
        position.set(screenX, screenY);
    }

    public void launch() {
        isFlying = true;
        velocity.set(launchVelocityX, launchVelocityY);
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
