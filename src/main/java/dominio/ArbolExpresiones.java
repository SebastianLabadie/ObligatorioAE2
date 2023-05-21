package dominio;
import java.util.Objects;
import java.util.Stack;

public class ArbolExpresiones {

    enum TipoOp{
        Multiplicar('*'),
        Sumar('+'),
        Restar('-'),
        Dividir('/');
        private char simbolo;

        TipoOp(char simbolo) {
            this.simbolo = simbolo;
        }
    }
    private static class Nodo{
        private TipoOp tipoOp;
        private int numero;

        private Nodo izq,der;

        public Nodo(TipoOp tipoOp, Nodo izq, Nodo der) {
            this.tipoOp = tipoOp;
            this.izq = izq;
            this.der = der;
        }

        public Nodo(int numero) {
            this.numero = numero;
        }
    }
    private Nodo raiz;

    private static Nodo parsearExpresion(String valor,int inicio,int fin){
        char charStart=valor.charAt(inicio);
        Nodo izquierda=null;
        int continuarDesde=fin;
        if(charStart=='('){
            int openCount=0;
            int i=inicio+1;
            while (i<fin && (valor.charAt(i)!=')' || openCount>0)){
                if(valor.charAt(i)=='('){
                    openCount++;
                }else if(valor.charAt(i)==')'){
                    openCount--;
                }
                i++;
            }
            if(i>=fin)return null;
            else {
                izquierda = parsearExpresion(valor, inicio+1, i);
                continuarDesde=i+1;
            }
        }else if(Character.isDigit(charStart)) {
            StringBuilder num = new StringBuilder();
            int i = inicio;
            while (i < fin && Character.isDigit(valor.charAt(i))) {
                num.append(valor.charAt(i));
                i++;
            }
            int numero = Integer.parseInt(num.toString());
            izquierda = new Nodo(numero);
            continuarDesde=i;
        }
        if(izquierda==null)return null;
        TipoOp op = null;
        if(continuarDesde<fin) {
            switch (valor.charAt(continuarDesde)) {
                case '*' -> op = TipoOp.Multiplicar;
                case '+' -> op = TipoOp.Sumar;
                case '-' -> op = TipoOp.Restar;
                case '/' -> op = TipoOp.Dividir;
            }
            Nodo der = parsearExpresion(valor, continuarDesde + 1, fin);
            if (der != null && op != null) {
                return new Nodo(op, izquierda, der);
            } else {
                return null;
            }
        }else{
            return izquierda;
        }
    }

    public ArbolExpresiones(Nodo raiz) {
        this.raiz = raiz;
    }

    public static ArbolExpresiones parsear(String valor) {
        String sinBlancos=valor.replaceAll("\\s","");
        Nodo raiz=parsearExpresion(sinBlancos,0,sinBlancos.length());
        Objects.requireNonNull(raiz,"La expresion no es valida");
        return new ArbolExpresiones(raiz);
    }

    @Override
    public String toString() {
        return toString(raiz);
    }

    private String toString(Nodo raizSubArbol) {
        if(raizSubArbol.tipoOp!=null){
            return String.format("(%s%s%s)",
                    toString(raizSubArbol.izq),raizSubArbol.tipoOp.simbolo,toString(raizSubArbol.der));
        }else{
            return raizSubArbol.numero+"";
        }
    }


    public int calcularResultado(){
        return calcularResultadoRec(raiz);
    }

    private int calcularResultadoRec(Nodo nodoAct) {
        Objects.requireNonNull(nodoAct);
        if (nodoAct.tipoOp != null) {
            int valorSubExpresionIzq = calcularResultadoRec(nodoAct.izq);
            int valorSubExpresionDer = calcularResultadoRec(nodoAct.der);

            switch (nodoAct.tipoOp){
                case Sumar:
                    return  valorSubExpresionIzq+valorSubExpresionDer;
                case Restar:
                    return  valorSubExpresionIzq-valorSubExpresionDer;
                case Dividir:
                    return  valorSubExpresionIzq/valorSubExpresionDer;
                case Multiplicar:
                    return  valorSubExpresionIzq*valorSubExpresionDer;
                default:
                    throw  new RuntimeException("operador invalido");
            }
        }else{
            return nodoAct.numero;
        }


    }

}
