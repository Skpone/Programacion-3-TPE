package servicios;

import java.util.ArrayList;
import java.util.List;
import estructuras.Grafo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ServicioDFS {

    private Grafo<?> grafo;
    private HashMap<Integer, String> colores;

	public ServicioDFS(Grafo<?> grafo) {
		this.grafo = grafo;
                this.colores = new HashMap<>();
	}
        
        private Iterator<Integer> blanquearVertices(){
            Iterator<Integer> iterator = this.grafo.obtenerVertices();
            while(iterator.hasNext()){
                Integer vertice = iterator.next();
                this.colores.put(vertice, "BLANCO");
            }
            return this.colores.keySet().iterator();
        }
        
	public List<Integer> dfsForest() {
                LinkedList<Integer> recorridoTotal = new LinkedList<>();
                Iterator<Integer> iterator = this.blanquearVertices(); //retorna los vertices del grafo pintados de blanco
                while(iterator.hasNext()){ //por cada vertice
                    Integer vertice = iterator.next();
                    if(this.colores.get(vertice).equals("BLANCO")){ //si esta pintado de blanco (no fue visitado)
                        recorridoTotal.addAll(this.dfsVisit(vertice)); //se recorre y registra su recorrido
                    }
                }
		return recorridoTotal;
	}
        
        
        private List<Integer> dfsVisit(Integer vertice){
            LinkedList<Integer> recorrido = new LinkedList<>();
            this.colores.put(vertice, "AMARILLO");//vertice visitado
            recorrido.add(vertice); //agregamos el vertice al recorrido
            
            Iterator<Integer> iterator = grafo.obtenerAdyacentes(vertice);
            while(iterator.hasNext()){ //por cada vertice adyacente del pasado por parametro
                Integer verticeAdyacente = iterator.next();
                if(this.colores.get(verticeAdyacente).equals("BLANCO")){ //si no fue visitado
                    recorrido.addAll(this.dfsVisit(verticeAdyacente)); //recorremos
                }
            }
            return recorrido;
        }
}
