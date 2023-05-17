package dominio;

public class Grafo {
    private class Aristas{
        private ListaGenerica<Pasajero> pasajeros;
        int vOrigen,vDestino;

        public Aristas(int vOrigen, int vDestino) {
            this.vOrigen = vOrigen;
            this.vDestino = vDestino;
            this.pasajeros = new ListaGenerica<>();
        }

        public boolean existe(){
            return !pasajeros.esVacia();
        }
    }


    private Aristas[][] aristas;
    private Pasajero[] vertices;
    private int maxV;

    private int largo;

    public Grafo(int maximoVertices) {
        this.aristas = new Aristas[maximoVertices][maximoVertices];
        this.vertices = new Pasajero[maximoVertices];
        this.maxV = maximoVertices;

        for (int i = 0; i <maximoVertices; i++) {
            for (int j = 0; j < maximoVertices; j++) {
                this.aristas[i][j] = new Aristas(i,j);
            }
        }
    }

    public void agregarVertice(Pasajero vertice){
        if (largo < maxV){
            //TO-DO revisar que no este repetido el vertice
//            for (Ciudad c:vertices) {
//                if (c.equals(vertice)) throw  new RuntimeException("Duplicado exception");
//            }

            this.vertices[largo]=vertice;
            largo++;
        }else{
            throw new RuntimeException("La estructura esta llena");
        }
    }


    public int buscarIndice(
            String nombreCiudad,
            boolean esOrigen
    ) throws MiException {
        for (int i = 0; i < vertices.length-1; i++) {
                if (vertices[i].getNombre().equals(nombreCiudad)) return i;
        }

      throw new MiException("No se encontro el indice");
    }
    public void agregarArista(String nombreOrigen,String nombreDestino,Pasajero pasajero) throws MiException {
        int idxOrigen = buscarIndice(nombreOrigen,true);
        int idxDestino = buscarIndice(nombreDestino,false);

        //A la lista generica hay que validarle que no este repetido con Contains
        //if this.aristas[idxOrigen][idxDestino].contains(carretera) throw duplicado
        this.aristas[idxOrigen][idxDestino].pasajeros.agregarInicio(pasajero);
    }

    public void imprimirMatriz (){
        for (int i = 0; i < maxV; i++) {
            System.out.print(vertices[i]!= null ? vertices[i].getNombre(): "  ");
        }
        System.out.print("  ");


        for (int fila = 0; fila < aristas.length; fila++) {
            if (vertices[fila]!=null){
                System.out.print(vertices);
            }else{

            }


            for (int columna = 0; columna < aristas[fila].length; columna++) {
                System.out.print(aristas[fila][columna].existe()? 1 : 0 + "  ");
            }
        }
    }


    public void imprimirMatrizAdyacencia() {
        System.out.print("   ");
        for (int i = 0; i <maxV; i++) {
            System.out.print(vertices[i]!=null?vertices[i].getNombre():"  ");
            System.out.print(" ");
        }
        System.out.println();
        for (int vOrigen = 0; vOrigen <aristas.length; vOrigen++) {
            if(vertices[vOrigen]!=null){
                System.out.print(vertices[vOrigen].getNombre());
            }else{
                System.out.print("  ");
            }
            System.out.print(" ");

            for (int vDestino = 0; vDestino < aristas[vOrigen].length; vDestino++) {
                System.out.print((aristas[vOrigen][vDestino].existe()?1:0)+"  ");
            }
            System.out.println();
        }
    }

    //los vertices a los que puedo llegar desde el origen directamente
    public void imprimirAdyacentesDesde(String origen) throws MiException {
        int idxOrigen=buscarIndice(origen,true);

        for (int vDestino = 0; vDestino <maxV; vDestino++) {// itero fila de la matriz de adyacencia
            if(esAdyacente(idxOrigen, vDestino)){
                System.out.println(vertices[vDestino].getNombre());
            }
        }


    }
    //los vertices con los que puedo llegar a el destino directamente
    public void imprimirAdyacentesA(String destino) throws MiException {
        int idxDestino=buscarIndice(destino,false);
        for (int vOrigen = 0; vOrigen <maxV; vOrigen++) {//itero columna de la matriz de adyacencia
            if(this.esAdyacente(vOrigen,idxDestino)){
                System.out.println(vertices[vOrigen].getNombre());
            }
        }
        throw new MiException("No tiene adyacentes");
    }

    private boolean esAdyacente(int vOrigen, int vDestino) {
        return this.aristas[vOrigen][vDestino].existe();
    }

    public void imprimirCosasALaQuePuedoLlegar(String origen) throws MiException {
        boolean[] visitados=new boolean[maxV];
        imprimirCosasALaQuePuedoLlegarRec(this.buscarIndice(origen,true),visitados);

    }

    private void imprimirCosasALaQuePuedoLlegarRec(int idxActual, boolean[] visitados) {
        if(visitados[idxActual])return;
        //vistio la "raiz"
        System.out.println(vertices[idxActual].getNombre());
        visitados[idxActual]=true;

        //visito los "hijos"
        for (int vDestino = 0; vDestino < maxV; vDestino++) {
            if(esAdyacente(idxActual,vDestino)){
                imprimirCosasALaQuePuedoLlegarRec(vDestino, visitados);
            }
        }
    }

    //búsqueda en profundidad
    public void imprimirDepthfirstSearch(String origen) throws MiException {
        int vOrigen=buscarIndice(origen,true);
        Pila<Integer> frontera= new Pila<>();
        frontera.push(vOrigen);
        boolean[] visitados=new boolean[maxV];
        while(!frontera.esVacia()){
            int vExplorar= frontera.pop();
            if(!visitados[vExplorar]){
                visitados[vExplorar]=true;
                System.out.println(vertices[vExplorar].getNombre());
                for (int vDestino = 0; vDestino < maxV; vDestino++) {
                    if(esAdyacente(vExplorar,vDestino)){
                        frontera.push(vDestino);
                    }
                }
            }

        }

    }

    //busqueda en anchura, el "caballo da circulos concentricos"
    public void imprimirBreadthFirstSearch(String origen) throws MiException {
        int vOrigen=buscarIndice(origen,true);
        Cola<Integer> frontera= new Cola<>();
        frontera.add(vOrigen);
        boolean[] visitados=new boolean[maxV];
        while(!frontera.isEmpty()){
            int vExplorar= frontera.poll();
            if(!visitados[vExplorar]){
                visitados[vExplorar]=true;
                System.out.println(vertices[vExplorar].getNombre());
                for (int vDestino = 0; vDestino < maxV; vDestino++) {
                    if(esAdyacente(vExplorar,vDestino)){
                        frontera.add(vDestino);
                    }
                }
            }

        }


    }


    public void imprimirBreadthFirstSearchConSaltos(String origen) throws MiException {
        int vOrigen=buscarIndice(origen,true);
        //el primer integer es para guardar los vertices y el 2do es para la cantidad de niveles
        Cola<Tupla<Integer,Integer>> frontera =new Cola<>();
        frontera.add(new Tupla(vOrigen,0));
        boolean[] visitados=new boolean[maxV];
        while(!frontera.isEmpty()){
            Tupla<Integer,Integer> verticeFrontera= frontera.poll();
            int vExplorar=verticeFrontera.getUno();
            if(!visitados[vExplorar]){
                visitados[vExplorar]=true;
                System.out.println(verticeFrontera.getDos()+"--"+ vertices[vExplorar].getNombre());
                for (int vDestino = 0; vDestino < maxV; vDestino++) {
                    if(esAdyacente(vExplorar,vDestino)){
                        frontera.add(new Tupla(vDestino,verticeFrontera.getDos()+1));
                    }
                }
            }

        }
    }


   /* public String toUrl(){
        return VisualizadorGraphViz.grafoToUrl(vertices,aristas,
                a->a.existe(),v->v.getNombre(),a->a.pasajeros.getNombre()+"");
    }*/
}
