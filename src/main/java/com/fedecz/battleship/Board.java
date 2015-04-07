package com.fedecz.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Board {
    private List<Attack> attacks;
    private Set<Square> hits;
    private Set<Square> misses;

    public Board() {
        attacks = new ArrayList<>();
        hits = new HashSet<>();
        misses = new HashSet<>();
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public Set<Square> getHits() {
        return hits;
    }

    public Set<Square> getMisses() {
        return misses;
    }

}
