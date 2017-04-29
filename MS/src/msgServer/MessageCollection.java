package msgServer;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Class to model a collection of messages.  The username of the recipient
 * is the key that is used to store each message.  Therefore it is easy
 * to retrieve messages destined for a particular user.
 */
public class MessageCollection {
    private Hashtable messages;
    private Hashtable reminders;
    private Hashtable timers;

    /**
     * Construct a new empty MessageCollection
     */
    public MessageCollection() {
        messages = new Hashtable();
        reminders = new Hashtable();
        timers = new Hashtable();
    }

    /**
     * Command to add a new message to the collection
     *
     * @param Message message is the message to be added
     */
    synchronized void addMessage(Message message) {
        if (messages.containsKey(message.getRecipient())) {
            Vector msgList = (Vector) messages.get(message.getRecipient());
            msgList.add(message);
        } else {
            Vector msgList = new Vector();
            msgList.add(message);
            messages.put(message.getRecipient(), msgList);
        }
    }
    
    synchronized void addReminder(Reminder reminder) {
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
    }
    
    


    /**
     * Command to retrieve the oldest message waiting for a specific user.
     * The message is returned and also deleted from the collection.
     *
     * @param String user is the user who the message is addressed to
     * @return Message The oldest message addressed to that user
     */
    synchronized public Message getNextMessage(String user) {
        Vector msgList = (Vector) messages.get(user);
        if (msgList != null) {
            Message message = (Message) msgList.firstElement();
            msgList.removeElementAt(0);
            if (msgList.size() == 0) {
                messages.remove(user);
            }
            return message;
        } else {
            return null;
        }
    }

    /**
     * Query to retrieve the number of messages waiting for a specific user.
     *
     * @param String user is the user whose messages we are asking about
     *               8 @return int The number of messages waiting for this user
     */
    synchronized public int getNumberOfMessages(String user) {
        Vector msgList = (Vector) messages.get(user);
        if (msgList != null) {
            return msgList.size();
        } else {
            return 0;
        }
    }

    /**
     * Command to retrieve all the messages waiting for a specific user.
     * The messages are deleted from the collection and
     * returned in an array of Messages.
     *
     * @param String user is the user who the messages are addressed to
     * @return Message[] An array of messages addressed to the user
     */
    synchronized public Message[] getAllMessages(String user) {
        Vector msgList = (Vector) messages.get(user);
        if (msgList != null) {
            messages.remove(user);
            return ((Message[]) msgList.toArray(new Message[msgList.size()]));
        }
        return null;
    }

	synchronized public Reminder getReminders(String User, Integer x) {
		Vector remList = (Vector) reminders.get(User);
        if (remList != null && x < remList.size()) {
            Reminder reminder = (Reminder) remList.get(x);
            return reminder;
        } else {
            return null;
        }
    }
	
	synchronized public String removeReminder(String User, Integer x){
		Vector remList = (Vector) reminders.get(User);
		if (remList.get(x) !=null){
	            remList.removeElementAt(x);
	            String s = ("Reminder (" + x + ") has been removed/updated \r\n");
	            return s;
	    }else{
		String e = "Error there is no reminder (" + x + ")! \r\n";
		return e;
	    }
	}
}
