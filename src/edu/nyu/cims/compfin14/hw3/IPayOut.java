package edu.nyu.cims.compfin14.hw3;

/**
 * Created by anirudhan on 4/16/15.
 * Interface for calculating the payout for a particular stockpath.
 */
public interface IPayOut {
    /**
     *  @param path: Stock path for calculating the payout
     *  @return Returns the payout of a particular option based on the stock path
     */
    public double getPayout(IStockPath path);
}