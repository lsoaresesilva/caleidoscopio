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
import javax.ws.rs.GET;
import model.FormaPagamento;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import model.Produto;
import model.Usuario;

/**
 *
 * @author macbookair
 */
@ManagedBean
@SessionScoped
@Path("/usuarios")
public class UsuarioController {
    
    private Usuario usuario;
    
    @PostConstruct
    public void init(){
        usuario = new Usuario();
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
    
    @GET
    @Produces("application/json")
    @Path("listar")
    public String listarVendasJson(){
        String json = Usuario.listarTodosJson();
        return json;
    }
    
}
