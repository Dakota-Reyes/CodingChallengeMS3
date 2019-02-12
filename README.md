# CodingChallengeMS3
Challenge:
Data for the project is included in the sample CSV file, attached. Please create a Github or Bitbucket repository for the project and push all code there; please email the link to the repository when you submit your project.
Customer X just informed us that we need to churn out a code enhancement ASAP for a new project.  Here is what they need:
1. We need a Java application that will consume a CSV file, parse the data and insert to a SQLite In-Memory database.  
a. Table X has 10 columns A, B, C, D, E, F, G, H, I, J which correspond with the CSV file column header names.
b. Include all DDL in submitted repository
c. Create your own SQLite DB
2. The data sets can be extremely large so be sure the processing is optimized with efficiency in mind.  
3. Each record needs to be verified to contain the right number of data elements to match the columns.  
a. Records that do not match the column count must be written to the bad-data-<timestamp>.csv file
b. Elements with commas will be double quoted
4. At the end of the process write statistics to a log file
a. # of records received
b. # of records successful
c. # of records failed
	
	In order to run the code, you should first change any filenames to the appropriate file paths or names. Then, create a jar file from the file called “CodingChallenge2.java” with the launch configuration of CodingChallenge2 – CodingChallenge, making sure to extract required libraries as well. It will take around 10 minutes to finish and will generate 3 files. 
	The default file names are “bad-data.csv”, with numbers that represent the time the jar was ran, ms3Interview.db, and ms3Interview.txt which is where all the statistics are written to.
 	For this project, the first thing I did was write a simple scanner to scan the .csv file. Because I've not worked with SQLite before I wanted to start simply then slowly build onto the project.
 	After the scanner was working, I looked at 3rd party libraries to add functionality to java to allow me to access to SQLite from a java file. I decided on SQLiteJDBC, as it seemed to be the most effective and useful SQLite library. 
 	I then implemented some basic functions of the library, such as an insert function and a function to create a table. Then it was a simple task of splitting the line that the scanner reads into a String array, checking if the array's info can be parsed into the insert function, then inserting it into the database or writing to the other .csv file. 
 	After that was functional, I just wrote to the .txt file and was finished.
	After I had finished programming I did a little more research into making the process more efficient. 
	However, I was unable to find any way to cut time substantially. 
	I do feel as though there is a way to make the process more efficient (probably by rewriting my insert command to not close the file every time an insertion is done), but because this is my first time utilizing SQLite in any capacity I'm not quite sure how I would do that.
	
3rd party libraries used:
sqlite-JDBC-3.23.1
opencsv-4.5

DDLs:

CREATE TABLE interviewquestion (
    last_name  TEXT,
    first_name TEXT,
    email      TEXT,
    gender     TEXT,
    image_url  TEXT,
    pay_info   TEXT,
    pay        TEXT,
    bool1      TEXT,
    bool2      TEXT,
    city_name  TEXT
);

"INSERT INTO interviewquestion(last_name,first_name,email,gender,image_url,pay_info,pay,bool1,bool2,city_name) VALUES(?,?,?,?,?,?,?,?,?,?)";
