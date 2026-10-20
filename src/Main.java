public class Main {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // Teste 1: casos pequenos e de borda (pivô fixo), imprimindo o vetor
        // ---------------------------------------------------------------
        System.out.println("=== Casos de borda (pivô fixo) ===");
        testarCaso("exemplo",         new int[]{5, 3, 8, 1, 9, 2, 7});
        testarCaso("ordenado",        new int[]{1, 2, 3, 4, 5, 6, 7});
        testarCaso("decrescente",     new int[]{7, 6, 5, 4, 3, 2, 1});
        testarCaso("um elemento",     new int[]{42});
        testarCaso("dois em ordem",   new int[]{1, 2});
        testarCaso("dois invertidos", new int[]{2, 1});
        testarCaso("vazio",           new int[]{});
        testarCaso("pivô menor",      new int[]{1, 5, 6});
        testarCaso("pivô maior",      new int[]{9, 4, 7, 2});

        // ---------------------------------------------------------------
        // Teste 2: permutações aleatórias, nas três versões pedidas
        // ---------------------------------------------------------------
        System.out.println();
        System.out.println("=== Permutações aleatórias ===");
        int[] tamanhos = {1, 2, 10, 1000, 100000, 1000000};

        for (int n : tamanhos) {
            Vetor a = GeradorVetores.gerarPermutacao(n);
            Quicksort.quicksortAleatorio(a, 0, a.tamanho() - 1);

            Vetor b = GeradorVetores.gerarPermutacao(n);
            Quicksort.quicksortProporcional(b, 0, b.tamanho() - 1, 1.0 / 4);

            Vetor c = GeradorVetores.gerarPermutacao(n);
            Quicksort.quicksortProporcional(c, 0, c.tamanho() - 1, 1.0 / 5);

            System.out.println("n = " + n
                + " | aleatório: " + a.estaOrdenado()
                + " | alpha 1/4: " + b.estaOrdenado()
                + " | alpha 1/5: " + c.estaOrdenado());
        }

        // ---------------------------------------------------------------
        // Teste 3: pior caso do pivô fixo (vetor já ordenado)
        // Sem o tratamento da pilha, isto daria StackOverflowError.
        // ---------------------------------------------------------------
        System.out.println();
        System.out.println("=== Pior caso do pivô fixo (vetor já ordenado) ===");
        Vetor pior = GeradorVetores.criarVetor(20000);
        Quicksort.quicksort(pior, 0, pior.tamanho() - 1);
        System.out.println("n = 20000 | ordenado: " + pior.estaOrdenado()
            + " (sem StackOverflowError)");
    }

    // Copia um int[] para um Vetor, ordena com pivô fixo e imprime
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