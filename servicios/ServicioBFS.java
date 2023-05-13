package servicios;

import java.util.List;
import estructuras.Grafo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ServicioBFS {

    private Grafo<?> grafo;
    private HashMap<Integer, Boolean> visitas;
    private LinkedList<Integer> fila; //fila con vertices visitados
    
	public ServicioBFS(Grafo<?> grafo) {
		this.grafo = grafo;
                this.visitas = new HashMap<>();
                this.fila = new LinkedList<>();
	}
	
        private Iterator<Integer> iniciarVertices(){
            Iterator<Integer> iterator = this.grafo.obtenerVertices();
            while(iterator.hasNext()){
                Integer vertice = iterator.next();
                this.visitas.put(vertice, false); //seteamos todos los vertices como no visitados
            }
            return this.visitas.keySet().iterator();
        }
        
	public List<Integer> bfsForest() {
            LinkedList<Integer> recorrido = new LinkedList<>();
            Iterator<Integer> iterator = this.iniciarVertices();
            while(iterator.hasNext()){ //por cada vertice
                Integer vertice = iterator.next();
                if(!this.visitas.get(vertice)){//si este vertice no fue visitado
                    recorrido.addAll(this.bfsVisit(vertice));
                }
            }
            return recorrido;
	}
        
        public List<Integer> bfsVisit(Integer vertice) {
            LinkedList<Integer> trama = new LinkedList<>();
            this.visitas.put(vertice, true);//ahora es un vertice visitado
            this.fila.add(vertice);//se agrega a la fila con vertices visitados
            
            while(!fila.isEmpty()){//si hay vertices en la fila
                Integer v = fila.removeFirst();//sacamos primer vertice de la fila
                trama.add(v);//lo agregamos a la trama
                Iterator<Integer> iterator = this.grafo.obtenerAdyacentes(v);
                while(iterator.hasNext()){//agregamos sus adyacentes a la lista (los que no fueron visitados)
                    Integer adyacente = iterator.next();
                    if(!this.visitas.get(adyacente)){//si no fue visitado
                        this.visitas.put(adyacente, true);//ahora lo es
                        this.fila.add(adyacente);
                    }
                }
            }
            return trama;
        }
}
