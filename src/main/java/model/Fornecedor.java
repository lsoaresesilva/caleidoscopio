/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import persistencia.Model;

/**
 *
 * @author macbookair
 */
@Entity
public class Fornecedor extends Model implements Serializable{
    
    private String nome;
    private String cnpj;

    public Fornecedor() {
        super();
    }

    public Fornecedor(String nome, String cnpj) {
        super();
        this.nome = nome;
        this.cnpj = cnpj;
    }

    /*public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    
    
    
    
}
