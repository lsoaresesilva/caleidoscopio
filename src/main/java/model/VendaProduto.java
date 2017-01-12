/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import persistencia.Model;

/**
 *
 * @author macbookair
 */
@Entity
@Table(name = "venda_produto")
public class VendaProduto extends Model implements Serializable {
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;*/
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "venda_id") 
    private Venda venda;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id") 
    private Produto produto;
    private double desconto;
    private int quantidadeCompra;

    public VendaProduto() {
        super();
        quantidadeCompra = 1;
    }
    
    public VendaProduto( Venda venda, Produto produto){
        super();
        this.venda = venda;
        this.produto = produto;
        quantidadeCompra = 1;
    }
    
    public double calcularDesconto(){
        if( produto == null){
            return 0.0;
        }
        
        if( desconto != 0.0){
            double valor = produto.getValorUnitarioVenda()*(desconto/100);
            return valor;
        }else{
            return 0.0;
        }
    }
    
    public double calcularValorComDesconto(){
        if( produto == null){
            return 0.0;
        }
        double desconto = calcularDesconto();
        double valor = (produto.getValorUnitarioVenda()-desconto)*quantidadeCompra;
        return valor;
    }
    
    public double calcularValorSemDesconto(){
        if( produto == null){
            return 0.0;
        }
        double valor = produto.getValorUnitarioVenda()*quantidadeCompra;
        return valor;
    }

    public int getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(int quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
    }
    
    

    /*public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/
    
    

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public boolean validar(){
        if( this.getDesconto() < 0.0 )
            return false;
        if( this.getQuantidadeCompra() < 1 )
            return false;
        
        return true;
    }
    
    
}
