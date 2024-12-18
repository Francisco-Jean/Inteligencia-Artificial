package algoritmogenetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individuo {
    private List<Integer> cromossomo;
    private int aptidao = 0;
    private boolean aptidaoAlterada = true;
    private int[][] matrizDeCustos;

    public Individuo(List<Integer> cromossomo, int[][] matrizDeCustos) {
        this.cromossomo = new ArrayList<>(cromossomo);
        this.matrizDeCustos = matrizDeCustos;
    }

    public List<Integer> getCromossomo() {
        aptidaoAlterada = true;
        return cromossomo;
    }

    public int getAptidao() {
        if (aptidaoAlterada) {
            aptidao = calcularAptidao();
            aptidaoAlterada = false;
        }
        return aptidao;
    }

    private int calcularAptidao() {
        int custoTotal = 0;
        for (int i = 0; i < cromossomo.size() - 1; i++) {
            custoTotal += matrizDeCustos[cromossomo.get(i)][cromossomo.get(i + 1)];
        }
        return custoTotal;
    }

    public void mutar() {
        // Garante que a cidade de origem (0) não seja alterada
        int pos1 = 1 + (int) ((cromossomo.size() - 2) * Math.random());
        int pos2 = 1 + (int) ((cromossomo.size() - 2) * Math.random());
        Collections.swap(cromossomo, pos1, pos2);
    }

    public static Individuo cruzar(Individuo pai1, Individuo pai2) {
        List<Integer> cromossomoFilho = new ArrayList<>(pai1.getCromossomo().size());
        for (int i = 0; i < pai1.getCromossomo().size(); i++) {
            cromossomoFilho.add(null);
        }

        int posicaoInicial = 1 + (int) (Math.random() * (pai1.getCromossomo().size() - 2));
        int posicaoFinal = 1 + (int) (Math.random() * (pai1.getCromossomo().size() - 2));

        for (int i = 0; i < cromossomoFilho.size(); i++) {
            if (posicaoInicial < posicaoFinal && i > posicaoInicial && i < posicaoFinal) {
                cromossomoFilho.set(i, pai1.getCromossomo().get(i));
            } else if (posicaoInicial > posicaoFinal) {
                if (!(i < posicaoInicial && i > posicaoFinal)) {
                    cromossomoFilho.set(i, pai1.getCromossomo().get(i));
                }
            }
        }

        for (int i = 0; i < pai2.getCromossomo().size(); i++) {
            if (!cromossomoFilho.contains(pai2.getCromossomo().get(i))) {
                for (int j = 0; j < cromossomoFilho.size(); j++) {
                    if (cromossomoFilho.get(j) == null) {
                        cromossomoFilho.set(j, pai2.getCromossomo().get(i));
                        break;
                    }
                }
            }
        }

        // Garante que a cidade de origem (0) esteja no início e no fim
        cromossomoFilho.set(0, 0);
        cromossomoFilho.set(cromossomoFilho.size() - 1, 0);

        return new Individuo(cromossomoFilho, pai1.matrizDeCustos);
    }
}