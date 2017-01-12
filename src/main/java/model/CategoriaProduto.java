/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
public class CategoriaProduto extends Model {
    
    private String nome;

    public CategoriaProduto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public static CategoriaProduto getByNome(String nome) {
        CategoriaProduto model = null;
        try {
            Session session = Model.abrirSessao();
            String hql = "from CategoriaProduto where nome = :nome";
            Query query = session.createQuery(hql);
            query.setParameter("nome", nome);
            List<CategoriaProduto> list = query.getResultList();
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
    
    public static List<CategoriaProduto> pesquisarPorNome(String nome) {
        Session session = Model.abrirSessao();
        String hql = "from CategoriaProduto where nome LIKE :nome";
        Query query = session.createQuery(hql);
        query.setParameter("nome", "%" + nome + "%");
        List<CategoriaProduto> list = query.getResultList();
        session.close();
        return list;
    }
    
}
