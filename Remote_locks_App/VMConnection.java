import java.net.Socket;


public class VMConnection {

	public static Socket androidClient;
	public static Socket raspberryPiClient;
	
	public static void main(String[] args) {
		
		int port = Integer.parseInt(args[0]);
    	
        VMServer androidServer = new VMServer(port);

        androidServer.start();
       
        VMServer raspberryPiServer = new VMServer(4000);
        
        raspberryPiServer.start();
        
        
        //RaspberryPiClient client = new RaspberryPiClient("acngroup10.utdallas.edu",5000);

        //client.start();
    }

}
