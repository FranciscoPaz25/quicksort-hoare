public class Main {
    public static void main(String[] args) {

        Vetor v = GeradorVetores.gerarPermutacao(100000);
        

        Quicksort.quicksortAleatorio(v, 0, v.tamanho() - 1);

        System.out.println(v.estaOrdenado());
    }
}