import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.security.*;

public class VMReceiverThread extends Thread{

	private Socket clientSocket;
	private BufferedReader input;
	private PrintWriter output;
	
	
	public VMReceiverThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Boolean validateCredentials(String usr, String pwd) throws Exception
	{
		// TODO Auto-generated method stub
		
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		MessageDigest m= MessageDigest.getInstance("MD5");
		m.update(pwd.getBytes(),0,pwd.length());
		String md5 = new BigInteger(1,m.digest()).toString(16);
		
		int cnt = 0;
	    try 
	    {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:acngroup10");
	    	
	    	c.setAutoCommit(false);
	    	System.out.println("Opened acngroup10 database successfully");
	    	stmt = c.createStatement();
	    	
	    	rs = stmt.executeQuery("select count(*),* from AndroidClients where username='"+ usr +"' and password='"+ md5+"';");
	    	
	    	while(rs.next()){
	    		cnt = Integer.parseInt(rs.getString(1));
	    		System.out.println("Count:"+cnt);
	    		System.out.println("ID:"+rs.getString(2));
	    		System.out.println("Username:"+rs.getString(3));
	    		System.out.println("Password:"+rs.getString(4));
	    	}
	    	System.out.println("Records Retrieved successfully");
	    	
	    	stmt.close();
	    	c.commit();
	    	c.close();
	    	if(cnt == 1){
	    		return true;
	    	}
	    	return false;
	    	
	    } 
	    catch (Exception e ) 
	    {
	      System.err.println( e.getClass().getName	() + ": " + e.getMessage() );
	      return false;
	    }
	    
		
	}
	
	private String validateRaspberryPi(String usr)
	{
		
		Connection c = null;
		Statement stmt = null;
		String serial_no = null;
		ResultSet rs = null;
		int cnt = 0;
	    try 
	    {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:acngroup10");
	    	
	    	c.setAutoCommit(false);
	    	System.out.println("Opened acngroup10 database successfully for retrieving matching Raspberry IDs");
	    	stmt = c.createStatement();
	    	
	    	rs = stmt.executeQuery("select count(*), r.serialno from androidclients a, raspberrypi r, users u where a.id = u.userid"+ 
	    							" and u.piid = r.id and a.username = '"+ usr + "';");
	    	
	    	while(rs.next()){
	    		cnt = Integer.parseInt(rs.getString(1));
	    		System.out.println("Count:"+cnt);
	    		serial_no = rs.getString(2);
	    		System.out.println("Raspberry Pi Serial #:"+ serial_no);
	    		
	    	}
	    	
	    	stmt.close();
	    	c.commit();
	    	c.close();
	    	if(cnt == 1){
	    		return serial_no;
	    	}
	    	
	    } 
	    catch (Exception e ) 
	    {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage());
	    }
	    return null;
		
	}

	public synchronized void run() {

		try {
			while(true) {
				try {
					String read = this.input.readLine();				
					System.out.println("\nMessage Received : " + read.toString());
					
					String[] words = read.split(":"); 
					if(words[0].equals("android_validation")){
						
						this.output = new PrintWriter(VMConnection.androidClient.getOutputStream(), true);
						Boolean valid_user = validateCredentials(words[1], words[2]);
						
						if(valid_user){		
							this.output.println("PASS");
						}
						else{
							this.output.println("FAIL");
						}
						break;
					}

					else if(words[0].equals("android")){
						
						this.output = new PrintWriter(VMConnection.raspberryPiClient.getOutputStream(), true);
						String serialno = validateRaspberryPi(words[1]);
						if(serialno != null)
						{
							if(words[2].equals("unlock")){
								System.out.println("Android client requests for unlock ... ");
								this.output.println("raspberrypi" + ":" + serialno + ":" + "unlock");
							} else if(words[2].equals("lock")) {
								System.out.println("Android client requests for lock ... ");
								this.output.println("raspberrypi" + ":" + serialno + ":" + "lock");
							}
						}
						else
						{
							PrintWriter send_android = new PrintWriter(VMConnection.androidClient.getOutputStream(), true);
							send_android.println("android" + "Error1: No RaspberryPi linked with your UserId");
							send_android.close();
						}
						
						break;
					} 
					
					else if(read.contains("raspberrypi")) 
					{						
						this.output = new PrintWriter(VMConnection.androidClient.getOutputStream(), true);
						if(read.contains("unlocked")) {
							System.out.println("raspberrypi unlocked the door successfully ... ");
							this.output.println("android" + " " + "unlocked");
						} else if(read.contains("locked")){
							System.out.println("raspberrypi locked the door successfully ... ");
							this.output.println("android" + " " + "locked");
						} else {
						System.out.println("Error2: RaspberryPi Serial Number is not matching");
						this.output.println("android" + " " + "Error2: RaspberryPi Serial Number is not matching");
						}
					}
					
					this.output.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} 
	}

}
