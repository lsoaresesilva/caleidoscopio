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
@FacesConverter("produtoConverter")
public class ProdutoConverter implements Converter{
 
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Produto produto = Produto.getByDescricao( value );
        return produto;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Produto produto = (Produto) value;
        return produto.getDescricao();
    }
    
}
