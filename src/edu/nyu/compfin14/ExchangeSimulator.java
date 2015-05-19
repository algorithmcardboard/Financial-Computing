package edu.nyu.compfin14;

import java.util.Iterator;

/**
 * Created by anirudhan on 5/6/15.
 */
public class ExchangeSimulator {
    public static void main(String[] args){

        ExchangeSimulator simulator = new ExchangeSimulator();

        OrdersIterator orders = new OrdersIterator();
        Iterator<Message> itr = orders.getIterator();
        boolean printBest = true; // boolean to print the best bid in the top of ask & bid books

        while(itr.hasNext()){
            Message msg = itr.next();
            Order order = null;
            OrderAction action = OrderAction.BUY;
            if(msg instanceof OrderCxR){
                OrderCxR cxR = (OrderCxR) msg;
                action = OrderAction.REPLACE;
                /*if(cxR.getSize() < 0){
                    action = OrderAction.SELL;
                }else if(cxR.getSize() == 0){
                    action = OrderAction.REPLACE;
                }*/
                if(Double.isNaN(cxR.getLimitPrice())){
                    order = new MarketOrder(cxR.getOrderId(), cxR.getSize(), cxR.getLimitPrice(), action);
                }else{
                    order = new LimitOrder(cxR.getOrderId(), cxR.getSize(), cxR.getLimitPrice(), action);
                }
            }
            if(msg instanceof NewOrder){
                NewOrder nOrder = (NewOrder)msg;
                if(nOrder.getSize() < 0){
                    action = OrderAction.SELL;
                }
                if(Double.isNaN(nOrder.getLimitPrice())){
                    order = new MarketOrder(nOrder.getSymbol(), nOrder.getOrderId(), nOrder.getSize(), nOrder.getLimitPrice(), action);
                }else{
                    order = new LimitOrder(nOrder.getSymbol(), nOrder.getOrderId(), nOrder.getSize(), nOrder.getLimitPrice(), action);
                }
            }
            if(order == null){
                System.out.println("Invalid message.  Continuing to consume next message.");
                continue;
            }
            simulator.processOrder(order);
            if(printBest){
                Book.getInstance().printBestBids(order.getSymbol());
            }
        }
    }

    /**
     * The entry point for processing an order
     * @param order
     */
    private void processOrder(Order order) {
        if(order.getAction() != OrderAction.REPLACE){
//            System.out.println("Executing a new order");
            order.executeOrder();
        }
        if(order.isExecuted()){
            return; //in case the new order is successfully executed we need not do anything
        }
        if(order.getAction() == OrderAction.REPLACE){
            String symbol = Book.getInstance().remove(order.getOrderId());
            order.setSymbol(symbol);
            order.changeReplaceToBuyOrSell();
        }
        if(order.getSize() != 0){
//            System.out.println("Adding to book");
            Book.getInstance().push(order);
        }
    }
}
