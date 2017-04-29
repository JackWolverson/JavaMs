package msgServer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class to model an individual message
 */
public class Reminder {
	private String username;
	private String content;
	private String rdate;

	/**
	 * Construct a new object of type Reminder.. 
	 */
	public Reminder(String username, String content, String rdate) {
		this.username = username;
		this.content = content;
		this.rdate = rdate;

	}
	/**
     * Query to obtain the user name of the reminder.
     *
     * @return String the user name
     */
	public String getUsername() {
		return username;
	}
	/**
     * Query to obtain the content of this reminder
     *
     * @return String The content of this reminder
     */
	public String getContent() {
		return content;

	}
	/**
     * Query to obtain the date of the reminder.
     *
     * @return String The date of the reminder
     */
	public String getRDateStr() {
		return rdate;

	}
	/**
     * Query to obtain the date if the reminder in GregorianCalendar format
     *
     * @return GregorianCalendar format of the date
     */
	public GregorianCalendar getRDate() {
		GregorianCalendar cal = null;
		try {
			DateFormat df = new SimpleDateFormat("dd MM yyyy hh mm");
			Date date = df.parse(rdate);
			cal = new GregorianCalendar();
			cal.setLenient(false);
			cal.setTime(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cal;
		
		
	}
	/**
     * Query to check it the input date is in correct format
     *
     * @return boolean true or false
     */
	public boolean isDateValid(String date) 
	{
		GregorianCalendar cal = null;
		try {
			DateFormat df = new SimpleDateFormat("dd MM yyyy hh mm");
			Date check = df.parse(rdate);
			cal = new GregorianCalendar();
			cal.setLenient(false);
			cal.setTime(check);
			return true;
		} catch (Exception e) {
			return false;// TODO: handle exception
		}
	}
}
