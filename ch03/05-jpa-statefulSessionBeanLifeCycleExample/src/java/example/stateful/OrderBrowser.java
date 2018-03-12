/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.stateful;

import examples.model.Order;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.EJBException;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.sql.DataSource;

/**
 *
 * @author barcvilla
 */
@Stateful
@DataSourceDefinition
(
        name="java:app/env/jdbc/postgresql9.4",
        className="org.postgresql.xa.PGXADataSource",
        portNumber=5432,
        serverName="localhost",
        databaseName="javaee7_big_picture",
        user="postgres",
        password="postgres"
)
public class OrderBrowser 
{
    @Resource(lookup="java:app/env/jdbc/postgresql9.4")
    DataSource ds;
    Connection conn;
    
    @PostConstruct
    void init()
    {
        acquireConnection();
    }
    
    @PrePassivate
    void passivate()
    {
        releaseConnection();
    }
    
    @PostActivate
    void activate()
    {
        acquireConnection();
    }
    
    @PreDestroy
    void shutdown()
    {
        releaseConnection();
    }
    
    void acquireConnection()
    {
        try
        {
            conn = ds.getConnection();
        }
        catch(SQLException e)
        {
            throw new EJBException(e);
        }
    }
    
    void releaseConnection()
    {
        try
        {
            conn =  ds.getConnection();
        }
        catch(SQLException e)
        {}
        conn = null;
    }
    
    public Collection<Order> listOrders()
    {
        return new ArrayList<Order>();
    }
    
    @Remove
    public void remove(){}
    
}
