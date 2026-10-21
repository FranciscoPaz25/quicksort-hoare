/**
 * Gera os vetores de entrada do trabalho: permutações aleatórias dos
 * inteiros de 1 até n, sem repetição.
 *
 * O embaralhamento usa o algoritmo de Fisher-Yates, que dá a mesma
 * chance a todas as n! permutações possíveis.
 */
public class GeradorVetores {

    /**
     * Cria um vetor já ordenado com os valores 1, 2, ..., n.
     * A posição i recebe o valor i + 1.
     */
    public static Vetor criarVetor(int n) {
        Vetor v = new Vetor(n);

        for (int i = 0; i < v.tamanho(); i++) {
            v.set(i, i + 1);
        }

        return v;
    }

    /**
     * Sorteia um inteiro entre 0 e max, inclusive.
     *
     * Math.random() devolve um decimal em [0, 1). Multiplicando por
     * (max + 1) e cortando a parte decimal com (int), o resultado fica
     * entre 0 e max. Os parênteses são necessários para que a
     * multiplicação aconteça antes da conversão para int.
     *
     * Toda a aleatoriedade do programa passa por este método.
     */
    public static int sortear(int max) {
        return (int) (Math.random() * (max + 1));
    }

    /**
     * Embaralha o vetor com o algoritmo de Fisher-Yates.
     *
     * Percorre as posições do fim para o começo. Em cada posição i,
     * sorteia uma posição j entre 0 e i e troca as duas. Depois disso,
     * a posição i está "decidida" e não é mais mexida. A posição 0 não
     * precisa de sorteio: sobra por eliminação.
     *
     * Como o primeiro passo tem n opções, o segundo n - 1, e assim por
     * diante, são n! caminhos possíveis, um para cada permutação.
     */
    public static void embaralhar(Vetor v) {
        for (int i = v.tamanho() - 1; i > 0; i--) {
            int j = sortear(i);   // posição entre 0 e i
            v.trocar(i, j);       // a posição i fica decidida
        }
    }

    /** Cria uma permutação aleatória dos inteiros de 1 a n. */
    public static Vetor gerarPermutacao(int n) {
        Vetor v = criarVetor(n);
        embaralhar(v);
        return v;
    }
}
