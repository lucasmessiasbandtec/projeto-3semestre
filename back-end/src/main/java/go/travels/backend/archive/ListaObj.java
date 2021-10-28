package go.travels.backend.archive;

public class ListaObj<T> {

    private T[] vetor;
    private int numeroElemento;

    public ListaObj(int tamanho) {
        vetor = (T[]) new Object[tamanho];
        numeroElemento = 0;
    }

    public boolean adiciona(T valor) {
        if (numeroElemento >= vetor.length) {
            System.out.println("Lista está cheia");
            return false;
        }
        else {
            vetor[numeroElemento++] = valor;
            return true;
        }
    }

    public void exibe() {
        if (numeroElemento == 0) {
            System.out.println("\nA lista está vazia");
            return;
        }
        System.out.println("\nExibindo elementos da lista:");
        for (int i = 0; i< numeroElemento; i++) {
            System.out.println(vetor[i] + "\t");
        }
        System.out.println();
    }

    public int busca(T valor) {
        for (int i = 0; i < numeroElemento; i++) {
            if (vetor[i].equals(valor))
                return i;
        }
        return -1;
    }


    public boolean removePeloIndice(int indice) {
        if (indice < 0 || indice >= numeroElemento)
            return false;
        for (int i = indice; i < numeroElemento -1; i++)
            vetor[i] = vetor[i+1];
        numeroElemento--;
        return true;
    }

    public boolean removeElemento(T valor) {
        return removePeloIndice(busca(valor));
    }

    public int getTamanho() {
        return numeroElemento;
    }

    public T getElemento(int indice) {
        if (indice < 0 || indice >= numeroElemento)
            return null;
        return vetor[indice];
    }

    public void limpa() {
        numeroElemento = 0;
    }
}
