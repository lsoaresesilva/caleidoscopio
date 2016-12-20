/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.ComissaoVendedor;
import model.Usuario;
import model.Venda;

/**
 *
 * @author macbookair
 */
@ManagedBean
@ViewScoped
public class RelatorioController {

    private ComissaoVendedor comissaoVendedor;

    public ComissaoVendedor getComissaoVendedor() {
        return comissaoVendedor;
    }

    public void setComissaoVendedor(ComissaoVendedor comissaoVendedor) {
        this.comissaoVendedor = comissaoVendedor;
    }
    
    public RelatorioController() {
        comissaoVendedor = new ComissaoVendedor();
    }
    
    public double calcularComissao(){
        // Recuperar todas as vendas entre data início e data término daquele vendedor
        return comissaoVendedor.calcularComissao();
        
    }
    
    
    
}
