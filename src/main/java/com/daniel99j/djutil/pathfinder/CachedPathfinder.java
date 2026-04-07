package com.daniel99j.djutil.pathfinder;

import java.util.ArrayList;
import java.util.List;

public class CachedPathfinder {
    private final PathfinderOptions options;
    private final ArrayList<PathfindPos> cache = new ArrayList<>();
    private PathfindPos oldStart, oldEnd;
    private final float maxDistance;

    public CachedPathfinder(PathfinderOptions options, float maxDistance) {
        this.options = options;
        this.maxDistance = maxDistance;
    }

    public List<PathfindPos> findPath(PathfindPos start, PathfindPos end) {
        boolean cacheInvalid = false;

        if((end.equals(this.oldEnd) && start.equals(this.oldStart)) || (this.maxDistance != 0 && this.oldStart.distanceTo(start) <= this.maxDistance && this.oldEnd.distanceTo(end) <= this.maxDistance)) {
            for (PathfindPos pathfindPos : this.cache) {
                if(!this.options.getWalkablePredicate().test(pathfindPos)) {
                    cacheInvalid = true;
                    break;
                }
            }
        } else cacheInvalid = true;

        if(cacheInvalid) {
            List<PathfindPos> path = Pathfinder.findPath(start, end, options);
            this.oldStart = start;
            this.oldEnd = end;
            this.cache.clear();
            this.cache.addAll(path);
        }

        return this.cache;
    }
}
