/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import model.Produto;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookair
 */
public class ModelTest {
    
    public ModelTest() {
    }

    
    @Test
    public void testModelExiste() {
        Produto p = new Produto();
        p.setToken("0a2f61b3-8808-4f14-aff4-e84c1deddafe");
        assertTrue(p.modelExiste());
    }

}
