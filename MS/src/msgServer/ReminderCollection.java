package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Timer;
import java.util.Vector;

/**
 * Class to model a collection of messages.  The username of the recipient
 * is the key that is used to store each message.  Therefore it is easy
 * to retrieve messages destined for a particular user.
 */
public class ReminderCollection {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;
    private Hashtable reminders;

    /**
     * Construct a new empty MessageCollection
     */
    public ReminderCollection() {
        reminders = new Hashtable();
    }

    /**
     * Command to add a new message to the collection
     *
     * @param Message message is the message to be added
     */
   
    
    public void addReminder(Reminder reminder) {
        if (reminders.containsKey(reminder.getUsername())) {
            Vector remList = (Vector) reminders.get(reminder.getUsername());
            remList.add(reminder);
            
        } else {
            Vector remList = new Vector();
            remList.add(reminder);
            reminders.put(reminder.getUsername(), remList);
            
            
            
        }
       
    }
    
    
    
    synchronized public Reminder getNextReminder (String user) {
        Vector remList = (Vector) reminders.get(user);
        if (remList != null) {
            Reminder reminder = (Reminder) remList.firstElement();
            remList.removeElementAt(0);
            if (remList.size() == 0) {
                reminders.remove(user);
            }
            return reminder;
        } else {
            return null;
        }
    }
    
    synchronized public int getNumberOfReminders(String user) {
        Vector remList = (Vector) reminders.get(user);
        if (remList != null) {
            return remList.size();
        } else {
            return 0;
        }
    }}