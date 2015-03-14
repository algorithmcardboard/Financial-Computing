package edu.nyu.cims.compfin14.hw2;

import java.util.*;

public class YieldCurve {

    Map<Double, Double> yieldCurve = new TreeMap<Double, Double>();

    public YieldCurve(List<Bond> bonds){
        yieldCurve.put(0.0d,0.0);
        for(Bond b: bonds){
            addYieldCurve(b);
        }
    }

    private void addYieldCurve(Bond b) {
        yieldCurve.put(b.getMaturity(), calculateRate(b));
    }

    private Double calculateRate(Bond b) {
        return Math.log(b.getFaceValue()/b.getPrice())*b.getMaturity();
    }

    /**
     *
     * @param time
     * Time is assumed to fall within the range of the yield curve.  If the value of time is greater than the max t in
     * yield curve, the behaviour of this function is undefined.
     * @return the interest rate at the specified time.  This function calculates the interest rate
     * from the yield curve object.
     */
    public double getInterestRate(double time) {
        if(yieldCurve.containsKey(time)) {
            return yieldCurve.get(time);
        }

        double minT1 = 0, minT2 = 0.0d;
        for(Map.Entry<Double, Double> e: yieldCurve.entrySet()){
            double t = e.getKey();
            if(t < time){
                minT1  = t; //Dont be scared.  We are iterating over treemap.
            }
            if(minT2 == 0.0d && t > time){
                minT2 = t;
            }
            if(minT1 != 0.0d && minT2 != 0.0d){
                break;
            }
        }

        if(minT2 - minT1 == 0 || minT2 == 0){
            return Double.NaN;
        }

        double rt1 = yieldCurve.get(minT1);
        double rt2 = yieldCurve.get(minT2);
        yieldCurve.put(time,((minT2 - time) * rt1 + (time - minT1) * rt2)/(minT2 - minT1));
        return yieldCurve.get(time);
    }

    public double getForwardRate(double t0, double t1){
        return 0.0;
    }

    public double getDiscountFactor(double t) {
        return 0.0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("%-10s: %s","Year","r"));
        str.append('\n');
        for(Map.Entry<Double, Double> e : yieldCurve.entrySet()){
            if(e.getKey() == 0.0d){
                continue;
            }
            str.append(String.format("%-10s: %.3f", e.getKey(), e.getValue()));
            str.append('\n');
        }
        return str.toString();
    }
}
