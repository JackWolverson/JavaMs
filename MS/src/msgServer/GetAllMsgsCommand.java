package msgServer;
/**
    Last edited: 20/03/17 by Aaron Jackson
    Class to print out all unread messages sent to user
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GetAllMsgsCommand implements Command {
    private BufferedReader in;
    private BufferedWriter out;
    private MsgSvrConnection conn;

    public GetAllMsgsCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
        this.in = in;
        this.out = out;
        this.conn = serverConn;
    }

    public void execute() throws IOException {
        //declare a variable user of type String and use it to get the user from the inputstream
        String user = in.readLine();
        //check if current user is not equal to null and current user is equal to the user (use the method getCurrentUser())
        if(!user.equals(null) && conn.getCurrentUser().equals(user)){
            //intialise an array (msgs) that is used to hold all the messages read and set it's initialised value to null
            Message[] msgs = null;
            //use the method getAllMessages(user) to populate the msgs array
            msgs = conn.getServer().getMessages().getAllMessages(user);
            //check if msgs is not equal to null
            if(!msgs.equals(null)){
                //write to the OutputStream the message status code as specified in the protocol
                out.write("" + MsgProtocol.MESSAGE + "\r\n");
                //write the length of the messages returned
                out.write("" + msgs.length + "\r\n");
                //Loop through the messages and write the sender, date and cotent to the outputstream (use provided methods)
                for(Message msg : msgs){
                    out.write(msg.getSender() + "\r\n");
                    out.write(msg.getDate() + "\r\n");
                    out.write(msg.getContent() + "\r\n");
                }
                //Flush the outputstream
                out.flush();
            }
            //capture adequet errors (No messages) or (You are not logged on)
            else {(new ErrorCommand(in, out, conn, "You have no messages")).execute();}
        }
        else {(new ErrorCommand(in, out, conn, "You are not logged in")).execute();}
    }

}
