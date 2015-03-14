package edu.nyu.cims.compfin14.hw2;

import java.util.ArrayList;
import java.util.List;

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
            return 0.0;
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
    }
}
