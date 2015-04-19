package edu.nyu.cims.compfin14.hw3;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by anirudhan on 4/18/15.
 */
public class AsianPayout implements IPayOut {

    private final Option option;

    public AsianPayout(Option option){
        this.option = option;
    }

    @Override
    public double getPayout(IStockPath path) {
        List<Pair<DateTime, Double>> prices = path.getPrices();

        double sum  = 0.0d, average = 0.0d;
        for (int i=1; i < prices.size(); i++) {
            sum += (Double)prices.get(i).getValue();
        }
        average = sum / prices.size();

        return Math.max(average - this.option.getStrikePrice(),0);
    }
}
