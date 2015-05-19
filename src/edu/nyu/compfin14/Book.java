package edu.nyu.compfin14;

import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;

import java.util.*;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by anirudhan on 5/6/15.
 */
public class Book {
    /**
     * The main data structure of book object.  Contains the list of symbol to <price, orders> pair
     */

    /**
     * ask book is for sell orders1
     */
    private Map<String, TreeMap<Double, LinkedList<Order>>> askBook = new HashMap<String, TreeMap<Double, LinkedList<Order>>>();

    /**
     * bid book for buy orders
     */
    private Map<String, TreeMap<Double, LinkedList<Order>>> bidBook = new HashMap<String, TreeMap<Double, LinkedList<Order>>>();
    private Map<String, Order> lookupTable = new HashMap<String, Order>();

    private static final Book book = new Book();

    public static Book getInstance() {
        return book;
    }

    /**
     * This method is used to insert orders into the book.  It inserts based on the type of order that is received.
     * The method selects ask/bid book based on the type of order
     * @param order
     */
    public void push(Order order) {
//        System.out.println("pushing to book");
        Map<String, TreeMap<Double, LinkedList<Order>>> bookToInsert = bidBook;

        /*if(order.getSymbol() == null || order.getSymbol().length() == 0){
            order = lookupTable.get(order.getOrderId());
        }*/

        if(order.getAction() == OrderAction.SELL){
            bookToInsert = askBook;
        }
        // if symbol is not present, create the symbol and the related values
        if(!bookToInsert.containsKey(order.getSymbol())){
            bookToInsert.put(order.getSymbol(), new TreeMap<Double, LinkedList<Order>>());
        }
        TreeMap<Double, LinkedList<Order>> priceToOrders = bookToInsert.get(order.getSymbol());
        if(!priceToOrders.containsKey(order.getLimitPrice())){
            priceToOrders.put(order.getLimitPrice(), new LinkedList<Order>());
        }
        priceToOrders.get(order.getLimitPrice()).add(order);
//        System.out.println(priceToOrders.get(order.getLimitPrice()).size());
        lookupTable.put(order.getOrderId(), order);
    }

    /**
     * Looks up the order and set the order to be invalid
     * @param orderId
     * @return The symbol of the order which was removed
     */
    public String remove(String orderId){
        if(!lookupTable.containsKey(orderId)){
            return "";
        }
        lookupTable.get(orderId).setInvalid();
        return lookupTable.get(orderId).getSymbol();
    }

    public TreeMap<Double, LinkedList<Order>> getOrdersFor(Order marketOrder) {
        if(!marketOrder.getLimitPrice().isNaN()){
            return null; // The input order is not a market order.
        }
        Map<String, TreeMap<Double, LinkedList<Order>>> bookToUse = bidBook;
        if(marketOrder.getAction() == OrderAction.BUY){
            bookToUse = askBook;
        }
        return bookToUse.get(marketOrder.getSymbol());
    }

    public boolean hasMarketOrder(String symbol, OrderAction action) {
        if(action == OrderAction.SELL){
            return bidBook.containsKey(symbol) && bidBook.get(symbol).containsKey(Double.NaN);
        }
        return askBook.containsKey(symbol) && askBook.get(symbol).containsKey(Double.NaN);
    }

    public List<Order> getOrdersForPrice(String symbol, Double limitPrice, OrderAction action) {
        if(action == OrderAction.SELL){
//            System.out.println("Size in bid book is " +bidBook.get(symbol).size());
//            System.out.println("Val is "+bidBook.get(symbol).get(limitPrice));
            return bidBook.get(symbol).get(limitPrice);
        }
        return askBook.get(symbol).get(limitPrice);
    }

    public Map<String, TreeMap<Double, LinkedList<Order>>> getBookFor(OrderAction action) {
        if(action == OrderAction.SELL){
            return bidBook;
        }
        return askBook;
    }

    public void printBestBids(String symbol) {
        System.out.println("Message for:   " + symbol);
        System.out.println("Best of the ask book:");
        if(askBook.get(symbol) != null){
            printBook(askBook.get(symbol).firstEntry().getValue(), symbol, "ask");
        }

        System.out.println("Message for:   " + symbol);
        System.out.println("Best of the bid book:");
        if(bidBook.get(symbol) != null) {
            printBook(bidBook.get(symbol).lowerEntry(Double.NaN).getValue(), symbol, "bid");
        }
    }

    private void printBook(LinkedList<Order> orders, String symbol, String bookName){
//        priceToOrders.firstEntry()
        StringBuffer sb = new StringBuffer();
        for(Order order: orders){

            if(!order.isValid()){
                continue;
            }
            sb.append(symbol);
            sb.append(" ");
            sb.append(Math.abs(order.getSize()));
            sb.append(" x ");
            sb.append(String.format("%.2f", order.getLimitPrice()));
            sb.append(" "+bookName+" ");
            sb.append("\n");
            break;
        }
        System.out.println(sb);
        /*
        for (Map.Entry<String, TreeMap<Double, LinkedList<Order>>> entry: askBook.entrySet()) {
            StringBuffer sb = new StringBuffer();
            // Skip dead order and only prints live order.
            for (Order order: entry.getValue().firstEntry().getValue()) {
                if (Math.abs(order.getSize()) > 0 && order.getSymbol() == orderfor) {
                    sb.append(entry.getKey());
                    sb.append(" ");
                    sb.append(Math.abs(order.getSize()));
                    sb.append(" x ");
                    sb.append(String.format("%.2f", order.getLimitPrice()));
                    sb.append(" ask ");
                    sb.append("\n");
                    break;
                }

            }
            System.out.println(sb);
        }*/
    }
}