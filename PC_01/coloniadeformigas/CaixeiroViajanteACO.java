package coloniadeformigas;

import java.util.List;
import java.util.stream.Collectors;

public class CaixeiroViajanteACO {
    public static void main(String[] args) {
        int[][] matrizDeCustos = {
            {0, 10, 15, 45, 5, 45, 50, 44, 30, 100, 67, 33, 90, 17, 50},
            {15, 0, 100, 30, 20, 25, 80, 45, 41, 5, 45, 10, 90, 10, 35},
            {40, 80, 0, 90, 70, 33, 100, 70, 30, 23, 80, 60, 47, 33, 25},
            {100, 8, 5, 0, 5, 40, 21, 20, 35, 14, 55, 35, 21, 5, 40},
            {17, 10, 33, 45, 0, 14, 50, 27, 33, 60, 17, 10, 20, 13, 71},
            {15, 70, 90, 20, 11, 0, 15, 35, 30, 15, 18, 35, 15, 90, 23},
            {25, 19, 18, 30, 100, 55, 0, 70, 55, 41, 55, 100, 18, 14, 18},
            {40, 15, 60, 45, 70, 33, 25, 0, 27, 60, 80, 35, 30, 41, 35},
            {21, 34, 17, 10, 11, 40, 8, 32, 0, 47, 76, 40, 21, 90, 21},
            {35, 100, 5, 18, 43, 25, 14, 30, 39, 0, 17, 35, 15, 13, 40},
            {38, 20, 23, 30, 5, 55, 50, 33, 70, 14, 0, 60, 30, 35, 21},
            {15, 14, 45, 21, 100, 10, 8, 20, 35, 43, 8, 0, 15, 100, 23},
            {80, 10, 5, 20, 35, 8, 90, 5, 44, 10, 80, 14, 0, 25, 80},
            {33, 90, 40, 18, 70, 45, 25, 23, 90, 44, 43, 70, 5, 0, 25},
            {25, 70, 45, 50, 5, 45, 20, 100, 25, 50, 35, 10, 90, 5, 0}
        };

        int numeroDeFormigas = 100;
        double evaporacao = 0.2;
        double alfa = 1.0;
        double beta = 2.0;
        int iteracoes = 1000;

        ColoniaDeFormigas colonia = new ColoniaDeFormigas(numeroDeFormigas, matrizDeCustos.length, matrizDeCustos, evaporacao, alfa, beta);
        colonia.executar(iteracoes);

        List<Integer> melhorCaminho = colonia.getMelhorCaminho().stream().map(cidade -> cidade + 1).collect(Collectors.toList());
        int custoMelhorCaminho = colonia.getCustoMelhorCaminho();

        System.out.println("Melhor caminho: " + melhorCaminho);
        System.out.println("Custo do melhor caminho: " + custoMelhorCaminho);
    }
}