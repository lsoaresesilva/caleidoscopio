/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author macbookair
 */
public class VendaDeSerializer implements JsonDeserializer<Venda> {


    @Override
    public Venda deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        
        final JsonObject jsonObject = (JsonObject)je;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(VendaProduto.class, new VendaProdutoDeSerializer());
        builder.registerTypeAdapter(ResgatePontosFidelidade.class, new ResgatePontosFidelidadeDeSerializer());
        Gson gson = builder.create();
        
        Venda v = new Venda();
        String vendaToken = jsonObject.get("token").getAsString();
        String usuarioToken = jsonObject.get("usuario_id").getAsString();
        Usuario u = (Usuario)Usuario.getByToken(usuarioToken);
        String clienteToken = jsonObject.get("cliente_id").getAsString();
        Cliente c = (Cliente)Cliente.getByToken(Cliente.class, clienteToken);
        String formaPagamento = jsonObject.get("formaPagamento").getAsString();
        String resgatePontos = jsonObject.get("resgate_pontos").getAsString();
        ResgatePontosFidelidade r = gson.fromJson(resgatePontos, ResgatePontosFidelidade.class);
        String dataCriacao = jsonObject.get("dataCriacao").getAsString();
        LocalDate data = LocalDate.parse(dataCriacao);
        v.setToken(vendaToken);
        v.setDataCriacao(data);
        v.setFormaPagamento(FormaPagamento.getByType(formaPagamento));
        v.setUsuario(u);
        v.setCliente(c);
        v.setPontuacaoUtilizada(r);
        
        //String produtos = jsonObject.get("produtos").getAsString();
        
        //Type collectionType = new TypeToken<ArrayList<VendaProduto>>(){}.getType();
        //ArrayList<VendaProduto> vendaProdutos = gson.fromJson(produtos, collectionType);
        JsonArray produtosArray = jsonObject.get("produtos").getAsJsonArray();
        ArrayList<VendaProduto> produtosVenda = new ArrayList<VendaProduto>();
        for (int i=0; i<produtosArray.size(); i++) {
            JsonPrimitive elemento = (JsonPrimitive) produtosArray.get(i);
            VendaProduto vp = gson.fromJson(elemento.getAsString(), VendaProduto.class);
            if( vp.getVenda() == null )
                vp.setVenda(v);
            produtosVenda.add(vp);
        }
        
        v.setProdutos(produtosVenda);
        
        
        return v;
    }
    
}
