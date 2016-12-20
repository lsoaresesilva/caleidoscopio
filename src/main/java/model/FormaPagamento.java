/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macbookair
 */
public enum FormaPagamento {
    DINHEIRO, DEBITO, CREDITOAVISTA, CREDITOPARCELADO;
    
    public static FormaPagamento getByType( String type){
        for (FormaPagamento f : FormaPagamento.values()) {
            if (f.name().toLowerCase().contains(type.toLowerCase())) {
                return f;
            }
        }
        return null;
    }
    
    public static List<FormaPagamento> pesquisarPorNome( String nome){
        ArrayList<FormaPagamento> formasPagamento = new ArrayList();
        for (FormaPagamento f : FormaPagamento.values()) {
            if (f.name().toLowerCase().contains(nome.toLowerCase())) {
                formasPagamento.add(f);
            }
        }
        return formasPagamento;
    }
    
    public String recuperarString(){
        if (this == DINHEIRO){
            return "Dinheiro";
        }else if( this == DEBITO){
            return "Débito";
        }else if( this == CREDITOAVISTA){
            return "Crédito à vista";
        }else if( this == CREDITOPARCELADO){
            return "Crédito parcelado";
        }
        
        return "";
        
    }
}
