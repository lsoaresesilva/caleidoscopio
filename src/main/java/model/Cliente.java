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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import persistencia.Model;
import static persistencia.Model.listarTodos;

/**
 *
 * @author macbookair
 */
@Entity
public class Cliente extends Model implements Serializable{
    
    @Expose
    private String nome;
    @Expose
    private String email;
    @Expose
    private String telefone;
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "cliente", targetEntity = ResgatePontosFidelidade.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ResgatePontosFidelidade> pontosUtilizados;

    public Cliente() {
        super();
        super.setNomeTabela("Cliente");
    }
    
    public Cliente(String nome, String email, String telefone, LocalDate dataNascimento) {
        this();
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
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

    public List<ResgatePontosFidelidade> getPontosUtilizados() {
        return pontosUtilizados;
    }

    public void setPontosUtilizados(List<ResgatePontosFidelidade> pontosUtilizados) {
        this.pontosUtilizados = pontosUtilizados;
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
    
    public String getDataAniversarioFormatada(){
        DateTimeFormatter formatter =
                      DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(formatter);
    }

    public LocalDate getDataNascimento() {
        
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String toString(){
        return this.nome;
    }
    
     public static String listarTodosJson() {
        List<Cliente> clientesCadastrados = (List<Cliente>)(Object)listarTodos("Cliente");
        
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(ResgatePontosFidelidade.class, new ResgatePontosFidelidadeSerializer());
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        
        String json = gson.toJson(clientesCadastrados);

        return json;
    }
     
    public String retornarPontuacaoFidelidade(){
        return String.valueOf(Fidelidade.calcularPontuacao(this));
    }
    
    public String retornarDescontoFidelidade(){
        return String.valueOf(Fidelidade.calcularDescontoComSaldoPontosFidelidade(this));
    }
    
    public HashMap<String, Double> calcularPontuacao(){
         double pontuacao = Fidelidade.calcularPontuacao(this); 
        double desconto = Fidelidade.calcularDescontoComSaldoPontosFidelidade(this);
        HashMap<String, Double> resultado = new HashMap<String, Double>();
        resultado.put("pontuacao", pontuacao);
        resultado.put("desconto", desconto);
        return resultado;
    }
     
    public String retornarPontuacaoJSON(){
        
        Gson gson = new Gson();
        String json = gson.toJson(calcularPontuacao());
        return json;
    }
    
    
     
     /*private static List<Cliente> listarTodosConversaoJSON(String nomeTabela){
        List<Cliente> resultado = null;
        try{
            Session session = abrirSessao();
            Query query = session.createQuery("from "+nomeTabela+" where ativo = :ativo"); //You will get Weayher object
            query.setParameter("ativo", true);
            resultado = query.getResultList(); //You are accessing  as list<WeatherModel>
                    //.createCriteria(MyEntity.class).list();
            if( resultado != null ){
                for(int i = 0; i < resultado.size(); i++ ){
                    Hibernate.initialize(resultado.get(i).getPontosUtilizados());
                }
            }
            
            session.close();
        }catch(HibernateException he){
            return null;
        }
        
        return resultado;
    }*/
    
}
