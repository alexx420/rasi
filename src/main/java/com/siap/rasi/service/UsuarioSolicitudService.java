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
import com.siap.rasi.pojo.Usuario;
import com.siap.rasi.pojo.UsuarioSolicitud;
import com.siap.rasi.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "solicitanteService")
@ApplicationScoped
public class UsuarioSolicitudService {

    public UsuarioSolicitudService() {
    }

    public List<UsuarioSolicitud> listRows() {
        List<UsuarioSolicitud> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM UsuarioSolicitud").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return list;
    }

//    public List<ViaSolicitud> getViasSolicitud() {
//        List<ViaSolicitud> list = new ArrayList<>();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            list = session.createQuery("FROM ViaSolicitud ORDER BY nombre").list();
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//        } finally {
//            session.close();
//        }
//        return list;
//    }
//
//    public List<TipoInformacion> getTiposInformacion() {
//        List<TipoInformacion> list = new ArrayList<>();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            list = session.createQuery("FROM TipoInformacion ORDER BY nombre").list();
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//        } finally {
//            session.close();
//        }
//        return list;
//    }
//
//    public List<Direccion> getDirecciones() {
//        List<Direccion> list = new ArrayList<>();
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            list = session.createQuery("FROM Direccion ORDER BY nombre").list();
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//        } finally {
//            session.close();
//        }
//        return list;
//    }
//
    public List<EntidadFederativa> getEntidades() {
        List<EntidadFederativa> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM EntidadFederativa ORDER BY nombre").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return list;
    }

    public List<Pais> getPaises() {
        List<Pais> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM Pais ORDER BY nombre").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return list;
    }

    public List<Ocupacion> getOcupaciones() {
        List<Ocupacion> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM Ocupacion ORDER BY nombre").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return list;
    }

    public UsuarioSolicitud addRow() {

        Usuario usuario = new Usuario();
        usuario.setUsername(SessionUtils.getUserName());
        UsuarioSolicitud si = new UsuarioSolicitud();
        si.setUsuario(usuario);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Long carID = null;
        try {
            tx = session.beginTransaction();
            carID = (Long) session.save(si);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        si.setId(carID);
        return si;
    }

    public void updateRow(UsuarioSolicitud us) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(us);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void deleteRow(Long id) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            UsuarioSolicitud car = (UsuarioSolicitud) session.get(UsuarioSolicitud.class, id);
            session.delete(car);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

}
