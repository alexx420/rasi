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
import com.siap.rasi.pojo.SolicitudInformacion;
import com.siap.rasi.pojo.TipoInformacion;
import com.siap.rasi.pojo.UsuarioSolicitud;
import com.siap.rasi.pojo.ViaSolicitud;
import com.siap.rasi.service.SolicitudInformacionService;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
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
    private UsuarioSolicitud us;

    private Long id;
    private Date fecha;
    private String tipoInformacion;
    private String especifiqueInformacion;
    private String viaSolicitud;
    private Date fechaAtencion;
    private String atendioSolicitud;
    private String username;
    private Long idUsuarioSolicitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoInformacion() {
        return tipoInformacion;
    }

    public void setTipoInformacion(String tipoInformacion) {
        this.tipoInformacion = tipoInformacion;
    }

    public String getEspecifiqueInformacion() {
        return especifiqueInformacion;
    }

    public void setEspecifiqueInformacion(String especifiqueInformacion) {
        this.especifiqueInformacion = especifiqueInformacion;
    }

    public String getViaSolicitud() {
        return viaSolicitud;
    }

    public void setViaSolicitud(String viaSolicitud) {
        this.viaSolicitud = viaSolicitud;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getAtendioSolicitud() {
        return atendioSolicitud;
    }

    public void setAtendioSolicitud(String atendioSolicitud) {
        this.atendioSolicitud = atendioSolicitud;
    }

    public Long getIdUsuarioSolicitud() {
        return idUsuarioSolicitud;
    }

    public void setIdUsuarioSolicitud(Long idUsuarioSolicitud) {
        this.idUsuarioSolicitud = idUsuarioSolicitud;
    }

    @ManagedProperty("#{solicitudService}")
    private SolicitudInformacionService service;

    @PostConstruct
    public void init() {
        rows = service.listRows();
        username = SessionUtils.getUserName();
    }

    public UsuarioSolicitud getUs() {
        return us;
    }

    public void setUs(UsuarioSolicitud us) {
        this.us = us;
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

    public SolicitudInformacionService getService() {
        return service;
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
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falló la edición", e.getClass().getName());
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        SolicitudInformacion si = (SolicitudInformacion) event.getObject();
        try {
            String sessionUserName = SessionUtils.getUserName();
            String userNameToDelete = si.getUsername();
            if (userNameToDelete.equals(sessionUserName)) {
                service.updateRow(si);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición correcta", "ID: " + String.valueOf(si.getId())));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Solo el usuario '" + si.getUsername() + "' puede editar el registro con ID: " + si.getId()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getClass().getName()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        SolicitudInformacion si = (SolicitudInformacion) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edición cancelada", String.valueOf(si.getId())));
    }

    public void addRow() {

        SolicitudInformacion si = new SolicitudInformacion(fecha, tipoInformacion, especifiqueInformacion, viaSolicitud, us.getId(), username);
        try {
            si = service.addRow(si);
            rows.add(0, si);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud agregada", "ID: " + si.getId()));
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getClass().getName()));
        }
    }

    public void isSelectedUser() {
        if (us != null) {
            RequestContext.getCurrentInstance().execute("PF('dlgAddSolicitud').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debes seleccionar un usuario"));
        }
    }

    public void deleteSelected() {
        int r = 0;
        if (selectedRows.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No hay registros seleccionados"));
        } else {
            String sessionUserName = SessionUtils.getUserName();
            for (SolicitudInformacion row : selectedRows) {
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitudes eliminadas", String.valueOf(r)));
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getClass().getName()));
        }
        rows.remove(selectedRow);
        selectedRow = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", "ID: " + String.valueOf(id)));
    }
}
