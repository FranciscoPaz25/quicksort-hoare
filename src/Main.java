/**
 * Ponto de entrada do programa.
 *
 * Entrada: nenhuma. Os vetores são gerados pelo próprio programa
 * (casos fixos de borda e permutações aleatórias de 1 a n).
 *
 * Saída (no terminal):
 *   1. os casos de borda ordenados com pivô fixo, impressos por completo;
 *   2. para vários tamanhos n, se cada versão pedida (pivô aleatório,
 *      proporcional com alpha = 1/4 e com alpha = 1/5) ordenou o vetor;
 *   3. o pior caso do pivô fixo (vetor já ordenado), mostrando que o
 *      tratamento da pilha evita o StackOverflowError.
 *
 * Os textos impressos não usam acentos para não aparecerem com
 * caracteres estranhos em terminais com outra codificação.
 */
public class Main {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // Teste 1: casos pequenos e de borda (pivô fixo)
        // ---------------------------------------------------------------
        System.out.println("=== Casos de borda (pivo fixo) ===");
        testarCaso("exemplo",         new int[]{5, 3, 8, 1, 9, 2, 7});
        testarCaso("ordenado",        new int[]{1, 2, 3, 4, 5, 6, 7});
        testarCaso("decrescente",     new int[]{7, 6, 5, 4, 3, 2, 1});
        testarCaso("um elemento",     new int[]{42});
        testarCaso("dois em ordem",   new int[]{1, 2});
        testarCaso("dois invertidos", new int[]{2, 1});
        testarCaso("vazio",           new int[]{});
        testarCaso("pivo menor",      new int[]{1, 5, 6});
        testarCaso("pivo maior",      new int[]{9, 4, 7, 2});

        // ---------------------------------------------------------------
        // Teste 2: permutações aleatórias, nas versões pedidas no trabalho
        // ---------------------------------------------------------------
        System.out.println();
        System.out.println("=== Permutacoes aleatorias ===");
        int[] tamanhos = {1, 2, 10, 1000, 100000, 1000000};

        for (int n : tamanhos) {
            Vetor a = GeradorVetores.gerarPermutacao(n);
            Quicksort.quicksortAleatorio(a, 0, a.tamanho() - 1);

            // 1.0 / 4 (e não 1 / 4, que em Java vale 0 por ser divisão inteira)
            Vetor b = GeradorVetores.gerarPermutacao(n);
            Quicksort.quicksortProporcional(b, 0, b.tamanho() - 1, 1.0 / 4);

            Vetor c = GeradorVetores.gerarPermutacao(n);
            Quicksort.quicksortProporcional(c, 0, c.tamanho() - 1, 1.0 / 5);

            System.out.println("n = " + n
                + " | aleatorio: " + a.estaOrdenado()
                + " | alpha 1/4: " + b.estaOrdenado()
                + " | alpha 1/5: " + c.estaOrdenado());
        }

        // ---------------------------------------------------------------
        // Teste 3: pior caso do pivô fixo (vetor já ordenado).
        // Sem a recursão no menor lado, isto daria StackOverflowError.
        // O tempo continua O(n^2), por isso n não é tão grande aqui.
        // ---------------------------------------------------------------
        System.out.println();
        System.out.println("=== Pior caso do pivo fixo (vetor ja ordenado) ===");
        Vetor pior = GeradorVetores.criarVetor(20000);
        Quicksort.quicksort(pior, 0, pior.tamanho() - 1);
        System.out.println("n = 20000 | ordenado: " + pior.estaOrdenado()
            + " (sem StackOverflowError)");
    }

    /** Copia um int[] para um Vetor, ordena com pivô fixo e imprime o resultado. */
    static void testarCaso(String nome, int[] valores) {
        Vetor v = new Vetor(valores.length);
        for (int i = 0; i < valores.length; i++) {
            v.set(i, valores[i]);
        }
        Quicksort.quicksort(v, 0, v.tamanho() - 1);
        System.out.print(nome + ": ");
        v.imprimir();
    }
}
