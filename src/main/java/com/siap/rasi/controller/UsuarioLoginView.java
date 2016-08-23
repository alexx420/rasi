/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.controller;

import com.siap.rasi.service.UsuarioService;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rafael.esquivel
 */
@ManagedBean(name = "usuarioLoginView")
@ViewScoped
public class UsuarioLoginView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private boolean logeado = false;

    @ManagedProperty("#{usuarioService}")
    private UsuarioService service;

    @PostConstruct
    public void init() {
    }

    public void setService(UsuarioService service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        try {
            if (username != null && username != null
                    && service.login(username, password)) {
                logeado = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", username);
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", username);
            } else {
                logeado = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de inicio de sesión",
                        "Credenciales no válidas");
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) {
            Logger.getLogger(UsuarioLoginView.class.getName()).log(Level.SEVERE, null, e);
            msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el servidor",
                    e.getMessage());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", logeado);
        if (logeado) {
            context.addCallbackParam("view", "solicitud.xhtml");
        } else {
            context.addCallbackParam("view", "login.xhtml");
        }
    }

    public void logout() {
        RequestContext context = RequestContext.getCurrentInstance();
        service.logout();
        logeado = false;
        context.addCallbackParam("view", "index.xhtml");
    }

}
