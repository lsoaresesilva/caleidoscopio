

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.Usuario;

/**
 *
 * @author macbookair
 */
@ManagedBean
public class LoginController {

    private Usuario usuario;
    
    public LoginController(){
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String fazerLogin(){
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Usuario usuarioLogado = usuario.fazerLogin();
            
            if (usuarioLogado != null) {

                context.getExternalContext().getSessionMap().put("usuarioLogado", usuarioLogado);
                context.addMessage(null, new FacesMessage("Success", "Login realizado com sucesso!"));
                return "sys/index";
            } else {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Login ou senha inválidos."));

            }
        } catch (RuntimeException re) {
            if (context != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "O banco de dados está desconectado. Por gentileza entre em contato com o administrador."));
            }
        }

        return "";
    }
    
}
