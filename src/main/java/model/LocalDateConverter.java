/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author macbookair
 */
@FacesConverter("localDateConverter")
public class LocalDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataOriginal = LocalDate.parse(string, formatter);

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataFinal = LocalDate.parse(dataOriginal.format(formatter));
            return dataFinal;
        } catch (DateTimeParseException de) {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof LocalDate) {

            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return ((LocalDate) o).format(formatter);
        } else {
            return "";
        }

    }

}
