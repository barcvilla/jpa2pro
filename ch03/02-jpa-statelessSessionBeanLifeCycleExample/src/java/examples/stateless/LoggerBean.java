/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.stateless;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 *
 * @author barcvilla
 */
public class LoggerBean 
{
    private Logger logger;
    
    @PostConstruct
    private void init()
    {
        logger = Logger.getLogger("notificacion");
    }
    
    public void logMessage(String message)
    {
        logger.info(message);
    }
}
