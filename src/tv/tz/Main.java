package tv.tz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import tv.tz.bot.MrJenkins;
import tv.tz.enums.CommandEnum;

public class Main {

	public static String SPLITTER = ":!:";
	public static int PORT = 7777;

	private boolean isServerStarted = true;
	private MrJenkins mrJenkins;

	public static void main(String[] args) {
		Main main = new Main();
		if(args.length>0){
			PORT=Integer.parseInt(args[0]);
		}
		main.initBot();
		main.initServer();

	}

	private void initBot() {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			mrJenkins = new MrJenkins();
			telegramBotsApi.registerBot(mrJenkins);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void initServer() {
		System.out.println("Telegram Jenkins server starting");
		ServerSocket servers = null;

		try {
			servers = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Couldn't listen to port "+PORT);
			System.exit(-1);
		}
		System.out.println("Telegram Jenkins server started on Port :" +PORT );
		while (isServerStarted) {
			Socket fromclient = null;

			try {
				System.out.print("Waiting for a client...");
				fromclient = servers.accept();
				System.out.println("Client connected");
				processNewClient(fromclient);
			} catch (IOException e) {
				System.out.println("Can't accept");
				continue;
			}
		}
		try {
			servers.close();
			System.out.println("Server SHUTDOWN");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 System.exit(-1);
	}

	private void processNewClient(Socket client) throws IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			StringBuilder result=new StringBuilder();
			String input;

			System.out.println("Wait for messages");
			while ((input = in.readLine()) != null) {
				result.append(input);
			}
			processInputText(result.toString());
		} catch (IOException e) {
			System.out.println("Can't read message");
		} finally {
			in.close();
			client.close();
		}

	}
	
	private void processInputText(String input){
		String[] split = input.split(SPLITTER);
		if (split.length == 1 && "exit".equalsIgnoreCase(split[0])) {
			isServerStarted = false;
		} else if (split.length >1) {
			CommandEnum command = CommandEnum.getByString(split[0]);
			
			if(command==null){
				System.out.println("Unknown command");
				return;
			}
			
			switch (command) {
			case SEND_MESSAGE:
				
				if(split.length==3){
					mrJenkins.sendMessage(split[1], split[2]);
				}
				
				break;

			default:
				break;
			}
			
			
		}
	}

}
