import java.io.*;
import java.net.*;

public class PeerClient2 {
	public static void main(String[] args)
	{
	
	}
    public int Obtain(String Filename) throws IOException {

        String serverHostname = new String ("mandar-VirtualBox");

   //     System.out.println ("Attemping to connect to host " +
     //           serverHostname + " on port 5061.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 5061);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
	    return 0;
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
         out.println(Filename);
	 out.println(Filename);
	 
         String FileContent;
	 
         int Lines = Integer.parseInt(in.readLine());
         String Check = in.readLine();
         if(Check.equals("true"))
         {
			// writing of file
			File file = new File(Filename);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
			for (int i=0; i<Lines; i++)  
           		{
			 FileContent = in.readLine();
			 bw.write(FileContent);
			 bw.newLine();
			}
			System.out.println("Done");
			bw.close();
		
         }
	
         System.out.println("Do you still want to continue?(y/n)");
         String ans = stdIn.readLine();
         if(ans.equals("y"))
			   {
				MainClient1 cl = new MainClient1();
				String[] arr = {"mandar-VirtualBox"};
			    	cl.main(arr);
			   }
         if(ans.equals("n"))
			  {
			    System.exit(1);
			  }
        
	 

		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
		return 1;
	} 
	}
