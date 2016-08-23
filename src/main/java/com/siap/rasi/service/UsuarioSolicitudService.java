/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.service;

import com.siap.rasi.controller.SessionUtils;
import com.siap.rasi.pojo.EntidadFederativa;
import com.siap.rasi.pojo.Ocupacion;
import com.siap.rasi.pojo.Pais;
import com.siap.rasi.pojo.UsuarioSolicitud;
import com.siap.rasi.util.DbSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "solicitanteService")
@ApplicationScoped
public class UsuarioSolicitudService {

    public UsuarioSolicitudService() {
    }

    public List<UsuarioSolicitud> listRows() {
        List<UsuarioSolicitud> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.UsuarioSolicitud");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UsuarioSolicitud(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getBoolean(14)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<EntidadFederativa> getEntidades() {
        List<EntidadFederativa> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.EntidadFederativa ORDER BY nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new EntidadFederativa(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<Pais> getPaises() {
        List<Pais> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.pais ORDER BY nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Pais(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<Ocupacion> getOcupaciones() {
        List<Ocupacion> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.ocupacion ORDER BY nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Ocupacion(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public UsuarioSolicitud addRow() {

        UsuarioSolicitud us = new UsuarioSolicitud(0L, "", "", "", "Masculino", "", "", "", "", "", "", "");
        us.setUsername(SessionUtils.getUserName());
        try (Connection conn = DbSingleton.getConnection(); Statement stmt = conn.createStatement();) {
            String query = "insert into v1_2.usuarioSolicitud (nombres,apellidoPaterno,"
                    + "apellidoMaterno,sexo,ocupacion,pais,correo,telefonoCelular,"
                    + "telefonoFijo,institucion,entidadFederativa,username,recibirInformacion)"
                    + " values ('" + us.getNombres() + "','" + us.getApellidoPaterno() + "','" + us.getApellidoMaterno()
                    + "','" + us.getSexo() + "','" + us.getOcupacion() + "','" + us.getPais() + "','" + us.getCorreo()
                    + "','" + us.getTelefonoCelular() + "','" + us.getTelefonoFijo() + "','" + us.getInstitucion()
                    + "','" + us.getEntidadFederativa() + "','" + us.getUsername() + "','" + us.isRecibirInformacion() + "')";
            int i = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println("insert");
            System.out.println(i);
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                while (rs.next()) {
                    long l = rs.getLong(1);
                    us.setId(l);
                    System.out.println("id");
                    System.out.println(l);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return us;
    }

    public void updateRow(UsuarioSolicitud us) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("update v1_2.UsuarioSolicitud set nombres=?,"
                    + "apellidoPaterno=?,"
                    + "apellidoMaterno=?,"
                    + "sexo=?,"
                    + "ocupacion=?,"
                    + "pais=?,"
                    + "correo=?,"
                    + "telefonoCelular=?,"
                    + "telefonoFijo=?,"
                    + "institucion=?,"
                    + "entidadFederativa=?,"
                    + "recibirInformacion=?,"
                    + "username=? where id = ?");
            ps.setLong(14, us.getId());
            ps.setString(1, us.getNombres());
            ps.setString(2, us.getApellidoPaterno());
            ps.setString(3, us.getApellidoMaterno());
            ps.setString(4, us.getSexo());
            ps.setString(5, us.getOcupacion());
            ps.setString(6, us.getPais());
            ps.setString(7, us.getCorreo());
            ps.setString(8, us.getTelefonoCelular());
            ps.setString(9, us.getTelefonoFijo());
            ps.setString(10, us.getInstitucion());
            ps.setString(11, us.getEntidadFederativa());
            ps.setString(12, us.getUsername());
            ps.setBoolean(13, us.isRecibirInformacion());
            int i = ps.executeUpdate();
            System.out.println("update");
            System.out.println(i);
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteRow(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("delete from v1_2.UsuarioSolicitud where id = ?");
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            System.out.println("delete");
            System.out.println(i);
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioSolicitudService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
