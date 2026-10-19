public class Main {
    public static void main(String[] args) {

        Vetor v = GeradorVetores.gerarPermutacao(10);
        v.imprimir();

        System.out.println();

        Quicksort.quicksort(v, 0, v.tamanho() - 1);
        v.imprimir();
    }
}