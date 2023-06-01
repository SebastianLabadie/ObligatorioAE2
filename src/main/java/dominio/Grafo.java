package dominio;

import Exceptions.*;
import interfaz.EstadoCamino;

public class Grafo {
    private class Aristas{
        private ListaGenerica<Conexion> conexiones;
        int vOrigen,vDestino;

        public Aristas(int vOrigen, int vDestino) {
            this.vOrigen = vOrigen;
            this.vDestino = vDestino;
            this.conexiones = new ListaGenerica<>();
        }

        public boolean existe(){
            return !conexiones.esVacia();
        }

    }


    private Aristas[][] aristas;
    private Estacion[] vertices;
    private int maxV;

    private int largo;

    public Grafo(int maximoVertices) {
        this.aristas = new Aristas[maximoVertices][maximoVertices];
        this.vertices = new Estacion[maximoVertices];
        this.maxV = maximoVertices;

        for (int i = 0; i <maximoVertices; i++) {
            this.vertices[i] = new Estacion(""+i,""+i);
            for (int j = 0; j < maximoVertices; j++) {
                this.aristas[i][j] = new Aristas(i,j);
            }
        }
    }

    public void agregarVertice(Estacion vertice) throws DuplicadoExcpetion, GrafoFullException {
        if (largo < maxV){
            //TO-DO revisar que no este repetido el vertice
            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i].getCodigo().equals(vertice.getCodigo())) throw  new DuplicadoExcpetion();
            }

            this.vertices[largo]=vertice;
            largo++;
        }else{
            throw new GrafoFullException();
        }
    }


    public int buscarIndice(
            String codigo,
            boolean esOrigen
    ) throws IndiceOrigenException, IndiceDestinoException {
        for (int i = 0; i < vertices.length-1; i++) {
                if (vertices[i].getCodigo().equals(codigo)) return i;
        }


        if (esOrigen){
            throw new IndiceOrigenException();
        }else{
            throw new IndiceDestinoException();
        }
    }
    public void actualizarArista(String codigoOrigen,String codigoDestino,Conexion conexion) throws IndiceDestinoException, IndiceOrigenException, NoExisteCaminoException {
        int idxOrigen = buscarIndice(codigoOrigen,true);
        int idxDestino = buscarIndice(codigoDestino,false);

        ListaGenerica<Conexion> listaRepetidos = this.aristas[idxOrigen][idxDestino].conexiones.filtrar(conexion1 -> conexion1.equals(conexion));
        if (!listaRepetidos.esVacia()){
            listaRepetidos.primero().setCodigoEstacionDestino(codigoDestino);
            listaRepetidos.primero().setCodigoEstacionOrigen(codigoOrigen);
            listaRepetidos.primero().setConexionId(conexion.getConexionId());
            listaRepetidos.primero().setCosto(conexion.getCosto());
            listaRepetidos.primero().setTiempo(conexion.getTiempo());
            listaRepetidos.primero().setKilometros(conexion.getKilometros());
            listaRepetidos.primero().setEstado(conexion.getEstado());
        }else{
            throw new NoExisteCaminoException();
        }
    }
    public void agregarArista(String codigoOrigen,String codigoDestino,Conexion conexion) throws IndiceDestinoException, IndiceOrigenException, DuplicadoExcpetion {
        int idxOrigen = buscarIndice(codigoOrigen,true);
        int idxDestino = buscarIndice(codigoDestino,false);

        if (this.aristas[idxOrigen][idxDestino].conexiones.contains(conexion)) throw new DuplicadoExcpetion();

        this.aristas[idxOrigen][idxDestino].conexiones.agregarInicio(conexion);

    }

    public void imprimirMatriz (){
        for (int i = 0; i < maxV; i++) {
//            System.out.print(vertices[i]!= null ? vertices[i].getNombre(): "  ");
        }
//        System.out.print("  ");


        for (int fila = 0; fila < aristas.length; fila++) {
            if (vertices[fila]!=null){
//                System.out.print(vertices);
            }else{

            }


            for (int columna = 0; columna < aristas[fila].length; columna++) {
//                System.out.print(aristas[fila][columna].existe()? 1 : 0 + "  ");
            }
        }
    }


    public void imprimirMatrizAdyacencia() {
//        System.out.print("   ");
        for (int i = 0; i <maxV; i++) {
//            System.out.print(vertices[i]!=null?vertices[i].getNombre():"  ");
//            System.out.print(" ");
        }
//        System.out.println();
        for (int vOrigen = 0; vOrigen <aristas.length; vOrigen++) {
            if(vertices[vOrigen]!=null){
//                System.out.print(vertices[vOrigen].getNombre());
            }else{
//                System.out.print("  ");
            }
//            System.out.print(" ");

            for (int vDestino = 0; vDestino < aristas[vOrigen].length; vDestino++) {
//                System.out.print((aristas[vOrigen][vDestino].existe()?1:0)+"  ");
            }
//            System.out.println();
        }
    }

    //los vertices a los que puedo llegar desde el origen directamente
    public void imprimirAdyacentesDesde(String origen) throws IndiceDestinoException, IndiceOrigenException {
        int idxOrigen=buscarIndice(origen,true);

        for (int vDestino = 0; vDestino <maxV; vDestino++) {// itero fila de la matriz de adyacencia
            if(esAdyacente(idxOrigen, vDestino)){
//                System.out.println(vertices[vDestino].getNombre());
            }
        }


    }
    //los vertices con los que puedo llegar a el destino directamente
//    public void imprimirAdyacentesA(String destino) throws MiException,IndiceDestinoException, IndiceOrigenException {
//        int idxDestino=buscarIndice(destino,false);
//        for (int vOrigen = 0; vOrigen <maxV; vOrigen++) {//itero columna de la matriz de adyacencia
//            if(this.esAdyacente(vOrigen,idxDestino)){
////                System.out.println(vertices[vOrigen].getNombre());
//            }
//        }
//        throw new MiException("No tiene adyacentes");
//    }

    private boolean esAdyacente(int vOrigen, int vDestino) {
        return this.aristas[vOrigen][vDestino].existe();
    }

    public void imprimirCosasALaQuePuedoLlegar(String origen) throws IndiceDestinoException, IndiceOrigenException {
        boolean[] visitados=new boolean[maxV];
        imprimirCosasALaQuePuedoLlegarRec(this.buscarIndice(origen,true),visitados);

    }

    private void imprimirCosasALaQuePuedoLlegarRec(int idxActual, boolean[] visitados) {
        if(visitados[idxActual])return;
        //vistio la "raiz"
//        System.out.println(vertices[idxActual].getNombre());
        visitados[idxActual]=true;

        //visito los "hijos"
        for (int vDestino = 0; vDestino < maxV; vDestino++) {
            if(esAdyacente(idxActual,vDestino)){
                imprimirCosasALaQuePuedoLlegarRec(vDestino, visitados);
            }
        }
    }

    //búsqueda en profundidad
    public void imprimirDepthfirstSearch(String origen) throws IndiceDestinoException, IndiceOrigenException {
        int vOrigen=buscarIndice(origen,true);
        Pila<Integer> frontera= new Pila<>();
        frontera.push(vOrigen);
        boolean[] visitados=new boolean[maxV];
        while(!frontera.esVacia()){
            int vExplorar= frontera.pop();
            if(!visitados[vExplorar]){
                visitados[vExplorar]=true;
//                System.out.println(vertices[vExplorar].getNombre());
                for (int vDestino = 0; vDestino < maxV; vDestino++) {
                    if(esAdyacente(vExplorar,vDestino)){
                        frontera.push(vDestino);
                    }
                }
            }

        }

    }

    //busqueda en anchura, el "caballo da circulos concentricos"
    public void imprimirBreadthFirstSearch(String origen) throws IndiceDestinoException, IndiceOrigenException {
        int vOrigen=buscarIndice(origen,true);
        Cola<Integer> frontera= new Cola<>();
        frontera.add(vOrigen);
        boolean[] visitados=new boolean[maxV];
        while(!frontera.isEmpty()){
            int vExplorar= frontera.poll();
            if(!visitados[vExplorar]){
                visitados[vExplorar]=true;
//                System.out.println(vertices[vExplorar].getNombre());
                for (int vDestino = 0; vDestino < maxV; vDestino++) {
                    if(esAdyacente(vExplorar,vDestino)){
                        frontera.add(vDestino);
                    }
                }
            }

        }


    }


    public void imprimirBreadthFirstSearchConSaltos(String origen,int cant,Visitor<Estacion> visitor) throws IndiceDestinoException, IndiceOrigenException {
//        imprimirMatrizAdyacencia();
        int vOrigen=buscarIndice(origen,true);
        //el primer integer es para guardar los vertices y el 2do es para la cantidad de niveles
        Cola<Tupla<Integer,Integer>> frontera =new Cola<>();
        frontera.add(new Tupla(vOrigen,0));
        boolean[] visitados=new boolean[maxV];
        while(!frontera.isEmpty()){
            Tupla<Integer,Integer> verticeFrontera= frontera.poll();
            int vExplorar=verticeFrontera.getUno();
            int saltos = verticeFrontera.getDos();

            if(!visitados[vExplorar] && saltos <= cant){
                visitados[vExplorar]=true;
//                System.out.println(verticeFrontera.getDos()+"--"+ vertices[vExplorar].getNombre());
                visitor.visitar(this.vertices[vExplorar]);

                for (int vDestino = 0; vDestino < maxV; vDestino++) {
                    if(esAdyacente(vExplorar,vDestino)){
                        frontera.add(new Tupla(vDestino,saltos+1));
                    }
                }
            }



        }
    }


    public Tupla<ListaGenerica<Estacion>,Double> dijsktra(String origen,String destino, Valor<Conexion> obtenerValor ) throws IndiceDestinoException, IndiceOrigenException, VacioException, NoExisteCaminoException {
        Validador.noVacio(origen);
        Validador.noVacio(destino);
        int vOrigen=buscarIndice(origen,true);
        int vDestino=buscarIndice(destino,false);

        boolean[] visitados=new boolean[maxV];
        double[] costos=new double[maxV];
        int[] padres=new int[maxV];

        //inicializar valores
        for (int i = 0; i < maxV; i++) {
            costos[i]=Double.MAX_VALUE;
            padres[i]=-1;
            visitados[i]=false;
        }

        costos[vOrigen]=0;
        padres[vOrigen]=vOrigen;


        //funcion
        while(!estaTodoVisitado(visitados,padres)){
            int vExplorar = verticeMasChico(visitados,costos,padres);
            for (int vAdyacente = 0; vAdyacente < maxV; vAdyacente++) {
                if (esAdyacente(vExplorar,vAdyacente)){
                    double costoHastaAdyacente = costos[vExplorar]+getCosto(aristas[vExplorar][vAdyacente].conexiones,obtenerValor);
//                    System.out.println("costos ex "+costos[vExplorar]);
//                    System.out.println("costoAdy "+costoHastaAdyacente);
//                    System.out.println("costos[vAdyacente] "+ vAdyacente+" - "+costos[vAdyacente]);
                    if (costoHastaAdyacente<costos[vAdyacente]){
                        costos[vAdyacente] = costoHastaAdyacente;
                        padres[vAdyacente]= vExplorar;
                    }
                }
            }

            visitados[vExplorar]=true;
        }

        //reconstruir camino
//        System.out.println("el costo es "+costos[vDestino]);
        return reconstruirCamino(padres,vOrigen,vDestino,costos);
    }

    private Tupla<ListaGenerica<Estacion>, Double> reconstruirCamino(int[] padres, int vOrigen, int vDestino,double[] costos) throws NoExisteCaminoException {
      if (padres[vDestino]==-1) throw new NoExisteCaminoException();
       int actual=vDestino;
       Tupla<ListaGenerica<Estacion>, Double> ret = new Tupla<>(new ListaGenerica<>(),1.0);

       while(actual!=vOrigen){
//           System.out.println("actual "+actual);
           ret.getUno().agregarInicio(vertices[actual]);
           actual=padres[actual];
       }
       ret.getUno().agregarInicio(vertices[vOrigen]);
       ret.setDos(costos[vDestino]);
//       System.out.println("ret "+ret.toString());
       return ret;
    }

    private double getCosto(ListaGenerica<Conexion> conexiones, Valor<Conexion> obtenerValor) {
        ListaGenerica<Conexion> conexionesBuenas = conexiones.filtrar(c->c.getEstado()!= EstadoCamino.MALO);
//       System.out.println("conexiones buenas - "+conexionesBuenas.toString());
       if (conexionesBuenas.esVacia()) return Double.MAX_VALUE; //Si no hay conexiones buenas entre el origen y el destino dejamos el costo como maximo
        conexionesBuenas.ordenarPorAtributo(obtenerValor);
        return conexionesBuenas.primero().devolverAtributoDinamico(obtenerValor);
    }

    private int verticeMasChico(boolean[] visitados, double[] costos, int[] padres) {
        double minCosto=Integer.MAX_VALUE;
        int minIdx=0;

        for (int i = 0; i < maxV; i++) {
            if (!visitados[i]){
                if (costos[i] < minCosto){
                    minCosto=costos[i];
                    minIdx=i;
                }
            }
        }

        return minIdx;
    }

    private boolean estaTodoVisitado(boolean[] visitados, int[] padres) {
        for (int i = 0; i < maxV; i++) {
            if (!visitados[i] && padres[i]>=0) return false;
        }

        return true;
    }

    public String toUrl(){
        return VisualizadorGraphViz.grafoToUrl(vertices,aristas,
                a->a.existe(),v->v.getNombre(),a->a.conexiones.toString()+"");
    }
}
