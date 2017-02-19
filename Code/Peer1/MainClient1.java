import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.lang.*;

public class MainClient1 {
	public static void main(String[] args) throws IOException{
		
		String serverHostname = new String ("mandar-VirtualBox");
		System.out.println ("Attemping to connect to host " + serverHostname + " on Server.");

		// Sockets defined for Servers
		Socket indexSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		// Sockets defined for Replication on Servers
		Socket indexSocket1 = null;
		PrintWriter out1 = null;
		BufferedReader in1 = null;

System.out.println("Enter the operation you want to perform according to syntax as:");
		System.out.println("For Registry: Put Filename PeerID");
		System.out.println("For Search: Search Filename");
		System.out.println("For Downloading a file: Obtain Filename");

		int hashvalue;
		int Serverloc;
		int Serverlocr;
		int noOfServers = 8;
		String userInput;
	int[] Portarray = {5050, 5051, 5052, 5053, 5054, 5055, 5056, 5058};

 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		while((userInput = stdIn.readLine()) != null)
		{
		 String[] tokenc = userInput.split(" ");
		 hashvalue = tokenc[1].hashCode();
	//abs value is taken as sometimes hashCode() gives negative values
		 Serverloc = (Math.abs(hashvalue) % noOfServers); 
		 Serverlocr = ((Math.abs(hashvalue))+1) % noOfServers;
		
		 boolean flag = false;
		 
	// Socket connections for with Server located thru hash function
		 try 
		 {
		 indexSocket = new Socket(serverHostname, Portarray[Serverloc]);
		 out = new PrintWriter(indexSocket.getOutputStream(), true);
	 in = new BufferedReader(new InputStreamReader(indexSocket.getInputStream()));
		 flag = true;
		 } catch (UnknownHostException e)
		 {
		 System.err.println("Don't know about host: " + serverHostname);
		 System.exit(1);
		 } catch (IOException e) 
		 {
		 System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
		  flag = false;
		 }
		
		boolean flag1 = false;

// Socket connections with server located thru hash func in case of replication 
		try 
		 {
	 indexSocket1 = new Socket(serverHostname, Portarray[Serverlocr]);
		 out1 = new PrintWriter(indexSocket1.getOutputStream(), true);
in1 = new BufferedReader(new InputStreamReader(indexSocket1.getInputStream()));
		 flag1 = true;
		 } catch (UnknownHostException e)
		 {
		 System.err.println("Don't know about host: " + serverHostname);
		 System.exit(1);
		 } catch (IOException e) 
		 {
		 System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
		 flag1 = false;
		 }

		if(tokenc[0].equalsIgnoreCase("Put"))
		{
 // registering with server location obtained thru hash func.
		System.out.println("Registering at Server number:" + Serverloc);
		 out.println(tokenc[0]);
		 out.println(tokenc[0]);
		 out.println(tokenc[1]);
		 out.println(tokenc[2]);
		 System.out.println(in.readLine());
		
 // replication registeration with server location obtained thru hash func.
		 System.out.println("Registering replicated entry at Server number:" + Serverlocr);
		 out1.println(tokenc[0]);
		 out1.println(tokenc[0]);
		 out1.println(tokenc[1]);
		 out1.println(tokenc[2]);
		 System.out.println("Replicated"+ " " +in1.readLine());
		 
		 }
		if(tokenc[0].equalsIgnoreCase("Search"))
		{
// Checks if server is up then sends query to that server else to the next server where replication is made
		if(flag){
		out.println(tokenc[0]);
		out.println(tokenc[0]);
		out.println(tokenc[1]);
		System.out.println(in.readLine());
		} 
		else if(flag1)
		{
		out1.println(tokenc[0]);
		out1.println(tokenc[0]);
		out1.println(tokenc[1]);
		System.out.println("Result from replicated server"+ " " + in1.readLine());
		}
		}
		String Loc = null;
		if(tokenc[0].equalsIgnoreCase("Obtain"))
		{
// Checks if server is up then sends query to that server else to the next server where replication is made
		if(flag){
		out.println(tokenc[0]);
		out.println(tokenc[0]);
		out.println(tokenc[1]);
		Loc = in.readLine();
		System.out.println("File is with Peer:" + Loc);
		} else if(flag1){
		out1.println(tokenc[0]);
		out1.println(tokenc[0]);
		out1.println(tokenc[1]);
		Loc = in1.readLine();
		System.out.println("From replicated Server::File is with Peer:" + Loc);
		}
		
		if(Loc.equalsIgnoreCase("Peer1"))
        	{
        	
        	PeerClient pcobj = new PeerClient();
        	String[] arr = {"mandar-VirtualBox"};
            	int x = pcobj.Obtain(tokenc[1]);
// obtain function will return 0 if that peerserver is down! all files of peer1 are replicated to peer2 so peerserver2 is called if peerserver is down
		if (x == 0)
		   {
			System.out.println("Peer1 Server not working");
			System.out.println("Getting replicated file from Peer2");
			Loc = "Peer2";
		   }
                    
       		}
        	if(Loc.equalsIgnoreCase("Peer2"))
        	{
        	
        	PeerClient2 pcobj2 = new PeerClient2();
        	String[] arr = {"mandar-VirtualBox"};
        	int y = pcobj2.Obtain(tokenc[1]);
// obtain function will return 0 if that peerserver2 is down! all files of peer2 are replicated to peer3 so peerserver3 is called if peerserver2 is down
		if (y == 0)
			{
			System.out.println("Peer2 Server not working");
			System.out.println("Getting replicated file from Peer3");
			Loc = "Peer3";
			}

        	}
        	if(Loc.equalsIgnoreCase("Peer3"))
       		{
        	
        	Peer3Client pcobj3 = new Peer3Client();
        	String[] arr = {"mandar-VirtualBox"};
        	int z = pcobj3.Obtain(tokenc[1]);
// obtain function will return 0 if that peerserver3 is down! all files of peer3 are replicated to peer1 so peerserver is called if peerserver3 is down
		if (z == 0)
			{
			System.out.println("Peer3 Server not working");
			System.out.println("Getting replicated file from Peer1");
			Loc = "Peer1";
			}
        	}
		}
		}

		out1.close();
		in1.close();
		indexSocket1.close();	
	
		out.close();
		in.close();
		stdIn.close();
		indexSocket.close();	
		}		

}
