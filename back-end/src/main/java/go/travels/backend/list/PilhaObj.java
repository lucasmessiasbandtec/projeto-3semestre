package go.travels.backend.list;

public class PilhaObj<T> {
    private int topo;		/* índice do topo da pilha */
    private T[] pilha;	/* vetor que representa a pilha */

    /* Construtor - recebe a capacidade da pilha */
    public PilhaObj(int capacidade) {
        topo = -1;				                /* inicializa topo com -1 */
        pilha = (T[]) new Object[capacidade];	/* cria o vetor da pilha */
    }

    /* Método isEmpty() - devolve true se a pilha está vazia
     * e false caso contrário
     */
    public boolean isEmpty() {
        return topo == -1;
    }

    /* Método isFull() - devolve true se a pilha está cheia
     * e false caso contrário
     */
    public boolean isFull() {
        return (topo == pilha.length - 1);
    }


    /* Método push - Recebe a info a ser empilhada
     * Se a pilha não estiver cheia, incrementa topo, e coloca
     * info em pilha[topo]
     */
    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
        }
        else {		/* pilha cheia */
            System.out.println("Pilha cheia");
        }
    }

    /* Método pop - se a pilha não estiver vazia, desempilha
     * e retorna o elemento do topo da pilha.
     * Se a pilha estiver vazia, retorna -1
     */
    public T pop() {
        if (!isEmpty()) {
            return pilha[topo--];
        }
        return null;
    }

    /* Método peek - Retorna o elemento do topo da pilha */
    public T peek() {
        if(!isEmpty()) {
            return pilha[topo];
        }
        return null;
    }

    /* Método exibe - Exibe os elementos da pilha */
    public void exibe() {
        if(isEmpty()) {
            System.out.println("Pilha vazia");
        }
        else {
            for(int i = 0; i <= topo; i++) {
                System.out.println(pilha[i]);
            }
        }

    }



}
