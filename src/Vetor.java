/**
 * TAD Vetor: encapsula um vetor de inteiros e oferece as operações
 * básicas usadas pelo QuickSort e pelo gerador de permutações.
 *
 * O array fica escondido (private). Quem usa a classe só acessa os
 * elementos pelos métodos abaixo, todos com custo O(1), exceto
 * imprimir() e estaOrdenado(), que percorrem o vetor (O(n)).
 */
public class Vetor {

    // Array interno com os elementos. Posições válidas: 0 até tamanho() - 1.
    private int[] elementos;

    /**
     * Cria um vetor com n posições, todas valendo 0.
     * @param n quantidade de posições (n >= 0)
     */
    public Vetor(int n) {
        elementos = new int[n];
    }

    /** Devolve a quantidade de elementos do vetor. */
    public int tamanho() {
        return elementos.length;
    }

    /**
     * Lê o elemento guardado na posição i.
     * @param i posição, entre 0 e tamanho() - 1
     */
    public int get(int i) {
        return elementos[i];
    }

    /**
     * Escreve um valor na posição i.
     * @param i     posição, entre 0 e tamanho() - 1
     * @param valor valor a ser guardado
     */
    public void set(int i, int valor) {
        elementos[i] = valor;
    }

    /**
     * Troca os elementos das posições i e j.
     * Usa uma variável auxiliar para não perder nenhum dos dois valores.
     */
    public void trocar(int i, int j) {
        int aux = elementos[i];
        elementos[i] = elementos[j];
        elementos[j] = aux;
    }

    /** Imprime todos os elementos numa única linha, separados por espaço. */
    public void imprimir() {
        for (int valor : elementos) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }

    /**
     * Verifica se o vetor está em ordem crescente.
     * Basta encontrar um par de vizinhos fora de ordem para responder false.
     * Um vetor vazio ou com um elemento é considerado ordenado.
     */
    public boolean estaOrdenado() {
        for (int i = 0; i < elementos.length - 1; i++) {
            if (elementos[i] > elementos[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
