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
@FacesConverter("formaPagamentoConverter")
public class FormaPagamentoConverter implements Converter{
 
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        FormaPagamento formaPagamento = FormaPagamento.getByType( value );
        return formaPagamento;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        FormaPagamento formaPagamento = (FormaPagamento) value;
        return formaPagamento.name();
    }
    
}
