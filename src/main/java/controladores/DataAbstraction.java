/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.text.DateFormatSymbols;
import java.util.Date;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.event.SelectEvent;

/**
 * Oferece uma estrutura para uso do dialog de seleção de datas de início e término
 * @author macbookair
 */
public abstract class DataAbstraction {
    
    protected Date dataInicio = null;
    protected Date dataTermino = null;
    
    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public void atualizarDataSelecionada(AjaxBehaviorEvent event) {
        SelectEvent eventoORiginal = (SelectEvent)event;
        Calendar c = (Calendar)eventoORiginal.getSource();
        Date date = (Date) eventoORiginal.getObject();
        if( c.getId().equals("popup") )
            this.dataInicio = date;
        else
            this.dataTermino = date;
    }
    
    public String getDataExibicao(){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(dataInicio);
        int dataSelecionadaInicio = cal.get(java.util.Calendar.DAY_OF_MONTH);
        int mesSelecionadoInicio = cal.get(java.util.Calendar.MONTH);
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        String mesSelecionadoInicioTexto = months[mesSelecionadoInicio];
        
        cal.setTime(dataTermino);
        int dataSelecionadaTermino = cal.get(java.util.Calendar.DAY_OF_MONTH);
        int mesSelecionadoTermino = cal.get(java.util.Calendar.MONTH);
        
        String mesSelecionadoTerminoTexto = months[mesSelecionadoTermino];
        return ""+dataSelecionadaInicio+" "+mesSelecionadoInicioTexto+" a "+dataSelecionadaTermino+" "+mesSelecionadoTerminoTexto;
    }
    
}
