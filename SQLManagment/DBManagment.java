package SQLManagment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManagment {
    private static final String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/xwiqrbxu";
    private static final String user = "xwiqrbxu";
    private static final String pass = "bmUBFybRD8eOI5qAa4kt4tQ_ld_j3TWQ";

    public DBManagment() {}

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Polaczenie z baza danych OK ! ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

//    public static String addPatient()
}
