/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Cliente;
import model.Produto;

/**
 *
 * @author macbookair
 */
@ManagedBean(name="clienteController")
@ViewScoped
public class ClienteController {
    private Cliente cliente;
    private List<Cliente> filteredClientes;

    public ClienteController() {
        cliente = new Cliente();
    }
    
    public double calcularPremiacao(){ 
        return this.cliente.calcularPremiacao();
    }
    
    public double calcularPontuacao(){ 
        return this.cliente.calcularPontuacao();
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
        if(this.cliente.getToken().equals("")){
            if( this.cliente.salvar() ){
                if (context != null) 
                    context.addMessage(null, new FacesMessage("Operação com sucesso", "Cliente cadastrado com sucesso!"));
                this.cliente = new Cliente();
            }else
                if (context != null) 
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível cadastrar o cliente. Tente novamente."));
        }else{
            if( this.cliente.atualizar() ){
                if (context != null) 
                    context.addMessage(null, new FacesMessage("Operação com sucesso", "Cliente atualizado com sucesso!"));
                this.cliente = new Cliente();
            }else
                if (context != null) 
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível atualizar o cliente. Tente novamente."));
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
    
    
}
