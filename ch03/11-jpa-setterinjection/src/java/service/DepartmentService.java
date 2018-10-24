/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PC
 */
@Stateless
public class DepartmentService {
    
    private AuditService auditService;
    
    @EJB
    public void setAuditService(AuditService audit)
    {
        this.auditService = audit;
    }
    
    public String performedAudit()
    {
        return auditService.audit();
    }
}
