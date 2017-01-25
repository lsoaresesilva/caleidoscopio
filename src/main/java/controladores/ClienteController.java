/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Cliente;
import model.ClienteDeserializer;
import model.Fidelidade;
import model.LancamentoFinanceiro;
import model.Produto;
import model.Venda;
import model.VendaDeSerializer;
import org.hibernate.HibernateException;

/**
 *
 * @author macbookair
 */
@ManagedBean(name="clienteController")
@ViewScoped
@Path("/clientes")
public class ClienteController {
    private Cliente cliente;
    private List<Cliente> filteredClientes;

    public ClienteController() {
        cliente = new Cliente();
    }
    
    
    
    public List<Cliente> filtrar(String consulta){
        List<Cliente> clientes = Cliente.pesquisarTodosCampos(consulta);
        return clientes;
    }
    
    public List<Cliente> listar(){
        return (List<Cliente>)(Object)Cliente.listarTodos("Cliente");
    }
    
    public void excluir(){
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.cliente.excluir() ){
            if (context != null) 
                    context.addMessage(null, new FacesMessage("Operação com sucesso", "Cliente excluído com sucesso!"));
        }else{
            if (context != null) 
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir o cliente. Tente novamente."));
        }
    }
    
    public void salvarOuAtualizar(){
        FacesContext context = FacesContext.getCurrentInstance();
        boolean sucesso = this.cliente.salvarOuAtualizar();

        if (sucesso) {
            if (context != null) {
                context.addMessage(null, new FacesMessage("Sucesso", "Cliente atualizado com sucesso!"));
            }
            this.cliente = new Cliente();
        } else {
            if (context != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível atualizar o cliente. Tente novamente."));
            }
        }

    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getFilteredClientes() {
        return filteredClientes;
    }

    public void setFilteredClientes(List<Cliente> filteredClientes) {
        this.filteredClientes = filteredClientes;
    }
    
    @GET
    @Produces("application/json")
    @Path("listar")
    public String listarClientesJson(){
        String json = Cliente.listarTodosJson();
        return json;
    }
    
    @GET
    @Produces("application/json")
    @Path("consultar_pontuacao")
    public String consultarPontuacao( @QueryParam("token") String token ){
        /**
         * TODO Optimizar para fazer isto em apenas uma consulta com join
         */
        Cliente clientePesquisa = (Cliente)Cliente.getByToken(Cliente.class, token);
        
        String json = clientePesquisa.retornarPontuacaoJSON();
        return json;
    }
    
    @POST
    @Path("salvar")
    @Consumes(MediaType.APPLICATION_JSON) 
    public Response salvarClientePDV(String json){
        
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Cliente.class, new ClienteDeserializer());
        Gson gson = builder.create();
        Response r = Response.serverError().build();
        try {
            Cliente c = gson.fromJson(json, Cliente.class);
            if( ! c.salvar() ){
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
