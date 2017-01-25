/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author macbookair
 */
public class ResgatePontosFidelidadeSerializer implements JsonSerializer<ResgatePontosFidelidade> {

    @Override
    public JsonElement serialize(ResgatePontosFidelidade t, Type type, JsonSerializationContext jsc) {
        
        Gson gson = new Gson();
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", t.getToken());
        jsonObject.addProperty("cliente_id", t.getCliente().getToken());
        jsonObject.addProperty("pontosUtilizados", t.getPontosUtilizados());
        return jsonObject;
    }
    
}
