package edu.nyu.cims.compfin14.hw3;

import java.util.ArrayList;

/**
 * Created by anirudhan on 4/18/15.
 */
public class Statistics {
    private final ArrayList<Double> meanList;
    private double meanSq = 0.0d;
    private double mean = 0.0d;
    private double stdVar = 0.0d;

    public Statistics(){
        this.meanList = new ArrayList<Double>();
    }

    public void add(double x){
        int n = meanList.size()+1;
        meanList.add(new Double(x));
        mean =(n - 1.0)/n*mean + x/n;
        meanSq =(n - 1.0)/n*meanSq + x*x/n;
        stdVar = Math.sqrt(meanSq-mean*mean);
    }

    public double getMean() {
        return mean;
    }

    public double getStdVar() {
        return stdVar;
    }
}
