/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sevices;

import javax.ejb.Stateless;

/**
 *
 * @author PC
 */
@Stateless
public class AuditService {
    public String audit()
    {
        return "Audit Performed";
    }
}
