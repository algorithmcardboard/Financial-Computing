package edu.nyu.cims.compfin14.hw2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Anirudhan on 3/11/2015.
 */
public class Test {
    public static class YieldCurveCalculator {
        /**
         *
         * @param ycm
         * @param bond
         * @return Calculate the bond's fair price given a yield curve object.
         */
        public double getPrice(YieldCurve ycm, Bond bond){
            double price = 0.0d;
            Map<Double, Double> cashFlow = bond.getCashFlow();
            for(Map.Entry<Double, Double> e: cashFlow.entrySet()){
//                System.out.println("time "+e.getKey()+" C "+e.getValue()+ " Interest rate "+ycm.getInterestRate(e.getKey()));
                price += e.getValue()*Math.pow(Math.E, (-1*ycm.getInterestRate(e.getKey())*e.getKey()/100));
            }
            return price;
        }

        /**
         *
         * @param bond
         * @param price
         * @return ; returns the yield-to-maturity of the bond for a particular price.
         */
        public double getYTM(Bond bond, double price){
            return 0.0;
        }

        /**
         *
         * @param bond
         * @param ytm
         * @return  returns the bond's fair price given the yield to maturity
         */
        public double getPrice(Bond bond, double ytm){
            return 0.0;
        }
    }
    public static void main(String[] args){
        List<Bond> bonds = new ArrayList<Bond>();
        bonds.add(new Bond(100d, 0.5, 95d));
        bonds.add(new Bond(1000d, 1, 895d));

        YieldCurve yc = new YieldCurve(bonds);
        System.out.println(yc);

        System.out.println(String.format("%.3f",yc.getInterestRate(0.75)));

        Bond b = new Bond(500d, 3d, -1, 5, 0.5);

        YieldCurveCalculator ycc = new YieldCurveCalculator();
        double price = ycc.getPrice(new YieldCurve(), b);

        System.out.println(String.format("%.3f", price));
        double ytm = ycc.getYTM(b,price);
//        System.out.println(ytm);
    }
}
