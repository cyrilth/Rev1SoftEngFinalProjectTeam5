import java.util.ArrayList; //allows us to store and manipulate data within an array list
import java.util.Scanner; //allows input output from the console 

/**
 * This Class is used to link to other console interfaces to perform operations. 
 * @author Ernesto Thermidor
 * @version Final
 */

public class InvoiceInterface 
{
	/**
	 * This is an User Interaction method.
	 */
	public void InvoiceDispaly()
	{
		boolean loopcounter = true; //instantiate the loop counter to true 
		boolean check = true; // instantiate the check variable to true 
		boolean success = false; // instantiate success to false because, it will not be successful intitially 
		InvoiceManagement displayInvoice = new InvoiceManagement(); //this will allow us to display theinvoice that is retieved from the invoiceManagement class
		ArrayList<Invoice> data = new ArrayList<Invoice>(); //create a new arraylist variable to hold th einvoices retireved from the managaement class
		Scanner scan = new Scanner(System.in); //used to read in information from the user
		int year = 0; //this willl hold the year attribute from the invoice
		int month = 0; //this willl hold the month attribute from the user
		int day = 0; // this will hold the day attribute from the invoice
		String lookDate = "2001-01-01"; //holds the whole date variable
		
	
		do
		{
			try
			{
				//menu to the user.
				System.out.println("\t\t\t\t Welcome to Invoice Management");
				System.out.println();
				System.out.println("(1) View Invoice & Update Invoice."); //view invoice will give the user options on what invoices(if not all) to look at
				System.out.println("(2) Create Invoice.");	//create inovice will create an invoice for the user 
				System.out.println("(3) Delete Invoice."); //delete invoice will remove and display  the removed invoice
				System.out.println("(0) To Back.");
				System.out.print("Enter (1,2,3,0): ");
				int input1 = scan.nextInt(); //instantiates the choice variable which is read in the selected option from the user
				switch (input1) 
				{
					case 1: //if user wants to view the invoice....selects option 1
					{	
						do
						{
							//menu to user for view invoice
							System.out.println("\n(1) View Invoices by Specfic ID & Update Invoice"); //asks if the user wants to search by invoice id
							System.out.println("(2) View All Invoices From New to Old"); //asks to display all invoices from recent to old
							System.out.println("(3) View Pending Invoices"); //asks if the user wants to display all pending invoices
							System.out.println("(4) View Paid Invoices"); //asks the user if they want to see all paid invoices
							System.out.println("(0) To Back"); //asks the user if they want to take one step back
							System.out.print("Enter(1,2,3,0): ");
							int input2 = scan.nextInt(); //
							
							switch (input2) 
							{
								case 1: //user sleects to view invoice by th einvoice id
									System.out.print("\nPlease Enter the Invoice ID: ");
									int invoiceID = scan.nextInt(); //reads in invoice id from the user
									data.add(displayInvoice.getInvoiceSingle(invoiceID)); //passes the positive number and asks for the invoice id
									if(data.size()<1) //if the size of data(theinvoice list) is less than 1
										System.out.println("\nNo Invoice Found By that ID!!!");
									else //which means there is an invoice found
										for(Invoice i : data) //for each invoice in data
										{
											System.out.println("-----------------------------------------");
											System.out.println(i.toString());
											System.out.println("-----------------------------------------");
										}
									System.out.println("\nDo you want to Update the Above Invoice(Enter (1) to Update (0) to Go Back)"); //asks user if they want to update the invoice
									System.out.print("Enter: ");
									int subInput1 = scan.nextInt(); //reads in update choice from user
									if (subInput1 == 1) //if the choice is to update the invoice
									{
										boolean looper = true; //intial variable
										
										//loop test
										while(looper) //while looper is true
										{
											System.out.println("\nWhat do you want to Update: ");
											System.out.println("[Price(1); CustomerInfo(2); Quantity(3); Paid Date(4); Status(5)]; To Go Back(0)"); //asks user what about the invoice do they want to update
											System.out.print("Enter:");
											int subInput2 = scan.nextInt(); //reads in the update choice from the user
											
											switch(subInput2) //depending on which choice they pick to update
											{
												case 1: //price choice
													System.out.println("\nCurrent Price: " + data.get(0).getPrice()); //displays the current price
													System.out.print("\nEnter New Price OR (-1) to keep current Values: ");
													double subSubInput1 = scan.nextDouble(); //reads in new price
													if(!(subSubInput1 == -1.0))
														data.get(0).setPrice(subSubInput1); //sets new price to invoice
													break;
												case 2: //customer info
													scan.nextLine();
													System.out.println("\nCurrent Customer Info:\n " + data.get(0).getCustomerInfo()); //displays the customer's info to the user
													System.out.print("\nEnter New Customer Info OR (-1) to keep current Values: ");
													String subSubInput2 = scan.nextLine(); //reads in the new updated information
													if(!(Integer.parseInt(subSubInput2) == -1))
														data.get(0).setCustomerInfo(subSubInput2); //sets the new updated information to the invoice
													break;
												case 3: //quantity choice
													System.out.println("\nCurrent Quantity: "+ data.get(0).getQuantity()); //displays the current quantity to the user
													System.out.print("\nEnter New Quantity OR (-1) to keep current Values: ");
													int subSubInput3 = scan.nextInt(); //reads in the quantity from the user
													if(!(subSubInput3 == -1))
														data.get(0).setQuantity(subSubInput3); //sets the new quantity 
													break;
												case 4: //paid date choice
													scan.nextLine();
													System.out.println("\nCurrent Paid Date: " + data.get(0).getPaidDate()); //displays paid date
													while(true)
													{
														System.out.print("\nPlease Enter Year in 4 digits(yyyy) OR (-1) to keep current Values: ");
														year = scan.nextInt(); //reads in the year from user
														if(year == -1) //Added this code by Cyril for early exist from the loop
															break;
														System.out.print("\nPlese Enter Month in 2 digits(MM): ");
														month = scan.nextInt(); //reads in the  month from the user
														System.out.print("\nPlease Enter Day in 2 digits(dd): ");
														day = scan.nextInt(); //reads in the day from the user
														//-----------------------DATE VALIDATION----------------------
														/*
														 * 
														 * if th eyear is after 1900, 
														 * the month must be between 1-12(included)
														 * and the days shall not pass 31
														 *  Are all in correct format then update invoice
														 * */
														if(year >= 1900 && month >= 01 && month <= 12 && day >00 && day <= 31)
														{
															lookDate =  String.valueOf(year) +"-"+String.valueOf(month)+"-"+String.valueOf(day);
															break;
														}//end if year
														else //the date is in the wrong format
														{	
															System.out.println("Wrong Date Input Please ReEnter!!!");
															System.out.print("\nDo you want to ReEnter the date If Yes Enter 1 or 0 to Quit"); //asks user if they want to quit
															int cont1 = scan.nextInt(); //reads in quit choice
															if(cont1 != 1) //if they do quit, leave loop
																break;
														}//end else
													}
													data.get(0).setPaidDate(lookDate); //set the new date to the invoice
													break;
												case 5: //status choice
													scan.nextLine();
													System.out.println("\nCurrent Status: " + data.get(0).getStatus()); //display the current status off the invoices to the user
													System.out.print("\nEnter New Status (1) Paid (2) Pending (3) Cancelled OR Leave Blank to Go Back: " ); //asks userwhat status they ware looking for
													int subSubInput5 = scan.nextInt(); //read in the status choice from the user
													if(subSubInput5 == 1)//if the user wants to update to paid invoice
													{
														data.get(0).setStatus("Paid"); //set invocie status to paid
													}//end if
													else if(subSubInput5 == 2) //if user wants to  update to pending invocie
													{
														data.get(0).setStatus("Pending"); //set invocie status to pending
													}//end else if
													else if(subSubInput5 == 3) //if user wants to  update to canceled status
													{
														data.get(0).setStatus("Cancelled"); //set status to canceled
													} //end else if
													break;
												
												default: //else
													looper =false; //exit loop
													break;
											} //end update switch
										}
										System.out.println("\nNew Updated Information");
										System.out.println("-----------------------------------------");
										System.out.println(data.get(0).toString()); //diplay information
										System.out.println("-----------------------------------------");
										System.out.print("Please Confirm Enter (1) to Save Changes or (0) to Discard: ");
										int subInput2 = scan.nextInt(); //reads in the choces fromthe user
										if(subInput2 == 1) //if the invoices are correct
											 success = displayInvoice.updateInvoice(data.get(0)); //display correct invoices
										if(success)//if success is true
											System.out.println("Update SuccessFul");
										else //if false 
											System.out.println("There was a problem with Updating the Invoices");
									}
									break;
								
								case 2: //view all invoices
									data = displayInvoice.getInvoiceList(1, 0);
									if(data.size()<1) //if the inoice list size is less than 1
										System.out.println("\nNo Invoices In Database");
									else //invoice list size is greater than 1
										for(Invoice i : data) //for each loop
										{
											System.out.println("-----------------------------------------");
											System.out.println(i.toString()); //display list
											System.out.println("-----------------------------------------");
										} //end for each loop
									break;
								case 3: //view all invoices
									data = displayInvoice.getInvoiceList(2, 0);
									if(data.size()<1) //if the inoice list size is less than 1
										System.out.println("\nNo Pending Invoices Found");
									else //invoice list size is greater than 1
										for(Invoice i : data) //for each
										{
											System.out.println("-----------------------------------------");
											System.out.println(i.toString());
											System.out.println("-----------------------------------------");
										} //end for each
									break;
								case 4: //view paid invoices
									data = displayInvoice.getInvoiceList(3, 0); //pass the option to view invoice class class
									if(data.size()<1) //if the invoice list size is less than 1
										System.out.println("\nNo Paid Invoices Found");
									else //if found greater than 1
										for(Invoice i : data)
										{
											System.out.println("-----------------------------------------");
											System.out.println(i.toString());
											System.out.println("-----------------------------------------");
										} //end for each loop
									break;
								default:
									check=false; //modify lcv
									break;
							}
						}while(check); //end do while loop
						break;
					}
					case 2: //create invoice list
					{	
						//scan.next();
						System.out.println("\nPlease Enter From Which Date to Create the Invoices From");
						while(true)
						{
							System.out.print("\nPlease Enter Year in 4 digits(yyyy): ");
							year = scan.nextInt(); //year was added
							System.out.print("\nPlese Enter Month in 2 digits(MM): ");
							month = scan.nextInt(); //month was added
							System.out.print("\nPlease Enter Dayin 2 digits(dd): ");
							day = scan.nextInt(); //day was added
							
							//-----------------------DATE VALIDATION----------------------
							/*
							 * 
							 * if th eyear is after 1900, 
							 * the month must be between 1-12(included)
							 * and the days shall not pass 31
							 *  Are all in correct format then update invoice
							 * */
							if(year >= 1900 && month >= 01 && month <= 12 && day >00 && day <= 31)
							{
								lookDate =  String.valueOf(year) +"-"+String.valueOf(month)+"-"+String.valueOf(day);
								break;
							}//end if valid
							else //not valid date
							{	
								System.out.println("Wrong Date Input Please ReEnter!!!");
								System.out.print("\nDo you want to ReEnter the date If Yes Enter 1 or 0 to Quit");
								int cont1 = scan.nextInt(); //quit option
								if(cont1 != 1)
									break;
							}//end else
						}
						
						System.out.println("\nNumber of Invoices Created: " + displayInvoice.createInvoices(lookDate));
						
						break;
					}//end while loop
			
					case 3: //delete invoice
					{
						System.out.print("Enter # of Days (00) back to delete Invoices: "); 
						int Days = scan.nextInt();//Hold user input of days. 
						data = displayInvoice.getInvoiceList(4, Days); //Call List of Invoices function
						for(Invoice i : data) // Display List of Invoices
						{
							System.out.println("-----------------------------------------");
							System.out.println(i.toString());
							System.out.println("-----------------------------------------");
						}
						scan.nextLine();
						System.out.println("\nWould you like to delete these Invoices? Y for yes, N for No"); //Ask for Confirmation
						String input = scan.nextLine(); //Holds Confirmation
						if(input.equalsIgnoreCase("Y")) //Test Confirmation
							System.out.println("# of Invoices Deleted: " + displayInvoice.deleteInvoice(Days)); //Invoke Delete Method
						break;
					}
					default:
						loopcounter = false; //modify lcv exit loop
						break;
				}
			}//end try
			catch(Exception e) //created by java
			{
				scan.nextLine();
				System.err.println(e);
			}//end catch exception e
			
		}while(loopcounter);//while the loop counter is true
		
		
		scan.close();//close scanner
	}//end void displ

}//end invoice interface class
