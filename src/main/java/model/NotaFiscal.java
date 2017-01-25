/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controladores.NotaFiscalController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.resource.spi.AuthenticationMechanism;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author macbookair
 */

public class NotaFiscal {
    
    UploadedFile nfeArquivo;
   
    // Custo variável da aquisição
    private double frete = 13.0;
    private double fronteira = 13.0;
    
    //private static NotaFiscal instance = null;
    
    ArrayList<Produto> produtos;
    
    private String nomeArquivo;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public UploadedFile getNfeArquivo() {
        return nfeArquivo;
    }

    public void setNfeArquivo(UploadedFile nfeArquivo) {
        this.nfeArquivo = nfeArquivo;
    }
    
    
    
    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public double getFronteira() {
        return fronteira;
    }

    public void setFronteira(double fronteira) {
        this.fronteira = fronteira;
    }
    
    public NotaFiscal(){
        produtos = new ArrayList<Produto>();
        
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }
    
    
    
    /*public static NotaFiscal getInstance(){
        if(instance == null)
            instance = new NotaFiscal();
        
        return instance;
    }*/
    
    public void atualizarPrecos(  ){
        
        for(int i = 0; i < produtos.size(); i++){
            produtos.get(i).calcularValorVenda();
            
        }
    }
    
    public boolean salvarProdutosBD(){
        try{
            for (int i = 0; i < this.produtos.size(); i++) {
                produtos.get(i).salvarOuAtualizar();
            }
        }catch( HibernateException he){
            return false; // TODO indicar quais não foram possíveis salvar ao invés de retornar false
        }
        
        return true;
    }
    
    
    
    public boolean gerenciarEnvioNfe(){
        UploadedFile file = getNfeArquivo();
        InputStream input = null;
        OutputStream output = null;
        if( file != null ){
            try {
                String fileName = FilenameUtils.getName(file.getFileName());
                if (fileName != null) {
                    input = file.getInputstream();
                    output = new FileOutputStream(new File("/Users/macbookair/Documents/files/", fileName));
                    IOUtils.copy(input, output);
                    setNomeArquivo(fileName);
                    carregarNotaFiscal();
                }
            } catch (IOException ex) {
                return false;
            } catch (NullPointerException np) {
                return false;
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
        }else{
            return false;
        }
        
        return true;
    }
    
    public void carregarNotaFiscal(){
        try {
            File fi = new File("/Users/macbookair/Documents/files/", getNomeArquivo());
            if (fi != null) { // Verificar os erros que podem acontecer
                //InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/42161100399603000612550020003661971000000005-procNFe.xml");
                InputStream is = new FileInputStream(fi);
                if (is != null) {
                    this.produtos = Produto.extrairProdutosNfe(is, this);
                } else { // Problema ao carregar o arquivo
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NotaFiscalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
