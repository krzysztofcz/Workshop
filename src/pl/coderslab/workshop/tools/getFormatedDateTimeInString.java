package pl.coderslab.workshop.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class getFormatedDateTimeInString {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return now();
	}
	
	public getFormatedDateTimeInString() {
		return;
	}

	public static String now() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatedDateTimeInString = now.format(formatter);
		return formatedDateTimeInString;
	}
}
