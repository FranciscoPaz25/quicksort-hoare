public class GeradorVetores {

    public static Vetor criarVetor(int n) {
        Vetor v = new Vetor(n);

        for (int i = 0; i < v.tamanho(); i++) {
            v.set(i, i + 1);
        }

        return v;
    }

    // Devolve um inteiro aleatório entre 0 e max, inclusive
    public static int sortear(int max) {
        return (int) (Math.random() * (max + 1));
    }

    // Embaralha o vetor com o algoritmo de Fisher-Yates
    public static void embaralhar(Vetor v) {
        for (int i = v.tamanho() - 1; i > 0; i--) {
            int j = sortear(i); // posição entre 0 e i
            v.trocar(i, j); // a posição i fica "decidida"
        }
    }

    // Cria uma permutação aleatória de 1 a n
    public static Vetor gerarPermutacao(int n) {
        Vetor v = criarVetor(n);
        embaralhar(v);
        return v;
    }

}
