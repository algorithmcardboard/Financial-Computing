package edu.nyu.compfin14;

import java.util.*;

/**
 * Created by anirudhan on 5/8/15.
 * An instance of limit order -  An order which is executed at a certain price or
 better.  Not guaranteed to be executed
 */
public class LimitOrder implements Order {

    boolean isExecuted = false;
    private int size;
    private double limitPrice;
    private OrderAction action;
    private String orderId;
    private String symbol;
    private boolean newOrder = false;
    private boolean valid = true;

    public LimitOrder(String symbol, String orderId, int size, double limitPrice, OrderAction action) {
        this.newOrder = true;
        this.symbol = symbol;
        this.orderId = orderId;
        this.size = size;
        this.limitPrice = limitPrice;
        this.action = action;
    }

    public LimitOrder(String orderId, int size, double limitPrice, OrderAction action) {
        this.newOrder = false;
        this.orderId = orderId;
        this.size = size;
        this.limitPrice = limitPrice;
        this.action = action;
    }

    @Override
    public boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public boolean isNewOrder() {
        return newOrder;
    }

    @Override
    public OrderAction getAction() {
        return action;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Double getLimitPrice() {
        return limitPrice;
    }

    @Override
    public void setInvalid() {
        this.valid = false;
        this.size = 0;
    }

    /**
     * You can change order only when the order is a new order
     * @param symbol
     */
    @Override
    public void setSymbol(String symbol) {
        if(this.symbol == null || this.symbol.length() == 0) {
            this.symbol = symbol;
        }
    }

    /**
     * Change from replace to buy only after removing from book.
     */
    @Override
    public void changeReplaceToBuyOrSell() {
        OrderAction action = OrderAction.BUY;
        if(this.size < 0){
            action = OrderAction.SELL;
        }
        if(this.action == OrderAction.REPLACE){
            this.action = action;
        }
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setSize(int i) {
        this.size = i;
    }

    /**
     * Tries to execute an order.  If execution is successful, it sets isExecuted to true in order object.
     * If execution is not successful, it pushes the order to ask or bid book
     * // check if market order is present for buy or sell
     */
    @Override
    public void executeOrder() {
        int sizeToConquer = Math.abs(this.size);
        if(Book.getInstance().hasMarketOrder(this.symbol, this.action)){
//            System.out.println("has market order");
            if(this.action == OrderAction.SELL){
                sizeToConquer = executeLimitOrder(Double.NaN, sizeToConquer);
            }else if(this.action == OrderAction.BUY){
                sizeToConquer = executeLimitOrder(Double.NaN, sizeToConquer);
            }
        }
        if(sizeToConquer <= 0){
//            System.out.println("returning due to zero size");
            isExecuted = true;
            return;
        }

        if(Book.getInstance().getBookFor(action).get(symbol) == null){
//            System.out.println("\n returning as symbol is null \n");
            return;
        }
        SortedSet<Double> candidatePrices =
                Book.getInstance().getBookFor(action).get(symbol).navigableKeySet().tailSet(limitPrice);
        for (Double canPrice: candidatePrices) {
            if (!canPrice.isNaN()) {
//                System.out.println("Executing for price "+canPrice);
                sizeToConquer = executeLimitOrder(canPrice.doubleValue(), sizeToConquer);
                if (sizeToConquer == 0) {
                    isExecuted = true;
                    break;
                }
            }
        }
    }

    private int executeLimitOrder(Double price, int sizeToConquer) {
        List<Order> list = Book.getInstance().getOrdersForPrice(symbol, price, action);
//        System.out.println("Executing limit order");
        if(list == null){
//            System.out.println("returning as list is null");
            return sizeToConquer;
        }
        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).isValid()){
//                System.out.println("returning as invalid");
                continue;
            }

            if(sizeToConquer > list.get(i).getSize()){
                if (list.get(i).getSize() != 0) {
                    String tradedOrderId = list.get(i).getOrderId();
                    printTrade(orderId, tradedOrderId, list.get(i).getSize(), limitPrice);
                }
                sizeToConquer -= list.get(i).getSize();
                list.remove(i);
                --i;
            }else if(sizeToConquer < list.get(i).getSize()){
                String tradedOrderId = list.get(i).getOrderId();
                    printTrade(orderId, tradedOrderId, list.get(i).getSize(), limitPrice);
                list.get(i).setSize(list.get(i).getSize() - sizeToConquer);
                sizeToConquer = 0;
                break;
            }else{
                if (list.get(i).getSize() != 0) {
                    String tradedOrderId = list.get(i).getOrderId();
                        printTrade(orderId, tradedOrderId, list.get(i).getSize(), limitPrice);
                }
                sizeToConquer = 0;
                list.remove(i);
                break;
            }
        }
        return sizeToConquer;
    }

    private void printTrade(String orderId, String tradedOrderId, int size, Double price) {
        System.out.println("  - - - - - - - -- - - - - - - -- ");
        System.out.println("||   Order "  + Math.abs(size) + " " + orderId + " traded with " + tradedOrderId + " @" + price + "  ||");
        System.out.println("  - - - - - - - -- - - - - - - -- ");
    }
}
