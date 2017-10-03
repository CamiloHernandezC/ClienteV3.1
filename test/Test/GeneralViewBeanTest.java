/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.clienteV31.entities.TiposDocumento;
import com.clienteV31.manageBeans.GeneralViewBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author chernandez
 */
//@RunWith(ConcurrentTestRunner.class)
public class GeneralViewBeanTest {

    private static Context  ctx;
    private static EJBContainer ejbContainer;
    private GeneralViewBean generalViewBean;
    
    public GeneralViewBeanTest() {
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
        generalViewBean = new GeneralViewBean();
        List<TiposDocumento> tiposDocumento = generalViewBean.getTiposDocumento();
        Assert.assertNotNull(tiposDocumento);
    }
}
