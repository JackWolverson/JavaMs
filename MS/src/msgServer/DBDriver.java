package msgServer;
import java.sql.*;
/**
 * Created by Jack on 14/04/2017.
 * NEEDS FURTHER IMPLEMENTATION BUT THIS IS THE MAIN BODY FOR CONNECTING TO A DATABASE
 */
public class DBDriver {
    public static void main(String args){
        try{
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src//javaDB.mdb");
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM userDetails";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
                System.out.println(rs.getString(6));

            }

            statement.clearBatch();
            //String test = "INSERT INTO userDetails(Username, DoB, PhoneNumber, Address, CityTown, Postcode) " +
            //        "VALUES('wolf', '220197','01234567890','qwe','qwert','1223')";
            //statement.execute(test);   //test to write to the database
            //System.out.println("test successful");
            statement.execute(args);
            //above line needs changing so it can access your string
            statement.close();
            conn.close();

        }catch(Exception e){
            System.out.println("Exception" + e);
        }

    }
}
