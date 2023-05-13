package servicios;

import java.util.ArrayList;
import java.util.List;
import estructuras.Grafo;
import java.util.Iterator;
import java.util.LinkedList;

public class ServicioCaminos {

    private Grafo<?> grafo;
	private int origen;
	private int destino;
	private int lim;//limite de cantidad de arcos (inclusive)
	
	// Servicio caminos
	public ServicioCaminos(Grafo<?> grafo, int origen, int destino, int lim) {
		this.grafo = grafo;
		this.origen = origen;
		this.destino = destino;
		this.lim = lim;
	}
        
	public List<List<Integer>> caminos() {
                return this.caminos(this.origen, this.destino, this.lim, null);
	}
        
        private List<List<Integer>> caminos(Integer vertice, Integer verticeDestino, Integer limiteArcos, Integer verticeAnterior){
            List<List<Integer>> caminos = new LinkedList<>();
            if(vertice.equals(verticeDestino)){//si por casualidad el vertice que buscamos es el de origen
                LinkedList<Integer> camino = new LinkedList<>();
                camino.add(vertice);
                return caminos;
            }
            Iterator<Integer> iterator = grafo.obtenerAdyacentes(vertice);
            while(iterator.hasNext()){//recorremos sus adyacentes
                Integer verticeAdyacente = iterator.next();
                
                //si el adyacente es el verticeDestino y no es el anterior(para no recorrer el mismo vertice dos veces)
                if(verticeAdyacente.equals(verticeDestino) && !verticeAdyacente.equals(verticeAnterior)){
                    LinkedList<Integer> camino = new LinkedList<>();
                    
                    //en caso de que no se permita ni tener un arco en el recorrido
                    if(limiteArcos < 1){
                        camino.add(verticeAdyacente);//agregamos solamente el adyacente(verticeDestino)
                        caminos.add(camino);
                    }else{//si se puede aunque sea tener un arco en el camino
                        camino.add(vertice);//agregamos el vertice
                        camino.add(verticeAdyacente);//y el adyacente(verticeDestino) a la lista
                        caminos.add(camino);
                    }
                    
                }else{//si el adyacente no es el verticeDestino
                    List<List<Integer>> caminosAdyacente = this.caminos(verticeAdyacente, verticeDestino, limiteArcos, vertice); //obtenemos los caminos del adyacente
                    Iterator<List<Integer>> it = caminosAdyacente.iterator();
                    while(it.hasNext()){//por cada camino de los caminos posibles
                        List<Integer> camino = it.next();
                        
                        //si la cantidad de arcos en el camino es menor a limiteArcos y retorno algun camino porque encontro el verticeDestino
                        if(camino.size()-1 < limiteArcos && !camino.isEmpty()){//(camino.size()-1) == cantidad de arcos.
                            camino.add(0, vertice);//agregamos al principio del camino el vertice
                            caminos.add(camino);
                        }
                    }
                }
            }
            return caminos;
        }
}
