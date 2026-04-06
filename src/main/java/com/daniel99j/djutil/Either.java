package com.daniel99j.djutil;

import java.util.Objects;
import java.util.function.Consumer;

public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> left(L value) {
        if(value == null) throw new NullPointerException("Either value cannot be null");
        return new Either<>(value, null);
    }

    public static <L, R> Either<L, R> right(R value) {
        if(value == null) throw new NullPointerException("Either value cannot be null");
        return new Either<>(null, value);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public boolean isRight() {
        return right != null;
    }

    public boolean isLeft() {
        return right != null;
    }

    public L getLeftOr(L other) {
        return left == null ? other : left;
    }

    public L getRightOr(L other) {
        return left == null ? other : left;
    }

    public void ifLeft(Consumer<? super L> consumer) {
        if(left != null) {consumer.accept(left);}
    }

    public void ifRight(Consumer<? super R> consumer) {
        if(right != null) {consumer.accept(right);}
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
        throw new IllegalStateException("Either is not left or right");
    }

    public String getString() {
        if(this.right != null) return this.right.toString();
        if(this.left != null) return this.left.toString();
        throw new IllegalStateException("Either is not left or right");
    }
}
