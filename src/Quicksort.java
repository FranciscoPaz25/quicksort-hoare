/**
 * QuickSort com particionamento de Hoare.
 *
 * Todas as versões usam o MESMO procedimento de particionamento
 * (particionaHoare). O que muda entre elas é só a escolha do pivô:
 *   - quicksort:             pivô fixo (primeiro elemento do pedaço);
 *   - quicksortAleatorio:    pivô sorteado entre inicio e fim;
 *   - quicksortProporcional: pivô escolhido para forçar a divisão
 *                            alpha : (1 - alpha).
 *
 * Tratamento do StackOverflowError: nas três versões, a recursão é
 * feita só no lado MENOR da partição. O lado maior é resolvido pelo
 * próprio laço while, sem chamada nova. Como o lado menor tem no
 * máximo metade dos elementos, a pilha de chamadas tem no máximo
 * cerca de log2(n) chamadas abertas, mesmo no pior caso.
 */
public class Quicksort {

    // =====================================================================
    // 1. QuickSort com pivô fixo (primeiro elemento do pedaço).
    //    Versão base, usada para testes. No vetor já ordenado cai no
    //    pior caso, O(n^2) em tempo.
    // =====================================================================
    static void quicksort(Vetor v, int inicio, int fim) {
        // Enquanto o pedaço tiver 2 ou mais elementos, há o que ordenar
        while (inicio < fim) {
            int j = particionaHoare(v, inicio, fim);

            // Tamanhos dos dois lados: [inicio..j] e [j+1..fim]
            int tamanhoEsquerda = j - inicio + 1;
            int tamanhoDireita = fim - j;

            if (tamanhoEsquerda < tamanhoDireita) {
                quicksort(v, inicio, j);   // recursão só no lado menor
                inicio = j + 1;            // o laço continua no lado maior
            } else {
                quicksort(v, j + 1, fim);  // recursão só no lado menor
                fim = j;                   // o laço continua no lado maior
            }
        }
    }

    // =====================================================================
    // 2. QuickSort com pivô aleatório.
    //    Sorteia uma posição entre inicio e fim e leva esse elemento para
    //    a posição inicio. Assim o particionaHoare não precisa mudar.
    // =====================================================================
    static void quicksortAleatorio(Vetor v, int inicio, int fim) {
        while (inicio < fim) {
            // sortear(fim - inicio) devolve de 0 até fim - inicio;
            // somando inicio, a posição cai entre inicio e fim
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
    // 3. QuickSort Proporcional.
    //    Escolhe o pivô pelo VALOR, não pela posição. Como o vetor é uma
    //    permutação de 1 a n, cada pedaço [inicio..fim] contém exatamente
    //    os valores inicio + 1 até fim + 1 (o Hoare manda todos os menores
    //    para a esquerda). O valor k tem k - 1 valores menores, então dá
    //    para escolher exatamente quantos elementos ficam do lado menor.
    //
    //    pivô = menor valor do pedaço + (alpha * tamanho, arredondado para baixo)
    //
    //    Em pedaços pequenos o arredondamento deixa a proporção inexata.
    // =====================================================================
    static void quicksortProporcional(Vetor v, int inicio, int fim, double alpha) {
        while (inicio < fim) {
            int tamanho = fim - inicio + 1;   // quantos elementos o pedaço tem
            int menorValor = inicio + 1;      // menor valor guardado no pedaço

            int qtdMenores = (int) (tamanho * alpha);   // quantos ficam do lado menor
            int valorPivo = menorValor + qtdMenores;    // valor com qtdMenores menores

            // Sabemos QUAL valor é o pivô; agora descobrimos ONDE ele está
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

    /**
     * Devolve a posição do valor procurado dentro de [inicio..fim],
     * ou -1 se ele não estiver no pedaço. Busca sequencial: O(n).
     */
    static int buscarPosicao(Vetor v, int inicio, int fim, int valor) {
        for (int i = inicio; i <= fim; i++) {
            if (v.get(i) == valor) {
                return i;
            }
        }
        return -1;
    }

    // =====================================================================
    // Particionamento de Hoare (usado pelas três versões). Custo: Θ(n).
    //
    // O pivô é o primeiro elemento do pedaço. Dois índices andam um em
    // direção ao outro:
    //   - j vem da direita e para num elemento <= pivô;
    //   - i vem da esquerda e para num elemento >= pivô.
    // Se ainda não se cruzaram, os dois elementos trocam de lugar.
    // Quando se encontram ou se cruzam (i >= j), devolve j.
    //
    // Garantia: tudo em [inicio..j] é <= tudo em [j+1..fim]. O pivô NÃO
    // fica necessariamente na posição final, por isso a recursão usa
    // (inicio, j) e (j + 1, fim), sem descartar nenhum elemento.
    // O j devolvido fica sempre entre inicio e fim - 1: nenhum lado vazio.
    // =====================================================================
    static int particionaHoare(Vetor v, int inicio, int fim) {
        int pivo = v.get(inicio);
        int i = inicio - 1;   // começa uma casa antes do pedaço
        int j = fim + 1;      // começa uma casa depois do pedaço

        while (true) {
            // j recua até achar um elemento <= pivô (anda antes de testar)
            do {
                j--;
            } while (v.get(j) > pivo);

            // i avança até achar um elemento >= pivô (anda antes de testar)
            do {
                i++;
            } while (v.get(i) < pivo);

            if (i >= j) {
                return j;          // índices se encontraram ou se cruzaram
            } else {
                v.trocar(i, j);    // coloca cada elemento do lado certo
            }
        }
    }
}
