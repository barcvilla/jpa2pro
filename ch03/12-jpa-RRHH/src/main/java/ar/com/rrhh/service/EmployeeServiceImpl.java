/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.service;

import ar.com.rrhh.dao.EmployeeDao;
import ar.com.rrhh.domain.Persona;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PC
 */
@Stateless
public class EmployeeServiceImpl implements EmployeeService{
    
    @EJB
    private EmployeeDao employeeDao;

    @Override
    public Persona createEmployee(Persona employee) {
        return employeeDao.createEmployee(employee);
    }

    @Override
    public Persona removeEmployee(int id) {
        return employeeDao.removeEmployee(id);
    }

    @Override
    public Persona findEmployee(int id) {
        return employeeDao.findEmployee(id);
    }

    @Override
    public Persona changeTelefono(int id, String telefono) {
        return employeeDao.changeTelefono(id, telefono);
    }

    @Override
    public List<Persona> findAllEmployee() {
        return employeeDao.findAllEmployee();
    }
    
}
