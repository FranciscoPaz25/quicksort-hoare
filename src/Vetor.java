public class Vetor {

    private int[] elementos;

    public Vetor(int n) {
        elementos = new int[n];
    }

    public int tamanho() {
        return elementos.length;
    }

    public int get(int i) {
        return elementos[i];
    }

    public void set(int i, int valor) {
        elementos[i] = valor;
    }

    public void trocar(int i, int j) {
        int aux = elementos[i];
        elementos[i] = elementos[j];
        elementos[j] = aux;
    }

    public void imprimir() {
        for (int valor : elementos) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }

    public boolean estaOrdenado() {
        for (int i = 0; i < elementos.length - 1; i++) {
            if(elementos[i] > elementos[i + 1]) {
                return false;
            }
        }

        return true;
    }
}