package com.daniel99j.djutil.pathfinder;

import java.util.Objects;

public class PathfindPos {
    private final int x, y;

    public PathfindPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PathfindPos that = (PathfindPos) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "PathfindPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float distanceTo(PathfindPos other) {
        float distanceX = Math.abs(x - other.x);
        float distanceY = Math.abs(y - other.y);
        return (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }
}
