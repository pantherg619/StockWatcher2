package com.google.gwt.sample.stockwatcher2.StockWatcher2.client;

public class StockPrice {
	
	private String symbol;
	private double price;
	private double change;
	
	/**
	 * Default Constructor for StockPrice
	 */
	public StockPrice(){
		
	}
	
	/**
	 * Constructor for StockPrice that takes three different parameters the stock
	 * symbol, the price of the stock, and the change in percent.
	 * @param symbol
	 * @param price
	 * @param change
	 */
	public StockPrice(String symbol, double price, double change){
		this.symbol = symbol;
		this.price = price;
		this.change = change;
	}
	
	/**
	 * Gets the value of the stock symbol
	 * @return symbol
	 */
	public String getSymbol(){
		return this.symbol;
	}
	
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public double getChangePercent(){
		return 100.0 * this.change / this.price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public void setChange(double change){
		this.change = change;
	}
	
	public double getChange(){
		return this.change;
	}
}
