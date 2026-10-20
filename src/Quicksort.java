public class Quicksort {

    static void quicksort(Vetor v, int inicio, int fim) {
        if (fim <= inicio) {
            return;
        }

        int j = particionaHoare(v, inicio, fim);
        quicksort(v, inicio, j);
        quicksort(v, j + 1, fim);
    }

    static void quicksortAleatorio(Vetor v, int inicio, int fim) {
        if (fim <= inicio) {
            return;
        }

        int posicaoPivo = inicio + GeradorVetores.sortear(fim - inicio);
        v.trocar(inicio, posicaoPivo);

        int j = particionaHoare(v, inicio, fim);
        quicksortAleatorio(v, inicio, j);
        quicksortAleatorio(v, j + 1, fim);
    }

    // Devolve a posição do valor procurado dentro de [inicio..fim],
    // ou -1 se ele não estiver no pedaço
    static int buscarPosicao(Vetor v, int inicio, int fim, int valor) {
        for (int i = inicio; i <= fim; i++) {
            if (v.get(i) == valor) {
                return i;
            }
        }
        return -1;
    }

    // QuickSort Proporcional: escolhe o pivô de forma a forçar
    // a divisão alpha : (1 - alpha) em toda chamada da recursão
    static void quicksortProporcional(Vetor v, int inicio, int fim, double alpha) {
        if (fim <= inicio) {
            return;
        }

        int tamanho = fim - inicio + 1;
        int menorValor = inicio + 1;

        int qtdMenores = (int) (tamanho * alpha);
        int valorPivo = menorValor + qtdMenores;

        int posicaoPivo = buscarPosicao(v, inicio, fim, valorPivo);
        v.trocar(inicio, posicaoPivo);

        int j = particionaHoare(v, inicio, fim);
        quicksortProporcional(v, inicio, j, alpha);
        quicksortProporcional(v, j + 1, fim, alpha);
    }

    static int particionaHoare(Vetor v, int inicio, int fim) {
        int pivo = v.get(inicio);
        int i = inicio - 1;
        int j = fim + 1;

        while (true) {
            do {
                j--;
            } while (v.get(j) > pivo);

            do {
                i++;
            } while (v.get(i) < pivo);

            if (i >= j) {
                return j;
            } else {
                v.trocar(i, j);
            }
        }
    }

}
