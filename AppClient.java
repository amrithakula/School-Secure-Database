import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;


public class AppClient {
	static AppClient client;
	BufferedReader bReader;
	String serverResponse, command;
	OutputStream oStream;
	PrintWriter out;
	ResultSet result;
	
	public AppClient () throws Exception {
		Socket socketEmail = new Socket ("localhost", 7878);
		
		InputStream iStream = socketEmail.getInputStream();
		InputStreamReader iStreamReader = new InputStreamReader (iStream);
		bReader = new BufferedReader (iStreamReader);
		
		serverResponse = bReader.readLine();
		System.out.println(serverResponse);
		
		if (!serverResponse.startsWith("220")) {
			throw new Exception ("220 reply not received from SERVER.\n");
		}
		
		oStream = socketEmail.getOutputStream();
		out = new PrintWriter (socketEmail.getOutputStream(), true);
	}
//	public static void main(String[] args) throws Exception{
//		AppClient client = new AppClient();
//		
//		//client.createUser("12001", "pass");
//		client.validateUser("12001", "pass");
//		client.studentGrade("12001");
//	}
	
	boolean createUser (String uName, String pWord) {
		try {
			out.println("createUser");
			
			out.println(uName);
			out.println(pWord);
			
			serverResponse = bReader.readLine();
		
		} catch (IOException ex) {
			System.out.println ("IOException occured in createUser.");
			System.exit(1);
		}
		if (serverResponse.startsWith("220")) {
			return true;
		}
		else return false;
	}
	
	boolean validateUser (String uName, String pWord) {
		try {
			out.println ("validateUser");
		
			out.println(uName);
			out.println(pWord);
			
			serverResponse = bReader.readLine();
		} catch (IOException ex) {
			System.out.println ("IOException occured in validateUser.");
			System.exit(1);
		}
		
		if (serverResponse.startsWith("220")) {
			return true;
		}
		else return false;
	}
	
	String studentGrade (String studentId) {
		try {
			out.println ("studentgrade");
			System.out.println(studentId);
			out.println(studentId);
			
			serverResponse = bReader.readLine();
			System.out.println(serverResponse);
			
		} catch (IOException ex) {
			System.out.println ("IOException occured in validateUser.");
			System.exit(1);
		}
		return serverResponse;
	
	}
	
	String checkBalance (String studentId) {
		try {
			out.println ("checkBalance");
			System.out.println("Check Bal " + studentId);
			out.println(studentId);
			
			serverResponse = bReader.readLine();
			System.out.println(serverResponse);
			
		} catch (IOException ex) {
			System.out.println ("IOException occured in validateUser.");
			System.exit(1);
		}
		return "You have paid till the month of "+ serverResponse;
	
	}
}
