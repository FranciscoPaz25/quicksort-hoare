public class Main {
    public static void main(String[] args) {

        int[] tamanhos = { 1, 2, 10, 1000 };

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
    }
}