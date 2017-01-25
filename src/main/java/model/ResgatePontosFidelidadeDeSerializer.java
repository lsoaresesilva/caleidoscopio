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
import model.ResgatePontosFidelidade;

/**
 *
 * @author macbookair
 */
public class ResgatePontosFidelidadeDeSerializer implements JsonDeserializer<ResgatePontosFidelidade> {


    @Override
    public ResgatePontosFidelidade deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        final JsonObject jsonObject = (JsonObject)je;
        Double pontosUtilizados = jsonObject.get("pontos_utilizados").getAsDouble();
        String token = jsonObject.get("token").getAsString();
        String clienteToken = jsonObject.get("cliente_id").getAsString();
        Cliente c = (Cliente)Cliente.getByToken(Cliente.class, clienteToken);
        ResgatePontosFidelidade r = new ResgatePontosFidelidade();
        
        r.setToken(token);
        r.setPontosUtilizados(pontosUtilizados);
        r.setCliente(c);
        return r;
    }
    
}
