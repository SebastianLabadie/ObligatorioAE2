package dominio;

import Exceptions.DuplicadoExcpetion;

import java.util.Objects;
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

//    public int min(){
//        return minREC(raiz);
//    }
//
//    private int minREC(NodoArbol actual){
//        if(actual == null) return Integer.MAX_VALUE; //neutro del minimo para que en la comparacion se quede con el menor
//
//        int valor = actual.dato;
//        int minDeIzq = minREC(actual.izq);
//        int minDeDer = minREC(actual.der);
//        int combinacionIzq = minimo(valor,minDeIzq);
//
//        return minimo(combinacionIzq,minDeDer);
//    }

    private int minimo(int valor, int minDeIzq) {
        return Math.min(valor,minDeIzq);
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

    public void insetarLargo(T dato, NodoABB nodoAct) throws DuplicadoExcpetion {
        Objects.requireNonNull(nodoAct);

        if (dato.compareTo(nodoAct.dato) > 0){ //Esto hay que pasarlo a predicate
            if (nodoAct.der == null){
                nodoAct.der = new NodoABB(dato);
            }else
            {
                insetarLargo(dato,nodoAct.der);
            }
        }
        //elseif
        else if (dato.compareTo(nodoAct.dato) < 0) {
            if (nodoAct.izq == null){
                nodoAct.izq = new NodoABB(dato);
            }else
            {
                insetarLargo(dato,nodoAct.izq);
            }
        }
        //es igual
        else{
            throw new DuplicadoExcpetion();
        }
    }

    //Lista generica con doble cabezal inicio y fin agregar ini agregar fin toString
    //ABB Generico
        //Filtrar predicate o herencia

    //Grafos


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

    public void imprimir(){
        inOrder(raiz);
        System.out.println("-------------");

//        preOrder(raiz);
//        System.out.println("-------------");
//
//        postOrder(raiz);
//        System.out.println("-------------");
    }

    public RetornoNuestro ListarInOrder (){
        RetornoNuestro r= RetornoNuestro.ok(0,"");
        ListarInOrderRec(raiz,r);
        return r;
    }

    private void ListarInOrderRec (NodoABB nodoAct,RetornoNuestro ret){
        if (nodoAct != null){
            ListarInOrderRec(nodoAct.izq,ret);
            if (esHoja(nodoAct)){
                ret.setValorString(ret.getValorString()+nodoAct.dato.toString());
            }else{
                ret.setValorString(ret.getValorString()+nodoAct.dato.toString()+"|");
            }
            //System.out.println(nodoAct.dato);
            ListarInOrderRec(nodoAct.der,ret);
        }
    }
    private void inOrder (NodoABB nodoAct){
        if (nodoAct != null){
            inOrder(nodoAct.izq);
            System.out.println(nodoAct.dato);
            inOrder(nodoAct.der);
        }
    }

    private void preOrder (NodoABB nodoAct){
        if (nodoAct != null){
            System.out.println(nodoAct.dato);
            preOrder(nodoAct.izq);
            preOrder(nodoAct.der);
        }
    }
    private void postOrder (NodoABB nodoAct){
        if (nodoAct != null){
            postOrder(nodoAct.izq);
            postOrder(nodoAct.der);
            System.out.println(nodoAct.dato);
        }
    }

    //Busquedas
    //5 y 6 de obligatorio se hace con in order
    //arbol de expresiones 3 del obligatorio pos order
    //arbol generico pal ob
    //predicado para el filtrado de pasajeros
    //da mas puntos al que hizo arbol generico
    //ver video de generics y predicate
    //arboles es la primera mitad del ob
    //grafos es la segunda mitad del ob/asdasd

    public boolean buscarDato(T dato){
        return buscarDatoREC(dato,raiz);
    }
    public RetornoNuestro buscarDatoRet(T dato){

        RetornoNuestro r= RetornoNuestro.ok(1,"");
        buscarDatoRECRet(dato,raiz,r);
        return r;
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

    public void imprimirNivel(){
        //To-Do asd
    }





    public String toString(){
        return VisualizadorGraphViz.arbolBinToUrl(raiz,n->n.dato,n->n.izq,n->n.der);
    }
}
