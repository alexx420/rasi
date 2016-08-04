/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.service;

import com.siap.rasi.pojo.Usuario;
import com.siap.rasi.util.HibernateUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "usuarioService")
@ApplicationScoped
public class UsuarioService {

    public UsuarioService() {
    }

    public List<Usuario> listUsers() {
        List<Usuario> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List cars = session.createQuery("FROM Usuario").list();
            for (Iterator iterator = cars.iterator(); iterator.hasNext();) {
                Usuario user = (Usuario) iterator.next();
                list.add(user);
            }
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

    public Long addUser(Usuario user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Long userID = null;
        try {
            tx = session.beginTransaction();
            userID = (Long) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userID;
    }

    public void updateUser(Usuario user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
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

    public void deleteUser(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Usuario user = (Usuario) session.get(Usuario.class, id);
            session.delete(user);
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

    private Usuario getUser(String username) {
        Usuario u = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Usuario a where a.username = :user");
            query.setParameter("user", username);
            List<Usuario> list = query.list();
            if (list.isEmpty()) {
                return null;
            }
            u = list.get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return u;
    }

    public boolean login(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Usuario user = getUser(username);
        if (user == null) {
            return false;
        }
        boolean result = user.getPassword().equals(sha1(password));
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.setAttribute("username", username);
        return result;
    }

    public static String getSessionUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        String attribute = (String) session.getAttribute("username");
        return attribute;
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
