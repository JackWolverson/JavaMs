package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Timer;
/**
 * Class to execute the set reminder command.
 */
public class SetReminderCommand implements Command {

	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	/* static class MyReminderTask extends TimerTask {
		private BufferedWriter out;
		private MsgSvrConnection conn;
		private Reminder reminder;

		public MyReminderTask(BufferedWriter out, MsgSvrConnection serverConn, Reminder reminder) {
			this.out = out;
			this.conn = serverConn;
			this.reminder = reminder;
		}

		@Override
		public void run() {
			if (conn.getCurrentUser() != null && conn.getCurrentUser().equals(reminder.getUsername())) {
				try {
					;
					/*out.write(reminder.getRDate().getTime().toString()+ "\r\n");
					out.write(reminder.getContent() + "\r\n");
					out.flush();
					
					if ((reminder = conn.getServer().getMessages().getNextReminder(reminder.getUsername())) != null){
		                out.write("" + MsgProtocol.MESSAGE + "\r\n");
		                out.write("" + 1 + "\r\n");
		                out.write(reminder.getUsername() + "\r\n");
		                out.write(reminder.getContent() + "\r\n");
		                out.flush();
				}} catch (IOException e) {
					
				}
			}
		}
	}*/

	public SetReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection conn)	{
		this.in = in;
		this.out = out;
		this.conn = conn;
	}

	public void execute() throws IOException {
	 
		String username = in.readLine();
		String content = in.readLine();
		String rdate = in.readLine();
	      if (conn.getCurrentUser() != null && 
	          conn.getCurrentUser().equals(username)) {
	        if (username != null && rdate != null & content != null) 
	        {
	        	Reminder reminder = new Reminder(username, content, rdate);
	        	
	        	if (conn.getServer().isValidUser(username)) {
	        		conn.getServer().getMessages().addReminder(reminder);
	        		if(reminder.isDateValid(reminder.getRDateStr()) == true){
	   		GregorianCalendar cal = reminder.getRDate();

	   		// Now create the time and schedule it
	   		Timer timer = new Timer();
	   		timer.schedule(new MyReminderTask(out, conn, reminder), cal.getTime());
	   		out.write("" + MsgProtocol.REMINDER + "\r\n");
			out.flush();
	   		out.flush();
	   		return;} 
	        		else {
	        			(new ErrorCommand(in, out,conn, "the date is not valid")).execute();
	        		}}
	        	
	        } else {
		  (new ErrorCommand(in, out,conn, "Error trying to set a reminder")).execute();                  
		}
	      } else {
	        (new ErrorCommand(in, out,conn, "You are not logged in")).execute();                          
	      }
	  }
	}
	
