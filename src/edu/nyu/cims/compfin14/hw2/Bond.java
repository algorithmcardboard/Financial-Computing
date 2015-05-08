package edu.nyu.cims.compfin14.hw2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anirudhan on 3/11/2015.
 * Bond interface which must be implemented by both Zero Coupon and Coupon bearing bonds.
 * T
 */
public interface Bond {
    public Map<Double, Double> getCashFlow();
    public double getPrice();
    public double getMaturity();
    public double getFaceValue();
    public double getCopon();
}
