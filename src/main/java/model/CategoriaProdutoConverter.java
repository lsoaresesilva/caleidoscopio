/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
/**
 *
 * @author macbookair
 */
@FacesConverter("categoriaProdutoConverter")
public class CategoriaProdutoConverter implements Converter{
 
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CategoriaProduto categoria = CategoriaProduto.getByNome( value );
        return categoria;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if( value == null )
            return "";
        
        CategoriaProduto categoria = (CategoriaProduto) value;
        return categoria.getNome();
    }
    
}
