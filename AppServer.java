import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Scanner;
import java.util.TreeMap;

public class AppServer {
	
		PrintWriter out, outF;
		BufferedReader in;
		String command;
		TreeMap<String, String> infoStored;
		FileWriter outFile;
		String hostName;
		static Scanner response = new Scanner(System.in);
		static Statement state;
		static Connection connect;
		public static void connection(){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void connectToMySql(){
			connection();
			String host = "jdbc:mysql://localhost/mydatabase";
			String username = "root";
			String password = "";
			
			try {
				connect = DriverManager.getConnection(host, username, password);
				System.out.println("connection established?");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() throws Exception {
			try {
				ServerSocket serverSocket = new ServerSocket(7878);
				int localPort = serverSocket.getLocalPort();
				hostName = InetAddress.getLocalHost().getHostName();
				System.out.println(hostName + " is listening on prt " + localPort + " ...");
				boolean finished = false;
				connectToMySql();
				state = connect.createStatement();
				
				do {
					Socket clientSocket = serverSocket.accept();
					String clientName = clientSocket.getInetAddress().getHostName();
					int clientPort = clientSocket.getPort();
					System.out.println("Accepted connection to " + clientName + " on port " + clientPort + " ...");
					out = new PrintWriter (clientSocket.getOutputStream(), true);
					in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
					outFile = new FileWriter("storedInfo.txt",true);
					outF = new PrintWriter (outFile);
					readInput();
					
					
					//} while (clientSocket.isClosed());
					clientSocket.close();
				} while (!finished);
			} catch (UnknownHostException ex) {
				System.out.println("UnknownHostException occured.");
			} catch (IOException ex) {
				System.out.println("IOException occured in run.");
			}
		}
		/**
		 * @param args
		 * @return 
		 * @throws SQLException 
		 */
		
		
		public static void main(String[] args) throws Exception {
			
			
			AppServer server = new AppServer();
			
			server.initialize();
			server.run();
			
			
		}
		
		public void initialize () {
			String uName = " ";
			String pWord = " ";
			infoStored = new TreeMap<String, String>();
			
			try {
				BufferedReader br = new BufferedReader(new FileReader("storedInfo.txt"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					String temp[] = line.split("\t");
					uName = temp[0];
					pWord = temp[1];
					
					infoStored.put(uName, pWord);
				}
				
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		void readInput () throws Exception {
			out.println ("220 " + hostName + " App Server is running");
				try {
			
					command = in.readLine();
				} catch (IOException ex) {
					System.out.println ("IOException occured in readInput.");
					System.exit(1);
				}
				
				if (command.equalsIgnoreCase("createUser")) createUser();
				
				else if (command.equalsIgnoreCase("validateUser")) validateUser();
				
				
				while(true){
					command = in.readLine();
					
					
					if(command.equalsIgnoreCase("studentgrade")) studentGrade();
					
					if(command.equalsIgnoreCase("checkBalance")) checkBalance();
				}
		}
		
		void createUser () {
			try {
				String uName = in.readLine();
				String pWord = in.readLine();
				
				if (infoStored.size() != 0) {
					if (!infoStored.containsKey(uName)) {
						infoStored.put(uName, pWord);
						outF.println(uName + "\t" + pWord);
						outF.flush();
						outF.close();
						outFile.close();
						out.println("220 User created successfully.");
					}
					else {
						out.println("500 User already exists!");
					}
				}
				else {
					infoStored.put(uName, pWord);
					outF.println(uName + "\t" + pWord);
					out.println("220 User created successfully.");
					outF.flush();
					outF.close();
					outFile.close();
				}
				
				System.out.println (uName + " " + infoStored.get(uName));
			} catch (IOException ex) {
				System.out.println ("IOException occured in createUser.");
				System.exit(1);
			}				
		}
		
		void validateUser () throws Exception {
			try {
				String uName = in.readLine();
				String pWord = in.readLine();
				
				System.out.println (uName);
				System.out.println (infoStored.get(uName));
				
				if (pWord.equals(infoStored.get(uName))) {
					out.println("220 Logged in successfully.");
					System.out.println("220 Logged in successfully.");
				}
				else {
					out.println("500 Username and/ or password do not match!");
					System.out.println("500 Username and/ or password do not match!");
				}
			} catch (IOException ex) {
				System.out.println ("IOException occured in validateUser.");
				System.exit(1);
			}
		}
		
		void studentGrade() throws SQLException {
			try{
				String studentId = in.readLine();
				
				if(infoStored.containsKey(studentId)){
					ResultSet myresult = state.executeQuery("Select studentID, lastName, firstName from Student where studentId =" + studentId);
					int id = 0;
					String lastName = null;
					String firstName= null;
					int staffId;
					int studentId2;
					String levelName= null;
					String grade= null;
					String semester= null;
					String year = null;
					while(myresult.next()){
						id = myresult.getInt("studentID");
						lastName = myresult.getString("lastName");
						firstName = myresult.getString("firstName");
						
						//out.println(id + " " + lastName + " " + firstName);
						//break;
					}
					
					ResultSet myresult2 = state.executeQuery("Select staffID, studentID, levelName, grade, semester, year from Teach where studentId =" +studentId);
					while(myresult2.next()){
						staffId = myresult2.getInt("staffID");
						studentId2= myresult2.getInt("studentID");
						levelName = myresult2.getString("levelName");
						grade = myresult2.getString("grade");
						semester = myresult2.getString("semester");
						year = myresult2.getString("year");
						System.out.println("got to here");
						//out.print("LOL");
					}
					
					String message = id + " " + lastName + ", " + firstName + " - Your grade for " + semester + " " + year + " in " + levelName + " is " + grade;
					System.out.println(message);
					out.println(message);
				}
				
				
					
				
			} catch (IOException ex) {
				System.out.println ("IOException occured in validateUser.");
				System.exit(1);
			}
		}
		
		void checkBalance() throws SQLException{
			try {
				int maxMonth = 0;
				String studentId = in.readLine();
				if(infoStored.containsKey(studentId)){
					ResultSet myresult = state.executeQuery("SELECT max(month) FROM Pay where studentId =" + studentId);
					
					while(myresult.next()){
						maxMonth = myresult.getInt("max(month)");
					}
					String monthString;
			        switch (maxMonth) {
			            case 1:  monthString = "January";
			                     break;
			            case 2:  monthString = "February";
			                     break;
			            case 3:  monthString = "March";
			                     break;
			            case 4:  monthString = "April";
			                     break;
			            case 5:  monthString = "May";
			                     break;
			            case 6:  monthString = "June";
			                     break;
			            case 7:  monthString = "July";
			                     break;
			            case 8:  monthString = "August";
			                     break;
			            case 9:  monthString = "September";
			                     break;
			            case 10: monthString = "October";
			                     break;
			            case 11: monthString = "November";
			                     break;
			            case 12: monthString = "December";
			                     break;
			            default: monthString = "Invalid month";
			                     break;
			        }
			        out.println(monthString);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


