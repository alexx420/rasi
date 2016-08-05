/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.service;

import com.siap.rasi.pojo.SolicitudInformacion;
import com.siap.rasi.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "solicitudService")
@ApplicationScoped
public class SolicitudInformacionService {

    public SolicitudInformacionService() {
    }

    public List<SolicitudInformacion> listRows() {
        List<SolicitudInformacion> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM SolicitudInformacion").list();
            System.out.println("//listrows");
            System.out.println(list);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    public SolicitudInformacion addRow() {
        SolicitudInformacion car = new SolicitudInformacion();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Long carID = null;
        try {
            tx = session.beginTransaction();
            carID = (Long) session.save(car);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        car.setId(carID);
        return car;
    }

    public void updateRow(SolicitudInformacion car) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(car);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteRow(Long id) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            SolicitudInformacion car = (SolicitudInformacion) session.get(SolicitudInformacion.class, id);
            session.delete(car);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
