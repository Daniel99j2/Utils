package com.daniel99j.djutil;

import java.util.Objects;

public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> left(L value) {
        return new Either<>(value, null);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Either<>(null, value);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Either<?,?> either) {
            if(this.left != null && either.left != null && this.left.equals(either.left)) return true;
            //noinspection RedundantIfStatement
            if(this.right != null && either.right != null && this.right.equals(either.right)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if(this.left != null) return "Either<Left>[left="+this.left+"]";
        if(this.right != null) return "Either<Right>[right="+this.right+"]";
        return "Either<No Left or Right>";
    }
}
