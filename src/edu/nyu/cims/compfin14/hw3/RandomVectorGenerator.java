package edu.nyu.cims.compfin14.hw3;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.UncorrelatedRandomVectorGenerator;

/**
 * Created by anirudhan on 4/16/15.
 */
public class RandomVectorGenerator implements IRandomVectorGenerator {
    protected org.apache.commons.math3.random.RandomVectorGenerator generator;
    private long seed;


    public RandomVectorGenerator(int numDays) {
        JDKRandomGenerator rnd = new JDKRandomGenerator();
        this.seed = System.currentTimeMillis();
        GaussianRandomGenerator gr = new GaussianRandomGenerator(rnd);
        this.generator = new UncorrelatedRandomVectorGenerator(numDays, gr);
    }

    @Override
    public void setSeed(long s) {
        this.seed = s;
    }

    @Override
    /**
     * Creates a Guassian random generator from a randomnumber generator.
     * Then we create an instance of the RandomVectorGenerator
     */
    public double[] getUniformRandomNumber() {
        return this.generator.nextVector();
    }
}
