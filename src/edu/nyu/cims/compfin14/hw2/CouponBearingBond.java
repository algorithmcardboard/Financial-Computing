package edu.nyu.cims.compfin14.hw2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anirudhan on 5/8/15.
 * A bond that pays a certain copon value on the given frequency till the date of maturity.
 */
public class CouponBearingBond implements Bond {

    private double price;
    private double copon;
    private double maturity;
    private double faceValue;
    private double frequency;

    private Map<Double, Double> cashFlow = new HashMap<Double, Double>();

    /**
     * private method for calculating the cash flow given a copoun bearing bond
     */
    private void calculateCashFlow() {
//        cashFlow.put(0.0d, 0d);
        for(double pf = frequency; pf < maturity; pf+=frequency){
            cashFlow.put(pf, copon);
        }
        cashFlow.put(maturity, faceValue + copon);
    }

    CouponBearingBond(double faceValue, double maturity, double price, double coponPercentage, double frequency){
        this.faceValue = faceValue;
        this.maturity = maturity;
        this.copon = coponPercentage*faceValue*frequency/100;

        this.frequency = frequency;
        this.price = 0.0d;
        calculateCashFlow();
    }

    @Override
    public double getFaceValue() {
        return faceValue;
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
