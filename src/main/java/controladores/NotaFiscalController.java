/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import model.NotaFiscal;
import model.Produto;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author macbookair
 */
@ManagedBean(name="nfController")
@SessionScoped
public class NotaFiscalController implements Serializable{
    
    NotaFiscal notaFiscal;
    
    
    @PostConstruct
    public void init() {

    }

    public NotaFiscalController() {
        notaFiscal = new NotaFiscal();

    }
    
    
    /**
     * Faz a leitura do XML da NFE enviada.
     * Extrai os produtos da nota;
     * Calcula o preço de venda;
     * Exibe toda a extração para o usuário ajustar antes de salvar os produtos no BD.
     * @return 
     */
    public String carregarNFE(){
        FacesContext context = FacesContext.getCurrentInstance();
        if( this.notaFiscal.gerenciarEnvioNfe() ){
            return "listagem_produtos.xhtml";
        }else{
            // Houve um erro ao enviar o XML ou ao decompor ele
            if( context != null)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve um erro ao tentar decompor o XML da NFE."));
            
        }
        
        return ""; // Exibir mensagem de erro
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
    
    public double valorAnteriorProduto( String codigo){
        return Produto.valorProdutoCadastrado(codigo);
    }
    
    public String salvar(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        if( this.notaFiscal.salvarProdutosBD() ){
            if( context != null)
                context.addMessage(null, new FacesMessage("Sucesso", "Produtos cadastrados com sucesso"));
            this.notaFiscal = new NotaFiscal();
            return "/sys/produtos/listagem_produtos";
        }else{
            if( context != null)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível salvar os produtos da notafiscal no banco de dados."));
        }
        
        return "";
    }
    
    
    
}
