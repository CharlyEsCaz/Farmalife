/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author CharlyEzCas
 */
@Stateless
public class HistorialVentasFacade extends AbstractFacade<HistorialVentas> {

    @PersistenceContext(unitName = "FarmalifePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialVentasFacade() {
        super(HistorialVentas.class);
    }
    
}
