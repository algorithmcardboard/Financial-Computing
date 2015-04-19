package edu.nyu.cims.compfin14.hw3;

/**
 * Created by anirudhan on 4/18/15.
 */
public class AntiTheticRVG extends RandomVectorGenerator{

    private double[] antitheticRandomNumber;

    public AntiTheticRVG(int numDays) {
        super(numDays);
    }

    @Override
    public double[] getUniformRandomNumber() {
        double[] rn = null;
        if(antitheticRandomNumber != null && antitheticRandomNumber.length != 0){
            rn = antitheticRandomNumber;
            antitheticRandomNumber = null;
        }else{
            rn = super.getUniformRandomNumber();
            antitheticRandomNumber = new double[rn.length];
            for(int i = 0; i < rn.length; i++){
                antitheticRandomNumber[i] = -1*rn[i];
            }
        }
        return rn;
    }
}
