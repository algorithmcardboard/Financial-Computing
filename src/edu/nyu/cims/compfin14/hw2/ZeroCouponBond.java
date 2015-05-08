package edu.nyu.cims.compfin14.hw2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anirudhan on 5/8/15.
 * A bond that doesn't provide any copon values.  There is not monthly payment to the person holding the bond
 */
public class ZeroCouponBond implements Bond {

    private double price;
    private double copon;
    private double maturity;
    private double faceValue;
    private double frequency;

    private Map<Double, Double> cashFlow = new HashMap<Double, Double>();

    ZeroCouponBond(double faceValue, double maturity, double price){
        this.faceValue = faceValue;
        this.maturity = maturity;
        this.copon = 0;
        this.price = price;
    }

    @Override
    public double getFaceValue() {
        return faceValue;
    }

    public void setCopon(double couponVal){
        this.copon = couponVal;
    }

    @Override
    public double getMaturity() {
        return maturity;
    }

    @Override
    public double getPrice(){
        return price;
    }

    @Override
    public double getCopon() {
        return copon;
    }

    @Override
    public Map<Double, Double> getCashFlow() {
        return cashFlow;
    }
}
