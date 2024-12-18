package algoritmogenetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Populacao {
    private List<Individuo> individuos;
    private int[][] matrizDeCustos;

    public Populacao(int tamanhoPopulacao, int[][] matrizDeCustos) {
        this.matrizDeCustos = matrizDeCustos;
        individuos = new ArrayList<>(tamanhoPopulacao);
        for (int i = 0; i < tamanhoPopulacao; i++) {
            individuos.add(new Individuo(gerarCromossomoAleatorio(matrizDeCustos.length), matrizDeCustos));
        }
    }

    private List<Integer> gerarCromossomoAleatorio(int tamanho) {
        List<Integer> cromossomo = new ArrayList<>(tamanho + 1);
        cromossomo.add(0); // Cidade de origem
        for (int i = 1; i < tamanho; i++) {
            cromossomo.add(i);
        }
        Collections.shuffle(cromossomo.subList(1, tamanho)); // Mantém a cidade de origem no início
        cromossomo.add(0); // Cidade de origem no fim
        return cromossomo;
    }

    public List<Individuo> getIndividuos() {
        return individuos;
    }

    public Individuo getMaisApto() {
        return Collections.min(individuos, (individuo1, individuo2) -> {
            int aptidao1 = individuo1.getAptidao();
            int aptidao2 = individuo2.getAptidao();
            return Integer.compare(aptidao1, aptidao2);
        });
    }
}