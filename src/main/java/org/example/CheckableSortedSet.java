package org.example;

import java.util.SortedSet;

public interface CheckableSortedSet<T> extends SortedSet<T> {
    boolean checkInvariant();

    int height();
}
