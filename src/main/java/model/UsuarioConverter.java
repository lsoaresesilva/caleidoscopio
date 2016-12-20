/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
/**
 *
 * @author macbookair
 */
@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter{
 
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Usuario usuarioSelecionado = null;
        List<Usuario> usuariosCadastrados = (List<Usuario>)(Object)Usuario.listarTodos("Usuario");
        for( int i = 0; i < usuariosCadastrados.size(); i++ ){
            String nome = usuariosCadastrados.get(i).getNome();
            if( nome.equalsIgnoreCase(value)){
                usuarioSelecionado =  usuariosCadastrados.get(i);
                break;
            }
        }        
        
        
        return usuarioSelecionado;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Usuario usuario = (Usuario) value;
        return usuario.getNome();
    }
    
}
