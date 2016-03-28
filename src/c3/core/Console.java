package c3.core;

public enum Console {
	
	INFO, WARNING, ERROR, CRITICAL;
	
	public static void log(Console level, Object string) {
		
		// TODO Implement console stack logging
		
		// Thread.currentThread().getStackTrace();
		
		switch(level) {
		
		case CRITICAL:
			System.err.println(string.toString());
			break;
		case ERROR:
			System.err.println(string.toString());
			break;
		case WARNING:
			System.out.println(string.toString());
			break;
		case INFO:
			System.out.println(string.toString());
			break;
		default:
			System.out.println(string.toString());
			break;
		
		}
		
	}
	
	public static void log(Object string) {
		System.out.println(string.toString());
	}
	
}
