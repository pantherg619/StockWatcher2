package com.google.gwt.sample.stockwatcher2.StockWatcher2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.Random;
import com.google.gwt.i18n.client.*;
import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.ui.Image;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StockWatcher2 implements EntryPoint {
	private VerticalPanel mainPanel;
	private FlexTable stocksflexTable;
	private HorizontalPanel addPanel;
	private TextBox newSymbolTextBox;
	private Button addButton;
	private Label lastUpdateLabel;
	private ArrayList <String> stocks = new ArrayList<String>();
	private static final int REFRESH_INTERVAL = 5000; 
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("stockList");
		rootPanel.setStyleName("rootPanel");
		
		mainPanel = new VerticalPanel();
		rootPanel.add(mainPanel, 10, 0);
		mainPanel.setSize("304px", "224px");
		
		Image image = new Image("images/GoogleCode.png");
		mainPanel.add(image);
		
		Label labelStockWatcher = new Label("Stock Watcher");
		labelStockWatcher.setStyleName("gwt-Label-StockWatcher");
		mainPanel.add(labelStockWatcher);
		
		stocksflexTable = new FlexTable();
		stocksflexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		stocksflexTable.setCellPadding(6);
		stocksflexTable.setStyleName("watchList");
		stocksflexTable.getCellFormatter().addStyleName(0, 1, "watchListN");
		stocksflexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
		stocksflexTable.setText(0, 0, "Symbol");
		stocksflexTable.setText(0, 1, "Price");
		stocksflexTable.setText(0, 2, "Change");
		stocksflexTable.setText(0, 3, "Remove");
		
		mainPanel.add(stocksflexTable);
		stocksflexTable.setWidth("222px");
		
		addPanel = new HorizontalPanel();
		mainPanel.add(addPanel);
		
		newSymbolTextBox = new TextBox();
		newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER){
					addStock();
				}
			}
		});
		addPanel.add(newSymbolTextBox);
		
		addButton = new Button("New button");
		addButton.setStyleName("gwt-Button-Add");
		addButton.setHeight("25px");
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addStock();
			}
		});
		addButton.setText("Add");
		addPanel.add(addButton);
		
		lastUpdateLabel = new Label("New label");
		mainPanel.add(lastUpdateLabel);
		// setup timer to refresh list automatically
		Timer refreshTimer = new Timer() {
			public void run()
			{
				refreshWatchList();
			}
		};
		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	}
	
	protected void refreshWatchList() {
		// TODO Auto-generated method stub
		final double MAX_PRICE = 100.0;
		final double MAX_PRICE_CHANGE = 0.02;
		
		StockPrice[] prices = new StockPrice[stocks.size()];
		for (int i = 0; i < stocks.size(); i++)
		{
			double price = Random.nextDouble() * MAX_PRICE;
			double change = price * MAX_PRICE_CHANGE *
			(Random.nextDouble() * 2.0 - 1.0);
			
			prices[i] = new StockPrice((String) stocks.get(i), price, change);
		}
		updateTable(prices);
	}

	@SuppressWarnings("deprecation")
	private void updateTable(StockPrice[] prices) {
		// TODO Auto-generated method stub
		for (int i = 0; i < prices.length; i++)
		{
			updateTable(prices[i]);
		}
		
		// change the last update timestamp
		lastUpdateLabel.setText("Last update : " + 
				DateTimeFormat.getMediumDateTimeFormat().format(new Date()));
	}
	
	

	private void updateTable(StockPrice stockPrice) {
		// TODO Auto-generated method stub
		if (!stocks.contains(stockPrice.getSymbol())){
			return;
		}
		
		int row = stocks.indexOf(stockPrice.getSymbol()) + 1;
		
		// Format the data in the Price and Change fields.
		String priceText = NumberFormat.getFormat("#,##0.0").format(stockPrice.getPrice());
		NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.0;-#,##0.0");
		String changeText = changeFormat.format(stockPrice.getChange());
		String changePercentText = changeFormat.format(stockPrice.getChangePercent());
		
		// Populate the Price and Change fields with new data.
		stocksflexTable.setText(row, 1, priceText);
		stocksflexTable.setText(row, 2, changeText + "(" + changePercentText + "%)");
	}

	/**
	 * Creates a method that display stock when button is clicked or when
	 * the enter button is selected.
	 */
	private void addStock(){
		final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
		newSymbolTextBox.setFocus(true);
		
		// Stock code must be between 1 and 10 chars that are numbers, letters. or dots
		
		if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
			Window.alert("'" + symbol + "' is not a valid symbol.");
			newSymbolTextBox.selectAll();
			return;
		}
		
		newSymbolTextBox.setText("");
		
		// Don't add the stock if it's already in the table.
		if (stocks.contains(symbol))
			return;
		// TODO Add the stock to the table.
		int row = stocksflexTable.getRowCount();
		stocks.add(symbol);
		stocksflexTable.setText(row, 0, symbol);
		
		// TODO Add a button to remove this stock from the table.
		Button removeStock = new Button("x");
		removeStock.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int removeIndex = stocks.indexOf(symbol);
				stocks.remove(removeIndex);
				stocksflexTable.removeRow(removeIndex + 1);
			}
		});
		stocksflexTable.setWidget(row, 3, removeStock);
		// TODO Get the stock price.
		
	}
	public FlexTable getStocksflexTable() {
		return stocksflexTable;
	}
}
