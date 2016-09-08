/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.service;

import com.siap.rasi.pojo.Usuario;
import com.siap.rasi.util.DbSingleton;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "usuarioService")
@ApplicationScoped
public class UsuarioService {

    public UsuarioService() {
    }

    private Usuario getUser(String username) {
        try (Connection connection = DbSingleton.getConnection();
                PreparedStatement ps = connection.prepareStatement("select * from v1_2.usuario where username = ? and activo = 1")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Usuario(rs.getString(1), rs.getString(2), rs.getBoolean(3));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean login(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        Usuario user = getUser(username);
        if (user == null) {
            return false;
        }
        boolean result = user.getPassword().equals(sha1(password));
        return result;
    }

    public static String sha1(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(s.getBytes("utf8"));
        byte[] digestBytes = digest.digest();
        return javax.xml.bind.DatatypeConverter.printHexBinary(digestBytes);
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
    }
}
