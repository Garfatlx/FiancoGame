package com.mycompany.app;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    private String name;
    private int color;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
