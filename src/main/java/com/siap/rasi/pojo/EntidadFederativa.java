package com.siap.rasi.pojo;
// Generated 19/08/2016 03:56:54 PM by Hibernate Tools 4.3.1



/**
 * EntidadFederativa generated by hbm2java
 */
public class EntidadFederativa  implements java.io.Serializable {


     private long id;
     private String nombre;
     private String nombreLargo;

    public EntidadFederativa() {
    }

	
    public EntidadFederativa(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public EntidadFederativa(long id, String nombre, String nombreLargo) {
       this.id = id;
       this.nombre = nombre;
       this.nombreLargo = nombreLargo;
    }
   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombreLargo() {
        return this.nombreLargo;
    }
    
    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }




}


