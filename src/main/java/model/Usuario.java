/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import persistencia.HibernateUtil;
import persistencia.Model;
import static persistencia.Model.listarTodos;

/**
 *
 * @author macbookair
 */
@Entity
public class Usuario extends Model implements Serializable{
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;*/
    @Expose
    private String email;
    @Expose
    private String senha;
    @Expose
    private String nome;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario(String login, String senha) {
        super();
        this.email = login;
        this.senha = senha;
    }

    public Usuario() {
        super();
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
    
    public static List<Usuario> pesquisarPorNome(String nome){
        Session session = Model.abrirSessao();
        String hql = "from Usuario where nome LIKE :nome";
        Query query = session.createQuery(hql);
        query.setParameter("nome", "%"+nome+"%");
        List<Usuario> list = query.getResultList();
        session.close();
        return list;
    }
    
    public Usuario fazerLogin(){
        // Verificar se existe usuário cadastrado no banco de dados e com a senha informada
        Usuario usuarioLogado = null;
        
        Session session = Model.abrirSessao();
        String hql = "from Usuario where email = :email and senha = :senha";
        Query query = session.createQuery(hql);
        query.setParameter("email", this.email);
        String senha =converterParaMD5();
        query.setParameter("senha", converterParaMD5());
        List<Usuario> list = query.getResultList();
        session.close();
        if( list.size() > 0 ){
            usuarioLogado=list.get(0);
        }
            
        
        return usuarioLogado;
    }
    
    public String converterParaMD5(){
        try {
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(senha.getBytes(),0,senha.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public static List<Usuario> filtrarPorNome( String nome){
        ArrayList<Usuario> vendedoresFiltrados = new ArrayList();
        List<Usuario> usuariosCadastrados = Usuario.pesquisarPorNome(nome);
        
        for (Usuario u : usuariosCadastrados) {
            if (u.getNome().toLowerCase().contains(nome.toLowerCase())) {
                vendedoresFiltrados.add(u);
            }
        }
        return vendedoresFiltrados;
    }
    
    public boolean equals( Usuario u ){
        if( this.getEmail().equals(u.getEmail()) )
            return true;
        return false;
    }
    
    // TODO generalizar para todos os models
    public static Usuario getByToken(String token){
        Usuario model = null;
        
        try{
            Session session = Model.abrirSessao();
            session.beginTransaction();
            model = session.get(Usuario.class, token);
            session.close();
        }catch(HibernateException he){
            return null;
        }
        
        return model;
    }
    
    public static String listarTodosJson(){
        List<Usuario> usuariosCadastrados = (List<Usuario>)(Object)listarTodos("Usuario");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        
        String json = gson.toJson(usuariosCadastrados);
        
        return json;
    }
    
}
