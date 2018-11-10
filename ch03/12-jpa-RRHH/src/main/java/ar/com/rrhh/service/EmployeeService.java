/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.rrhh.service;

import ar.com.rrhh.domain.Persona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface EmployeeService {
    
    public Persona createEmployee(Persona employee);
    public Persona removeEmployee(int id);
    public Persona findEmployee(int id);
    public Persona changeTelefono(int id, String telefono);
    public List<Persona> findAllEmployee();
}
