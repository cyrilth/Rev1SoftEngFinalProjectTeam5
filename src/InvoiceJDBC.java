

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * This class is used to connect to JDBC to retrieve or set Invoices.
 * @author Team 5
 * @version final
 */
public class InvoiceJDBC 
{
	private static final String USERNAME="dbuser"; //Holds UserName for MySQL Server
	private static final String PASSWORD="enter516"; //Holds PassWord for MySQL Server
	private static final String CONNECT_URL ="jdbc:mysql://192.168.1.18:3306/purchasesystem"; //Holds the path for the MySQL Server. 
	private static Connection conn = null; //conn is used to maintain JDBC Connection so that different methods could call it to perform action. 
	
	/**
	 * This Method creates the connection to MySQL using JDBC
	 * Author: Cyril Thomas
	 */
	public  void getConnection()
	{
		if(conn != null) return;
		
		try
		{
			conn = DriverManager.getConnection(CONNECT_URL, USERNAME, PASSWORD); //Initiates the connection with MySQL
			//System.out.println("Connected: " + conn); //Debug Connection
		} 
		catch (SQLException e) 
		{
			
			System.err.println(e);
		}
		
	}
	
	/**
	 * Author: Cyril Thomas
	 * This Method Closes any Connection from MySQL
	 */
	public  void disConnect()
	{
		try
		{
			if(conn != null)
			{
				conn.close();
				//System.out.println("Connected: " + conn); //Debug
			}  
		} 
		catch(SQLException e)
		{
			System.err.println(e);
		}
	}
	/**
	 * This method retrieves a single Invoice from the Database based on the Invoice ID Num. 
	 * Author : Ernesto Thermidor
	 * @param newInvoiceID : Accepts Invoice ID, that needs to be in SQL parameter
	 * @return invoiceData : Returns a single invoice object.
	 */
	public Invoice getSingleInvoiceFromJDBC(int newInvoiceID)
	{
		
		Invoice invoiceData = new Invoice();
		try 
		{	String gsISQL= "SELECT * FROM InvoiceTable WHERE invoiceNUM = ?"; //SQL Statement
			PreparedStatement s = conn.prepareStatement(gsISQL); //Passing SQL statement to PreparedStatement
			s.setInt(1, newInvoiceID); //Passing the Invoice ID to the SQL using the set method. 
			ResultSet rs = s.executeQuery(); //passing the result to ResultSet which are rows. 
			
			while(rs.next()) //Creates an Invoice object 
			{
				invoiceData.setInvoiceNum(rs.getInt("invoiceNum"));
				invoiceData.setDateOfOrder(rs.getString("DateOfOrder"));
				invoiceData.setPrice(rs.getDouble("Price"));
				invoiceData.setCustomerInfo(rs.getString("CustomerInfo"));
				invoiceData.setQuantity(rs.getInt("Quantity"));
				invoiceData.setPaidDate(rs.getString("paidDate"));
				invoiceData.setStatus(rs.getString("Status"));
				invoiceData.setCompanyCode(rs.getString("CompanyCode"));
			}
		} 
		catch (Exception e) 
		{
			System.err.println(e);
		}
		return invoiceData;
	}
	/**
	 * This method returns multiple invoice list based on the select parameter. 
	 * Author: Ernesto Thermidor
	 * @param select : An integer parameter to used in switch statement. 
	 * 				   Allowed Values For Select are: 
	 * 				   (1) select = 1 For Returning All The invoices in database.
	 * 				   (2) select = 2 For Returning Pending Invoices
	 * 				   (3) select = 3 For Returning Paid Invoices.
	 * 				   (4) select = 4 For Returning paid Invoices Older than the Current Days. Used in combination with the Parameter Days.
	 * @param Days : An integer parameter to hold days used in combination when Parameter select = 4
	 * @return invoiceData : Returns an Array List of invoices 
	 */
	public ArrayList<Invoice> getInvoiceListFromJDBC(int select, int Days)
	{
		ArrayList<Invoice> invoiceData = new ArrayList<Invoice>();
		Statement stmt ;
		ResultSet rs = null;
		try
		{
			stmt = conn.createStatement();
			
			switch (select) 
			{
				case 1:
					String disAllSql = "SELECT * FROM InvoiceTable ORDER BY invoiceNum DESC"; //SQL Statement
					rs = stmt.executeQuery(disAllSql);
					break;
				case 2:
					String disSPenSql = "SELECT * FROM InvoiceTable WHERE STATUS ='Pending' ORDER BY 'invoiceNUM' ASC";//SQL Statement
					rs = stmt.executeQuery(disSPenSql);
					break;
				case 3:
					String disSPaSql = "SELECT * FROM InvoiceTable WHERE STATUS ='Paid' ORDER BY 'invoiceNUM' ASC";//SQL Statement
					rs = stmt.executeQuery(disSPaSql);
					break;
				case 4:
					String disPDSql ="SELECT * FROM InvoiceTable WHERE paidDate <= (CURDATE() - INTERVAL ? DAY)";//SQL Statement
					PreparedStatement prepStmt = conn.prepareStatement(disPDSql);
					prepStmt.setInt(1, Days);
					rs = prepStmt.executeQuery();
					break;
				default:
					break;
			}
			
			while(rs.next()) //Creates The invoice object and adds it to ArrayList as long as there is rows in the resultset rs. 
			{
				Invoice newInvoice = new Invoice();
				newInvoice.setInvoiceNum(rs.getInt("invoiceNum"));
				newInvoice.setDateOfOrder(rs.getString("DateOfOrder"));
				newInvoice.setPrice(rs.getDouble("Price"));
				newInvoice.setCustomerInfo(rs.getString("CustomerInfo"));
				newInvoice.setQuantity(rs.getInt("Quantity"));
				newInvoice.setPaidDate(rs.getString("paidDate"));
				newInvoice.setStatus(rs.getString("Status"));
				newInvoice.setCompanyCode(rs.getString("CompanyCode"));
				
				invoiceData.add(newInvoice);
			}
		}
		catch(SQLException e)
		{
			System.err.println(e);
		}
		return invoiceData;
		
	}
	
	/**
	 * This methods creates Invoices from Orders Database
	 * Author : Cyril Thomas
	 * @param newDate : Accept String Date in format of "yyyy-MM-dd" and pass that value to the SQL parameter. 
	 * @return -1 if failed or rsInt the # of row created. 
	 */
	public int createInvoiceFromOrderList(String newDate)
	{
		try 
		{
			String createSQL = "INSERT INTO InvoiceTable( invoiceNum, DateOfOrder, Price, CustomerInfo, Quantity, CompanyCode )"
								+ 	" SELECT OrderID, OrderDate, OrderPrice, CustomerInfo, ItemQuantity, PaymentInfo"
								+	" FROM OrdersTable"
								+	" WHERE OrdersTable.PaymentInfo LIKE 'C%'"
								+	" AND OrdersTable.OrderDate >= ?"
								+	" AND NOT EXISTS" 
								+ 	" (SELECT InvoiceNum FROM InvoiceTable WHERE InvoiceNum = OrderID)";
			PreparedStatement s = conn.prepareStatement(createSQL);
			s.setString(1, newDate);
			int rsInt = s.executeUpdate();
			return rsInt;
		} 
		catch (SQLException e)
		{
			System.err.println(e);
		}
		return -1;
	}
	/**
	 * This method updates an existing invoice attribute in database. 
	 * Author: Cyril Thomas
	 * @param newInvoice : Accept an Invoice Object that hold current or new Invoice values for each attributes. 
	 * @return affected : Returns the # of rows affected. 
	 */
	public int UpdateInvoiceJDBC(Invoice newInvoice)
	{
		int affected = 0;
		String updateSQL =  "UPDATE InvoiceTable SET " +
							 " InvoiceTable.Price = ?, InvoiceTable.CustomerInfo = ?, InvoiceTable.Quantity = ?, InvoiceTable.paidDate = ?, InvoiceTable.Status = ? " +
							 " WHERE InvoiceTable.invoiceNum = ? "	;
		try 
		{
			PreparedStatement upStmt = conn.prepareStatement(updateSQL);
			//Extracting the new Invoice data and passing that to the SQL statement. 
			upStmt.setDouble(1, newInvoice.getPrice());
			upStmt.setString(2, newInvoice.getCustomerInfo());
			upStmt.setInt(3, newInvoice.getQuantity());
			upStmt.setString(4, newInvoice.getPaidDate());
			upStmt.setString(5, newInvoice.getStatus());
			upStmt.setInt(6, newInvoice.getInvoiceNum());
			
			affected = upStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.err.println(e);
		}
		return affected;
	}
	/**
	 * This method deletes Older Invoices determined by how old the paid dates of the Invoices are. 
	 * Author : Kahliik Burrell
	 * @param Days : Integer Days that is used to determine old invoices and pass the value to SQL Parameter. 
	 * @return rowsaffected: Returns how many Invoices where deleted
	 */
	public int deleteInvoiceJDBC(int Days)
	{
		int rowsaffected = 0;
		String deleteSQL = "DELETE FROM InvoiceTable WHERE paidDate <= (CURDATE() - INTERVAL ? DAY)";
		
		try 
		{
			PreparedStatement deStmt = conn.prepareStatement(deleteSQL);
			deStmt.setInt(1, Days);
			rowsaffected = deStmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.err.println(e);
		}
		return rowsaffected;
	}
}
