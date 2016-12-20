/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static model.FormaPagamento.CREDITOAVISTA;
import static model.FormaPagamento.CREDITOPARCELADO;
import static model.FormaPagamento.DEBITO;
import static model.FormaPagamento.DINHEIRO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import persistencia.HibernateUtil;
import persistencia.Model;

/**
 *
 * @author macbookair
 */
@Entity
public class Produto extends Model implements Serializable{
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;*/
    private String codigo;
    @Expose
    private String descricao;
    private long ncm;
    private int cfog;
    @Expose
    private int quantidadeEstoque;
    private double valorUnitarioAquisicao;
    private double aliquotaICMS;
    private double aliquotaIPI;
    @Expose
    private double valorUnitarioVenda;
    public static enum VALORES_POSSIVEIS_ESTOQUE { 
    
        TODOS("Todos"), POSITIVO("Positivo"), ZERADO("Zerado ou negativo");
        
        private final String label;

        private VALORES_POSSIVEIS_ESTOQUE(String label) {
          this.label = label;
        }

        public String getLabel() {
          return this.label;
        }
    };

    
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fornecedor_id")
    private Fornecedor fornecedor;
    @Transient
    private NotaFiscal nfe; // tirar o Transient e fazer o relacionamento
    @Transient
    public double margemLucro = 20;
    @Transient
    public double margemContribuicao;
    // Despesas variáveis da venda
    
    public static double SIMPLES = 4;
    
    public static double TAXA_CARTAO = 4;
    
    public static double COMISSAO_VENDAS = 2;
    
    public static double TAXAS_ADICIONAIS = 10;
    
    public static double VALOR_SACOLAS = 2;
    
    public static double DESPESAS_FIXAS = 40;
    
    public static int MARGEM_CONTRIBUICAO_DESEJADA = 27;
    

    public Produto() {
        super();
        super.setNomeTabela("Produto");
    }

    public Produto(String codigo, String descricao, long ncm, int cfog, int quantidade, double valorUnitario, long aliquotaICMS, long aliquotaIPI) {
        super();
        super.setNomeTabela("Produto");
        this.codigo = codigo;
        this.descricao = descricao;
        this.ncm = ncm;
        this.cfog = cfog;
        this.quantidadeEstoque = quantidade;
        this.valorUnitarioAquisicao = valorUnitario;
        this.aliquotaICMS = aliquotaICMS;
        this.aliquotaIPI = aliquotaIPI;
        this.margemLucro = 20;
    }
    
    public static ArrayList<Produto> extrairProdutosNfe(InputStream is, NotaFiscal nfe){
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        try {
            builder = factory.newDocumentBuilder();

            Document doc = builder.parse(is);
            ArrayList<Produto> produtosNfe = new ArrayList<Produto>();
            
            
            NodeList emitNode = doc.getElementsByTagName("emit");
            Element emitElement = (Element)emitNode.item(0);
            NodeList xNomeNode = emitElement.getElementsByTagName("xNome");
            String nomeFornecedor = xNomeNode.item(0).getTextContent();
            NodeList cnpjNode = emitElement.getElementsByTagName("CNPJ");
            String cnpjFornecedor = cnpjNode.item(0).getTextContent();
            Fornecedor fornecedor = new Fornecedor(nomeFornecedor, cnpjFornecedor);
            
            NodeList detalhamentoProdutoNode = doc.getElementsByTagName("det");
            //NodeList nos = nfeProc.getChildNodes();
            for(int i = 0; i < detalhamentoProdutoNode.getLength(); i++){
                Element detalheElement = (Element)detalhamentoProdutoNode.item(i);
                // Especificação do imposto ICMS
                NodeList impostoNode = detalheElement.getElementsByTagName("imposto");
                Element impostoElement = (Element)impostoNode.item(0);
                
                Element icmsElement = (Element)impostoElement.getElementsByTagName("ICMS").item(0);
                
                
                Element icms00 = (Element)icmsElement.getElementsByTagName("ICMS00").item(0);
                Element icms10 = (Element)icmsElement.getElementsByTagName("ICMS10").item(0);
                Element icms20 = (Element)icmsElement.getElementsByTagName("ICMS20").item(0);
                Element icms30 = (Element)icmsElement.getElementsByTagName("ICMS30").item(0);
                Element icms40 = (Element)icmsElement.getElementsByTagName("ICMS40").item(0);
                Element icms41 = (Element)icmsElement.getElementsByTagName("ICMS41").item(0);
                Element icms50 = (Element)icmsElement.getElementsByTagName("ICMS50").item(0);
                Element icms51 = (Element)icmsElement.getElementsByTagName("ICMS51").item(0);
                Element icms60 = (Element)icmsElement.getElementsByTagName("ICMS60").item(0);
                Element icms70 = (Element)icmsElement.getElementsByTagName("ICMS70").item(0);
                Element icms90 = (Element)icmsElement.getElementsByTagName("ICMS90").item(0);
                
                Element icmssn101 = (Element)icmsElement.getElementsByTagName("ICMSSN101").item(0);
                
                Element icmssn102 = (Element)icmsElement.getElementsByTagName("ICMSSN102").item(0);
                Element icmssn103 = (Element)icmsElement.getElementsByTagName("ICMSSN103").item(0);
                Element icmssn300 = (Element)icmsElement.getElementsByTagName("ICMSSN300").item(0);
                Element icmssn400 = (Element)icmsElement.getElementsByTagName("ICMSSN400").item(0);
                
                Element icmssn201 = (Element)icmsElement.getElementsByTagName("ICMSSN201").item(0);
                Element icmssn202 = (Element)icmsElement.getElementsByTagName("ICMSSN202").item(0);
                Element icmssn203 = (Element)icmsElement.getElementsByTagName("ICMSSN203").item(0);
                Element icmssn500 = (Element)icmsElement.getElementsByTagName("ICMSSN500").item(0);
                
                Element icmssn900 = (Element)icmsElement.getElementsByTagName("ICMSSN900").item(0);
                
                Element icms = null;
                
                if(icms00 != null){
                    icms = icms00;
                }else if(icmssn101 != null){
                    icms = icmssn101;
                }else if(icms10 != null){
                    icms = icms10;
                }else if(icms20 != null){
                    icms = icms20;
                }else if(icms30 != null){
                    icms = icms30;
                }else if(icms40 != null){
                    icms = icms40;
                }else if(icms41 != null){
                    icms = icms41;
                }else if(icms50 != null){
                    icms = icms50;
                }else if(icms51 != null){
                    icms = icms51;
                }else if(icms70 != null){
                    icms = icms70;
                }else if(icms90 != null){
                    icms = icms90;
                }else if(icmssn101 != null){
                    icms = icmssn101;
                }else if(icmssn102 != null){
                    icms = icmssn102;
                }else if(icmssn103 != null){
                    icms = icmssn103;
                }else if(icmssn300 != null){
                    icms = icmssn300;
                }else if(icmssn400 != null){
                    icms = icmssn400;
                }else if(icmssn201 != null){
                    icms = icmssn201;
                }else if(icmssn202 != null){
                    icms = icmssn202;
                }else if(icmssn203 != null){
                    icms = icmssn203;
                }else if(icmssn500 != null){
                    icms = icmssn500;
                }else if(icmssn900 != null){
                    icms = icmssn900;
                }
                
                Double valorICMSProduto = 0.0;
                Double valorIPIProduto = 0.0;
                
                NodeList percentualICMS = icms.getElementsByTagName("pICMS");
                
                Element percentualIcmsElement = (Element)percentualICMS.item(0);
                if( percentualICMS.item(0) != null ){
                    Number valorICMSTemporario = NumberFormat.getInstance().parse(percentualICMS.item(0).getTextContent().replace(".", ",")); // atentar ao fato de que deve ser usardo , para separar e não . , porém, se o número for milhar deve ser invertido . por , e , por .
                    valorICMSProduto = valorICMSTemporario.doubleValue();
                }

                // Especificação do imposto IPI - é opcional
                Element ipiElement = (Element)impostoElement.getElementsByTagName("IPI").item(0);
                if( ipiElement != null ){
                    Element ipiTribElement = (Element)ipiElement.getElementsByTagName("IPITrib").item(0);
                    if( ipiTribElement != null ){
                        NodeList percentualIPI = ipiTribElement.getElementsByTagName("pIPI");
                        if( percentualIPI != null ){
                            Element percentualIPIElement = (Element)percentualIPI.item(0);
                                if( percentualIPIElement != null ){
                                    
                                    Number valorIPItemporario = NumberFormat.getInstance().parse(percentualIPI.item(0).getTextContent().replace(".", ",")); // atentar ao fato de que deve ser usardo , para separar e não . , porém, se o número for milhar deve ser invertido . por , e , por .
                                    valorIPIProduto = valorIPItemporario.doubleValue();
                                }
                        }
                    }
                }
                
                // Especificação do produto
                NodeList produtoNode = detalheElement.getElementsByTagName("prod");
                Element produtoEment = (Element)produtoNode.item(0);
                NodeList produto = produtoEment.getElementsByTagName("cProd");
                NodeList codigoProdutoNode = produtoEment.getElementsByTagName("cProd");
                String codigoProduto = codigoProdutoNode.item(0).getTextContent();
                NodeList nomeProdutoNode = produtoEment.getElementsByTagName("xProd");
                String nomeProduto = nomeProdutoNode.item(0).getTextContent();
                NodeList ncmProdutoNode = produtoEment.getElementsByTagName("NCM");
                long ncmProduto = Long.parseLong(ncmProdutoNode.item(0).getTextContent());
                NodeList quantidadeProdutoNode = produtoEment.getElementsByTagName("qCom");
                String qtd = quantidadeProdutoNode.item(0).getTextContent().replaceAll("\\..*$", "");
                int quantidadeProduto = Integer.parseInt(qtd);
                NodeList valorProdutoNode = produtoEment.getElementsByTagName("vUnCom");
                String valorString = valorProdutoNode.item(0).getTextContent();
                
                Number valorProdutoTemporario = NumberFormat.getInstance().parse(valorString.replace(".", ",")); // atentar ao fato de que deve ser usardo , para separar e não . , porém, se o número for milhar deve ser invertido . por , e , por .
                Double valorUnitarioProduto = valorProdutoTemporario.doubleValue();
                
                Produto produtoNota = new Produto();
                produtoNota.setAliquotaICMS(valorICMSProduto);
                produtoNota.setAliquotaIPI(valorIPIProduto);
                produtoNota.setCodigo(codigoProduto);
                produtoNota.setNcm(ncmProduto);
                produtoNota.setDescricao(nomeProduto);
                produtoNota.setValorUnitario(valorUnitarioProduto);
                produtoNota.setNfe(nfe);
                produtoNota.setQuantidadeEstoque(quantidadeProduto);
                produtoNota.calcularValorVenda();
                produtoNota.setFornecedor(fornecedor);
                produtosNfe.add(produtoNota);
                
                
            }
            
            return produtosNfe;
        } catch (SAXException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }catch (ParserConfigurationException ex) {
            return null;
        } catch (ParseException ex) {
            return null;
        }catch(NumberFormatException n){
            return null;
        }        
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public NotaFiscal getNfe() {
        return nfe;
    }

    public void setNfe(NotaFiscal nfe) {
        this.nfe = nfe;
    }

/*    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
*/  
    public double getValorUnitarioAquisicao() {
        return valorUnitarioAquisicao;
    }

    public void setValorUnitarioAquisicao(double valorUnitarioAquisicao) {
        this.valorUnitarioAquisicao = valorUnitarioAquisicao;
    }

    public double getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public double getMargemContribuicao() {
        return margemContribuicao;
    }

    public void setMargemContribuicao(double margemContribuicao) {
        this.margemContribuicao = margemContribuicao;
    }

    public double getValorUnitarioVenda() {
        return valorUnitarioVenda;
    }

    public void setValorUnitarioVenda(double valorUnitarioVenda) {
        this.valorUnitarioVenda = valorUnitarioVenda;
    }
    
    public void calcularMargemContribuicao(){
        
        double precoVenda = this.valorUnitarioVenda;
        double custoVariavel = this.valorUnitarioAquisicao+
                               this.valorUnitarioAquisicao*(nfe.getFrete()/100)+
                               (valorUnitarioAquisicao+this.valorUnitarioAquisicao*(nfe.getFrete()/100))*(aliquotaICMS/100)+
                               this.valorUnitarioAquisicao*(aliquotaIPI/100)+
                               this.valorUnitarioAquisicao*(nfe.getFronteira()/100);
        double despesaVariavel = valorUnitarioVenda*(SIMPLES/100)+
                                 valorUnitarioVenda*(TAXA_CARTAO/100)+
                                 valorUnitarioVenda*(COMISSAO_VENDAS/100)+
                                 valorUnitarioVenda*(TAXAS_ADICIONAIS/100)+
                                 VALOR_SACOLAS;
        double margemContribuicaoReais = precoVenda-custoVariavel-despesaVariavel;
        margemContribuicao = Math.round((margemContribuicaoReais/precoVenda)*100);
        
    }
    
    // TODO: Refatorar para retornar o valor do cálculo, ao invés de colocar direto na propriedade
    public void calcularValorVenda(){
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        
        // Cálculo 1
        // uso antigo //double varTemp = ((DESPESAS_FIXAS + nfe.getFrete() + nfe.getFronteira() + aliquotaICMS + aliquotaIPI)+100)/100;
        double frete = (nfe.getFrete()/100);
        double valorFrete = valorUnitarioAquisicao*frete;
        double custoProduto = valorFrete+
                              (valorFrete+valorUnitarioAquisicao)*(aliquotaICMS/100)+
                              valorUnitarioAquisicao*(aliquotaIPI/100)+
                              valorUnitarioAquisicao*(nfe.getFronteira()/100)+
                              valorUnitarioAquisicao*(DESPESAS_FIXAS/100);
                
        valorUnitarioVenda = custoProduto+valorUnitarioAquisicao;
        valorUnitarioVenda = valorUnitarioVenda+valorUnitarioVenda*(SIMPLES/100);
        valorUnitarioVenda = valorUnitarioVenda+valorUnitarioVenda*(TAXA_CARTAO/100);
        valorUnitarioVenda = valorUnitarioVenda+valorUnitarioVenda*(COMISSAO_VENDAS/100);
        valorUnitarioVenda = valorUnitarioVenda+valorUnitarioVenda*(TAXAS_ADICIONAIS/100);
        valorUnitarioVenda = valorUnitarioVenda+valorUnitarioVenda*(margemLucro/100);
        valorUnitarioVenda = valorUnitarioVenda+VALOR_SACOLAS;
        
        valorUnitarioVenda = Math.round(valorUnitarioVenda * 100.0) / 100.0;
        
        this.calcularMargemContribuicao();
        if( margemContribuicao < 27.0){
            margemLucro += 0.5;
            calcularValorVenda();
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getNcm() {
        return ncm;
    }

    public void setNcm(long ncm) {
        this.ncm = ncm;
    }

    public int getCfog() {
        return cfog;
    }

    public void setCfog(int cfog) {
        this.cfog = cfog;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public void subtrairEstoqueVenda(int quantidadeVendida){
        
        this.quantidadeEstoque = this.quantidadeEstoque-quantidadeVendida;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getValorUnitario() {
        return valorUnitarioAquisicao;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitarioAquisicao = valorUnitario;
    }

    public double getAliquotaICMS() {
        return aliquotaICMS;
    }

    public void setAliquotaICMS(double aliquotaICMS) {
        this.aliquotaICMS = aliquotaICMS;
    }

    public double getAliquotaIPI() {
        return aliquotaIPI;
    }

    public void setAliquotaIPI(double aliquotaIPI) {
        this.aliquotaIPI = aliquotaIPI;
    }
    
    public static List<Produto> pesquisarPorNome(String nome){
        Session session = Model.abrirSessao();
        String hql = "from Produto where descricao LIKE :descricao";
        Query query = session.createQuery(hql);
        query.setParameter("descricao", "%"+nome+"%");
        List<Produto> list = query.getResultList();
        session.close();
        return list;
    }
    
    public static Produto getByDescricao(String descricao){
        Produto model = null;
        try{
            Session session = Model.abrirSessao();
            String hql = "from Produto where descricao = :descricao";
            Query query = session.createQuery(hql);
            query.setParameter("descricao", descricao);
            List<Produto> list = query.getResultList();
            session.close();
            if( list.size() > 0){
                return list.get(0);
            }else{
                return null;
            }
        }catch(HibernateException he){
            return null;
        }
        
        
    }
    
    /*public Produto getByCodigo(){
        Produto model = null;
        try{
            Session session = sessionFactory.openSession();
            String hql = "from Produto where codigo = :codigo";
            Query query = session.createQuery(hql);
            query.setParameter("codigo", codigo);
            List<Produto> list = query.getResultList();
            session.close();
            if( list.size() > 0){
                return list.get(0);
            }else{
                return null;
            }
        }catch(HibernateException he){
            return null;
        }
    }*/
    
    // TODO é possível otimizar para fazer isto com um JOIN e apenas uma consulta
    public static List<Produto> listarProdutosPorDia( LocalDate dia ){
        // Listar todas as vendas do dia
        List<Venda> vendasDoDia = Venda.listarVendasPorData(dia);
        ArrayList<Produto> produtosVendidos = new ArrayList<Produto>();
        for( int i = 0; i < vendasDoDia.size(); i++){
            List<VendaProduto> vendaProdutos = listarProdutosPorVenda(vendasDoDia.get(i));
            for( int j = 0; j < vendaProdutos.size(); j++ ){
                produtosVendidos.add(vendaProdutos.get(j).getProduto());
            }
        }
        
        return produtosVendidos;
    }

    public static List<VendaProduto> listarProdutosPorVenda(Venda venda) {
       if( !venda.getToken().equals("")  ){
            try{

                Session session = Model.abrirSessao();
                //String hql = "from VendaProduto vp inner join vp.produto p where vp.venda = :venda";
                String hql = "from VendaProduto where venda_id = :venda";
                Query query = session.createQuery(hql);
                query.setParameter("venda", venda.getToken());

                List<VendaProduto> list = query.getResultList();
                session.close();
                return list;
                /*if( list.size() > 0){
                        List<VendaProduto> resultado = new ArrayList<VendaProduto>();
                        // TODO optmizar a consulta para retornar apenas produtos ao invés de produtos e vendaprodutos
                        for(int i = 0; i < list.size(); i++ ){
                                Object[] resultadosJoin = list.get(i);
                                for(int j = 0; j < resultadosJoin.length; j++){
                                    // Verificar se classe é do tipo Produto, add na lista
                                    if( resultadosJoin[j] instanceof VendaProduto)
                                        resultado.add((VendaProduto)resultadosJoin[j]);
                                }
//resultado.add(list.get(i).)
                        }
                    return resultado;
                }else{
                    return null;
                }*/
            }catch(HibernateException he){
                return null;
            }
       }
       return new ArrayList<VendaProduto>();
    }

    public boolean filtrarPorEstoque(String filtro, Integer quantidade){
        if( filtro == null || filtro.equals("") || filtro.equals(Produto.VALORES_POSSIVEIS_ESTOQUE.TODOS.name()) )
            return true;
        
        String opa = Produto.VALORES_POSSIVEIS_ESTOQUE.POSITIVO.name();
        
        if( filtro.equals(Produto.VALORES_POSSIVEIS_ESTOQUE.POSITIVO.name()) && quantidade > 0)
            return true;
        else if( filtro.equals(Produto.VALORES_POSSIVEIS_ESTOQUE.POSITIVO.name()) && quantidade <= 0 ){
            return false;
        }
        
        if( filtro.equals(Produto.VALORES_POSSIVEIS_ESTOQUE.ZERADO.name()) && quantidade <= 0)
            return true;
        else if( filtro.equals(Produto.VALORES_POSSIVEIS_ESTOQUE.ZERADO.name()) && quantidade > 0 )
            return false;
        
        return true;
    }
    
    public static String listarTodosJson(){
        List<Produto> produtosCadastrados = (List<Produto>)(Object)listarTodos("Produto");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        
        String json = gson.toJson(produtosCadastrados);
        
        return json;
    }
    
    public static Produto getByToken(String token){
        Produto model = null;
        
        try{
            Session session = Model.abrirSessao();
            session.beginTransaction();
            model = session.get(Produto.class, token);
            session.close();
        }catch(HibernateException he){
            return null;
        }
        
        return model;
    }
    
    // TODO apagar se não for usar
    /*public List<Produto> filtrarPorEstoque(String valorEstoqueSelecionado) {
        
        String hql;
        
        if(valorEstoqueSelecionado.equals("Positivo")){
            hql = "from Produto where quantidadeEstoque > 0";
        }else{
            hql = "from Produto where quantidadeEstoque <= 0";
        }
        
        try{
            Session session = sessionFactory.openSession();
            
            Query query = session.createQuery(hql);
            List<Produto> list = query.getResultList();
            session.close();
            if( list.size() > 0){
                return list;
            }else{
                return null;
            }
        }catch(HibernateException he){
            return null;
        }
    }*/

    
    
}
