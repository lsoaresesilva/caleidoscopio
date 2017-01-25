/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.annotations.Expose;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import org.hibernate.Session;
import persistencia.Model;

/**
 *
 * @author macbookair
 */
@Entity
public class ResgatePontosFidelidade extends Model{
    
    @Expose
    private double pontosUtilizados;
    @OneToOne
    @JoinColumn(name="cliente_id") 
    private Cliente cliente;
    private double fatorPontuacao;

    protected ResgatePontosFidelidade() {
        super();
        super.setNomeTabela("ResgatePontosFidelidade");
        super.ativo = true;
    }
    
    public ResgatePontosFidelidade(Cliente cliente){
        this();
        this.cliente = cliente;
        
        this.pontosUtilizados = Fidelidade.calcularPontuacao(cliente);
    }
    
    public static List<ResgatePontosFidelidade> listarPorCliente( Cliente cliente ){
        Session session = Model.abrirSessao();
        String hql = "from ResgatePontosFidelidade where cliente_id = :cliente"; // usar groupby
        Query query = session.createQuery(hql);
        query.setParameter("cliente", cliente.getToken());
        List<ResgatePontosFidelidade> list = query.getResultList();
        
        session.close();
        return list;
    }

    public double getPontosUtilizados() {
        return pontosUtilizados;
    }

    public void setPontosUtilizados(double pontosUtilizados) {
        this.pontosUtilizados = pontosUtilizados;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    
}
