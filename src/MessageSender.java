import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageSender {

	public static void main(String args[]) throws UnknownHostException, IOException {
		if (args.length == 0) {
			System.out.println("Missing arguments");
			return;
		}

		Socket serverSocket = new Socket("localhost", 7777);
		PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
		StringBuilder text=new StringBuilder();
		for(int i=0;i<args.length;i++){
			text.append(args[i]).append(" ");
		}
		out.println(text.toString());
		out.close();
		serverSocket.close();
	}

}
