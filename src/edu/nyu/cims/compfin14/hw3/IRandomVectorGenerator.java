package edu.nyu.cims.compfin14.hw3;

/**
 * Created by anirudhan on 4/16/15.
 */
public interface IRandomVectorGenerator {
    public void setSeed(long seed);
    public double[] getUniformRandomNumber();
}