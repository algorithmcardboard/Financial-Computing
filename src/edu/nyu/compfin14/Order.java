package edu.nyu.compfin14;

/**
 * Created by anirudhan on 5/8/15.
 */
public interface Order {
    boolean isExecuted();
    boolean isNewOrder();

    void executeOrder();

    OrderAction getAction();

    String getSymbol();

    String getOrderId();

    int getSize();

    Double getLimitPrice();

    void setInvalid();

    void setSymbol(String symbol);

    void changeReplaceToBuyOrSell();

    boolean isValid();

    void setSize(int i);
}
