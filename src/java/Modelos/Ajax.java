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
    private String producto_name;
    private String nombre;
    int id_p;
    int id_pb;

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

    public String getProducto_name() {
        return producto_name;
    }

    public void setProducto_name(String producto_name) {
        this.producto_name = producto_name;
    }

    public int getId_pb() {
        return id_pb;
    }

    public void setId_pb(int id_pb) {
        this.id_pb = id_pb;
    }
    
    
    
    public void CambiaNombreProducto(AjaxBehaviorEvent event){
        
        if(p_facade.Cambia_nombre_p(nombre, id_p).equals("ok"))
            mensaje = "Nombre Actualizado correctamente";
        else
            mensaje = "ERROR AL ACTUALIZAR";
    }
    
    public void buscarNombreProducto(AjaxBehaviorEvent event){
        
        Productos  pro = p_facade.find(id_pb);
        producto_name =  pro.getNombre();
    }
    
    public Ajax() {
    }
    
}
