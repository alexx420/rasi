/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.siap.rasi.util.HibernateUtil;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author rafael.esquivel
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  // load the driver
            // line below needs to be modified to include the database name, user, and password (if any)
            DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=rasi;user=sa;password=Password123;");

            System.out.println("Connected to database !");

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List list = session.createQuery("FROM Usuario").list();
            System.out.println("//main");
            System.out.println(list);

        } catch (SQLException sqle) {
            System.out.println("Sql Exception :" + sqle.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception :" + e.getMessage());
        }
    }

}
