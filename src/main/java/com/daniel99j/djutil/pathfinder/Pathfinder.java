package com.daniel99j.djutil.pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;

public class Pathfinder {
    private record Node(PathfindPos pos, double fScore) {}

    public static List<PathfindPos> findPath(
            PathfindPos start,
            PathfindPos end
    ) {
        return findPath(start, end, PathfinderOptions.builder().build());
    }

    public static List<PathfindPos> findPath(PathfindPos start, PathfindPos end, PathfinderOptions options) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::fScore));
        Set<PathfindPos> closedSet = new HashSet<>();
        Map<PathfindPos, PathfindPos> cameFrom = new HashMap<>();
        Map<PathfindPos, Double> gScore = new HashMap<>();

        gScore.put(start, 0.0);
        openSet.add(new Node(start, options.getHeuristicFunction().applyAsDouble(start, end)));

        int iterations = 0;
        while (!openSet.isEmpty() && iterations < options.getMaxIterations()) {
            Node currentNode = openSet.poll();
            assert currentNode != null;
            PathfindPos current = currentNode.pos();
            if (closedSet.contains(current)) continue;

            options.getOnVisitConsumer().accept(current);
            if (current.equals(end)) {

                List<PathfindPos> path = reconstructPath(cameFrom, current);

                if(options.getDebugRenderConsumer() != null) {
                    options.getDebugRenderConsumer().accept(new PathfindDebugPos(null, null, 0, PathfindDebugType.BEGIN_MARKER_NOTREAL));
                    options.getDebugRenderConsumer().accept(new PathfindDebugPos(null, start, 0, PathfindDebugType.START));
                    options.getDebugRenderConsumer().accept(new PathfindDebugPos(null, end, 0, PathfindDebugType.END));
                    cameFrom.forEach((pos, pos2) -> options.getDebugRenderConsumer().accept(new PathfindDebugPos(pos2, pos, 0, PathfindDebugType.CONNECTION)));
                    openSet.forEach(node -> options.getDebugRenderConsumer().accept(new PathfindDebugPos(null, node.pos(), 0, PathfindDebugType.OPEN_SET)));
                    closedSet.forEach(node -> options.getDebugRenderConsumer().accept(new PathfindDebugPos(null, node, 0, PathfindDebugType.CLOSED_SET)));
                    int index = 0;
                    for (PathfindPos pos : path) {
                        if(index > 0) options.getDebugRenderConsumer().accept(new PathfindDebugPos(path.get(index-1), pos, gScore.get(pos).intValue(), PathfindDebugType.SUCCESSFUL_PATH));
                        index++;
                    }
                    options.getDebugRenderConsumer().accept(new PathfindDebugPos(null, null, 0, PathfindDebugType.END_MARKER_NOTREAL));
                }

                return path;
            }

            closedSet.add(current);
            Iterable<PathfindPos> neighbours = options.getNeighbourProvider().apply(current);
            if (neighbours == null) {
                iterations++;
                continue;
            }

            double currentGScore = gScore.getOrDefault(current, Double.POSITIVE_INFINITY);
            for (PathfindPos neighbour : neighbours) {
                if (neighbour == null) continue;
                if (closedSet.contains(neighbour)) continue;
                if (!neighbour.equals(end) && !options.getWalkablePredicate().test(neighbour)) continue;

                double tentativeGScore = currentGScore
                        + options.getMovementCostFunction().applyAsDouble(current, neighbour)
                        + options.getPositionCostFunction().applyAsDouble(neighbour);

                if (tentativeGScore < gScore.getOrDefault(neighbour, Double.POSITIVE_INFINITY)) {
                    cameFrom.put(neighbour, current);
                    gScore.put(neighbour, tentativeGScore);
                    double fScore = tentativeGScore + options.getHeuristicFunction().applyAsDouble(neighbour, end);
                    openSet.add(new Node(neighbour, fScore));
                }
            }

            iterations++;
        }

        return Collections.emptyList();
    }

    private static List<PathfindPos> reconstructPath(Map<PathfindPos, PathfindPos> cameFrom, PathfindPos current) {
        ArrayList<PathfindPos> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }
}
