package coloniadeformigas;

import java.util.ArrayList;
import java.util.List;

public class ColoniaDeFormigas {
    private int numeroDeFormigas;
    private int numeroDeCidades;
    private int[][] matrizDeCustos;
    private double[][] feromonio;
    private double evaporacao;
    private double alfa;
    private double beta;
    private List<Formiga> formigas;

    public ColoniaDeFormigas(int numeroDeFormigas, int numeroDeCidades, int[][] matrizDeCustos, double evaporacao, double alfa, double beta) {
        this.numeroDeFormigas = numeroDeFormigas;
        this.numeroDeCidades = numeroDeCidades;
        this.matrizDeCustos = matrizDeCustos;
        this.feromonio = new double[numeroDeCidades][numeroDeCidades];
        this.evaporacao = evaporacao;
        this.alfa = alfa;
        this.beta = beta;
        this.formigas = new ArrayList<>();
        for (int i = 0; i < numeroDeFormigas; i++) {
            formigas.add(new Formiga(numeroDeCidades, matrizDeCustos, feromonio, alfa, beta));
        }
        inicializarFeromonio();
    }

    private void inicializarFeromonio() {
        for (int i = 0; i < numeroDeCidades; i++) {
            for (int j = 0; j < numeroDeCidades; j++) {
                feromonio[i][j] = 1.0;
            }
        }
    }

    public void executar(int iteracoes) {
        for (int iteracao = 0; iteracao < iteracoes; iteracao++) {
            for (Formiga formiga : formigas) {
                formiga.iniciarCaminho();
                while (formiga.getCaminho().size() < numeroDeCidades) {
                    formiga.escolherProximaCidade();
                }
            }
            atualizarFeromonio();
        }
    }

    private void atualizarFeromonio() {
        for (int i = 0; i < numeroDeCidades; i++) {
            for (int j = 0; j < numeroDeCidades; j++) {
                feromonio[i][j] *= (1 - evaporacao);
            }
        }
        for (Formiga formiga : formigas) {
            int custoCaminho = formiga.calcularCustoCaminho();
            List<Integer> caminho = formiga.getCaminho();
            for (int i = 0; i < caminho.size() - 1; i++) {
                int cidadeAtual = caminho.get(i);
                int proximaCidade = caminho.get(i + 1);
                feromonio[cidadeAtual][proximaCidade] += 1.0 / custoCaminho;
                feromonio[proximaCidade][cidadeAtual] += 1.0 / custoCaminho;
            }
            int ultimaCidade = caminho.get(caminho.size() - 1);
            int primeiraCidade = caminho.get(0);
            feromonio[ultimaCidade][primeiraCidade] += 1.0 / custoCaminho;
            feromonio[primeiraCidade][ultimaCidade] += 1.0 / custoCaminho;
        }
    }

    public List<Integer> getMelhorCaminho() {
        Formiga melhorFormiga = formigas.get(0);
        int menorCusto = melhorFormiga.calcularCustoCaminho();
        for (Formiga formiga : formigas) {
            int custoCaminho = formiga.calcularCustoCaminho();
            if (custoCaminho < menorCusto) {
                melhorFormiga = formiga;
                menorCusto = custoCaminho;
            }
        }
        return melhorFormiga.getCaminho();
    }

    public int getCustoMelhorCaminho() {
        Formiga melhorFormiga = formigas.get(0);
        int menorCusto = melhorFormiga.calcularCustoCaminho();
        for (Formiga formiga : formigas) {
            int custoCaminho = formiga.calcularCustoCaminho();
            if (custoCaminho < menorCusto) {
                melhorFormiga = formiga;
                menorCusto = custoCaminho;
            }
        }
        return menorCusto;
    }
}