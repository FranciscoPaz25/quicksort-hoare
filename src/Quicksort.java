public class Quicksort {

    static void quicksort(int[] vect, int inicio, int fim) {
        if (fim <= inicio) {
            return;
        }

        int j = particionaHoare(vect, inicio, fim);
        quicksort(vect, inicio, j);
        quicksort(vect, j + 1, fim);
    }

    static int particionaHoare(int[] vect, int inicio, int fim) {
        int pivo = vect[inicio];
        int i = inicio - 1;
        int j = fim + 1;

        while (true) {
            do {
                j--;
            } while (vect[j] > pivo);

            do {
                i++;
            } while (vect[i] < pivo);

            if (i >= j) {
                return j;
            } else {
                int aux = vect[i];
                vect[i] = vect[j];
                vect[j] = aux;
            }
        }
    }
}
