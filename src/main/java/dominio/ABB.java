package dominio;

import Exceptions.DuplicadoExcpetion;

import java.util.Objects;
import java.util.function.Predicate;

import interfaz .*;
public class ABB<T extends Comparable<T>>  {

    private NodoABB raiz;

    public ABB(NodoABB raiz) {
        this.raiz = raiz;
    }

    public ABB() {
    }

    public class NodoABB {
        private T dato;
        private NodoABB izq;
        private NodoABB der;

        public NodoABB(){

        }

        public NodoABB(T dato) {
            this.dato = dato;
        }

        public NodoABB(T dato, NodoABB izq, NodoABB der) {
            this.dato = dato;
            this.izq = izq;
            this.der = der;
        }

    }


    public int altura(){
        return alturaREC(raiz);
    }

    private int alturaREC(NodoABB raizSubArbol){
        if(raizSubArbol==null){
            return -1;
        }
        else if(esHoja(raizSubArbol))
            return 0;
        else{
            int alturaCombinadaDescendientes=Math.max( alturaREC(raizSubArbol.izq), alturaREC(raizSubArbol.der));
            return 1+alturaCombinadaDescendientes;
        }
    }


    private boolean esHoja(NodoABB nodo) {
        return nodo!=null && (nodo.izq==null && nodo.der==null);
    }

    public int cantHojas(){
        return cantHojasREC(raiz);
    }

    private int cantHojasREC(NodoABB actual){
        if(actual == null) return 0; //neutro de la suma para que no aporte en la suma de las hojas

        int valor = 0;
        if (actual.der == null && actual.izq == null) {
            valor=1;
        }

        int cantIzq = cantHojasREC(actual.izq);
        int cantDer = cantHojasREC(actual.der);
        return valor + cantIzq + cantDer;
    }



    public int profundidad(NodoABB nodo){
        if (nodo ==null) return -1;

        return profundidadREC(nodo,raiz,0);
    }

    private int profundidadREC(NodoABB nodoABuscar, NodoABB raizSubarbol, int profundidadActual) {
        if (raizSubarbol == null){
            return -1;
        } else if (nodoABuscar == raizSubarbol) {
            return profundidadActual;
        }else {
            if (profundidadREC(nodoABuscar,raizSubarbol.izq,profundidadActual) >= 0) {
                return profundidadREC(nodoABuscar,raizSubarbol.izq,profundidadActual+1);
            }else{
                return profundidadREC(nodoABuscar,raizSubarbol.der,profundidadActual+1);
            }
        }
    }

    public void insertarDato(T dato) throws DuplicadoExcpetion {
        this.raiz =  insertarCorto(dato,this.raiz);
    }
    private NodoABB insertarCorto(T dato,NodoABB nodoAct) throws DuplicadoExcpetion {
        if (nodoAct == null) return new NodoABB(dato);

        if (dato.compareTo(nodoAct.dato) > 0){
            nodoAct.der = insertarCorto(dato,nodoAct.der);
        } else if (dato.compareTo(nodoAct.dato) < 0){
            nodoAct.izq = insertarCorto(dato,nodoAct.izq);
        }else {
            throw new DuplicadoExcpetion();
        }
        return nodoAct;
    }



    //Recorridas (profundidad(inorder,)-anchura)
    //Forma sistematica de recorrer los nodos de un arbol


    public void listarInOrder(Visitor<T> visitor){

        listarInOrderRec(raiz,visitor);
    }
    private void listarInOrderRec(NodoABB nodoAct, Visitor<T> visitor){

        if (nodoAct != null){
            listarInOrderRec(nodoAct.izq,visitor);
            visitor.visitar(nodoAct.dato);
            listarInOrderRec(nodoAct.der,visitor);
        }
    }



    public boolean buscarDato(T dato){
        return buscarDatoREC(dato,raiz);
    }
    public Tupla<T,Integer> buscarDatoRet(T dato){
        return buscarRecVersion2(raiz,dato,0);
    }

    private boolean buscarDatoREC(T dato, NodoABB nodoAct) {
        if (nodoAct == null) return false;

        if (dato.compareTo(nodoAct.dato) > 0){
            return buscarDatoREC(dato,nodoAct.der);
        } else if (dato.compareTo(nodoAct.dato) <0){
            return buscarDatoREC(dato,nodoAct.izq);
        }

        return true;
    }



    public Tupla<T,Integer> buscarRecVersion2(NodoABB nodoAct,T dato,int profundidad){

        if(nodoAct==null)return null;

        if (esMayor(dato,nodoAct.dato)){
            return buscarRecVersion2(nodoAct.der,dato,profundidad+1);
        } else if (esMenor(dato,nodoAct.dato)){
            return buscarRecVersion2(nodoAct.izq,dato,profundidad+1);
        }

        return new Tupla<T,Integer>(nodoAct.dato,profundidad);
    }

    private boolean esMayor(T dato,T datoNodo){
        return dato.compareTo(datoNodo) > 0;
    }

    private boolean esMenor(T dato,T datoNodo){
        return dato.compareTo(datoNodo) < 0;
    }
    private boolean buscarDatoRECRet(T dato, NodoABB nodoAct,RetornoNuestro ret) {
        if (nodoAct == null) return false;

        if (dato.compareTo(nodoAct.dato) > 0){
            ret.setValorInteger(ret.getValorInteger()+1);
            return buscarDatoRECRet(dato,nodoAct.der,ret);
        } else if (dato.compareTo(nodoAct.dato) <0){
            ret.setValorInteger(ret.getValorInteger()+1);
            return buscarDatoRECRet(dato,nodoAct.izq,ret);
        }

        ret.setValorString(nodoAct.dato.toString());

        return true;
    }

    public void filtrar(Visitor<T> visitor,Predicate<T> predicado) {
        filtrarRec(raiz,visitor,predicado);
    }

    private void filtrarRec(NodoABB nodoAct, Visitor<T> visitor, Predicate<T> predicado) {
        if (nodoAct ==null){

        }else{
            filtrarRec(nodoAct.izq,visitor,predicado);
            if (predicado.test(nodoAct.dato)){
                visitor.visitar(nodoAct.dato);
            }
            filtrarRec(nodoAct.der,visitor,predicado);
        }
    }


    public String toString(){
        return VisualizadorGraphViz.arbolBinToUrl(raiz,n->n.dato,n->n.izq,n->n.der);
    }
}
