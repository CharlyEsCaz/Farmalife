/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
/**
 *
 * @author CharlyEzCas
 */
@Named(value = "ajax")
@RequestScoped
public class Ajax {
    
    @EJB
    private ProductosFacade p_facade;
    
    private String mensaje;
    private String nombre;
    int id_p;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }
    
    public void CambiaNombreProducto(AjaxBehaviorEvent event){
        
        if(p_facade.Cambia_nombre_p(nombre, id_p).equals("ok"))
            mensaje = "Nombre Actualizado correctamente";
        else
            mensaje = "ERROR AL ACTUALIZAR";
    }
    
    public Ajax() {
    }
    
}
