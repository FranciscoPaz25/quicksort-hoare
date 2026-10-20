public class Quicksort {

    // =====================================================================
    // 1. QuickSort com pivô fixo (primeiro elemento do pedaço)
    // Versão base, usada só para estudo e testes.
    // =====================================================================
    static void quicksort(Vetor v, int inicio, int fim) {
        while (inicio < fim) {
            int j = particionaHoare(v, inicio, fim);

            int tamanhoEsquerda = j - inicio + 1;
            int tamanhoDireita = fim - j;

            if (tamanhoEsquerda < tamanhoDireita) {
                quicksort(v, inicio, j); // recursão no lado menor
                inicio = j + 1; // o laço continua no lado maior
            } else {
                quicksort(v, j + 1, fim); // recursão no lado menor
                fim = j; // o laço continua no lado maior
            }
        }
    }

    // =====================================================================
    // 2. QuickSort com pivô aleatório
    // Sorteia uma posição entre inicio e fim e leva esse elemento
    // para a primeira posição antes de particionar.
    // =====================================================================
    static void quicksortAleatorio(Vetor v, int inicio, int fim) {
        while (inicio < fim) {
            int posicaoPivo = inicio + GeradorVetores.sortear(fim - inicio);
            v.trocar(inicio, posicaoPivo);

            int j = particionaHoare(v, inicio, fim);

            int tamanhoEsquerda = j - inicio + 1;
            int tamanhoDireita = fim - j;

            if (tamanhoEsquerda < tamanhoDireita) {
                quicksortAleatorio(v, inicio, j);
                inicio = j + 1;
            } else {
                quicksortAleatorio(v, j + 1, fim);
                fim = j;
            }
        }
    }

    // =====================================================================
    // 3. QuickSort Proporcional
    // Escolhe o pivô pelo VALOR, de forma a forçar a divisão
    // alpha : (1 - alpha) em toda chamada.
    // =====================================================================
    static void quicksortProporcional(Vetor v, int inicio, int fim, double alpha) {
        while (inicio < fim) {
            int tamanho = fim - inicio + 1;
            int menorValor = inicio + 1;

            int qtdMenores = (int) (tamanho * alpha);
            int valorPivo = menorValor + qtdMenores;

            int posicaoPivo = buscarPosicao(v, inicio, fim, valorPivo);
            v.trocar(inicio, posicaoPivo);

            int j = particionaHoare(v, inicio, fim);

            int tamanhoEsquerda = j - inicio + 1;
            int tamanhoDireita = fim - j;

            if (tamanhoEsquerda < tamanhoDireita) {
                quicksortProporcional(v, inicio, j, alpha);
                inicio = j + 1;
            } else {
                quicksortProporcional(v, j + 1, fim, alpha);
                fim = j;
            }
        }
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

    // =====================================================================
    // Particionamento de Hoare (usado pelas três versões)
    // Pivô = primeiro elemento do pedaço.
    // Ao final: tudo em [inicio..j] <= tudo em [j+1..fim].
    // =====================================================================
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