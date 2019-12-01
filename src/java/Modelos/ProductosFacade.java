/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author CharlyEzCas
 */
@Stateless
public class ProductosFacade extends AbstractFacade<Productos> {

    @PersistenceContext(unitName = "FarmalifePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductosFacade() {
        super(Productos.class);
    }
    
    public String Cambia_nombre_p(String nombre,int idproducto){
        Query consulta = em.createNamedQuery("Productos.cambiarNombre",Productos.class)
                .setParameter("nombre_p", nombre)
                .setParameter("id_p", idproducto);
        consulta.executeUpdate();
        return "ok";
    }
}
