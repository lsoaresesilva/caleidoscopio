/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import persistencia.Model;

/**
 *
 * @author macbookair
 */
@Entity
public class LancamentoFinanceiro extends Model implements Serializable{
    @Enumerated(EnumType.STRING)
    private TipoLancamentoFinanceiro tipo;
    private String descricao;
    private LocalDate data;
    private double valor;
    

    public LancamentoFinanceiro() {
         super();
        super.setNomeTabela("LancamentoFinanceiro");
    }

    public TipoLancamentoFinanceiro getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamentoFinanceiro tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
}
