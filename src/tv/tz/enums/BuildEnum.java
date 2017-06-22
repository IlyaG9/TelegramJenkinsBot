package tv.tz.enums;

public enum BuildEnum {

	GETSEE("Getsee_ppl"),
	UPDATE_FOR_TEST("getsee_update_for_test"),
	;
	
	private final String name;
	
	private BuildEnum(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public static BuildEnum getByString(String command) {
		
		if("getsee".equalsIgnoreCase(command)){
			return GETSEE;
		}else if("updates".equalsIgnoreCase(command)){
			return UPDATE_FOR_TEST;
		}else{
			return null;
		}
		
		
	}
}
