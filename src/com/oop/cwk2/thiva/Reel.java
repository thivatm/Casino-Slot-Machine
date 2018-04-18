package com.oop.cwk2.thiva;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;

public class Reel{

    //An array list to store all the instances of Symbols
    private ArrayList<Symbol> slotReels = new ArrayList<>();

    public ArrayList<Symbol> getSlotReels() {
        return slotReels;
    }


    public ArrayList<Symbol> spin(){
        /*Creating 6 instances
        * for 6 symbols
        */
        Symbol sym1 = new Symbol(new Image("/Images/redseven.png"), 7);
        Symbol sym2 = new Symbol(new Image("/Images/bell.png"), 6);
        Symbol sym3 = new Symbol(new Image("/Images/watermelon.png"), 5);
        Symbol sym4 = new Symbol(new Image("/Images/plum.png"), 4);
        Symbol sym5 = new Symbol(new Image("/Images/lemon.png"), 3);
        Symbol sym6 = new Symbol(new Image("/Images/cherry.png"), 2);

        slotReels.add(sym1);
        slotReels.add(sym2);
        slotReels.add(sym3);
        slotReels.add(sym4);
        slotReels.add(sym5);
        slotReels.add(sym6);

        return slotReels;
    }


}
