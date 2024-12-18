package coloniadeformigas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Formiga {
    private List<Integer> caminho;
    private boolean[] visitado;
    private int[][] matrizDeCustos;
    private double[][] feromonio;
    private double alfa;
    private double beta;

    public Formiga(int numeroDeCidades, int[][] matrizDeCustos, double[][] feromonio, double alfa, double beta) {
        this.caminho = new ArrayList<>();
        this.visitado = new boolean[numeroDeCidades];
        this.matrizDeCustos = matrizDeCustos;
        this.feromonio = feromonio;
        this.alfa = alfa;
        this.beta = beta;
    }

    public void iniciarCaminho() {
        caminho.clear();
        for (int i = 0; i < visitado.length; i++) {
            visitado[i] = false;
        }
        caminho.add(0); // Cidade inicial é sempre 0 (cidade 1)
        visitado[0] = true;
    }

    public void escolherProximaCidade() {
        int cidadeAtual = caminho.get(caminho.size() - 1);
        double[] probabilidades = calcularProbabilidades(cidadeAtual);
        int proximaCidade = selecionarCidade(probabilidades);
        caminho.add(proximaCidade);
        visitado[proximaCidade] = true;
    }

    private double[] calcularProbabilidades(int cidadeAtual) {
        double[] probabilidades = new double[visitado.length];
        double soma = 0.0;
        for (int i = 0; i < visitado.length; i++) {
            if (!visitado[i]) {
                probabilidades[i] = Math.pow(feromonio[cidadeAtual][i], alfa) * Math.pow(1.0 / matrizDeCustos[cidadeAtual][i], beta);
                soma += probabilidades[i];
            }
        }
        for (int i = 0; i < probabilidades.length; i++) {
            probabilidades[i] /= soma;
        }
        return probabilidades;
    }

    private int selecionarCidade(double[] probabilidades) {
        double rand = Math.random();
        double soma = 0.0;
        for (int i = 0; i < probabilidades.length; i++) {
            soma += probabilidades[i];
            if (soma >= rand) {
                return i;
            }
        }
        return probabilidades.length - 1;
    }

    public List<Integer> getCaminho() {
        return caminho;
    }

    public int calcularCustoCaminho() {
        int custoTotal = 0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            custoTotal += matrizDeCustos[caminho.get(i)][caminho.get(i + 1)];
        }
        custoTotal += matrizDeCustos[caminho.get(caminho.size() - 1)][caminho.get(0)];
        return custoTotal;
    }
}