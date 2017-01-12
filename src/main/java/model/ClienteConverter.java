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
@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter{
 
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Cliente cliente = Cliente.getByConsulta( value );
        return cliente;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Cliente cliente = (Cliente) value;
        return cliente.getNome();
    }
    
}
