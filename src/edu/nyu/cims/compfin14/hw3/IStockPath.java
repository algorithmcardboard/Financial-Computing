package edu.nyu.cims.compfin14.hw3;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * Interface for finding the stock prices of a stock.  Can swap in mutliple implementations by using this interface
 * Created by anirudhan on 4/16/15.
 */
public interface IStockPath {
    /**
     *
     * @return Returns the date ordered list of prices for a stock path.
     */
    public List<Pair<DateTime,Double>> getPrices();
}
