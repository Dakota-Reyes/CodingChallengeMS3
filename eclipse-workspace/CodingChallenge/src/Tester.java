import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Tester {
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite//Databases//ms3Interview.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	public static void createNewTable() {
        // SQLite connection string
		String dataBaseUrl = "jdbc:sqlite:C://sqlite//Databases//ms3Interview.db"; 
		String table = "CREATE TABLE IF NOT EXISTS interviewquestion (\n"
	            + "	last_name text, \n"
	            + " first_name text, \n"
	           + "	email text PRIMARY KEY, \n"  
	           + "	gender text, \n"
	           + "	image_url text, \n"
	           + "	pay_info text, \n"
	           + "	pay text, \n"
	           + "	bool1 text, \n"
	           + "	bool2 text, \n"
	           + "	city_name text \n"
	            + ");";
      Connection conn1=null;
        try (Connection conn = DriverManager.getConnection(dataBaseUrl);
                Statement stmt = conn.createStatement()) {
        	DatabaseMetaData met = conn.getMetaData();
			System.out.println("The driver handling this is" + met.getDriverName());
			System.out.println("Database: ms3Interview was created");
            // create a new table
            stmt.execute(table);
            conn1=conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       
    }
	 public void insert( String lastName,String firstName,String email,String gender,String imageURL,String payInfo,String pay,String bool1,String bool2,String cityName) {
	        String sql = "INSERT INTO interviewquestion(last_name,first_name,email,gender,image_url,pay_info,pay,bool1,bool2,city_name) VALUES(?,?,?,?,?,?,?,?,?,?)";	 
	        try (Connection conn=this.connect();
	        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
	 
public static void main (String[] args) throws IOException {
	String fileName="ms3Interview.csv";
	 BufferedReader reader = new BufferedReader(new FileReader(fileName));
	int lineOfError, lineNumber, numOfErrors;
	long start = System.currentTimeMillis();
	lineNumber=0;
	lineOfError=0;
	numOfErrors=0;
	int numofColm=10;
	File file =new File(fileName);
	Tester test = new Tester();
	test.createNewTable();
	String line;
		while((line= reader.readLine())!= null) {
			lineNumber++;		
			String[] variables=line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
			System.out.println(lineNumber);
			int varSize=variables.length;
			if(varSize>=numofColm) {
			test.insert(variables[0],variables[1],variables[2],variables[3],variables[4],variables[5],variables[6],variables[7],variables[8],variables[9]);
			}
			else {
				System.out.println("Error on line:" + lineNumber);
			}
			System.out.println(line);
			variables=null;
			System.out.println(variables);
		}
		reader.close();

		System.out.println(lineNumber);
		long end = System.currentTimeMillis();
		System.out.println((end-start)+"ms");
	}
}


