package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Timer;

public class UpdateReminderCommand implements Command {
    private BufferedReader in;
    private BufferedWriter out;
    private MsgSvrConnection conn;

    public UpdateReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
        this.in = in;
        this.out = out;
        this.conn = serverConn;
    }

	@Override
	public void execute() throws IOException {
		String user = in.readLine();
        if (conn.getCurrentUser() != null &&
                conn.getCurrentUser().equals(user)) {
        	Reminder r = null;
        	if((r = conn.getServer().getMessages().getReminders(user, 0)) != null){
        		Integer z =  conn.getServer().getMessages().getNumberOfReminders(user);
	        	for(Integer x = 0 ; x < z+1; x++){
	        		r = conn.getServer().getMessages().getReminders(user, x);
	        		if (r != null){
		        	    out.write("Current Reminder:" + x + "\r\n");
		        	    out.write(r.getContent() + "\r\n");
		        	    out.write(r.getRDateStr() + "\r\n");
		        	    out.flush();
		        		}
	        	}
	        	
	        	out.write("Enter the number of the reminder you would like to update: \r\n");
	        	out.write("to delete this reminder just press enter twice! \r\n");
	        	out.flush();
                String RemNum = in.readLine();
                Integer y = Integer.parseInt(RemNum);
                
                out.write("Enter the new details \r\n");
                out.write("Content: \r\n");
                out.flush();
                String cont = in.readLine();
                
                out.write("Date: 'dd mm yyyy hh mm' \r\n");
                out.flush();
                String Date = in.readLine();
                
                String S = conn.getServer().getMessages().removeReminder(user, y);
                out.write(S);
                out.flush();
                
                if(cont != null && Date != null){
                	Reminder rem = new Reminder(user, cont, Date);
                	
                	if (conn.getServer().isValidUser(user)) {
                		conn.getServer().getMessages().addReminder(rem);
                		if(rem.isDateValid(rem.getRDateStr()) == true){
           		GregorianCalendar cal = rem.getRDate();

           		// Now create the time and schedule it
           		Timer timer = new Timer();
           		timer.schedule(new MyReminderTask(out, conn, rem), cal.getTime());
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
                (new ErrorCommand(in, out, conn, "No Reminders")).execute();
                
            }
        } else {
            (new ErrorCommand(in, out, conn, "You are not logged on")).execute();
        }
	}
}
