/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;
import Modelos.UsuariosFacade;
import Modelos.Usuarios;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author mteluis
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {
    
    private String username;
    private String password;
    
    private HttpServletRequest httpservlet;
    
    @EJB
    private UsuariosFacade usufacade;
    
    private Usuarios usuautenticado;

    public Usuarios getUsuautenticado() {
        return usuautenticado;
    }

    public void setUsuautenticado(Usuarios usuautenticado) {
        this.usuautenticado = usuautenticado;
    }
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    /**
     * Creates a new instance of Login
     */
    public Login() {
        httpservlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    
    
    public void Acceso() throws IOException{
         
        httpservlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        usuautenticado = usufacade.Buscar(username, password);
        if(usuautenticado!=null){
            httpservlet.getSession().setAttribute("nombre", usuautenticado.getNombre());
            httpservlet.getSession().setAttribute("rol", usuautenticado.getRol());
            httpservlet.getSession().setAttribute("usuario", usuautenticado);
            switch(usuautenticado.getRol()){
                case "Administrador":
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index_admin.xhtml");
                    break;
                case "Cliente":
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index_client.xhtml");
                    break;
                default:
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                    break;
            }
            
            //return "Acceder";
        }else{
            System.out.println("no se encuentra de la base");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Usuario o password incorrecto",null));
        }
        
        //return null;
        
    }
    
    public void cerrarSesion(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("ingresar.xhtml");
            
        } catch (Exception e) {
        }
    }
    
    public void verificaSesionynivel(String rol) throws IOException{
        
        httpservlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Usuarios usu = (Usuarios) httpservlet.getSession().getAttribute("usuario");
        if(usu != null){
            if(usu.getRol().equals(rol)){
            }else{
                FacesContext.getCurrentInstance().getExternalContext().redirect("sin_privilegios.xhtml");
            }
            
        }else{
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }
    
}
