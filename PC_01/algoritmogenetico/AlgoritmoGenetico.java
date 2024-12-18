package algoritmogenetico;

import java.util.List;

public class AlgoritmoGenetico {
    private int tamanhoPopulacao;
    private double taxaMutacao;
    private double taxaCruzamento;
    private int elitismo;
    private int[][] matrizDeCustos;

    public AlgoritmoGenetico(int tamanhoPopulacao, double taxaMutacao, double taxaCruzamento, int elitismo, int[][] matrizDeCustos) {
        this.tamanhoPopulacao = tamanhoPopulacao;
        this.taxaMutacao = taxaMutacao;
        this.taxaCruzamento = taxaCruzamento;
        this.elitismo = elitismo;
        this.matrizDeCustos = matrizDeCustos;
    }

    public Populacao evoluir(Populacao populacao) {
        return mutarPopulacao(cruzarPopulacao(populacao));
    }

    private Populacao cruzarPopulacao(Populacao populacao) {
        Populacao novaPopulacao = new Populacao(populacao.getIndividuos().size(), matrizDeCustos);

        for (int i = 0; i < populacao.getIndividuos().size(); i++) {
            Individuo pai1 = populacao.getMaisApto();
            if (taxaCruzamento > Math.random() && i >= elitismo) {
                Individuo pai2 = selecionarPai(populacao);
                Individuo filho = Individuo.cruzar(pai1, pai2);
                novaPopulacao.getIndividuos().set(i, filho);
            } else {
                novaPopulacao.getIndividuos().set(i, pai1);
            }
        }

        return novaPopulacao;
    }

    private Populacao mutarPopulacao(Populacao populacao) {
        Populacao novaPopulacao = new Populacao(populacao.getIndividuos().size(), matrizDeCustos);

        for (int i = 0; i < populacao.getIndividuos().size(); i++) {
            Individuo individuo = populacao.getIndividuos().get(i);

            if (i >= elitismo) {
                for (int j = 0; j < individuo.getCromossomo().size(); j++) {
                    if (taxaMutacao > Math.random()) {
                        individuo.mutar();
                    }
                }
            }

            novaPopulacao.getIndividuos().set(i, individuo);
        }

        return novaPopulacao;
    }

    private Individuo selecionarPai(Populacao populacao) {
        List<Individuo> individuos = populacao.getIndividuos();
        double aptidaoTotalPopulacao = individuos.stream().mapToDouble(Individuo::getAptidao).sum();
        double posicaoRoleta = Math.random() * aptidaoTotalPopulacao;

        double roleta = 0;
        for (Individuo individuo : individuos) {
            roleta += individuo.getAptidao();
            if (roleta >= posicaoRoleta) {
                return individuo;
            }
        }
        return individuos.get(individuos.size() - 1);
    }
}