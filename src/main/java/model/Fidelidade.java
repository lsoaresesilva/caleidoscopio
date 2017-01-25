/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author macbookair
 */
public class Fidelidade {

    private static final double FATOR_MULTIPLICACAO = 0.03;

    public static double calcularPontuacao(Cliente cliente) {

        double pontuacao = 0.0;
        if (cliente != null) {
            double valorComprado = 0.0;
            List<Venda> vendasCliente = Venda.listarPorCliente(cliente);
            for (int i = 0; i < vendasCliente.size(); i++) {
                valorComprado = valorComprado + vendasCliente.get(i).calcularValorFinal();

            }
            
            double pontosUtilizados = 0.0;
            
            List<ResgatePontosFidelidade> pontosResgatados = ResgatePontosFidelidade.listarPorCliente(cliente);
            for (int i = 0; i < pontosResgatados.size(); i++) {
                pontosUtilizados = pontosUtilizados + pontosResgatados.get(i).getPontosUtilizados();

            }

            pontuacao = valorComprado-pontosUtilizados;
        }
        /**
         * TODO recuperar o total de pontos resgatados. A pontuação final será =
         * valorComprado - pontosResgatados;
         */
        return pontuacao;
    }

    public static double calcularDescontoComSaldoPontosFidelidade(Cliente cliente) {

        // Ter o total de pontuacao
        double desconto = 0.0;
        if (cliente != null) {

            double pontuacao = Fidelidade.calcularPontuacao(cliente);
            desconto = calcularDesconto(pontuacao);
        }
        return desconto;

    }

    public static double calcularDesconto(double pontos) {

        // Ter o total de pontuacao
        double desconto = 0.0;

        // Verificar quanto de desconto é possível obter com aquele desconto
        // Regra de cálculo: para cada 150 reais o cliente ganha 5 reais de desconto
        if (pontos > 150) {
            desconto = pontos * FATOR_MULTIPLICACAO; // 5 reais de 150 representa 3%
        }

        return desconto;

    }

}
