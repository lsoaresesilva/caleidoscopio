/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import model.Cliente;
import model.LancamentoFinanceiro;
import model.Produto;
import model.TipoLancamentoFinanceiro;

/**
 *
 * @author macbookair
 */
@ManagedBean(name="lfController")
public class LancamentoFinanceiroController {
    
    private LancamentoFinanceiro lancamentoSelecionado;

    public LancamentoFinanceiroController() {
        lancamentoSelecionado = new LancamentoFinanceiro();
    }
    
    public String listar(){
        return "lancamento_financeiro/listagem.xhtml";
    }
    
    public List<LancamentoFinanceiro> listarLancamentos(){
        return (List<LancamentoFinanceiro>)(Object)LancamentoFinanceiro.listarTodos("LancamentoFinanceiro");
    }
    
    public void excluir(){
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.lancamentoSelecionado.excluir() ){
            if (context != null) 
                    context.addMessage(null, new FacesMessage("Sucesso", "Lançamento excluído com sucesso!"));
        }else{
            if (context != null) 
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir o lançamento. Tente novamente."));
        }
    }
    
    public SelectItem[] getTiposLancamentos(){
        
        SelectItem[] items = new SelectItem[TipoLancamentoFinanceiro.values().length];
                
        int i = 0;
        for (TipoLancamentoFinanceiro p : TipoLancamentoFinanceiro.values()) {
            items[i++] = new SelectItem(p, p.getLabel());
        }
        return items;
    }
    
    public void salvarOuAtualizar(){
        FacesContext context = FacesContext.getCurrentInstance();
        boolean sucesso = this.lancamentoSelecionado.salvarOuAtualizar();

        if (sucesso) {
            if (context != null) {
                context.addMessage(null, new FacesMessage("Sucesso", "Lançamento atualizado com sucesso!"));
            }
            this.lancamentoSelecionado = new LancamentoFinanceiro();
        } else {
            if (context != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível atualizar o lançamento. Tente novamente."));
            }
        }
        
    }

    public LancamentoFinanceiro getLancamentoSelecionado() {
        return lancamentoSelecionado;
    }

    public void setLancamentoSelecionado(LancamentoFinanceiro lancamentoSelecionado) {
        this.lancamentoSelecionado = lancamentoSelecionado;
    }
    
    
}
