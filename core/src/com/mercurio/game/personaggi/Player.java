package com.mercurio.game.personaggi;

import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 currentTile; // Current tile position
    private Vector2 targetTile; // Target tile position
    private Vector2 position; // Pixel position for rendering
    private float tileSize; // Size of a tile in pixels
    private float speed; // Movement speed (tiles per second)
    private boolean moving; // Whether the player is moving
    private Direction facing; // Current facing direction

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Player(Vector2 startTile, float tileSize, float speed) {
        this.currentTile = new Vector2(startTile);
        this.targetTile = new Vector2(startTile);
        this.position = new Vector2(startTile.x * tileSize, startTile.y * tileSize);
        this.tileSize = tileSize;
        this.speed = speed;
        this.moving = false;
        this.facing = Direction.DOWN; // Default facing direction
    }

    // Update player logic (movement interpolation)
    public void update(float delta) {
        if (moving) {
            float moveAmount = speed * delta * tileSize; // Movimento per frame
    
            if (position.dst2(new Vector2(targetTile.x * tileSize, targetTile.y * tileSize)) <= moveAmount * moveAmount) {
                // Se è abbastanza vicino alla destinazione, allinea alla tile
                position.set(targetTile.x * tileSize, targetTile.y * tileSize);
                currentTile.set(targetTile);
                moving = false;
            } else {
                // Sposta il giocatore nella direzione della targetTile
                Vector2 direction = new Vector2(targetTile.x * tileSize, targetTile.y * tileSize).sub(position).nor();
                position.add(direction.scl(moveAmount));
            }
        }
    }
    

    // Handle movement input
    public void move(Direction direction, boolean walkable) {
        if (!moving && walkable) { // Ensure no movement if already moving or tile is blocked
            switch (direction) {
                case UP:
                    targetTile.set(currentTile.x, currentTile.y + 1);
                    break;
                case DOWN:
                    targetTile.set(currentTile.x, currentTile.y - 1);
                    break;
                case LEFT:
                    targetTile.set(currentTile.x - 1, currentTile.y);
                    break;
                case RIGHT:
                    targetTile.set(currentTile.x + 1, currentTile.y);
                    break;
            }
            moving = true;
            facing = direction; // Update facing direction
        }
    }

    // Getters for rendering and state management
    public Vector2 getPosition() {
        return position; // Pixel position for rendering
    }

    public Direction getFacing() {
        return facing;
    }

    public boolean isMoving() {
        return moving;
    }

    public Vector2 getCurrentTile() {
        return currentTile;
    }
}
