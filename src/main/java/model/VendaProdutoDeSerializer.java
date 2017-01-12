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

/**
 *
 * @author macbookair
 */
public class VendaProdutoDeSerializer implements JsonDeserializer<VendaProduto> {


    @Override
    public VendaProduto deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        final JsonObject jsonObject = (JsonObject)je;
        String vendaToken = jsonObject.get("venda_id").getAsString();
        Venda v = (Venda)Venda.getByToken(Venda.class, vendaToken);
        String produtoToken = jsonObject.get("produto_id").getAsString();
        Produto p = (Produto)Produto.getByToken(Produto.class, produtoToken);
        int quantidadeCompra = jsonObject.get("quantidadeCompra").getAsInt();
        double desconto = jsonObject.get("desconto").getAsDouble();
        VendaProduto vp = new VendaProduto(v, p);
        vp.setQuantidadeCompra(quantidadeCompra);
        vp.setDesconto(desconto);
        return vp;
    }
    
}
