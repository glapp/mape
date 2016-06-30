package ch.uzh.glapp.mdp2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class debugOutput {
	private static String currentDate;
	
	public static void setDate(Date currentDate) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd_kkmmss");
		debugOutput.currentDate = formater.format(currentDate);
		System.out.println(debugOutput.currentDate);
	}
	
	public static void log(String output) {
		try {
			String path = "debugLog_" + currentDate + ".txt";
			BufferedWriter debugLog = new BufferedWriter(new FileWriter(path, true));
			debugLog.write(output + "\n\n");
			debugLog.flush();
			debugLog.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
