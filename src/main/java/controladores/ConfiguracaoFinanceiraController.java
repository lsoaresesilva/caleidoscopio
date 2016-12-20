/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import javax.faces.bean.ManagedBean;
import model.ConfiguracaoFinanceira;
import persistencia.HibernateUtil;

/**
 *
 * @author macbookair
 */
@ManagedBean(name="cfController")
public class ConfiguracaoFinanceiraController {
    
    private ConfiguracaoFinanceira configuracaoFinanceira;
    
    public ConfiguracaoFinanceiraController(){
        configuracaoFinanceira = new ConfiguracaoFinanceira();
    }

    public ConfiguracaoFinanceira getConfiguracaoFinanceira() {
        return configuracaoFinanceira;
    }

    public void setConfiguracaoFinanceira(ConfiguracaoFinanceira configuracaoFinanceira) {
        this.configuracaoFinanceira = configuracaoFinanceira;
    }
    
    public String salvar(){
        //HibernateUtil.getInstance().save(this.configuracaoFinanceira);
        return "";
    }
}
