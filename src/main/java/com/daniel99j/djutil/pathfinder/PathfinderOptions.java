package com.daniel99j.djutil.pathfinder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

public class PathfinderOptions {
    private final Function<PathfindPos, Iterable<PathfindPos>> neighbourProvider;
    private final Predicate<PathfindPos> walkablePredicate;
    private final ToDoubleFunction<PathfindPos> positionCostFunction;
    private final ToDoubleBiFunction<PathfindPos, PathfindPos> movementCostFunction;
    private final ToDoubleBiFunction<PathfindPos, PathfindPos> heuristicFunction;
    private final Consumer<PathfindPos> onVisitConsumer;
    private final Consumer<PathfindDebugPos> debugRenderConsumer;
    private final int maxIterations;
    public static boolean debugging = false;

    private PathfinderOptions(Builder builder) {
        this.neighbourProvider = builder.neighbourProvider;
        this.walkablePredicate = builder.walkablePredicate;
        this.positionCostFunction = builder.positionCostFunction;
        this.movementCostFunction = builder.movementCostFunction;
        this.heuristicFunction = builder.heuristicFunction;
        this.onVisitConsumer = builder.onVisitConsumer;
        this.maxIterations = builder.maxIterations;
        this.debugRenderConsumer = builder.debugRenderConsumer;
    }

    public Function<PathfindPos, Iterable<PathfindPos>> getNeighbourProvider() {
        return neighbourProvider;
    }

    public Predicate<PathfindPos> getWalkablePredicate() {
        return walkablePredicate;
    }

    public ToDoubleFunction<PathfindPos> getPositionCostFunction() {
        return positionCostFunction;
    }

    public ToDoubleBiFunction<PathfindPos, PathfindPos> getMovementCostFunction() {
        return movementCostFunction;
    }

    public ToDoubleBiFunction<PathfindPos, PathfindPos> getHeuristicFunction() {
        return heuristicFunction;
    }

    public Consumer<PathfindPos> getOnVisitConsumer() {
        return onVisitConsumer;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public Consumer<PathfindDebugPos> getDebugRenderConsumer() {
        return debugRenderConsumer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private static final ToDoubleBiFunction<PathfindPos, PathfindPos> DEFAULT_HEURISTIC =
                (from, to) -> Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY());

        private Function<PathfindPos, Iterable<PathfindPos>> neighbourProvider = (pos) -> List.of(
                new PathfindPos(pos.getX() + 1, pos.getY()),
                new PathfindPos(pos.getX() - 1, pos.getY()),
                new PathfindPos(pos.getX(), pos.getY() + 1),
                new PathfindPos(pos.getX(), pos.getY() - 1)
        );
        private Predicate<PathfindPos> walkablePredicate = pos -> true;
        private ToDoubleFunction<PathfindPos> positionCostFunction = pos -> 0.0;
        private ToDoubleBiFunction<PathfindPos, PathfindPos> movementCostFunction = (from, to) -> 1.0;
        private ToDoubleBiFunction<PathfindPos, PathfindPos> heuristicFunction = DEFAULT_HEURISTIC;
        private Consumer<PathfindPos> onVisitConsumer = pos -> {};
        private Consumer<PathfindDebugPos> debugRenderConsumer = null;
        private int maxIterations = Integer.MAX_VALUE;

        private Builder() {

        }

        public Builder walkablePredicate(Predicate<PathfindPos> walkablePredicate) {
            this.walkablePredicate = walkablePredicate;
            return this;
        }

        public Builder positionCostFunction(ToDoubleFunction<PathfindPos> positionCostFunction) {
            this.positionCostFunction = positionCostFunction;
            return this;
        }

        public Builder movementCostFunction(ToDoubleBiFunction<PathfindPos, PathfindPos> movementCostFunction) {
            this.movementCostFunction = movementCostFunction;
            return this;
        }

        public Builder heuristicFunction(ToDoubleBiFunction<PathfindPos, PathfindPos> heuristicFunction) {
            this.heuristicFunction = heuristicFunction;
            return this;
        }

        public Builder neighbourProvider(Function<PathfindPos, Iterable<PathfindPos>> neighbourProvider) {
            this.neighbourProvider = neighbourProvider;
            return this;
        }

        public Builder diagonalNeighbourProvider() {
            return neighbourProvider((pos) -> List.of(
                    new PathfindPos(pos.getX() + 1, pos.getY()),
                    new PathfindPos(pos.getX() - 1, pos.getY()),
                    new PathfindPos(pos.getX(), pos.getY() + 1),
                    new PathfindPos(pos.getX(), pos.getY() - 1),
                    new PathfindPos(pos.getX() + 1, pos.getY() + 1),
                    new PathfindPos(pos.getX() + 1, pos.getY() - 1),
                    new PathfindPos(pos.getX() - 1, pos.getY() + 1),
                    new PathfindPos(pos.getX() - 1, pos.getY() - 1)
            ));
        }

        public Builder debugRenderConsumer(Consumer<PathfindDebugPos> onVisitConsumer) {
            this.debugRenderConsumer = onVisitConsumer;
            return this;
        }

        public Builder onVisitConsumer(Consumer<PathfindPos> onVisitConsumer) {
            this.onVisitConsumer = onVisitConsumer;
            return this;
        }

        public Builder maxIterations(int maxIterations) {
            this.maxIterations = maxIterations;
            return this;
        }

        public PathfinderOptions build() {
            return new PathfinderOptions(this);
        }
    }
}
