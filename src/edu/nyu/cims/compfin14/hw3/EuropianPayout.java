package edu.nyu.cims.compfin14.hw3;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by anirudhan on 4/18/15.
 */
public class EuropianPayout implements IPayOut {
    private final Option option;

    public EuropianPayout(Option option) {
        this.option = option;
    }

    @Override
    public double getPayout(IStockPath path) {
        List<Pair<DateTime, Double>> prices = path.getPrices();
        return Math.max((Double)(prices.get(prices.size() - 1).getValue()) - this.option.getStrikePrice(), 0);
    }
}
