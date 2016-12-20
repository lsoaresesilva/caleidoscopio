/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Transient;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import model.FormaPagamento;
import model.Produto;
import model.Venda;
import model.Usuario;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.DateViewChangeEvent;
import org.primefaces.event.SelectEvent;
/**
 *
 * @author macbookair
 */
@ManagedBean
@ViewScoped 
@Path("/vendas")
public class VendaController {
    
    private Venda venda;
    private Date dataPesquisa = null;
    
    private Produto produtoTemporario; // usado para ajustar as defini
    
    @PostConstruct
    public void init(){
        produtoTemporario = new Produto();
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioLogado = (Usuario)context.getExternalContext().getSessionMap().get("usuarioLogado");
        // Verificar se usuário está logado (diferente de Nulo), se não emitir erro
        venda = new Venda(usuarioLogado);
        /*Produto p = new Produto("123", "Produto", 123, 1, 1, 2.4, 7, 0);
        p.salvar();
        
        Venda v = new Venda();
        v.setFormaPagamento(FormaPagamento.CREDITOAVISTA);

        VendaProduto vp = new VendaProduto();
        vp.setProduto(p);
        
        ArrayList<VendaProduto> vendaProdutos = new ArrayList();
        vendaProdutos.add(vp);
        v.setProdutos(vendaProdutos);
        vp.setVenda(v);
        v.salvar();*/
    }

    public Date getDataPesquisa() {
        return dataPesquisa;
    }

    public void setDataPesquisa(Date dataPesquisa) {
        this.dataPesquisa = dataPesquisa;
    }
    
    public void atualizarDataSelecionada(SelectEvent event) {
        Date date = (Date) event.getObject();
        this.dataPesquisa = date;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    public Produto getProdutoTemporario() {
        return produtoTemporario;
    }

    public void setProdutoTemporario(Produto produtoTemporario) {
        this.produtoTemporario = produtoTemporario;
    }
    
    public List<Produto> filtrarProdutos(String consulta){
        List<Produto> produtos = Produto.pesquisarPorNome(consulta);
        return produtos;
    }
    
    public List<FormaPagamento> filtrarFormasPagamento(String consulta){
        List<FormaPagamento> formaPagamento = FormaPagamento.pesquisarPorNome(consulta);
        return formaPagamento;
    }
    
    public void adicionarProduto(){
        
        this.venda.adicionarProduto(produtoTemporario);
        produtoTemporario = null;
        produtoTemporario = new Produto();
        
    }
    
    public void removerProduto(int index){
        venda.getProdutos().remove(index);
    }

    public VendaController() {
        
    }
    
    public double calcularDescontoProduto(int index){
        return venda.getProdutos().get(index).calcularDesconto();
    }
    
    public String onFlowProcess(FlowEvent event) {
            String evento = event.getNewStep();
            return event.getNewStep();
    }
    
    public double calcularTroco(String valorRecebido){
        if( !valorRecebido.equals(""))
            return this.venda.calcularTroco(valorRecebido);
        return 0.0;
    }
    
    public String salvar(){
        
        if( this.venda.salvar() ){
            return "listagem_vendas.xhtml";
        }
        
        // Adicionar mensagem de erro
        return "";
    }
    
    public List<Venda> listarVendasPorData(){
        if( dataPesquisa == null ) // Irá retornar todas as vendas cadastradas
            return this.venda.listarTodos();
        else{
            return Venda.listarVendasPorData(dataPesquisa.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }
    
    public List<Produto> listarProdutosPorVenda(){
        return null;
    }
    
    public List<Venda> listarVendasPorProduto( Produto produto ){
        return Venda.listarVendasPorProduto(produto);
    }
    
    @POST
    @Path("salvar")
    public String salvarVenda(){
        return "";
    }
    
}
