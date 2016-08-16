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

    @PostConstruct
    public void init() {
        rows = service.listRows();
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
        FacesMessage msg;
        SolicitudInformacion si = (SolicitudInformacion) event.getObject();
        try {
            service.updateRow(si);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición correcta", "ID: " + String.valueOf(si.getId()));
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falló la edición", e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        SolicitudInformacion si = (SolicitudInformacion) event.getObject();
        FacesMessage msg = new FacesMessage("Edición cancelada", String.valueOf(si.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addRow() {
        SolicitudInformacion car = service.addRow();
        rows.add(0, car);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro agregado", "ID: " + car.getId()));
    }

    public void deleteSelected() {
        FacesMessage msg;
        int size = selectedRows.size();
        if (selectedRows.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay registros seleccionados"));
        } else {
            selectedRows.stream().map((selectedCar) -> selectedCar.getId()).forEach((id) -> {
                try {
                    service.deleteRow(id);
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
                }
            });
            rows.removeAll(selectedRows);
            selectedRows.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registros eliminados", String.valueOf(size)));
        }
    }

    public void deleteRow() {
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
