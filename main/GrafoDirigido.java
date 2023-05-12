package main;

import java.util.Iterator;
import estructuras.Grafo;
import estructuras.Arco;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;

public class GrafoDirigido<T> implements Grafo<T> {
        HashMap<Integer, LinkedList<Arco<T>>> vertices;

        public GrafoDirigido() {
            vertices = new HashMap<>();
        }
        
        @Override
	public void agregarVertice(int verticeId) {
            vertices.putIfAbsent(verticeId, new LinkedList());
	}

	@Override
	public void borrarVertice(int verticeId) {
            if(this.contieneVertice(verticeId)){
                vertices.remove(verticeId);
                Iterator<Arco<T>> iterator = this.obtenerArcos();
                while(iterator.hasNext()){
                    Arco<T> arco = iterator.next();
                    if(arco.getVerticeDestino() == verticeId){
                        this.borrarArco(arco.getVerticeOrigen(), verticeId); //necesitamos borrar los arcos que otros vertices apuntan a nuestro vertice
                    }
                }
            }
	}

	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
		LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1); //si es que el vertice no existe, retorna null
                if(arcosVertice != null){ //si el vertice existe
                    Arco arco = new Arco(verticeId1, verticeId2, etiqueta); //arco que vamos a agregar
                    if(arcosVertice.contains(arco)){ //si ya existe el arco
                        return;
                    }
                    arcosVertice.add(arco);
                }
	}

	@Override
	public void borrarArco(int verticeId1, int verticeId2) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            if(arcosVertice != null){
                Arco arco = new Arco(verticeId1, verticeId2); //instanciamos un arco con las caracteristicas del que queremos eliminar
                if(arcosVertice.contains(arco)){ //si existe el arco buscado
                    arcosVertice.remove(arco); //lo borramos
                }
            }
	}

	@Override
	public boolean contieneVertice(int verticeId) {
		return vertices.containsKey(verticeId);
	}

	@Override
	public boolean existeArco(int verticeId1, int verticeId2) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            if(arcosVertice != null){
                Arco arco = new Arco(verticeId1, verticeId2);
                return arcosVertice.contains(arco); //verdadero si esta, falso si no. el arco buscado
            }
            return false; //no existe el vertice 1
	}

	@Override
	public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            if(arcosVertice != null){
		Arco arco = new Arco(verticeId1, verticeId2); //este arco se usa para ver si existe en el vertice
                if(arcosVertice.contains(arco)){
                    return arco; //retornamos el clon (por la estructura del arco.. es lo mismo que retornar el original)
                }
            }
            return null; //no existe el vertice 1 o el arco no existe
	}

	@Override
	public int cantidadVertices() {
		return vertices.size();
	}

	@Override
	public int cantidadArcos() {
            int cantidad = 0;
            for(LinkedList lista : vertices.values()){ //por cada lista de arco de cada vertice
                cantidad += lista.size(); //aumentamos la cantidad de arcos
            }
            return cantidad;
	}

	@Override
	public Iterator<Integer> obtenerVertices() {
		return vertices.keySet().iterator(); //ketSey me da una coleccion de los id del hashmap, retornamos un iterator de esa coleccion
	}

	@Override
	public Iterator<Integer> obtenerAdyacentes(int verticeId) {
            LinkedList<Integer> verticesAdyacentes = new LinkedList<>();
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId);
            if(arcosVertice != null){
                for(Arco arco : arcosVertice){//por cada arco
                    verticesAdyacentes.add(arco.getVerticeDestino()); //agregamos el vertice adyacente a la lista
                }
            }
            return verticesAdyacentes.iterator();
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos() {
            LinkedList<Arco<T>> arcos = new LinkedList<>();
            for(LinkedList<Arco<T>> listaArcos : vertices.values()){ //recorro cada lista de arcos de cada uno de mis vertices
                arcos.addAll(listaArcos); //agregamos todos los arcos de ese 'x' vertice
            }
            return arcos.iterator(); //retornamos el iterador
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos(int verticeId) {
            if(vertices.get(verticeId) != null)
                return vertices.get(verticeId).iterator(); //retornamos el iterator de la lista de arcos del vertice
            else //en caso de que no exista el vertice
                return Collections.emptyListIterator(); //tengo que retornar una lista vacía
	}
        
}
