package com.mycompany.app;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    private String name;
    private int color;
    private ArrayList<Piece> pieces;

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
    public void setPieces(ArrayList<Piece> pieces){
        this.pieces = pieces;
    }
    public ArrayList<Piece> getPieces(){
        return pieces;
    }
    public void addPiece(Piece piece){
        pieces.add(piece);
    }
    public void removePiece(Piece piece){
        pieces.remove(piece);
    }
}
