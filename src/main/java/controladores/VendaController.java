/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Cliente;
import model.Fidelidade;
import model.FormaPagamento;
import model.Produto;
import model.ResgatePontosFidelidade;
import model.Venda;
import model.Usuario;
import model.VendaDeSerializer;
import model.VendaProduto;
import model.VendaProdutoDeSerializer;
import org.hibernate.HibernateException;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.DateViewChangeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
/**
 *
 * @author macbookair
 */
@ManagedBean(name="vendaController")
@ViewScoped 
@Path("/vendas")
public class VendaController extends DataAbstraction{
    
    private Venda venda;
    
    private boolean utilizarCupomDesconto;
    @ManagedProperty(value="#{clienteController}")
    private ClienteController cliente;
    
    private Produto produtoTemporario; // Ao ser adicionado à venda é resetado para ser usado novamente
    
    @PostConstruct
    public void init(){
        utilizarCupomDesconto = false;
        produtoTemporario = new Produto();
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioLogado = null;
        if( context != null){ // TODO o que fazer com o user?
            usuarioLogado = (Usuario)context.getExternalContext().getSessionMap().get("usuarioLogado");
        // Verificar se usuário está logado (diferente de Nulo), se não emitir erro
        }
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

    public boolean isUtilizarCupomDesconto() {
        return utilizarCupomDesconto;
    }

    public void setUtilizarCupomDesconto(boolean utilizarCupomDesconto) {
        this.utilizarCupomDesconto = utilizarCupomDesconto;
    }
    
    

    public ClienteController getCliente() {
        return cliente;
    }

    public void setCliente(ClienteController cliente) {
        this.cliente = cliente;
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
    
    public double calcularDescontoPontosFidelidade(){ 
        return Fidelidade.calcularDescontoComSaldoPontosFidelidade(venda.getCliente());
    }
    
    public double calcularPontuacao(){ 
        return Fidelidade.calcularPontuacao(venda.getCliente());
    }
    
    /**
     * É invocado pelo controlador ao remzover um cliente previamente selecionado na venda.
     * @param event 
     */
    public void tirarCliente(AjaxBehaviorEvent event){
        this.venda.setCliente(null);
    }
    
    public void selecionarCliente(SelectEvent event) {
        Cliente clienteSelecionado = (Cliente)event.getObject();
        this.venda.setCliente(clienteSelecionado);
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
        
        if (produtoTemporario != null) {
            this.venda.adicionarProduto(produtoTemporario);
            produtoTemporario = null;
            produtoTemporario = new Produto();
        }
        
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
        FacesContext context = FacesContext.getCurrentInstance();
        if ( this.venda.validar()) {
            
            if (this.venda.salvarOuAtualizar()) {
                if (context != null) 
                    context.addMessage(null, new FacesMessage("Successo", "Venda realizada com sucesso!"));
                return "listagem_vendas.xhtml";
            } else if (context != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Houve uma falha ao cadastrar a venda. Por gentileza, refaça a operação."));
            }
        }else{
            if (context != null) 
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é possível cadastrar a venda. Verifique se foram adicionados produtos ou se a forma de pagamento foi informada."));
        }
        
        // Adicionar mensagem de erro
        return "";
    }
    
    public List<Venda> listarVendasPorData(){
        if( dataInicio == null || dataTermino == null) // Irá retornar todas as vendas cadastradas
            return this.venda.listarTodos();
        else{
            return Venda.listarVendasPorData(dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), dataTermino.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }
    
    public List<Produto> listarProdutosPorVenda(){
        return null;
    }
    
    public List<Venda> listarVendasPorProduto( Produto produto ){
        return Venda.listarVendasPorProduto(produto);
    }
    
    public void excluirVenda(){
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.getVenda().excluir() ){
            if (context != null) 
                    context.addMessage(null, new FacesMessage("Operação com sucesso", "Venda excluída com sucesso!"));
        }else{
            if (context != null) 
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir a venda. Tente novamente."));
        }
    }
    
    public void definirCupomDesconto(){
        this.venda.utilizarPontosFidelidade(utilizarCupomDesconto);
    }
    
    @POST
    @Path("salvar")
    @Consumes(MediaType.APPLICATION_JSON) 
    public Response salvarVendaPDV(String json){
        
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Venda.class, new VendaDeSerializer());
        Gson gson = builder.create();
        Response r = Response.serverError().build();
        try {
            Venda v = gson.fromJson(json, Venda.class);
            if( ! v.salvarVendaPDV() ){
                return r;
            }
            r = Response.ok().build();
            return r;
        } catch (HibernateException he) {
            return r;
        } catch (JsonSyntaxException je) {
            return r;
        }
    }
    
    
    
}
