package edu.nyu.cims.compfin14.hw3;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anirudhan on 4/18/15.
 */
public class BrownianStockPath implements IStockPath {

    private final Option option;
    private List<Pair<DateTime, Double>> path = new ArrayList<Pair<DateTime, Double>>();
    IRandomVectorGenerator generator;

    /**
     * This calculates the stock path using brownian formula
     * Strikeprice * Math.exp((option.getRateOfInterest() - volatility*volatility/2)+volatility*random)
     */
    public BrownianStockPath(Option option) {
        this.option = option;
    }


    /**
     * returns the stock path created through geometric browinan motion.
     * The path is created in constructor so that we dont run into any race conditions.
     */
    @Override
    public List<Pair<DateTime, Double>> getPrices() {
        this.path.clear();
        this.path.add(new Pair(DateTime.now(), option.getAsset().getCurrentPrice()));
        double[] uniformRandomNumber = new AntiTheticRVG(option.getNumDays()).getUniformRandomNumber();

        double St = option.getAsset().getCurrentPrice();
        DateTime t = DateTime.now();
        for(int i=0; i< uniformRandomNumber.length ;i++) {
            t = t.plusDays(1);
            double volatility = option.getAsset().getVolatility();
            St = St * Math.exp((option.getAsset().getRiskFreeRate() - volatility*volatility/2)+volatility*uniformRandomNumber[i]);
            this.path.add(new Pair(t, St));
        }
        return path;
    }
}