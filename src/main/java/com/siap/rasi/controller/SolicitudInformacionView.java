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
import com.siap.rasi.pojo.Direccion;
import com.siap.rasi.pojo.EntidadFederativa;
import com.siap.rasi.pojo.Ocupacion;
import com.siap.rasi.pojo.SolicitudInformacion;
import com.siap.rasi.pojo.TipoInformacion;
import com.siap.rasi.pojo.ViaSolicitud;
import com.siap.rasi.service.SolicitudInformacionService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "solicitudView")
@ViewScoped
public class SolicitudInformacionView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<SolicitudInformacion> rows;
    private List<SolicitudInformacion> selectedRows;
    private List<SolicitudInformacion> filteredRows;
    private SolicitudInformacion selectedRow;

    @ManagedProperty("#{solicitudService}")
    private SolicitudInformacionService service;
    private String username;

    @PostConstruct
    public void init() {
        rows = service.listRows();
        username = SessionUtils.getUserName();
    }

    public String getUsername() {
        return username;
    }

    public SolicitudInformacion getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(SolicitudInformacion selectedRow) {
        this.selectedRow = selectedRow;
    }

    public List<SolicitudInformacion> getRows() {
        return rows;
    }

    public List<ViaSolicitud> getViasSolicitud() {
        return service.getViasSolicitud();
    }

    public List<Direccion> getDirecciones() {
        return service.getDirecciones();
    }

    public List<TipoInformacion> getTiposInformacion() {
        return service.getTiposInformacion();
    }

    public List<Ocupacion> getOcupaciones() {
        return service.getOcupaciones();
    }

    public List<EntidadFederativa> getEntidades() {
        return service.getEntidades();
    }

    public void setRows(List<SolicitudInformacion> rows) {
        this.rows = rows;
    }

    public List<SolicitudInformacion> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(List<SolicitudInformacion> selectedRows) {
        this.selectedRows = selectedRows;
    }

    public List<SolicitudInformacion> getFilteredRows() {
        return filteredRows;
    }

    public void setFilteredRows(List<SolicitudInformacion> filteredRows) {
        this.filteredRows = filteredRows;
    }

    public void setService(SolicitudInformacionService service) {
        this.service = service;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        SolicitudInformacion currentCar = rows.get(event.getRowIndex());
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
        Behavior behavior = event.getBehavior();
        System.out.println("//" + behavior);
        SolicitudInformacion si = (SolicitudInformacion) event.getObject();
        try {
            String sessionUserName = SessionUtils.getUserName();
            String userNameToDelete = si.getUserName();
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
        SolicitudInformacion si = (SolicitudInformacion) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edición cancelada", String.valueOf(si.getId())));
    }

    public void addRow() {
        SolicitudInformacion car = service.addRow();
        rows.add(0, car);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro agregado", "ID: " + car.getId()));
    }

    public void deleteSelected() {
        int r = 0;
        if (selectedRows.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No hay registros seleccionados"));
        } else {
            String sessionUserName = SessionUtils.getUserName();
            for (SolicitudInformacion row : selectedRows) {
                try {
                    String userNameToDelete = row.getUserName();
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
        String userNameToDelete = selectedRow.getUserName();
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
