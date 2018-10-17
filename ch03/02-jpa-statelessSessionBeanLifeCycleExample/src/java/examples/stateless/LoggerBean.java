/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.stateless;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author barcvilla
 */
@Stateless
public class LoggerBean 
{
    private Logger logger;
    
    /**
     * El server llama PostConstruct tan pronto como el server ha completado la inicializacion todos los servicios
     * containers para el bean. Este reemplaza el Constructor como la ubicacion para la inicializacion de la logica
     * ya que es solo aqui que los servicios de contenedor se garantiza la disponibilidad.
     */
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
