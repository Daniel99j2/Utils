package com.daniel99j.djutil;

import org.jetbrains.annotations.Contract;

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
        return left != null;
    }

    public L getLeftOr(L other) {
        return left == null ? other : left;
    }

    public R getRightOr(R other) {
        return right == null ? other : right;
    }

    public void ifLeft(Consumer<? super L> consumer) {
        if(left != null) {consumer.accept(left);}
    }

    public void ifRight(Consumer<? super R> consumer) {
        if(right != null) {consumer.accept(right);}
    }

    public void map(Consumer<? super L> left, Consumer<? super R> right) {
        if(isRight()) right.accept(this.right);
        else if(isLeft()) left.accept(this.left);
        else throw new IllegalStateException("Either is not left or right");
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;

        if(obj instanceof Either<?,?> either) {
            if(this.isLeft() && either.isLeft() && this.left.equals(either.left)) return true;
            if(this.isRight() && either.isRight() && this.right.equals(either.right)) return true;
        }
        if(this.isLeft() && left.equals(obj)) return true;
        //noinspection RedundantIfStatement
        if(this.isRight() && right.equals(obj)) return true;
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
