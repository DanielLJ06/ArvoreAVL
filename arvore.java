class No {
    int info;
    No esquerda;
    No direita;
    int altura;

    No(int dado) {
        this.info = dado;
        this.esquerda = null;
        this.direita = null;
        this.altura = 0;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }
    public void setDireita(No direita) {
        this.direita = direita;
    }
    public void setInfo(int info) {
        this.info = info;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }

    public No getEsquerda() {
        return esquerda;
    }
    public No getDireita() {
        return direita;
    }
    public int getInfo() {
        return info;
    }
    public int getAltura() {
        return altura;
    }
}

class ArvoreAVL {
    No raiz;

    ArvoreAVL() {
        this.raiz = null;
    }

    private int AnalisedeAltura(No n) {
        if (n == null) {
            return -1;
        }
        return n.getAltura();
    }

    private void AtualizarAltura(No n) {
        int esquerda = AnalisedeAltura(n.getEsquerda());
        int direita = AnalisedeAltura(n.getDireita());
        if (esquerda > direita) {
            n.setAltura(esquerda + 1);
        } else {
            n.setAltura(direita + 1);
        }
    }

    private No rotacaoEsquerda(No primeirono) {
        No segundono = primeirono.getDireita();
        No terceirono = segundono.getEsquerda();

        segundono.setEsquerda(primeirono);
        primeirono.setDireita(terceirono);

        AtualizarAltura(primeirono);
        AtualizarAltura(segundono);

        return segundono;
    }

    private No rotacaoDireita(No primeirono) {
        No segundono = primeirono.getEsquerda();
        No terceirono = segundono.getDireita();

        segundono.setDireita(primeirono);
        primeirono.setEsquerda(terceirono);

        AtualizarAltura(primeirono);
        AtualizarAltura(segundono);

        return segundono;
    }


    private No rotacaoduplaEsquerdaeDireita(No n) {
        n.setEsquerda(rotacaoEsquerda(n.getEsquerda()));
        return rotacaoDireita(n);
    }


    private No rotacaoduplaDireitaEsquerda(No n) {
        n.setDireita(rotacaoDireita(n.getDireita()));
        return rotacaoEsquerda(n);
    }

    private No balancear(No n) {
        AtualizarAltura(n);

        int fb = AnalisedeAltura(n.getEsquerda()) - AnalisedeAltura(n.getDireita());

        if (fb > 1) {
            int fbFilho = AnalisedeAltura(n.getEsquerda().getEsquerda())
                    - AnalisedeAltura(n.getEsquerda().getDireita());
            if (fbFilho >= 0) {
                return rotacaoDireita(n);
            } else {
                return rotacaoduplaEsquerdaeDireita(n);
            }
        }

        if (fb < -1) {
            int fbFilho = AnalisedeAltura(n.getDireita().getEsquerda())
                    - AnalisedeAltura(n.getDireita().getDireita());
            if (fbFilho <= 0) {
                return rotacaoEsquerda(n);
            } else {
                return rotacaoduplaDireitaEsquerda(n);
            }
        }

        return n;
    }

    private No inserir(No n, int valor) {
        if (n == null) {
            return new No(valor);
        }

        if (valor < n.getInfo()) {
            n.setEsquerda(inserir(n.getEsquerda(), valor));
        } else if (valor > n.getInfo()) {
            n.setDireita(inserir(n.getDireita(), valor));
        } else {
            return n;
        }


        return balancear(n);
    }

    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
    }

    private No encontrarMENORdaDireita(No n) {
        while (n.getEsquerda() != null) {
            n = n.getEsquerda();
        }
        return n;
    }

    private No remover(No n, int valor) {
        if (n == null) {
            return null;
        }

        if (valor < n.getInfo()) {
            n.setEsquerda(remover(n.getEsquerda(), valor));
        } else if (valor > n.getInfo()) {
            n.setDireita(remover(n.getDireita(), valor));
        } else {
            if (n.getEsquerda() == null) {
                return n.getDireita();
            } else if (n.getDireita() == null) {
                return n.getEsquerda();
            }


            No sucessor = encontrarMENORdaDireita(n.getDireita());
            n.setInfo(sucessor.getInfo());
            n.setDireita(remover(n.getDireita(), sucessor.getInfo()));
        }

        return balancear(n);
    }

    public void remover(int valor) {
        raiz = remover(raiz, valor);
    }

    public void emOrdem(No n) {
        if (n != null) {
            emOrdem(n.getEsquerda());
            System.out.print(n.getInfo() + " ");
            emOrdem(n.getDireita());
        }
    }


    public void preOrdem(No n) {
        if (n != null) {
            System.out.print(n.getInfo() + " ");
            emOrdem(n.getEsquerda());
            emOrdem(n.getDireita());
        }
    }
    public void posOrdem(No n) {
        if (n != null) {

            emOrdem(n.getEsquerda());
            emOrdem(n.getDireita());
            System.out.print(n.getInfo() + " ");
        }
    }


    public void imprimirEmOrdem() {
        emOrdem(raiz);
        System.out.println();
    }

    public void imprimirposOrdem() {
        posOrdem(raiz);
        System.out.println();
    }

    public void imprimirpreOrdem() {
        posOrdem(raiz);
        System.out.println();
    }

}

public class Main {
    public static void main(String[] args){
        ArvoreAVL arvore = new ArvoreAVL();


        arvore.inserir(10);
        arvore.inserir(20);
        arvore.inserir(30);
        arvore.inserir(40);
        arvore.inserir(50);
        arvore.inserir(25);
        arvore.remover(30);
        arvore.remover(50);

        System.out.print("Em ordem:   ");
        arvore.imprimirEmOrdem();

        System.out.print("Pre ordem:  ");
        arvore.imprimirpreOrdem();

        System.out.print("Pos ordem:  ");
        arvore.imprimirposOrdem();
    }

}