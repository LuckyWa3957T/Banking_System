import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    public static Connection con;
    public static void connect(){
        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank",
                    "root",
                    "122626");  // apna mysql password
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
