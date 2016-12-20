/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import model.Produto;
import model.Venda;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author macbookair
 */
@MappedSuperclass
public class Model {
    
    @Id
    @GenericGenerator(name = "token_gen", strategy = "persistencia.TokenGenerator")
    @GeneratedValue(generator = "token_gen")
    private String token; // TODO Usar para gerar o identificador único entre BDs
    @Transient
    private String nomeTabela; // Utilizado para gerenciar consultas em que o nome da tabela é necessário
    protected static SessionFactory sessionFactory = null;
    
    static {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            
        }
    }

    public Model() {
        token = new String();
    }


    public String getNomeTabela() {
        return nomeTabela;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    // TODO ver como optimizar esta consulta
    public boolean modelExiste(){
        try {
            Session session = abrirSessao();
            Query query = session.createQuery("from " + nomeTabela + " where token = :token"); //You will get Weayher object
            query.setParameter("token", this.getToken());
            Model m = (Model) query.getSingleResult();

            session.close();
            if (m != null) {
                return true;
            }
//Query query = session.createQuery("select 1 from "+nomeTabela+" where token = :key");
            //query.setString("key", instance.getKey() );
            //return (query.uniqueResult() != null);
            return false;
        } catch (NoResultException nre ){
            return false;
        }
    }
    
    protected static Session abrirSessao(){
        Session session = sessionFactory.openSession();
        return session;
    } 
    
    public boolean salvar() throws HibernateException{
        
        if (!this.modelExiste()) {
            Session session = abrirSessao();
            session.beginTransaction();
            session.save(this);
            session.getTransaction().commit();
            session.close();
            return true;
        }
        
        
        return false;
    }
    
    public boolean atualizar() {
        // PROBLEMA: campos nao usados vem nulo
        Session session = abrirSessao();
        session.beginTransaction();
        session.update(this);
        session.getTransaction().commit();
        session.close();
        
        return true;
    }
    
    public static List<Object> listarTodos(String nomeTabela){
        List<Object> resultado = null;
        try{
            Session session = abrirSessao();
            Query query = session.createQuery("from "+nomeTabela); //You will get Weayher object
            
            resultado = query.getResultList(); //You are accessing  as list<WeatherModel>
                    //.createCriteria(MyEntity.class).list();
                    
            session.close();
        }catch(HibernateException he){
            return null;
        }
        
        return resultado;
    }
    
    @Override
    public boolean equals( Object v ){
        Model objetoComparado = (Model)v;
        
        if( objetoComparado.getToken().equals(this.getToken()) )
            return true;
        return false;
    }

    
    
    
}
