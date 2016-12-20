/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.FormaPagamento;
import model.Usuario;

/**
 *
 * @author macbookair
 */
@ManagedBean
@SessionScoped
public class UsuarioController {
    
    private Usuario usuario;
    
    @PostConstruct
    public void init(){
        usuario = new Usuario();
    }
    
    public String cadastrar(){
        usuario.salvar();
        return "";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Usuario> filtrarPorNome(String consulta){
        
        List<Usuario> vendedores = Usuario.filtrarPorNome(consulta);
        return vendedores;
    }
    
    
    
}