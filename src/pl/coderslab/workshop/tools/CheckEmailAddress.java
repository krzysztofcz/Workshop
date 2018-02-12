package pl.coderslab.workshop.tools;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class CheckEmailAddress {

	public boolean isValid(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
}
