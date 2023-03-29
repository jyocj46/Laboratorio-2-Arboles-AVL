/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arboles.avl.umg;

import java.util.Scanner;

/**
 *
 * @author RobertoPG
 */
public class ArbolesAVLUMG {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de números que desea agregar: ");
        int n = scanner.nextInt();
        ArbolAVL arbol = new ArbolAVL();
        for (int i = 0; i < n; i++) {
            System.out.print("Ingrese el número " + (i+1) + ": ");
            int dato = scanner.nextInt();
            arbol.raiz = arbol.insertar(arbol.raiz, dato);
        }
        System.out.println("Árbol Equilibrado AVL: " + arbol.estaBalanceado(arbol.raiz));
        System.out.print("Recorrido del árbol en preorden: ");
        arbol.preorden(arbol.raiz);
        System.out.println();
        System.out.print("Recorrido del árbol en inorden: ");
        arbol.inorden(arbol.raiz);
        System.out.println();
        System.out.print("Recorrido del árbol en postorden: ");
        arbol.postorden(arbol.raiz);
        System.out.println();
        System.out.println("Altura del Árbol AVL: " + arbol.altura(arbol.raiz));
        System.out.print("Nodos Hoja del Árbol: ");
        arbol.nodosHoja(arbol.raiz);
        scanner.close();
    }
}

class Nodo {
    int dato;
    Nodo izquierdo;
    Nodo derecho;
    int altura;

    public Nodo(int dato) {
        this.dato = dato;
        this.altura = 1;
    }
}

class ArbolBinario {
    Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public Nodo insertar(Nodo nodo, int dato) {
        if (nodo == null) {
            return new Nodo(dato);
        }
        if (dato < nodo.dato) {
            nodo.izquierdo = insertar(nodo.izquierdo, dato);
        } else if (dato > nodo.dato) {
            nodo.derecho = insertar(nodo.derecho, dato);
        }
        return nodo;
    }

    public void preorden(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.dato + " ");
            preorden(nodo.izquierdo);
            preorden(nodo.derecho);
        }
    }

    public void inorden(Nodo nodo) {
        if (nodo != null) {
            inorden(nodo.izquierdo);
            System.out.print(nodo.dato + " ");
            inorden(nodo.derecho);
        }
    }

    public void postorden(Nodo nodo) {
        if (nodo != null) {
            postorden(nodo.izquierdo);
            postorden(nodo.derecho);
            System.out.print(nodo.dato + " ");
        }
    }

    public int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    public int factorEquilibrio(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    public boolean estaBalanceado(Nodo nodo) {
        if (nodo == null) {
            return true;
        }
        int fe = factorEquilibrio(nodo);
        return Math.abs(fe) <= 1 && estaBalanceado(nodo.izquierdo) && estaBalanceado(nodo.derecho);
    }

    public Nodo rotacionDerecha(Nodo nodo) {
        Nodo nodoIzquierdo = nodo.izquierdo;
        Nodo nodoIzquierdoDerecho = nodoIzquierdo.derecho;
        nodoIzquierdo.derecho = nodo;
        nodo.izquierdo = nodoIzquierdoDerecho;
        nodo.altura = Math.max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;
        nodoIzquierdo.altura = Math.max(altura(nodoIzquierdo.izquierdo), altura(nodoIzquierdo.derecho)) + 1;
        return nodoIzquierdo;
    }

    public Nodo rotacionIzquierda(Nodo nodo) {
        Nodo nodoDerecho = nodo.derecho;
        Nodo nodoDerechoIzquierdo = nodoDerecho.izquierdo;
        nodoDerecho.izquierdo = nodo;
        nodo.derecho = nodoDerechoIzquierdo;
        nodo.altura = Math.max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;
        nodoDerecho.altura = Math.max(altura(nodoDerecho.izquierdo), altura(nodoDerecho.derecho)) + 1;
        return nodoDerecho;
    }
}

class ArbolAVL extends ArbolBinario {
    public Nodo insertar(Nodo nodo, int dato) {
        nodo = super.insertar(nodo, dato);
        int fe = factorEquilibrio(nodo);
        if (fe > 1 && dato < nodo.izquierdo.dato) {
            return rotacionDerecha(nodo);
        }
        if (fe < -1 && dato > nodo.derecho.dato) {
            return rotacionIzquierda(nodo);
        }
        if (fe > 1 && dato > nodo.izquierdo.dato) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }
        if (fe < -1 && dato < nodo.derecho.dato) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }
        nodo.altura = Math.max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;
        return nodo;
    }

    public void nodosHoja(Nodo nodo) {
        if (nodo != null) {
            if (nodo.izquierdo == null && nodo.derecho == null) {
                System.out.print(nodo.dato + " ");
            }
            nodosHoja(nodo.izquierdo);
            nodosHoja(nodo.derecho);
        }
    }
    
}
