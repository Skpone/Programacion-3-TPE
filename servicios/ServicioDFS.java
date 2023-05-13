package servicios;

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
        
        private Iterator<Integer> iniciarVertices(){
            Iterator<Integer> iterator = this.grafo.obtenerVertices();
            while(iterator.hasNext()){
                Integer vertice = iterator.next();
                this.colores.put(vertice, "BLANCO");
            }
            return this.colores.keySet().iterator();
        }
        
	public List<Integer> dfsForest() {
                LinkedList<Integer> recorrido = new LinkedList<>();
                Iterator<Integer> iterator = this.iniciarVertices(); //retorna los vertices del grafo pintados de blanco
                while(iterator.hasNext()){ //por cada vertice
                    Integer vertice = iterator.next();
                    if(this.colores.get(vertice).equals("BLANCO")){ //si esta pintado de blanco (no fue visitado)
                        recorrido.addAll(this.dfsVisit(vertice)); //se recorre y registra su trama
                    }
                }
		return recorrido;
	}
        
        
        private List<Integer> dfsVisit(Integer vertice){
            LinkedList<Integer> trama = new LinkedList<>(); //trama = x trama del camino
            trama.add(vertice); //agregamos el vertice a la trama
            this.colores.put(vertice, "AMARILLO");//ahora es un vertice visitado
            
            Iterator<Integer> iterator = grafo.obtenerAdyacentes(vertice);
            while(iterator.hasNext()){ //por cada vertice adyacente del pasado por parametro
                Integer verticeAdyacente = iterator.next();
                if(this.colores.get(verticeAdyacente).equals("BLANCO")){ //si no fue visitado
                    trama.addAll(this.dfsVisit(verticeAdyacente)); //agregamos sus tramas
                }
            }
            return trama;
        }
}
