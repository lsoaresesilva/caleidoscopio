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
import java.util.ArrayList;
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
import org.primefaces.model.UploadedFile;

/**
 *
 * @author macbookair
 */
@ManagedBean(name="nfController")
@SessionScoped
public class NotaFiscalController {
    
    
    NotaFiscal notaFiscal;
    
    @PostConstruct
    public void init() {
        
        // Não posso colocar a NF aqui, pos nas requisições AJAX ele chama e reinicia a NF.
    }

    public NotaFiscalController() {
        notaFiscal = new NotaFiscal();

    }
    
    
    
    public String gerenciarEnvio(){
        
        if( this.notaFiscal.gerenciarEnvioNfe() ){
            return "listagem_produtos.xhtml";
        }else{
            // Houve um erro ao enviar o XML ou ao decompor ele
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro!",  "Houve um problema ao tentar converter o XML") );
            
        }
        
        return ""; // Exibir mensagem de erro
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
    
    public String salvar(){
        if( this.notaFiscal.salvarProdutosBD() ){
            this.notaFiscal = new NotaFiscal();
            return "/sys/produtos/listagem_produtos";
        }
        
        return "";
    }
    
    
    
}
