package com.siap.rasi.pojo;
// Generated 4/08/2016 04:32:58 PM by Hibernate Tools 4.3.1



/**
 * EntidadFederativa generated by hbm2java
 */
public class EntidadFederativa  implements java.io.Serializable {


     private int id;
     private String nombre;
     private String nombreLargo;

    public EntidadFederativa() {
    }

	
    public EntidadFederativa(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public EntidadFederativa(int id, String nombre, String nombreLargo) {
       this.id = id;
       this.nombre = nombre;
       this.nombreLargo = nombreLargo;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
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


