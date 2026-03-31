package com.daniel99j.djutil;

public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L> Either<L, ?> left(L value) {
        return new Either<>(value, null);
    }

    public static <R> Either<?, R> right(R value) {
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
        return this.left == null ? this.right.hashCode() : this.left.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Either<?,?> either) {
            if(this.left != null && either.left != null && this.left == either.left) return true;
            //noinspection RedundantIfStatement
            if(this.right != null && either.right != null && this.right == either.right) return true;
        }
        return false;
    }
}
