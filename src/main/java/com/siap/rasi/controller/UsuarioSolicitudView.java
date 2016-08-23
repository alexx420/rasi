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

    @ManagedProperty("#{solicitanteService}")
    private UsuarioSolicitudService service;
    @ManagedProperty("#{solicitudView}")
    private SolicitudInformacionView siv;
    private String username;

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
        FacesMessage msg = new FacesMessage("Mostrando solicitudes", "usuario ID: " + ((UsuarioSolicitud) event.getObject()).getId().toString());
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
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falló la edición", e.getMessage());
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        UsuarioSolicitud si = (UsuarioSolicitud) event.getObject();
        try {
            String sessionUserName = SessionUtils.getUserName();
            String userNameToDelete = si.getUsername();
            if (userNameToDelete.equals(sessionUserName)) {
                service.updateRow(si);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición correcta", "ID: " + String.valueOf(si.getId())));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Solo el usuario que capturó el registro puede editarlo"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        UsuarioSolicitud si = (UsuarioSolicitud) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edición cancelada", String.valueOf(si.getId())));
    }

    public void addRow() {
        UsuarioSolicitud us = service.addRow();
        rows.add(0, us);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro agregado", "ID: " + us.getId()));
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
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Solo el usuario que capturó el registro puede eliminarlo"));
                    }
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
                }
            }
            selectedRows.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registros eliminados", String.valueOf(r)));
        }
    }

    public void deleteRow() {
        String userNameToDelete = selectedRow.getUsername();
        String sessionUserName = SessionUtils.getUserName();
        if (!userNameToDelete.equals(sessionUserName)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Solo el usuario que capturó el registro puede eliminarlo"));
            return;
        }

        long id = selectedRow.getId();
        try {
            service.deleteRow(selectedRow.getId());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
        rows.remove(selectedRow);
        selectedRow = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", "ID: " + String.valueOf(id)));
    }
}
