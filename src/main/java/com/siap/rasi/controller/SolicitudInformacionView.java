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
import com.siap.rasi.pojo.SolicitudInformacion;
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

@ManagedBean(name = "solicitudView")
@ViewScoped
public class SolicitudInformacionView implements Serializable {

    private List<SolicitudInformacion> rows;
    private List<SolicitudInformacion> selectedRows;
    private List<SolicitudInformacion> filteredRows;
    private boolean buttonDisabled;

    @ManagedProperty("#{carService}")
    private SolicitudInformacionService service;

    @PostConstruct
    public void init() {
        rows = service.listCars();
        buttonDisabled = !rows.isEmpty();
        buttonDisabled = true;
    }

    public List<SolicitudInformacion> getRows() {
        return rows;
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

    public boolean isButtonDisabled() {
        return buttonDisabled;
    }

    public void setButtonDisabled(boolean buttonDisabled) {
        this.buttonDisabled = buttonDisabled;
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
                service.updateCar(currentCar);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición correcta", "Anterior: " + oldValue + ", Nuevo:" + newValue);
            } catch (Exception e) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falló edición", e.getMessage());
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void disableIfEmpty() {
        this.buttonDisabled = selectedRows.isEmpty();
    }

    public void addRow() {
        SolicitudInformacion car = service.addCar();
        rows.add(0, car);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro agregado", "ID: " + car.getId()));
    }

    public void deleteSelected() {
        int size = selectedRows.size();
        selectedRows.stream().map((selectedCar) -> selectedCar.getId()).forEach((id) -> {
            try {
                service.deleteCar(id);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            }
        });
        rows.removeAll(selectedRows);
        selectedRows.clear();
        disableIfEmpty();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registros eliminados", String.valueOf(size)));
    }

}
