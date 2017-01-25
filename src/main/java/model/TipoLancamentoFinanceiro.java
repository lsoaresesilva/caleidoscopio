/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static model.FormaPagamento.CREDITOAVISTA;
import static model.FormaPagamento.CREDITOPARCELADO;
import static model.FormaPagamento.DEBITO;
import static model.FormaPagamento.DINHEIRO;

/**
 *
 * @author macbookair
 */
public enum TipoLancamentoFinanceiro {

    CREDITO("Crédito"), DEBITO("Débito");

    private final String label;
    
    private TipoLancamentoFinanceiro(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }
    
    
   
}
