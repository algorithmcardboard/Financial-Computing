package edu.nyu.compfin14;

import java.util.*;

/**
 * Created by anirudhan on 5/8/15.
 * An instance of Market order -  An order which is executed at the prevailing
 price at that moment.  No control on the executed price
 */
public class MarketOrder implements Order {

    private final boolean newOrder;
    private int size;
    private double limitPrice;
    private OrderAction action;
    private String orderId;
    private String symbol;
    boolean isExecuted = false;
    private boolean valid = true;

    public MarketOrder(String symbol, String orderId, int size, double limitPrice, OrderAction action) {
        this.newOrder = true;
        this.symbol = symbol;
        this.orderId = orderId;
        this.size = size;
        this.limitPrice = limitPrice;
        this.action = action;
    }

    public MarketOrder(String orderId, int size, double limitPrice, OrderAction action) {
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
    }// check if market order is present for buy or sell

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
     */
    @Override
    public void executeOrder() {
        Set<Map.Entry<Double, LinkedList<Order>>> entrySet;
        TreeMap<Double, LinkedList<Order>> ordersFor = Book.getInstance().getOrdersFor(this);
        if(ordersFor == null){
            return;
        }
        if(action == OrderAction.BUY){
            entrySet = ordersFor.descendingMap().entrySet();
        }else{
            entrySet = ordersFor.entrySet();
        }
        int sizeToConquer = Math.abs(this.size);
        for (Map.Entry<Double, LinkedList<Order>> entry : entrySet) {
            if(entry.getKey().isNaN()){
                continue; // continue if market order.  We can trade market orders only with limit orders
            }
            LinkedList<Order> list = entry.getValue();
            for(int i = 0; i < list.size(); i++){
                if(!list.get(i).isValid()){
                    continue;
                }

                if(sizeToConquer > list.get(i).getSize()){
                    if (list.get(i).getSize() != 0) {
                        String tradedOrderId = list.get(i).getOrderId();
                        printTrade(orderId, tradedOrderId, list.get(i).getSize(), entry.getKey());
                    }
                    sizeToConquer -= list.get(i).getSize();
                    list.remove(i);
                    --i;
                }else if(sizeToConquer < list.get(i).getSize()){
                    String tradedOrderId = list.get(i).getOrderId();
                    printTrade(orderId, tradedOrderId, list.get(i).getSize(), entry.getKey());
                    list.get(i).setSize(list.get(i).getSize() - sizeToConquer);
                    sizeToConquer = 0;
                    break;
                }else{
                    if (list.get(i).getSize() != 0) {
                        String tradedOrderId = list.get(i).getOrderId();
                        printTrade(orderId, tradedOrderId, list.get(i).getSize(), entry.getKey());
                    }
                    sizeToConquer = 0;
                    list.remove(i);
                    break;
                }
            }
            if(sizeToConquer == 0){
                break;
            }
        }

    }

    private void printTrade(String orderId, String tradedOrderId, int size, Double price) {
        System.out.println("  - - - - - - - -- - - - - - - -- ");
        System.out.println("||   Order "  + Math.abs(size) + " " + orderId + " traded with " + tradedOrderId + " @" + price + "  ||");
        System.out.println("  - - - - - - - -- - - - - - - -- ");
    }
}
