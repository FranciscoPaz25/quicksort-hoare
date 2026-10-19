public class Quicksort {

    static void quicksort(Vetor v, int inicio, int fim) {
        if (fim <= inicio) {
            return;
        }

        int j = particionaHoare(v, inicio, fim);
        quicksort(v, inicio, j);
        quicksort(v, j + 1, fim);
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
