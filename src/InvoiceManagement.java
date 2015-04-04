

import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;
/**
 * This Class is to manage and perform operation on the single or multiple invoices.
 * @author Cyril Thomas
 * @version Final
 */
public class InvoiceManagement 
{
	private InvoiceJDBC jdbcInvoice = new InvoiceJDBC();
	/**
	 * Invoke Static JDBC getConnection method. 
	 */
	public InvoiceManagement()
	{	
		jdbcInvoice.getConnection();
	}
	/**
	 * This Method is used to Call a Single Invoice by the Invoice ID Number. 
	 * @param newInvoiceID : Accept A Single Invoice ID and Pass it to Invoice JDBC Class getSingleInvoiceFromJDBC(newInvoiceID) 
	 * @return singleInvoices : Returns a Single Invoice back to the Caller. 
	 */
	public Invoice getInvoiceSingle(int newInvoiceID)
	{
		Invoice singleInvoice = jdbcInvoice.getSingleInvoiceFromJDBC(newInvoiceID);
		return singleInvoice;
	}
	/**
	 * This Method is used to Call an Array List of Invoices.
	 * @param select : An integer parameter to pass the value to JDBC getInvoiceList(select,days).
	 * 				   Allowed Values For Select are: 
	 * 				   (1) select = 1 For Returning All The invoices in database.
	 * 				   (2) select = 2 For Returning Pending Invoices
	 * 				   (3) select = 3 For Returning Paid Invoices.
	 * 				   (4) select = 4 For Returning paid Invoices Older than the Current Days. Used in combination with the Parameter Days.
	 * @param Days : An integer parameter to hold days used in combination when Parameter select = 4.
	 * @return InvoiceList : Returns an Array List of Invoices. 
	 */
	public ArrayList<Invoice> getInvoiceList(int select, int Days)
	{
		ArrayList<Invoice> InvoiceList =jdbcInvoice.getInvoiceListFromJDBC(select, Days);
		return InvoiceList;
	}
	/**
	 * This methods creates Invoices from Orders Database
	 * @param newDate : Accept String Date in format of "yyyy-MM-dd" and pass that value to JDBC Create Invoice Method
	 * @return rowsInt : Returns the # of new Rows which are Invoices Created. 
	 */
	public int createInvoices(String newDate)
	{
		int rowsInt = jdbcInvoice.createInvoiceFromOrderList(newDate);
		return rowsInt;
	}
	/**
	 * This method updates existing values of an already existing Invoices. 
	 * @param newInvoice : Accepts a single Invoice Object with values that are passed to JDBC Update Class. 
	 * @return boolean : true if successful or false if the update fails. 
	 */
	public boolean updateInvoice(Invoice newInvoice)
	{
		
		int successInt = jdbcInvoice.UpdateInvoiceJDBC(newInvoice);
		if(successInt == 1)
			return true;
		else
			return false;
	}
	/**
	 * This method deletes Older Invoices determined by how old the paid dates of the Invoices are. 
	 * @param Days : Integer Days that is used to determine old invoices and pass the value to JDBC Delete method. 
	 * @return rowsaffected: Returns how many Invoices where deleted
	 */
	public int deleteInvoice(int Days)
	{
		int rowsaffected = 0;
		rowsaffected = jdbcInvoice.deleteInvoiceJDBC(Days);
		return rowsaffected;
	}
}
