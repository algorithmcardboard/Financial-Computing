package edu.nyu.cims.compfin14.hw3;

/**
 * Created by anirudhan on 4/18/15.
 */
public class Asset {
    private final double riskFreeRate;
    private final String assetName;
    private final double currentPrice;
    private final double volatility;

    Asset(String assetName, double currentPrice, double volatility, double riskFreeRate){
        this.assetName = assetName;
        this.currentPrice = currentPrice;
        this.volatility = volatility;
        this.riskFreeRate = riskFreeRate;
    }

    public double getRiskFreeRate() {
        return riskFreeRate;
    }

    public String getAssetName() {
        return assetName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getVolatility() {
        return volatility;
    }
}
