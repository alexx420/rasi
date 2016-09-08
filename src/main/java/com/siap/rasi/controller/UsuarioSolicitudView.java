/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siap.rasi.controller;

/**
 *
 * @author rafael.esquivel
 */
import com.siap.rasi.pojo.EntidadFederativa;
import com.siap.rasi.pojo.Ocupacion;
import com.siap.rasi.pojo.Pais;
import com.siap.rasi.pojo.UsuarioSolicitud;
import com.siap.rasi.service.UsuarioSolicitudService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "solicitanteView")
@ViewScoped
public class UsuarioSolicitudView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<UsuarioSolicitud> rows;
    private List<UsuarioSolicitud> selectedRows;
    private List<UsuarioSolicitud> filteredRows;
    private UsuarioSolicitud selectedRow;

    private Long id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String sexo;
    private String ocupacion;
    private String pais;
    private String correo;
    private boolean recibirInformacion;
    private String telefonoCelular;
    private String telefonoFijo;
    private String institucion;
    private String entidadFederativa;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isRecibirInformacion() {
        return recibirInformacion;
    }

    public void setRecibirInformacion(boolean recibirInformacion) {
        this.recibirInformacion = recibirInformacion;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getEntidadFederativa() {
        return entidadFederativa;
    }

    public void setEntidadFederativa(String entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }

    @ManagedProperty("#{solicitanteService}")
    private UsuarioSolicitudService service;
    @ManagedProperty("#{solicitudView}")
    private SolicitudInformacionView siv;

    @PostConstruct
    public void init() {
        rows = service.listRows();
        username = SessionUtils.getUserName();
    }

    public SolicitudInformacionView getSiv() {
        return siv;
    }

    public void setSiv(SolicitudInformacionView siv) {
        this.siv = siv;
    }

    public String getUsername() {
        return username;
    }

    public UsuarioSolicitud getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(UsuarioSolicitud selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<UsuarioSolicitud> getRows() {
        return rows;
    }

    public List<Ocupacion> getOcupaciones() {
        return service.getOcupaciones();
    }

    public List<EntidadFederativa> getEntidades() {
        return service.getEntidades();
    }

    public List<Pais> getPaises() {
        return service.getPaises();
    }

    public void setRows(List<UsuarioSolicitud> rows) {
        this.rows = rows;
    }

    public List<UsuarioSolicitud> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(List<UsuarioSolicitud> selectedRows) {
        this.selectedRows = selectedRows;
    }

    public List<UsuarioSolicitud> getFilteredRows() {
        return filteredRows;
    }

    public void setFilteredRows(List<UsuarioSolicitud> filteredRows) {
        this.filteredRows = filteredRows;
    }

    public void setService(UsuarioSolicitudService service) {
        this.service = service;
    }

    public void onRowSelect(SelectEvent event) {
        UsuarioSolicitud us = (UsuarioSolicitud) event.getObject();
        siv.setRows(siv.getService().listRows(us.getId()));
        siv.setUs(us);//setea el solicitante seleccionado, para poder usarlo en la vista solicitud info
        FacesMessage msg = new FacesMessage("Mostrando solicitudes de", "ID: " + us.getId() + ", " + us.getNombres());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        UsuarioSolicitud currentCar = rows.get(event.getRowIndex());
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg;
            try {
                service.updateRow(currentCar);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición correcta", "Anterior: " + oldValue + ", Nuevo:" + newValue);
            } catch (Exception e) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falló la edición", e.getClass().getName());
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        UsuarioSolicitud us = (UsuarioSolicitud) event.getObject();
        try {
            String sessionUserName = SessionUtils.getUserName();
            String userNameToDelete = us.getUsername();
            if (userNameToDelete.equals(sessionUserName)) {
                service.updateRow(us);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición correcta", "ID: " + String.valueOf(us.getId())));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Solo el usuario '" + us.getUsername() + "' puede editar el registro con ID: " + us.getId()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getClass().getName()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        UsuarioSolicitud si = (UsuarioSolicitud) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edición cancelada", String.valueOf(si.getId())));
    }

    public void addRow() {
        UsuarioSolicitud us = new UsuarioSolicitud(0L, nombres, apellidoPaterno,
                apellidoMaterno, sexo, ocupacion, pais, entidadFederativa, institucion, telefonoFijo, telefonoCelular, correo, recibirInformacion);
        us = service.addRow(us);
        rows.add(0, us);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario agregado", "ID: " + us.getId()));
    }

    public void deleteSelected() {
        int r = 0;
        if (selectedRows.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No hay registros seleccionados"));
        } else {
            String sessionUserName = SessionUtils.getUserName();
            for (UsuarioSolicitud row : selectedRows) {
                try {
                    String userNameToDelete = row.getUsername();
                    if (userNameToDelete.equals(sessionUserName)) {
                        service.deleteRow(row.getId());
                        rows.remove(row);
                        r++;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Solo el usuario '" + row.getUsername() + "' puede eliminar el registro con ID: " + row.getId()));
                    }
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getClass().getName()));
                }
            }
            selectedRows.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuarios eliminados", String.valueOf(r)));
        }
    }

    public void deleteRow() {
        String userNameToDelete = selectedRow.getUsername();
        String sessionUserName = SessionUtils.getUserName();
        if (!userNameToDelete.equals(sessionUserName)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Solo el usuario que capturó el registro puede eliminarlo"));
            return;
        }

        long idRow = selectedRow.getId();
        try {
            service.deleteRow(selectedRow.getId());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getClass().getName()));
        }
        rows.remove(selectedRow);
        selectedRow = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", "ID: " + String.valueOf(idRow)));
    }
}
