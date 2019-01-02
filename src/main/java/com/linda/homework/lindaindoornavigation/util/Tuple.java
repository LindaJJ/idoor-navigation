package com.linda.homework.lindaindoornavigation.util;

import java.util.Objects;

/**
 * 二元组
 */
public class Tuple<T, E> {

    /**
     * 元祖左
     */
    private T left;

    /**
     * 元祖右
     */
    private E right;

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public E getRight() {
        return right;
    }

    public void setRight(E right) {
        this.right = right;
    }

    public Tuple() {
    }

    public Tuple(T left, E right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return left.equals(tuple.left) &&
                right.equals(tuple.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
