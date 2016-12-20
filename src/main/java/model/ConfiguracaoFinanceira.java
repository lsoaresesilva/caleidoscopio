/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author macbookair
 */
@Entity
public class ConfiguracaoFinanceira implements Serializable {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private double simples;
    private double taxaCartao;
    private double comissaoVendas;
    private double taxasAdicionais;
    private double valorAcrescimo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSimples() {
        return simples;
    }

    public void setSimples(double simples) {
        this.simples = simples;
    }

    public double getTaxaCartao() {
        return taxaCartao;
    }

    public void setTaxaCartao(double taxaCartao) {
        this.taxaCartao = taxaCartao;
    }

    public double getComissaoVendas() {
        return comissaoVendas;
    }

    public void setComissaoVendas(double comissaoVendas) {
        this.comissaoVendas = comissaoVendas;
    }

    public double getTaxasAdicionais() {
        return taxasAdicionais;
    }

    public void setTaxasAdicionais(double taxasAdicionais) {
        this.taxasAdicionais = taxasAdicionais;
    }

    public double getValorAcrescimo() {
        return valorAcrescimo;
    }

    public void setValorAcrescimo(double valorAcrescimo) {
        this.valorAcrescimo = valorAcrescimo;
    }
    
    
}
