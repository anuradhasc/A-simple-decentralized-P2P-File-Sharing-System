import java.net.*; 
import java.util.*;
import java.io.*; 
import java.util.concurrent.ConcurrentHashMap;

public class Server0 extends Thread {
	
	protected Socket clientSocket;
	// ConcurrentHashmap defined
	static Map<String, List<String>> StoreTable = new ConcurrentHashMap<String, List<String>>();
	public static void main(String[] args) throws IOException{
		
		ServerSocket serversock = null;
		
		try{
			serversock = new ServerSocket(5050);
			System.out.println("Connection Socket Created : Port number 5050: Server0");
			try{
				while(true){
					System.out.println("Waiting for Connection on port 5050");
					new Server0 (serversock.accept());
				}
			}
			catch (IOException e) 
            { 
             System.err.println("Accept failed."); 
             System.exit(1); 
            } 	
		}
		catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 5050."); 
         System.exit(1); 
        } 
	finally
        {
         try {
              serversock.close(); 
             }
         catch (IOException e)
             { 
              System.err.println("Could not close port: 5050."); 
              System.exit(1); 
             } 
        }
	}

	private Server0 (Socket clientsock){
		
		clientSocket = clientsock;
		start();
	}
	
	public void run(){
		System.out.println ("New Communication Thread Started");
		try
		{
	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
			String inputLine;
			String Value;
			List<String> Location;
			while((inputLine = in.readLine()) != null)
			{
				String function = in.readLine();
				System.out.println(function);
				
		        if(function.equalsIgnoreCase("Put"))
		        {
				String key = in.readLine();
				String HashValue = in.readLine();
				Location = StoreTable.get(key);
 	            		if ( Location == null)
 	            		{
 	                	Location = new ArrayList<String>();
 	            		}
				Location.add(HashValue);
		        	StoreTable.put(key, Location);
		        	System.out.println("Entry added");
				out.println("Entry added");
				
		        }
		        if(function.equalsIgnoreCase("Search"))
		        {
				String key = in.readLine();
		        	Location = StoreTable.get(key);
				System.out.println("Location is:"+Location);
		        	out.println("Location of file is:"+Location);
		        }
		        if(function.equalsIgnoreCase("Obtain"))
		        {
				String key = in.readLine();
		       		Location = StoreTable.get(key);
				String loc = Location.get(0);
				System.out.println("File is at:" + loc);
		       		out.println(loc);
		        }
		       	}	
			in.close();
			out.close();
		}
		catch(IOException e)
		{
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}	
	}
}
