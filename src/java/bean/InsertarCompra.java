/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Modelos.Pedido;
import Modelos.PedidoFacade;
import Modelos.Usuarios;
import Modelos.UsuariosFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CharlyEzCas
 */
@Named(value = "insertarcompra")
@SessionScoped

public class InsertarCompra implements Serializable {
   
    private Integer idpedido;
    private Date fechaCompra;
    private double subtotal;
    private double descuento;
    private double total;
    private String estado;
    private Integer idCliente;
    
    private HttpServletRequest httpservlet;
    
    @EJB
    private PedidoFacade pedido_facade;
    private Pedido pedido;
    private UsuariosFacade usuario_facade;
    private Usuarios usuario;
    
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public InsertarCompra(){
        httpservlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    public void insertarPedido() throws IOException{
        httpservlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Pedido pedi= new Pedido(idpedido,fechaCompra, subtotal, descuento, total,estado);
        //pedido_facade.create(pedi);
        System.out.println(pedi.getDescuento());
        FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/Farmalife/faces/index_client.xhtml");
    }
}
