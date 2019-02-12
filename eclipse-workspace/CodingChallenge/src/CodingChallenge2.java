import java.util.Scanner;
import com.opencsv.CSVWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CodingChallenge2 {
	/*
	 * Connection override
	 * Override to connect to where I have sqlite installed
	 * If any user has sqlite installed in a different area, 
	 * or doesn't have a database folder
	 * this function needs to be updated
	 */
    private Connection connect() {
    	//update next line to where you would like the database to be saved
        String url = "jdbc:sqlite:C://sqlite//Databases//ms3Interview.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    /*createNewTable Function
     * Creates a Database and a table for the Java program to populate
     * Note: If you'd like to change any of the headers for the columns make sure you 
     * update them in both this function and the insert() function
     * otherwise you'll likely get an error.
     * (You should only have to update them in the "statement" string)
     * This is another DDL I use
     * 
     * 
     */
	public static void createNewTable() {
        // like above, if sqlite's install location is changed, or user wishes to make the file in a different location
		//update next line
		String dataBaseUrl = "jdbc:sqlite:C://sqlite//Databases//ms3Interview.db"; 
		//you can change the table name if you'd like, just make sure to update on the "statement" string
		String table = "CREATE TABLE IF NOT EXISTS interviewquestion (\n"
	            + "	last_name text, \n"
	            + " first_name text, \n"
	           + "	email text, \n"  
	           + "	gender text, \n"
	           + "	image_url text, \n"
	           + "	pay_info text, \n"
	           + "	pay text, \n"
	           + "	bool1 text, \n"
	           + "	bool2 text, \n"
	           + "	city_name text \n"
	            + ");";
    
        try (Connection conn = DriverManager.getConnection(dataBaseUrl);
                Statement stmt = conn.createStatement()) {
			System.out.println("Database: ms3Interview was created");
            // create a new table
            stmt.execute(table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       
    }
	
	
	/* Insert Function
	 * Used to quickly insert variables into a table
	 * @Param String lastName = variable in column A
	 * @Param String firstName = variable in column B
	 * @Param String email = variable in column C
	 * @Param String gender = variable in column D
	 * @Param String image_url = variable in column E
	 * @Param String pay_info = variable in column F
	 * @Param String pay = variable in column G
	 * @Param String bool1 = variable in column H
	 * @Param String bool2 = variable in column I
	 * @Param String cityName = variable in column J
	 * This is one of the DDL's I use
     */
	 public void insert( String lastName,String firstName,String email,String gender,String imageURL,String payInfo,String pay,String bool1,String bool2,String cityName) {
	     //this is the line you'd want to update if table name or other variables are changed   
		 String statement = "INSERT INTO interviewquestion(last_name,first_name,email,gender,image_url,pay_info,pay,bool1,bool2,city_name) VALUES(?,?,?,?,?,?,?,?,?,?)";	 
	        try (Connection conn=this.connect();
	        	PreparedStatement pstmt = conn.prepareStatement(statement)) {
	            pstmt.setString(1, lastName);
	            pstmt.setString(2, firstName);
	            pstmt.setString(3, email);
	            pstmt.setString(4, gender);
	            pstmt.setString(5, imageURL);
	            pstmt.setString(6, payInfo);
	            pstmt.setString(7, pay);
	            pstmt.setString(8, bool1);
	            pstmt.setString(9, bool2);
	            pstmt.setString(10, cityName);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	 /*
	  * main function
	  * call to execute the program
	  */
	 public static void main (String[] args) throws IOException {
		 //where we get the info from, this will need to change to where the info is if java throws an error on the next line
		 String fileName="ms3Interview.csv";
		 //used to keep track of lines, errors and an int to compare vs columns of data
		 int lineNumber, numOfErrors, numOfColm;
		 //used for time stamp
		 Date currDate = new Date();
		 long time = currDate.getTime();
		 //where we will write bad lines and endLog
		 File errorCSV =new File ("C://sqlite//Databases//bad-data-"+time+".csv");
		 File endLog =new File ("C://sqlite//Databases//ms3Interview.txt");
		 //set up a csvWriter and a buffered writer
		 CSVWriter csvWriter = new CSVWriter(new FileWriter(errorCSV));
		 BufferedWriter finLog = new BufferedWriter(new FileWriter(endLog));  
		 // Initializing the ints
		 lineNumber=0;
		 numOfErrors=0;
		 numOfColm=10;
		 //new file object to scan from
		 File file =new File(fileName);
		 //make a codingChallenge2 object to create table and database 
		 CodingChallenge2 test = new CodingChallenge2();
		 //make table and database,  
		 test.createNewTable();
		 try {
			 //important to note, as the file has strange characters I made sure to scan using UTF-8 codeset, otherwise the file ends reading at line 30 ish
			 Scanner scan = new Scanner(file, "UTF-8");
			 while(scan.hasNextLine()) {
				 //increase line number
				 lineNumber++;		
				 //set a string to what is being read
				 String line = scan.nextLine();
				 /*split it using commas that are not double quoted, and apply it as many times as possible
				  * considering we might need all data for the database -1 is what you'd want to use
				  * if you'd like to ignore empty values you could do so by adding a splitter that trims empty space
				  * one such splitter is : https://google.github.io/guava/releases/20.0/api/docs/com/google/common/base/Splitter.html
				  * Splitter commaSplitIgnoreEmpty = Splitter.on(',').omitEmptyStrings();
				  * If you do this, you might have to change the String[] to an iterable<String>or to a <String>List
				  * 
				  */
				 
				 String[] variables=line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				 //the size of variables = the length
				 int varSize=variables.length;
				 //compare to column size
				 if(varSize>=numOfColm) {
					 /*if you wanted to be lazy and not implement a Splitter, but still wanted to ignore blank lines
					  *you could have an if for each value of variable and if it .equals "" then print to error file
					  *however, that would be incredibly inefficient
					  */
					 
					 //this inserts all data if variables can fit info in it
					 test.insert(variables[0],variables[1],variables[2],variables[3],variables[4],variables[5],variables[6],variables[7],variables[8],variables[9]);
				 }
				 else {
					 //write any errors to a csv file and increase the value of the number of errors
					 csvWriter.writeNext(variables);
					 numOfErrors=numOfErrors+1;
					 //print to show error was caught, this is not necessary and can be removed if you'd like
					 System.out.println("Error on line:" + lineNumber);
				 }
			 }
			 //close scanner and csvWriter
			 scan.close();
			 csvWriter.close();
		 }
		 catch (FileNotFoundException e) {
		e.printStackTrace();
		 }
		 //write the info to the final log and close it
		 int successfulWrites = (lineNumber-numOfErrors);
		 finLog.write("Records recieved:"+ lineNumber+ "\r\nSuccessful lines: " + successfulWrites+ "\r\nNumber of errors: " + numOfErrors);
		 finLog.close();
	 }
}
