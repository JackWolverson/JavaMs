package msgServer;

public class MsgProtocol {
    /*
     * The location of the password file.
     */
    public static final String PASSWORD_FILE = "src\\pwd.txt";

  /* -------------- Commands --------------- */
    /**
     * client requests to login
     * Following lines are
     * username\r\n
     * password\r\n
     */
    public static final int LOGIN = 101;
    /**
     * Client requests logout
     * following line is:
     * username\r\n
     */
    public static final int LOGOUT = 102;
    /**
     * client requests send a message
     * Following lines are:
     * sender name\r\n
     * recipient name\r\n
     * content\r\n
     */
    public static final int SEND = 103;
    /**
     * client requests the number of messages
     * following lines are
     * username\r\n
     */
    public static final int MESSAGES_AVAILABLE = 104;
    /**
     * Client requests to get a single message
     * Following lines are:
     * username\r\n
     */
    public static final int GET_NEXT_MESSAGE = 105;
    /**
     * Client requests to get all messages
     * Following lines are:
     * username\r\n
     * Server reponds by sending all messages for that user
     */
    public static final int GET_ALL_MESSAGES = 106;
    
    public static final int SET_REMINTER = 107;
    /**
     * Client requests to set a reminder
     * Following lines are:
     * username\r\n
     * contents of the reminder \r\n
     * dd MM yyyy hh mm \r\n (day month year hour minute)
     * Server responds by setting a reminder for that date and hour
     * 
     * 
     */

    public static final int REGISTER = 108;
    /**
     * Client requests to register
     * Following lines are:
     * username\r\n
     * password\r\n
     * confirmPassword\r\n
     * date of birth\r\n
     * phone number\r\n
     * address\r\n
     * city/town\r\n
     * postcode\r\n
     * server then creates the user
     */
    
    public static final int UPDATE_DETAILS = 109;
    /**
     * Client requests to update their details
     * following lines are:
     * username \r\n
     * password \r\n
     * DOB \r\n
     * PhoneNumber \r\n
     * Address \r\n
     * CityTown \r\n
     * PostCode \r\n
     * server responds by changing the users registration details and
     * setting the new currentUser.
     */
    
    public static final int UPDATE_REMINDER = 110;
  /* -------------- Responses --------------- */
    /**
     * Server reponds OK
     */
    public static final int OK = 200;
    /**
     * Server reponds by sending one or more messages
     * following will be
     * An integer specifying number of messages terminated by \r\n
     * Then repeated for the number of messages are
     * sender terminated by \r\n
     * content terminated by \r\n
     */
    public static final int MESSAGE = 201;
    public static final int REMINDER = 701;
    /**
     * The server sends an error message
     * Requires a one line error message terminated by \r\n
     */
    public static final int ERROR = 500;
}
