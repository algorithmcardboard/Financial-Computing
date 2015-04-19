package edu.nyu.cims.compfin14.hw3;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

/**
 * Created by anirudhan on 4/17/15.
 */
public class Test {
    private static final double NORMAL_DISTRION_96_PERCENTILE = 2.0537489106318234;

    public static void main(String[] args){
        Asset ibm = new Asset("IBM", 152.35, 0.01, 0.0001);

        Option europianOption = new Option(ibm, 165.00d, 252);
        IStockPath europianPath = new BrownianStockPath(europianOption);

        IPayOut europianPayout = new EuropianPayout(europianOption);

        double mean = getMean(europianPath, europianPayout);
        double price1 = mean * Math.exp(- europianOption.getAsset().getRiskFreeRate() * europianOption.getNumDays() );

        System.out.println("Price of option is "+price1);

        Option asianOption = new Option(ibm, 164.00d, 252);
        IStockPath asianPath = new BrownianStockPath(asianOption);
        IPayOut asianPayout = new AsianPayout(asianOption);

        double mean2 = getMean(asianPath, asianPayout);
        double price2 = mean2 * Math.exp(- asianOption.getAsset().getRiskFreeRate() * asianOption.getNumDays() );

        System.out.println("Price of option is "+price2);
    }

    private static double getMean(IStockPath path, IPayOut payout) {
        Statistics statistics = new Statistics();
        int count = 0;
        double a = 0.0;
        while (true) {
            double payout1 = payout.getPayout(path);
            statistics.add(payout1);
            double val = NORMAL_DISTRION_96_PERCENTILE * statistics.getStdVar() / Math.sqrt(count);
            if (val < 0.01 && count > 10000 ) {
                break;
            }
            if(count % (100 * 1000) == 0){
                System.out.println(count + " " + val);
            }
            count++;
        }
        return statistics.getMean();
    }
}
