package tv.tz.enums;

public enum CommandEnum {

	EXIT,
	SEND_MESSAGE,
	BUILD,
	PRINT_HELP,
	;

	public static CommandEnum getByString(String command) {
		
		if("mr_jenkins_exit".equalsIgnoreCase(command)){
			return EXIT;
		}else if("mr_jenkins_send_message".equalsIgnoreCase(command)){
			return SEND_MESSAGE;
		}else if("/help".equalsIgnoreCase(command)){
			return PRINT_HELP;
		}else if(command!=null&&command.startsWith("/build")){
			return BUILD;
		}else{
			return null;
		}
		
		
	}
}
