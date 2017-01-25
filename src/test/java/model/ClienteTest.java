/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookair
 */
public class ClienteTest {
    
    public ClienteTest() {
    }
    
    //@Test
    public void testListarTodosJson() {
        String json = Cliente.listarTodosJson();
        String res = json;
    
    }
    
    @Test
    public void testSalvarcliente(){
        
    }
    
    @Test
    public void test(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //formatter = formatter.withLocale( Locale. );
        LocalDate dataOriginal = LocalDate.parse("21/01/1987", formatter);
        
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataFinal = LocalDate.parse(dataOriginal.format(formatter));
        LocalDate opa = dataFinal;
    }
}
