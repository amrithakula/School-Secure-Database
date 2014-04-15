import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GUI extends JFrame implements Observer {
	
	static AppClient client;
	
	private JLabel instruction = new JLabel();
	
	public StudentAccessFrame studentFrame = null;
	
	public String userId;
	
	public String toAddress;
	public String emailContent;
	

	
	JTextField username;
	JPasswordField password;
	
	public GUI(AppClient client){
		
		this.client = client;
		
		JFrame window = new JFrame();
		
		JPanel panel = new JPanel (new BorderLayout());
			
			
		JPanel userPanel = new JPanel(new WrapLayout(WrapLayout.CENTER));
			//Instruction
			JLabel instruction = new JLabel("username:");
			//username field
				username = new JTextField("enter schoolid");
				username.addActionListener(new UsernameInput());
			userPanel.add(instruction);
			userPanel.add(username);
			panel.add(userPanel, BorderLayout.NORTH);
		JPanel passPanel = new JPanel(new WrapLayout(WrapLayout.CENTER));
			JLabel thePassword = new JLabel("Password:");				
			//password field
			password = new JPasswordField(10);
				password.addActionListener(new PasswordInput());
				passPanel.add(thePassword);
				passPanel.add(password);
				panel.add(passPanel, BorderLayout.CENTER);
		JPanel southPanel = new JPanel(new WrapLayout(WrapLayout.CENTER));	
			//login button
			JButton login = new JButton("Login");
				login.addActionListener(new LoginListener());
			//register button
			JButton register = new JButton("Register");
				register.addActionListener(new RegisterListener());
			
			southPanel.add(login);
			southPanel.add(register);
			panel.add(southPanel, BorderLayout.SOUTH);
		
		
			
			window.add(panel);
			window.setTitle("Elmentary School Application");
			window.setSize(230, 130);
			window.setLocationRelativeTo(null);
			window.setDefaultCloseOperation(EXIT_ON_CLOSE);
			window.setVisible(true);
		
	}
	
	class UsernameInput implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class PasswordInput implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class LoginListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			userId = (username.getText());
			String thePass = new String(password.getPassword());
			System.out.println(userId);
			System.out.println(thePass);
			if(client.validateUser (userId, thePass) == true){
				System.out.println ("Login successful.");
				studentFrame = new StudentAccessFrame();
			}
			else{
				Component passwordFail = null;
				JOptionPane.showMessageDialog(passwordFail, "User Id/Password Combination does not match or exist");
				System.exit(1);
			}
			
			
			
		}
		
	}
	
	class RegisterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			userId = (username.getText());
			String thePass = new String(password.getPassword());
			System.out.println(userId);
			System.out.println(thePass);
			if (client.createUser (userId, thePass) == true) {
				Component createUserSuccess = null;
				JOptionPane.showMessageDialog(createUserSuccess, "User Succesfully Created");
				System.exit(1);
			}
			else {
				Component userExists = null;
				JOptionPane.showMessageDialog(userExists, "User name already exists!");
				System.exit(1);
			}
		}
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws Exception{
		
		client = new AppClient();
		GUI theGui = new GUI(client);
	}
	JTextArea message;
	JTextField toAddressField;
	class StudentAccessFrame{
		
		JFrame window2 = new JFrame();
		
		public StudentAccessFrame(){
			
			JPanel theMain = new JPanel(new BorderLayout());
			
			
			JPanel south = new JPanel(new WrapLayout(WrapLayout.CENTER));
				JButton checkGrade = new JButton("Check Grades");
				checkGrade.addActionListener(new checkGrade());
				JButton checkBalance = new JButton("Check Balance");
				checkBalance.addActionListener(new checkBalanceListener());
				south.add(checkGrade);
				south.add(checkBalance);
				theMain.add(south, BorderLayout.SOUTH);
			
			window2.add(theMain);
			window2.setTitle("Elementary School Student Access");
			window2.setSize(200,100);
			window2.setLocationRelativeTo(null);
			window2.setVisible(true);
		}	
		class toAddressListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		class checkGrade implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String theGrade = client.studentGrade(userId);
				System.out.print(theGrade);
				Component displayGrade = null;
				JOptionPane.showMessageDialog(displayGrade, theGrade);
			}
			
		}
		
		class checkBalanceListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				String balance = client.checkBalance(userId);
				Component displayPaidTill = null;
				JOptionPane.showMessageDialog(displayPaidTill, balance);
			}
			
		}
		
	}


}
