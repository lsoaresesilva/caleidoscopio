/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookair
 */
public class ProdutoTest {
    
    public ProdutoTest() {
    }

    
    //@Test
    public void testExtrairProdutosNFE() throws FileNotFoundException{
        
        File fi = new File("/Users/macbookair/Documents/files/", "xml_test.xml");
        if (fi != null) { // Verificar os erros que podem acontecer
            //InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/42161100399603000612550020003661971000000005-procNFe.xml");
            InputStream is = new FileInputStream(fi);
            if (is != null) {
                NotaFiscal nfe = new NotaFiscal();
                ArrayList<Produto> produtosExtraidos = Produto.extrairProdutosNfe(is, nfe);
                assertNotNull(produtosExtraidos);
            } else { // Problema ao carregar o arquivo
            }
        }
        
    }
    
    @Test
    public void testListarTodosJson(){

        //String json = Produto.listarTodosJson();
    }
    
    //@Test
    public void testListarProdutosPorDia(){
        List<Produto> produtosPorDiaTest = new ArrayList<Produto>();
        Produto pTest = (Produto)Produto.getByToken(Produto.class, "0a2f61b3-8808-4f14-aff4-e84c1deddafe");
        produtosPorDiaTest.add(pTest);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        List<Produto> produtosPorDia = Produto.listarProdutosPorDia(LocalDate.parse("2016-12-19", formatter));
        assertEquals(produtosPorDiaTest, produtosPorDia);
    }
    
    //@Test
    public void testSalvarProdutoJaCadastrado(){
        Produto p = new Produto();
        Fornecedor f = new Fornecedor();
        f.setCnpj("21874");
        f.setNome("Opa!");
        p.setDescricao("Bla");
        p.setCodigo("maoe"); // TODO mudar o token
        p.setQuantidadeEstoque(10);
        p.setFornecedor(f);
        
        Produto p2 = (Produto)Produto.getByToken(Produto.class, "012e3eae-d817-4a23-a259-a286c952f2a4");
        int qtdAnterior = p2.getQuantidadeEstoque();
        p.salvar();
        p2 = (Produto)Produto.getByToken(Produto.class, "012e3eae-d817-4a23-a259-a286c952f2a4");
        int qtdAtual = p2.getQuantidadeEstoque();
        assertEquals(qtdAtual,qtdAnterior+p.getQuantidadeEstoque() );
    }
    
    //@Test
    public void testSalvarProduto(){
        Produto p = new Produto();
        Fornecedor f = new Fornecedor();
        f.setCnpj("21874");
        f.setNome("Opa!");
        p.setDescricao("Bla");
        p.setCodigo("maoe");
        p.setQuantidadeEstoque(10);
        p.setFornecedor(f);
        assertTrue(p.salvar());
        
        
    }
    
    //@Test
    public void testDeletar(){
        Produto p = new Produto();
        
        p.setCodigo("maoe");
        p = Produto.getByCodigo(p.getCodigo());
        p.del();
    }
    
}
