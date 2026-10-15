public class Main {
    public static void main(String[] args) {

        int[] exemplo = { 5, 3, 8, 1, 9, 2, 7 }; // → 1 2 3 5 7 8 9

        int[] ordenado = { 1, 2, 3, 4, 5, 6, 7 }; // → 1 2 3 4 5 6 7

        int[] decrescente = { 7, 6, 5, 4, 3, 2, 1 }; // → 1 2 3 4 5 6 7

        int[] umElemento = { 42 }; // → 42

        int[] doisOrdem = { 1, 2 }; // → 1 2

        int[] doisInverso = { 2, 1 }; // → 1 2

        int[] vazio = {}; // → (nada, e sem erro!)

        int[] pivoMenor = { 1, 5, 6 }; // → 1 5 6

        int[] pivoMaior = { 9, 4, 7, 2 }; // → 2 4 7 9

        Quicksort.quicksort(pivoMaior, 0, pivoMaior.length - 1);
        for (int i : pivoMaior) {
            System.out.print(i + " ");
        }
    }
}