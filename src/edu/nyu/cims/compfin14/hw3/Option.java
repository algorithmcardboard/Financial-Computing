package edu.nyu.cims.compfin14.hw3;

/**
 * Created by anirudhan on 4/18/15.
 */
public class Option {
    private final int numDays;
    private final double strikePrice;
    private final Asset asset;

    public Option(Asset asset, double strikePrice, int numDays){
        this.asset = asset;
        this.strikePrice = strikePrice;
        this.numDays = numDays;
    }

    public Asset getAsset() {
        return asset;
    }

    public int getNumDays() {
        return numDays;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public double getPrice(IStockPath path, IPayOut payout) {
        return 0;
    }
}