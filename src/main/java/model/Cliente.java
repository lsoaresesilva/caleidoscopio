/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import persistencia.Model;

/**
 *
 * @author macbookair
 */
@Entity
public class Cliente extends Model implements Serializable{
    
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataAniversario;

    public Cliente() {
        super();
        super.setNomeTabela("Cliente");
    }
    
    public static List<Cliente> pesquisarTodosCampos(String consulta) {
        Session session = Model.abrirSessao();
        String hql = "from Cliente where nome LIKE :nome and ativo = 1";
        Query query = session.createQuery(hql);
        query.setParameter("nome", "%" + consulta + "%");
        List<Cliente> list = query.getResultList();
        session.close();
        return list;
    }
    
    public static Cliente getByConsulta(String consulta) {
        Cliente model = null;
        try {
            Session session = Model.abrirSessao();
            String hql = "from Cliente where nome = :nome and ativo = 1";
            Query query = session.createQuery(hql);
            query.setParameter("nome", consulta);
            List<Cliente> list = query.getResultList();
            session.close();
            if (list.size() > 0) {
                return list.get(0);
            } else {
                return null;
            }
        } catch (HibernateException he) {
            return null;
        }

    }
    
    public double calcularPremiacao(){
        // Ter o total de pontuacao
        double premio = 0.0;
        double pontucao = calcularPontuacao();
        // Verificar quanto de desconto é possível obter com aquele desconto
        // Regra de cálculo: para cada 150 reais o cliente ganha 5 reais de desconto
        if( pontucao > 150 )
            premio = pontucao*0.03; // 5 reais de 150 representa 3%
        
        return premio;
    }
    
    public double calcularPontuacao( ){
        double pontuacao = 0.0;
        List<Venda> vendasCliente = Venda.listarPorCliente(this);
        for( int i = 0; i < vendasCliente.size(); i++ ){
            pontuacao = pontuacao+vendasCliente.get(i).calcularValorTotalComDesconto();
        }
        // TODO acrescentar o fator multiplicador e subtrair do resgate
        
        return pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(LocalDate dataAniversario) {
        this.dataAniversario = dataAniversario;
    }
    
    
}
