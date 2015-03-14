package edu.nyu.cims.compfin14.hw2;

import java.util.Map;

/**
 * Created by Anirudhan on 3/11/2015.
 */
public class Bond {

    Bond(double faceValue, double maturity, double price){
        this.faceValue = faceValue;
        this.maturity = maturity;
        this.copon = 0;
        this.price = price;
    }

    Bond(double price, double copon, double maturity, double faceValue){
        this.faceValue = faceValue;
        this.maturity = maturity;
        this.copon = copon;
        this.price = price;
    }

    private double price;
    private double copon;
    private double maturity;
    private double faceValue;

    public double getPrice(){
        return price;
    }

    public double getCopon() {
        return copon;
    }

    public Map<Double, Double> getCashFlow(){
        return null;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public double getMaturity() {
        return maturity;
    }
}
