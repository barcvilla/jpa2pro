/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.dao;

import ar.com.rrhh.domain.Persona;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class EmployeeDaoImpl implements EmployeeDao{
    @PersistenceContext(unitName = "persistencerrhh")
    EntityManager em;

    @Override
    public Persona createEmployee(Persona employee) {
        if(employee != null)
        {
            em.persist(employee);
            return employee;
        }
        return null;
    }

    @Override
    public Persona removeEmployee(int id) {
        Persona employee = findEmployee(id);
        if(employee != null)
        {
            em.remove(employee);
            return employee;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Persona findEmployee(int id) {
        return em.find(Persona.class, id);
    }

    @Override
    public Persona changeTelefono(int id, String telefono) {
        Persona employee = findEmployee(id);
        if(employee != null)
        {
            employee.setTelefono(telefono);
            return employee;
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Persona> findAllEmployee() {
        return em.createNamedQuery("Persona.findAll", Persona.class).getResultList();
    }
}
