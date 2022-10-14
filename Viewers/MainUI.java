package Viewers;

import javax.swing.*;
import javax.swing.table.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import Controllers.ITradeResult;
import Controllers.TradeResult;
import Models.Trade;
import Models.TradeDB;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.*;

/**
 * @author Colton Undseth
 * @author Bella Dong
 * @author Joey Gendy
 * @author Scott Guo
 * 
 * The MainUI is the main window that is opened after logging in. This is where the main method is located. 
 * The MainUI consists of a JFrame that contains a histogram, a table of completed trades, and a table of to add brokers.
 * The MianUI also contains a button for adding/removing rows to/from the broker table and performing trades.
 */
public class MainUI implements ActionListener, IMainUI{		
	//an instance of the MainUI for the singleton pattern
	private static IMainUI instance;
	//create main JFrame
	private JFrame frame = new JFrame();
	//make button to add row to broker table
	private JButton addRowButton = new JButton("Add Row");
	//make button to remove row from broker table
	private JButton removeRowButton = new JButton("Remove Row");
	//make button to perform trade
	private JButton performTradeButton = new JButton("Perform Trade");
	//table for brokers to be added to
	private JTable brokerTable;
	//table for completed trade to go into
	private JTable completedTrades;
	//default model for brokerTable
	private DefaultTableModel brokerModel = new DefaultTableModel();
	//default model for completedTrades
	private DefaultTableModel completedTradesModel = new DefaultTableModel() {
		//override the isCellEditable() method from the DefaultTableModel class to disallow all cell editing
		public boolean isCellEditable(int row, int column) {
	        return false;
	    }
	};
	//list of trading strategies for dropdown menu in broker table
	private String[] tradingStrategies = {null, "Strategy A", "Strategy B", "Strategy C", "Strategy D"};
	//combo box is the drop down menu in broker table
	private JComboBox<String> strategyBox = new JComboBox<String>(tradingStrategies);
	//Scroll Pane for the table of completed trades
	private JScrollPane completedTradesScrollPane;
	//scroll pane for the table of brokers
	private JScrollPane brokerScrollPane;
	//a panel for the histogram to be added to once created
	private JPanel histogramPanel = new JPanel();
	//static instance of the dataset to be used by the histogram (must be converted from TradeResult objects to work with histogram)
	private static DefaultCategoryDataset dataset;
	
	/**
	 * @return instance A static instance of the MainUI
	 * Returns a static instance of the MainUI class and limits the number of times the MainUI constructor can be called to 1
	 */
	public static IMainUI getInstance() {
		//singleton pattern for mainUI so only one instance of MainUI exists at a time
		if (instance == null) {
			instance = new MainUI();
		}
		return instance;
	}
	
	/**
	 * Initializes all of the tables, buttons, and JFrame elements and then sets all of their attributes and then adds them all to the main window.
	 */
	private MainUI() {		
		//add column names to broker table
		brokerModel.addColumn("Broker Name");
		brokerModel.addColumn("Coin List");
		brokerModel.addColumn("Trading Strategy");
		
		brokerTable = new JTable(brokerModel);
		//enable row selections
		brokerTable.setRowSelectionAllowed(true);
		//enable cell selections
		brokerTable.setCellSelectionEnabled(true);
		//set color and font attributes for table
		brokerTable.setBackground(Color.white);
		brokerTable.setForeground(Color.black);
		brokerTable.setGridColor(Color.black);
		brokerTable.setSelectionBackground(Color.blue);
		brokerTable.setSelectionForeground(Color.white);
		brokerTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		brokerTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		brokerTable.setRowHeight(20);
		
		//add column names to completed trades table
		completedTradesModel.addColumn("Trader");
		completedTradesModel.addColumn("Strategy");
		completedTradesModel.addColumn("CryptoCoin");
		completedTradesModel.addColumn("Action");
		completedTradesModel.addColumn("Quantity");
		completedTradesModel.addColumn("Price");
		completedTradesModel.addColumn("Date");
		
		completedTrades = new JTable(completedTradesModel);
		//disable row selections
		completedTrades.setRowSelectionAllowed(false);
		//disable cell selections
		completedTrades.setCellSelectionEnabled(false);
		//set color and font attributes for table
		completedTrades.setBackground(Color.white);
		completedTrades.setForeground(Color.black);
		completedTrades.setGridColor(Color.black);
		completedTrades.setSelectionBackground(Color.blue);
		completedTrades.setSelectionForeground(Color.white);
		completedTrades.setFont(new Font("Tahoma", Font.PLAIN, 12));
		completedTrades.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		completedTrades.setRowHeight(20);
		
		//make the column at index 2 (strategy column) a drop down menu
		TableColumn column = brokerTable.getColumnModel().getColumn(2);
	    column.setCellEditor(new DefaultCellEditor(strategyBox));
		
		//make the broker table a scrollable pane
		brokerScrollPane = new JScrollPane(brokerTable);
		brokerScrollPane.setForeground(Color.WHITE);
		brokerScrollPane.setBackground(Color.black);
		brokerScrollPane.setBounds(763, 0, 537, 692);
		
		//set attributes for table of completed trades
		completedTradesScrollPane = new JScrollPane(completedTrades);
		completedTradesScrollPane.setForeground(Color.white);
		completedTradesScrollPane.setBackground(Color.black);
		completedTradesScrollPane.setBounds(0, 0, 757, 361);
		
		//create a button to add rows to broker table
		addRowButton.setBounds(763, 704, 117, 29);
		addRowButton.addActionListener(this);
		
		//create a button to remove rows from the table
		removeRowButton.setBounds(866, 704, 117, 29);
		removeRowButton.addActionListener(this);
		
		//create a button to initialize a trade
		performTradeButton.setBounds(763, 729, 220, 29);
		performTradeButton.addActionListener(this);
		
		//create the histogram panel
		histogramPanel.setBounds(0, 375, 757, 350);
		histogramPanel.setBackground(Color.LIGHT_GRAY);
		
		//set attributes of main JFrame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1300, 800);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setForeground(Color.black);
		frame.getContentPane().setLayout(null);
		
		//add other elements to main JFrame
		frame.getContentPane().add(brokerScrollPane);
		frame.getContentPane().add(addRowButton);
		frame.getContentPane().add(removeRowButton);		
		frame.getContentPane().add(performTradeButton);
		
		//make main JFrame visible
		frame.setVisible(true);
	}

	/**
	 * @param event When an element that has an action listener attached, the source of the event will be tracked so we know what button was pressed
	 * performs appropriate operations depending on which button was pressed
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		//make sure the cell is not being edited before doing any actions
		if (brokerTable.isEditing()) {
		    brokerTable.getCellEditor().stopCellEditing();
		}
		
		//if the addBrokerButton is pushed
		if (event.getSource() == addRowButton) {
			//add another row at the bottom of the table
			brokerModel.insertRow(brokerTable.getRowCount(), new String[] {null, null, null});
			return;
		}
		//if the remove row button is pushed
		else if (event.getSource() == removeRowButton) {
			//find index of the row currently selected (returns -1 if there is no row selected)
			int rowIndex = brokerTable.getSelectedRow();
			//if a row is selected, remove it
			if (rowIndex != -1) {
				brokerModel.removeRow(rowIndex);			
			}
			return;
		}
		//if the perform trade button is pushed
		else if (event.getSource() == performTradeButton) {
			//perform the trade (all back end work to make the TradingBroker objects, execute the strategies, and update the TradeDB)
			//Trade acts as a facade for all of the complex operations that have to happen to perform a trade
			Trade.PerformTrade();
			refreshVisuals();
			return;
		}
	}
	
	/**
	 * Refreshes the table of trades as well as the histogram after a trade is completed
	 */
	private void refreshVisuals() {
		//refresh the table of trades
		refreshTable();
		//refresh the histogram 
		refreshBar();
	}
	
	/**
	 * Uses the database of trades to update the table of completed trades after each time the perform trade button is pressed
	 */
	private void refreshTable() {
		// get the list of trades
		LinkedList<ITradeResult> tradeList = TradeDB.getInstance().getTradeList();
		//if a trade has been completed so far
		if (tradeList.size() > 0) {
			//the index being the number of rows in the table ensures only newly completed trades are added to the table (no trade is added twice)
			int index = completedTrades.getRowCount();
			//start the loop at the number of trades already being displayed in the table
			for (int i = index; i < tradeList.size(); i++) {
				ITradeResult currentResult = tradeList.get(i);
				//insert the trade at the bottom of table with all of the trade info
				
				//price and quantity will be null if there is a failed TradeResult so the price and quantity sections can be left blank
				String quantity = currentResult.getQuantity() != null ? String.format("%,.2f", currentResult.getQuantity()) : "";
				String price = currentResult.getPrice() != null ? String.format("%,.2f", currentResult.getPrice()) : "";
				completedTradesModel.insertRow(completedTrades.getRowCount(), 
						new String[] {
								currentResult.getBroker().getName(), 
								currentResult.getStrategy().toString(), 
								currentResult.getCoin(), 
								currentResult.getBuyOrSell(),
								quantity, 
								price,
								currentResult.getDate()
								});
			}
			// if the table is not showing yet, add it to main window
			if (!completedTradesScrollPane.isShowing()) {
				frame.getContentPane().add(completedTradesScrollPane);
			}
		}
	}
	
	/**
	 * @return DefaultCategoryDataset returns a DefaultCategoryDataset object which is the format of data set that the histogram requires to generate a visual
	 * converts the TradeResult objects in our TradeDB into the DefaultCategoryDataset format so that its usable by the histogram
	 */
	private static DefaultCategoryDataset getDataSet() {
		//create the dataset that the histogram uses based on our list of TradeResult objects
		// use singleton pattern to make sure the dataset is constant throughout program execution
		if (dataset == null) {
			dataset = new DefaultCategoryDataset();
		}
		//get the list of trades from TradeDB				
		LinkedList<ITradeResult> tradeList = TradeDB.getInstance().getTradeList();
		
		HashSet<String> traderNames = new HashSet<String>();
		
		// create a set of all brokers who have completed a trade so far
		for (int i = 0; i < tradeList.size(); i++) {
			traderNames.add(tradeList.get(i).getBroker().getName());
		}
		
		//for every broker who has completed a trade, total the number times they have used each strategy
		for (String name : traderNames) {
			//keeps track of how many times each strategy has been used ([0] = "TradingStrategyA", [1] = "TradingStrategyB", [2] = "TradingStrategyC", [3] = "TradingStrategyD")
			int[] numTimesUsingStrat = {0, 0, 0, 0};
			//loop through the list of trades to count how many times each one has been used
			for (int i = 0; i < tradeList.size(); i++) {
				//to isolate trades performed by each individual trader, only count the trades where the name matches the trader we are counting for
				if (tradeList.get(i).getBroker().getName().equals(name) && 
						//don't count trades that failed
						!tradeList.get(i).getBuyOrSell().equals("Fail")) {
					//total the number of times each trader used each strategy in the array
					if (tradeList.get(i).getStrategy().toString().equals("Strategy A")) {
						numTimesUsingStrat[0]++;
					}
					else if(tradeList.get(i).getStrategy().toString().equals("Strategy B")) {
						numTimesUsingStrat[1]++;
					}
					else if (tradeList.get(i).getStrategy().toString().equals("Strategy C")) {
						numTimesUsingStrat[2]++;
					}
					else if(tradeList.get(i).getStrategy().toString().equals("Strategy D")) {
						numTimesUsingStrat[3]++;
					}
				}
			}
			//set the number of times that the trader used each of the strategies in the dataset
			dataset.setValue(numTimesUsingStrat[0], name, "Strategy A");
			dataset.setValue(numTimesUsingStrat[1], name, "Strategy B");
			dataset.setValue(numTimesUsingStrat[2], name, "Strategy C");
			dataset.setValue(numTimesUsingStrat[3], name, "Strategy D");
		}
		return dataset;
	}
	
	
	/**
	 * Uses the database of trades to generate a DefaultCategoryDataset and then uses it to update the histogram
	 */
	private void refreshBar() {
		//get the list of trades from the tradeDB
		LinkedList<ITradeResult> tradeList = TradeDB.getInstance().getTradeList();
		
		//if there has been at least one trade completed so far
		if (tradeList.size() > 0) {
			//create dataset
			getDataSet();
	
			//this code has been taken from the DataVisualization class in the sample-code files
			CategoryPlot plot = new CategoryPlot();
			BarRenderer barrenderer1 = new BarRenderer();
	
			plot.setDataset(0, dataset);
			plot.setRenderer(0, barrenderer1);
			
			CategoryAxis domainAxis = new CategoryAxis("Strategy");
			plot.setDomainAxis(domainAxis);
			 
			NumberAxis rangeAxis = new NumberAxis("Actions (Buys or Sells)");
			plot.setRangeAxis(rangeAxis);
			
			rangeAxis.setRange(0.0, 20.0);
			
			
			JFreeChart barChart = new JFreeChart("Actions Performed By Traders So Far", new Font("Tahoma", Font.BOLD, 15), plot,true);
	
			ChartPanel chartPanel = new ChartPanel(barChart);
			chartPanel.setPreferredSize(new Dimension(757, 325));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			chartPanel.setBackground(Color.white);
			
			//make sure the histogramPanel is only displaying one graph at a time so remove the graph already showing before updating it
			histogramPanel.removeAll();
			histogramPanel.add(chartPanel);
			
			// if the histogram panel is not yet showing, add it to main window
			if (!histogramPanel.isShowing()) {
				frame.getContentPane().add(histogramPanel);
			}
			histogramPanel.revalidate();
		}
			
	}

	/**
	 * @return brokerTable returns the table of broker information entered by the user
	 * returns the table of broker information entered by the user so that broker objects can be generated from the Trade class
	 */
	@Override
	public JTable getBrokerTable() {
		//returns the table of brokers on the MainUI
		return brokerTable;
	}
	
	/**
	 * opens the LoginForm to start the program
	 */
	public static void main(String[] args) {
		//open the login form the start the program
		LoginForm.getInstance();
	}
}
