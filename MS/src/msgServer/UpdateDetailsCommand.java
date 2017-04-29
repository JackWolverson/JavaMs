package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;

public class UpdateDetailsCommand implements Command {
	private BufferedWriter out;
	private BufferedReader in;
	private MsgSvrConnection conn;

	public UpdateDetailsCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void execute() throws IOException {

		if (conn.getCurrentUser() != null) {

			String username1 = conn.getCurrentUser();
			String password1 = conn.getServer().getUserPassword(username1);

			File f1 = null;
			File f2 = null;
			FileInputStream fis = null;
			BufferedReader br = null;

			f1 = new File("src\\pwd.txt");
			f2 = new File("src\\temp_pwd.txt");
			fis = new FileInputStream(f1);
			br = new BufferedReader(new InputStreamReader(fis));
			PrintWriter fw = new PrintWriter(new FileWriter(f2, true));
			String line;
			
			out.write("Enter your new details: \r\n");
			out.write("Username: \r\n");
			out.flush();
			String username = in.readLine();
			out.write("Password: \r\n");
			out.flush();
			String password = in.readLine();
			out.write("Date of Birth: \r\n");
			out.flush();
			String DOB = in.readLine();
			/*
			 * if(!DOB.matches("\\d{1,2}\\\\d{1,2}\\\\d{4}$")){
			 * out.write("Date Format Incorrect\r\n"); out.flush(); }
			 */
			out.write("Phone number: \r\n");
			out.flush();
			String phone = in.readLine();
			/*
			 * if(!phone.matches("\\d{11}")){
			 * out.write("Phone number Incorrect\r\n"); out.flush(); }
			 */
			out.write("Address: \r\n");
			out.flush();
			String address = in.readLine();
			out.write("City / Town \r\n");
			out.flush();
			String cityTown = in.readLine();
			out.write("Postcode: \r\n");
			out.flush();
			String postcode = in.readLine();
			String UpdateSQL = "UPDATE userDetails " + "SET  Username = " + "'" + username + "'" + ", DoB = " + "'"
					+ DOB + "'" + ", PhoneNumber=" + "'" + phone + "'" + ", Address=" + "'" + address + "'"
					+ ", CityTown=" + "'" + cityTown + "'" + ", PostCode=" + "'" + postcode + "'" + "WHERE Username = "
					+ "'" + username1 + "'" + ";";

			if (password != null && username != null) {

				while ((line = br.readLine()) != null) {
					if (line.contains(username1) && line.contains(password1)) {
						line = line.replace(username1, username);
						line = line.replace(password1, password);

					}
					fw.println(line);
				}

				br.close();
				fis.close();
				fw.flush();
				fw.close();

				DBDriver.main(UpdateSQL);
				f1.delete();
				f2.renameTo(new File("src\\pwd.txt"));
				conn.setCurrentUser(username);

				out.write("200\r\n");
				out.write("Details updated \r\n");
				out.flush();
			} else {
				(new ErrorCommand(in, out, conn, "You are not logged in!")).execute();
			}

		}
	}
}