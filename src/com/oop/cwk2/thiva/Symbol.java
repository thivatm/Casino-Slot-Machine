package com.oop.cwk2.thiva;

import javafx.scene.image.Image;

public class Symbol implements ISymbol {

    private Image symbolImg;
    private int value;

    public Symbol(Image symbolImg, int value) {
        this.symbolImg = symbolImg;
        this.value = value;
    }

    @Override
    public void setImage(Image symbolImg) {
        this.symbolImg = symbolImg;
    }

    @Override
    public Image getImage() {
        return this.symbolImg;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
