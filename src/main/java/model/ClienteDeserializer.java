/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 *
 * @author macbookair
 */
public class ClienteDeserializer implements JsonDeserializer<Cliente> {


    @Override
    public Cliente deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        final JsonObject jsonObject = (JsonObject)je;
        String token = jsonObject.get("token").getAsString();
        String nome = jsonObject.get("nome").getAsString();
        String telefone = jsonObject.get("telefone").getAsString();
        String email = jsonObject.get("email").getAsString();
        String dataNascimento = jsonObject.get("dataNascimento").getAsString();
        LocalDate dataNascimentoConvertida = LocalDate.parse(dataNascimento);
        Cliente c = new Cliente(nome, email, telefone, dataNascimentoConvertida);
        return c;
    }
    
}
