/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sevices;

import java.rmi.RemoteException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author PC
 */
@Stateless
@EJB(name = "audit", beanInterface = AuditService.class) //declaramos la dependencia de un sessio bean
public class DepartmentService implements SessionBean{
    
    SessionContext context;
    AuditService auditService;
    
    @Override
    public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
        this.context = ctx;
    }
    
    /**
     * CallBack Method
     */
    @PostConstruct
    public void init()
    {
        // recuperamos el objeto session bean AuditService de un contexto JNDI
        auditService = (AuditService)context.lookup("audit");
    }
    
    public String performAudit()
    {
        return auditService.audit();
    }

    @Override
    public void ejbRemove() throws EJBException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
