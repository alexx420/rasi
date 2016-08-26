/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.service;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.siap.rasi.controller.SessionUtils;
import com.siap.rasi.pojo.Direccion;
import com.siap.rasi.pojo.SolicitudInformacion;
import com.siap.rasi.pojo.TipoInformacion;
import com.siap.rasi.pojo.UsuarioSolicitud;
import com.siap.rasi.pojo.ViaSolicitud;
import com.siap.rasi.util.DbSingleton;
import com.siap.rasi.util.Tool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "solicitudService")
@ApplicationScoped
public class SolicitudInformacionService {

    public SolicitudInformacionService() {
    }

    public List<SolicitudInformacion> listRows() {
        List<SolicitudInformacion> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.solicitudInformacion");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SolicitudInformacion(rs.getLong(1), rs.getDate(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6),
                        rs.getString(7), rs.getLong(8), rs.getString(9)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<SolicitudInformacion> listRows(Long id) {
        List<SolicitudInformacion> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.solicitudInformacion where idUsuarioSolicitud = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SolicitudInformacion(rs.getLong(1), rs.getDate(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6),
                        rs.getString(7), rs.getLong(8), rs.getString(9)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<ViaSolicitud> getViasSolicitud() {
        List<ViaSolicitud> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.ViaSolicitud ORDER BY nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ViaSolicitud(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<TipoInformacion> getTiposInformacion() {
        List<TipoInformacion> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = DbSingleton.getConnection();
            ps = conn.prepareStatement("select * from v1_2.tipoInformacion ORDER BY nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TipoInformacion(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<Direccion> getDirecciones() {
        List<Direccion> list = new ArrayList<>();
        try (Connection conn = DbSingleton.getConnection(); PreparedStatement ps = conn.prepareStatement("select * from v1_2.Direccion ORDER BY nombre"); ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                list.add(new Direccion(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public SolicitudInformacion addRow(UsuarioSolicitud us) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        SolicitudInformacion si = new SolicitudInformacion(Calendar.getInstance().getTime(), "", "", "", us.getId(), SessionUtils.getUserName());
        try (Connection conn = DbSingleton.getConnection(); Statement stmt = conn.createStatement();) {
            String query = "insert into v1_2.solicitudInformacion (fecha,tipoInformacion,"
                    + "especifiqueInformacion,viaSolicitud,"
                    + "idUsuarioSolicitud,username) values ('" + sdf.format(si.getFecha()) + "','" + si.getTipoInformacion() + "','" + si.getEspecifiqueInformacion()
                    + "','" + si.getViaSolicitud() + "','" + si.getIdUsuarioSolicitud()
                    + "','" + si.getUsername() + "')";
            int i = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println("insert");
            System.out.println(i);
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                while (rs.next()) {
                    long l = rs.getLong(1);
                    si.setId(l);
                    System.out.println("id");
                    System.out.println(l);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
        return si;
    }

    public void updateRow(SolicitudInformacion si) throws SQLException {

        try (Connection conn = DbSingleton.getConnection(); PreparedStatement ps
                = conn.prepareStatement("update v1_2.solicitudInformacion set fecha=?,"
                        + "tipoInformacion=?,"
                        + "especifiqueInformacion=?,"
                        + "viaSolicitud=?,"
                        + "fechaAtencion=?,"
                        + "atendioSolicitud=?,"
                        + "idUsuarioSolicitud=?,"
                        + "username=? where id = ?");) {
            ps.setLong(9, si.getId());
            ps.setDate(1, Tool.toSqlDate(si.getFecha()));
            ps.setString(2, si.getTipoInformacion());
            ps.setString(3, si.getEspecifiqueInformacion());
            ps.setString(4, si.getViaSolicitud());
            ps.setDate(5, Tool.toSqlDate(si.getFechaAtencion()));
            ps.setString(6, si.getAtendioSolicitud());
            ps.setLong(7, si.getIdUsuarioSolicitud());
            ps.setString(8, si.getUsername());
            int i = ps.executeUpdate();
            System.out.println("update");
            System.out.println(i);
        } catch (SQLException ex) {
            Logger.getLogger(SolicitudInformacionService.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
    }

    public void deleteRow(Long id) throws SQLException {
        try (Connection conn = DbSingleton.getConnection(); PreparedStatement ps = conn.prepareStatement("delete from v1_2.SolicitudInformacion where id = ?");) {
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            System.out.println("delete");
            System.out.println(i);
        } catch (SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
    }

}
