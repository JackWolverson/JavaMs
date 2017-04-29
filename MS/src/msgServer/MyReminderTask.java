package msgServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.TimerTask;

public class MyReminderTask extends TimerTask {
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
					out.write(reminder.getRDate().getTime().toString()+ "\r\n");
					out.write(reminder.getContent() + "\r\n");
					out.flush();
					/*if ((reminder = conn.getServer().getMessages().getNextReminder(reminder.getUsername())) != null){
		                out.write("" + MsgProtocol.MESSAGE + "\r\n");
		                out.write("" + 1 + "\r\n");
		                out.write(reminder.getUsername() + "\r\n");
		                out.write(reminder.getContent() + "\r\n");
		                out.flush();*/
				} catch (IOException e) {
					
				}
			}
		}
	}

