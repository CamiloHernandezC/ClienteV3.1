/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.clienteV31.facades.PersonasFacade;
import com.clienteV31.entities.Personas;
import com.clienteV31.entities.TiposDocumento;
import com.clienteV31.utils.Result;
import com.clienteV31.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author chernandez
 */
@RunWith(ConcurrentTestRunner.class)
public class ConcurrentPersistenceTest {

    private static Context  ctx;
    private static EJBContainer ejbContainer;
    private int counter=3;
    
    public ConcurrentPersistenceTest() {
    }
    
    @BeforeClass
    public static void setUp() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("org.glassfish.ejb.embedded.glassfish.instance.root", "C:/Workspace/GlasfishServer/glassfish/domains/camilo");
        ejbContainer = EJBContainer.createEJBContainer(properties);
        System.out.println("Opening the container" );
        ctx = ejbContainer.getContext();
    }

    @AfterClass
    public static void tearDown() {
        ejbContainer.close();
        System.out.println("Closing the container" );
    }

    @Test
    public void createPerson() throws NamingException {
        PersonasFacade personasFacade;
        //EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        personasFacade = (PersonasFacade)ctx.lookup("java:global/classes/PersonasDAO");
        Personas person = new Personas();
        person.setTipoDocumento(new TiposDocumento(1));
        String numDocum = String.valueOf(getCounter());
        person.setNumeroDocumento(numDocum);
        System.out.println(numDocum);
        person.setNombre1("Camilo");
        person.setApellido1("Hernandez");
        Result result = personasFacade.create(person);
        assertEquals(Constants.OK, result.errorCode);
        
    }
    
    private synchronized int getCounter(){
        return counter++;
    }
    /*
    private PersonasDAO lookupPersonasDAOBean() {
        try {
            PersonasDAO dao = (PersonasDAO) ctx.lookup("java:global/ClienteV3.1/PersonasDAO");
            assertNotNull(dao);
            return dao;
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }*/
}
