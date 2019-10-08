/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CharlyEzCas
 */
@Entity
@Table(name = "historial_ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialVentas.findAll", query = "SELECT h FROM HistorialVentas h")
    , @NamedQuery(name = "HistorialVentas.findByIdVenta", query = "SELECT h FROM HistorialVentas h WHERE h.idVenta = :idVenta")
    , @NamedQuery(name = "HistorialVentas.findByFechaCompra", query = "SELECT h FROM HistorialVentas h WHERE h.fechaCompra = :fechaCompra")
    , @NamedQuery(name = "HistorialVentas.findByFechaEntrega", query = "SELECT h FROM HistorialVentas h WHERE h.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "HistorialVentas.findByEstadoFinal", query = "SELECT h FROM HistorialVentas h WHERE h.estadoFinal = :estadoFinal")
    , @NamedQuery(name = "HistorialVentas.findByComentario", query = "SELECT h FROM HistorialVentas h WHERE h.comentario = :comentario")})
public class HistorialVentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venta")
    private Integer idVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "estado_final")
    private String estadoFinal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "idpedido", referencedColumnName = "idpedido")
    @ManyToOne(optional = false)
    private Pedido idpedido;
    @JoinColumn(name = "idcliente", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private Usuarios idcliente;

    public HistorialVentas() {
    }

    public HistorialVentas(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public HistorialVentas(Integer idVenta, Date fechaCompra, Date fechaEntrega, String estadoFinal, String comentario) {
        this.idVenta = idVenta;
        this.fechaCompra = fechaCompra;
        this.fechaEntrega = fechaEntrega;
        this.estadoFinal = estadoFinal;
        this.comentario = comentario;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Pedido getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Pedido idpedido) {
        this.idpedido = idpedido;
    }

    public Usuarios getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Usuarios idcliente) {
        this.idcliente = idcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialVentas)) {
            return false;
        }
        HistorialVentas other = (HistorialVentas) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + idVenta;
    }
    
}
