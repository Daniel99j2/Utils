package com.daniel99j.djutil.pathfinder;

public class PathfindDebugPos {
    private final PathfindPos previous;
    private final PathfindPos pos;
    private final int cost;
    private final int colour;

    public PathfindDebugPos(PathfindPos previous, PathfindPos pos, int cost, int colour) {
        this.previous = previous;
        this.pos = pos;
        this.cost = cost;
        this.colour = colour;
    }
}
