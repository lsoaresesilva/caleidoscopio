/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookair
 */
public class VendaTest {
    
    public VendaTest() {
    }

    

    @Test
    public void testCalcularComissao() throws ParseException {
        
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dataInicio = sdf.parse("2016-12-01");
        Date dataTermino = sdf.parse("2016-12-31");
        
        Usuario usuarioTest = new Usuario("oi", "827ccb0eea8a706c4c34a16891f84e7b");
        usuarioTest = usuarioTest.fazerLogin();
        
        
        ComissaoVendedor comissao = new ComissaoVendedor();
        comissao.setDataInicio(dataInicio);
        comissao.setDataTermino(dataTermino);
        comissao.setUsuarioComissao(usuarioTest);
        comissao.setPercentualComissao(1);
        
        double valorComissao = comissao.calcularComissao();
        assertEquals(0.65, valorComissao, 0.1);*/
    }
    
    @Test
    public void testBuildFromJson(){
        /*Venda vendaTest = Venda.getById(1);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(vendaTest);
        String opa = json;*/
    }
    
    //@Test
    public void testListarVendasPorProduto(){
        Produto p = new Produto();
        p.setToken("0a2f61b3-8808-4f14-aff4-e84c1deddafe");
        
        ArrayList<Venda> vendas = Venda.listarVendasPorProduto(p);
        Venda v = (Venda)Venda.getByToken(Venda.class, "930b973d-e3e4-4f06-b8c9-77227424b22a");
        ArrayList<Venda> vendasTest = new ArrayList<Venda>();
        vendasTest.add(v);
        assertEquals(vendasTest, vendas);
    }

    @Test
    public void testGetDataFormatada() {
    }

    @Test
    public void testCalcularValorTotalSemDesconto() {
    }

    @Test
    public void testCalcularValorTotalComDesconto() {
    }

    @Test
    public void testListarVendasPorData() {
    }

    @Test
    public void testCalcularValorTotalData() {
    }

    @Test
    public void testCalcularTroco() {
    }

    @Test
    public void testGetDataCriacao() {
    }

    @Test
    public void testSetDataCriacao() {
    }

    @Test
    public void testGetFormaPagamento() {
    }

    @Test
    public void testSetFormaPagamento() {
    }

    @Test
    public void testGetUsuario() {
    }

    @Test
    public void testSetUsuario() {
    }

    @Test
    public void testGetProdutos() {
    }

    @Test
    public void testSetProdutos() {
    }

    @Test
    public void testAdicionarProduto() {
    }

    @Test
    public void testListarTodos() {
    }

    @Test
    public void testSalvar() {
    }

    @Test
    public void testListarPorDataUsuario() {
    }

    @Test
    public void testBuildFromJSON() {
    }

    @Test
    public void testGetByToken() {
    }
    
}
