package tv.tz.bot;

import java.io.File;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import tv.tz.enums.BuildEnum;
import tv.tz.enums.CommandEnum;
import tv.tz.service.JenkinsService;

public class MrJenkins extends TelegramLongPollingBot {

	private static final String TOKEN = "381875075:AAFbKtFCeN5k4VQUKLdJcBHBzSs_LmcwGwk";
	public static final String BOT_NAME = "Mr.Jenkins";

	@Override
	public String getBotUsername() {
		return BOT_NAME;
	}

	@Override
	public String getBotToken() {
		return TOKEN;
	}
/*
	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new MrJenkins());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
*/
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message != null && message.hasText()) {

			String text = message.getText();
			if (text.startsWith("/mr_jenkins")) {
				logMessage(text);
				text=text.replaceFirst("/mr_jenkins", "");
				
				CommandEnum command=null;
				
				if (text.equals("/getsee")) {
					sendMsg(message, "XZetsy");
				} else if ((command=CommandEnum.getByString(text))!=null) {


					switch (command) {
					case PRINT_HELP:
						sendMsg(message,getHelpString());
						break;

					case BUILD:
						String[] split = text.split("/");
						BuildEnum build=null;
						if(split.length==3&&(build=BuildEnum.getByString(split[2]))!=null){
							try {
								
								
								if(isJobAlreadyRunned(message)==false){
									
									sendMsg(message, "Запускаю сборку "+build.getName());
									JenkinsService.getInstance().build(build);
								}
						
								
							} catch (Exception e) {
								sendMsg(message, "Не удалось запустить сборку "+build.getName());
							}
						}else{
							sendMsg(message, "Я не знаю такой сборки");
						}
						break;
						
					default:
						sendMsg(message, "Я не знаю что ответить на это");
						break;
					}
					
					
				} else {
					sendMsg(message, "Я не знаю что ответить на это");
				}
			}
		}

	}
	
	public void sendMessage(String chatId,String text){
		text=text.replace("_", "");
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId);
		sendMessage.setText(text);
		try {
			sendMessage(sendMessage);
			logResponse(text);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendMsg(Message message, String text) {
		text=text.replace("_", "");
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setReplyToMessageId(message.getMessageId());
		sendMessage.setText(text);
		try {
			sendMessage(sendMessage);
			logResponse(text);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void logMessage(String msg){
		System.out.println("Mr.Jenkins got the message: "+msg);
	}
	private void logResponse(String msg){
		System.out.println("Mr.Jenkins response: "+msg);
	}
	
	private String getHelpString(){
		return "/help - Доступные команды \n"
			   +"/build/getsee  -Собрать GetSee \n"
			   +"/build/updates -Собрать апдейты для тестов";
	}
	
	private boolean isJobAlreadyRunned(Message message){
		for(BuildEnum enm:BuildEnum.values()){
			File file=new File(enm.getName());
			if(file.exists()){
				sendMsg(message,"Сейчас запущена "+enm.getName()+". Попробуй позже");
				return true;
			}
		}
		return false;
	}
	
}
