
import com.siap.rasi.pojo.SolicitudInformacion;
import com.siap.rasi.pojo.Usuario;
import com.siap.rasi.util.DbSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael.esquivel
 */
public class NewMain {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
//        try (Connection connection = DbSingleton.getConnection(); PreparedStatement ps = connection.prepareStatement("select * from usuario where username=?")) {
//            ps.setString(1, "admin");
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Usuario usuario = new Usuario(rs.getString(1), rs.getString(2), rs.getBoolean(3));
//                    System.out.println(usuario);
//                }
//            }
//        }
//        try (Connection connection = DbSingleton.getConnection(); PreparedStatement ps = connection.prepareStatement("select * from usuario"); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Usuario usuario = new Usuario(rs.getString(1), rs.getString(2), rs.getBoolean(3));
//                System.out.println(usuario);
//            }
//        }
        try (Connection connection = DbSingleton.getConnection(); PreparedStatement ps = connection.prepareStatement("select * from v1_2.solicitudInformacion"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SolicitudInformacion si = new SolicitudInformacion(rs.getLong(1), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getLong(8), rs.getString(9));
                System.out.println(si);
            }
        }
    }

}
