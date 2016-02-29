package server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AuditLog {
	private static FileHandler fileTxt;
	private final static Logger logger = Logger.getLogger( AuditLog.class.getName() );
	
	private AuditLog() {}
	
	public static void setUp() {
		try {
			logger.setUseParentHandlers(false);
			
			fileTxt = new FileHandler("../server/data/log", true);
			SimpleFormatter formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void log(String logMsg) {
		logger.log(Level.INFO, logMsg);
	}
}
