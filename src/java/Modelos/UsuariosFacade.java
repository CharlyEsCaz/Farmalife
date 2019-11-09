/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author CharlyEzCas
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "FarmalifePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
    public List<Usuarios> validar(Usuarios current){
        return em.createNamedQuery("Usuarios.validar").setParameter("correo",current.getCorreo())
                .setParameter("contraseña", current.getContraseña()).getResultList();
    }
    
    public Usuarios Buscar(String usu, String pas){
        Query consulta = em.createNamedQuery("Usuarios.validar",Usuarios.class)
                .setParameter("correo", usu)
                .setParameter("contraseña", pas);
        List<Usuarios> lista = consulta.getResultList();
        if(!lista.isEmpty()){
            return lista.get(0);
        }
        return null;
    }
    
}
