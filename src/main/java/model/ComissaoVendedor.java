/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import static model.Venda.listarPorDataUsuario;

/**
 *
 * @author macbookair
 */
public class ComissaoVendedor {
    private Date dataInicio;
    private Date dataTermino;
    private double percentualComissao;
    private Usuario usuarioComissao;
    private double valorComissao;

    public ComissaoVendedor() {
        percentualComissao = 0.0;
        usuarioComissao = new Usuario();
        valorComissao = 0.0;
    }
    
    public double calcularComissao(  ){
        LocalDate localDateInicio = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
        LocalDate localDateTermino = dataTermino.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
                                
        
        List<Venda> vendas = Venda.listarPorDataUsuario(localDateInicio, localDateTermino, usuarioComissao);
        double valorTotalVendas = 0.0;
        
        for( int i = 0; i< vendas.size(); i++ ){
            valorTotalVendas += vendas.get(i).calcularValorTotalComDesconto();
        }
        
        if( valorTotalVendas != 0.0 ){
            valorComissao = (percentualComissao/100)*valorTotalVendas;
        }
        
        return valorComissao;
        
    }

    public double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(double valorComissao) {
        this.valorComissao = valorComissao;
    }
    
    

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    public Usuario getUsuarioComissao() {
        return usuarioComissao;
    }

    public void setUsuarioComissao(Usuario usuarioComissao) {
        this.usuarioComissao = usuarioComissao;
    }
    
    
}
