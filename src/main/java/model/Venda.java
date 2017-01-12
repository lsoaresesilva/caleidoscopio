/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Transient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import persistencia.Model;

/**
 *
 * @author macbookair
 */
@Entity
public class Venda extends Model implements Serializable{
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;*/
    @OneToMany(mappedBy = "venda", targetEntity = VendaProduto.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VendaProduto> produtos;
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    private LocalDate dataCriacao;
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    private double cupomDesconto; // obtido a partir da premiação de fidelidade do cliente
    
    
    public Venda() {
        super();
        super.setNomeTabela("Venda");
        produtos = new ArrayList<VendaProduto>();
        dataCriacao = LocalDate.now();
        LocalDate temp = dataCriacao;
        formaPagamento = null;
        cupomDesconto = 0.0;
    }
    
    public Venda(Usuario usuario) {
        this();
        this.usuario = usuario;
    }
    
    public String getDataFormatada(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataCriacao.format(formatter);
    }

    public double calcularValorTotalSemDesconto(){
        double valorTotal = 0.0;
        for(int i = 0; i < produtos.size(); i++){
            //valorTotal += produtos.get(i).getProduto().getValorUnitarioVenda();
            valorTotal += produtos.get(i).calcularValorSemDesconto();
        }
        return valorTotal;
    }
    
    public double calcularValorTotalComDesconto(){
        double valorTotal = 0.0;
        for(int i = 0; i < produtos.size(); i++){
            valorTotal += produtos.get(i).calcularValorComDesconto();
        }
        
        return valorTotal-cupomDesconto;
    }
    
    
    
    public static List<Venda> listarVendasPorData(LocalDate dataInicio, LocalDate dataTermino){
        Session session = Model.abrirSessao();
        String hql = "from Venda AS v where v.dataCriacao BETWEEN :dataInicio and :dataTermino and ativo = 1"; // usar groupby
        Query query = session.createQuery(hql);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataTermino", dataTermino);
        List<Venda> list = query.getResultList();
        session.close();
        return list;
    }
    
    public double calcularValorTotalData(LocalDate data){
        // somar as colunas filtradas pela data especificada
        List<Venda> vendasLocalizadas = listarVendasPorData(data, data);
        double valorTotal = 0.0;
        for(int i = 0; i < vendasLocalizadas.size(); i++){
            List<VendaProduto> vendaProdutos = vendasLocalizadas.get(i).getProdutos();      
            for( int j = 0; j < vendaProdutos.size() ; j++){
                valorTotal += vendaProdutos.get(j).calcularValorComDesconto();
            }
            
        }
        
        return valorTotal;
    }
    
    
    
    public double calcularTroco(String valorRecebido){
        // Pegar o valor total da compra
        double valorTotal = calcularValorTotalComDesconto();
        // Subtrair do valor Recebido
        
        double troco = Double.parseDouble(valorRecebido)-valorTotal; // verificar erro com a string sendo vazia
        // Retornar o valor do troco
        return troco;
    }

    public double getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(double cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }    

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<VendaProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<VendaProduto> produtos) {
        this.produtos = produtos;
    }
    
    public void adicionarProduto(Produto produto){
        VendaProduto vendaProduto = new VendaProduto(this, produto);
        this.getProdutos().add(vendaProduto);
        
    }
    
    // TODO filtrar para que itens inativos não sejam exibidos
    public List<Venda> listarTodos(){
        Session session = Model.abrirSessao();
        String hql = "from Venda where ativo = 1 order by dataCriacao"; 
        Query query = session.createQuery(hql);

        List<Venda> list = query.getResultList();
        session.close();
        return list;
        
        /*List<Venda> vendasCadastradas = (List<Venda>)(Object)super.listarTodos(NOMETABELA);
        return vendasCadastradas;*/
    }
    
    public boolean salvar() {
        
        // Cada venda realizada deve reduzir do estoque dos produtos
        if (this.produtos != null) {
            for (int i = 0; i < this.produtos.size(); i++) {
                this.produtos.get(i).getProduto().subtrairEstoqueVenda(this.produtos.get(i).getQuantidadeCompra());
            }

        }
        return super.salvar();
    }
    
    public static List<Venda> listarPorDataUsuario( LocalDate dataInicio, LocalDate dataTermino, Usuario vendedor){
        Session session = Model.abrirSessao();
        String hql = "from Venda where usuario_id = :usuario AND dataCriacao BETWEEN :dataInicio AND :dataTermino"; // usar groupby
        Query query = session.createQuery(hql);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataTermino", dataTermino);
        query.setParameter("usuario", vendedor.getToken());
        List<Venda> list = query.getResultList();
        
        
        session.close();
        return list;
    }
    
    public static List<Venda> listarPorCliente( Cliente cliente ){
        Session session = Model.abrirSessao();
        String hql = "from Venda where cliente_id = :cliente"; // usar groupby
        Query query = session.createQuery(hql);
        query.setParameter("cliente", cliente.getToken());
        List<Venda> list = query.getResultList();
        
        session.close();
        return list;
    }
    
    
    
    public Venda buildFromJSON(String json){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Venda vendaConvertida = gson.fromJson(json, Venda.class);
        return vendaConvertida;
    }

    public static ArrayList<Venda> listarVendasPorProduto(Produto produto) {
       ArrayList<Venda> vendas = new ArrayList<Venda>();
       if( !produto.getToken().equals("") ){
            try{

                Session session = Model.abrirSessao();
                
                String hql = "from VendaProduto where produto_id = :produto";
                Query query = session.createQuery(hql);
                query.setParameter("produto", produto.getToken());

                List<VendaProduto> list = query.getResultList();
                if( list.size() > 0){
                        
                        // TODO optmizar a consulta para retornar apenas produtos ao invés de produtos e vendaprodutos
                        for(int i = 0; i < list.size(); i++ ){
                                vendas.add(list.get(i).getVenda());
                                
                                
                        }
                    
                }else{
                    return null;
                }
                
                session.close();
                return vendas;
                
            }catch(HibernateException he){
                return null;
            }
       }
       return vendas;
    
    }
    
    public boolean validar(){
        // Venda precisa ter itens
        if( getProdutos().size() == 0)
            return false;
        // Qtd itens ou desconto não pode ser 0 ou negativo
        for( VendaProduto vp: this.getProdutos() ){
            if( !vp.validar() )
                return false;
        }
        
        if( getFormaPagamento() == null )
            return false;
        
        return true;
    }
    
    
}
