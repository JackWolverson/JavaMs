package msgServer;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;


public class RegisterCommand implements Command  {
    private BufferedWriter out;
    private BufferedReader in;
    private MsgSvrConnection conn;

    public RegisterCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn){
        this.in = in;
        this.out = out;
        this.conn = serverConn;
    }

    public void execute() throws IOException {
        //reads information from the user
        //validation of format for inputs
        String username = in.readLine();
        if(conn.getServer().isValidUser(username)) {
            (new ErrorCommand(in, out, conn, "Username already in use")).execute();
        }else {
            String password = in.readLine();
            String confirmPassword = in.readLine();
            if (!password.equals(confirmPassword)) {
                (new ErrorCommand(in, out, conn, "Password mismatch")).execute();
            } else {
                String DOB = in.readLine();
                if (!DOB.matches("\\d{1,2}/\\d{1,2}/\\d{4}$")) {
                    (new ErrorCommand(in, out, conn, "Date Format incorrect")).execute();
                } else {
                    String phone = in.readLine();
                    if (!phone.matches("\\d{11}")) {
                        (new ErrorCommand(in, out, conn, "Phone number format incorrect")).execute();
                    } else {
                        String address = in.readLine();
                        if(address.length() < 1){
                            (new ErrorCommand(in, out, conn, "Please add address")).execute();
                        }else {
                            String cityTown = in.readLine();
                            if (cityTown.length() < 1) {
                                (new ErrorCommand(in, out, conn, "Please add city/Town")).execute();
                            } else {
                                String postcode = in.readLine();
                                if (postcode.length() < 0) {
                                    (new ErrorCommand(in, out, conn, "Please add postcode")).execute();
                                } else {
                                    //creates sql statement to insert the data into the datbase
                                    String registerSQL = "INSERT INTO userDetails (Username, DoB, PhoneNumber, Address, CityTown, PostCode)"
                                            + "values ('" + username + "', '" + DOB + "', '" + phone + "', '" + address + "', '" + cityTown + "', '" + postcode + "')";
                                    //sets the user on the server for what the user just entered
                                    conn.getServer().setUser(username, password);
                                    //writes the username and password to file
                                    FileOutputStream fout;
                                    fout = new FileOutputStream("src\\pwd.txt", true);
                                    String reg = "\r\n" + username + "=" + password;
                                    fout.write(reg.getBytes());
                                    fout.flush();
                                    fout.close();
                                    //calls the db's main class to execute sql statements on the db
                                    DBDriver.main(registerSQL);
                                    out.write("200\r\n");
                                    out.flush();
                                }
                            }
                        }
                    }
                }

            }

        }
    }
}