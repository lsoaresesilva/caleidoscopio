/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.Transient;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import model.Produto;
import model.Venda;
import model.VendaProduto;

/**
 *
 * @author macbookair
 */
@ManagedBean
@ViewScoped
@Path("/produtos")
public class ProdutoController {

    Produto produto;
    String valorSelecionadoEstoque; //TODO ver como nào precisar dessa variável no filtro select
    
    private List<Produto> filteredProducts;

    public String getValorSelecionadoEstoque() {
        return valorSelecionadoEstoque;
    }

    public void setValorSelecionadoEstoque(String valorSelecionadoEstoque) {
        this.valorSelecionadoEstoque = valorSelecionadoEstoque;
    }
    

    public ProdutoController() {
        this.produto = new Produto();
    }
    
    public List<Produto> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(List<Produto> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public List<Produto> listarProdutos(){
        return (List<Produto>)(Object)this.produto.listarTodos("Produto");
    }
    
    public List<VendaProduto> listarProdutosPorVenda( Venda venda){
        return this.produto.listarProdutosPorVenda( venda );
    }
    
    public boolean salvarOuAtualizar(){
        if(!this.produto.getToken().equals("")){
            return this.produto.salvar();
        }else{
            return this.produto.atualizar();
        }
        
    }
    
    
    
    public SelectItem[] getValoresEstoque(){
        
        SelectItem[] items = new SelectItem[Produto.VALORES_POSSIVEIS_ESTOQUE.values().length];
                
        int i = 0;
        for (Produto.VALORES_POSSIVEIS_ESTOQUE p : Produto.VALORES_POSSIVEIS_ESTOQUE.values()) {
            items[i++] = new SelectItem(p, p.getLabel());
        }
        return items;
        //List<Produto.valoresPossiveisEstoque> somethingList = Arrays.asList(Produto.valoresPossiveisEstoque.values());
        
        //return somethingList;
    }
    
   
    
    public boolean filtrarPorEstoque(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        
        
        return this.produto.filtrarPorEstoque((String)filter, (Integer)value);
        
    }
    
    @GET
    @Produces("application/json")
    @Path("listar")
    public String listarProdutosJson(){
        String json = Produto.listarTodosJson();
        return json;
    }
    
}
