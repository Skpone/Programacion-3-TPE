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
	public void agregarVertice(int verticeId) {//O(1)
            vertices.putIfAbsent(verticeId, new LinkedList());//O(1)
	}

	@Override
	public void borrarVertice(int verticeId) {//O(n^2) (eliminar el vertice y obtener todos los arcos requiere de recorrido. Y luego recorreriendo esos arcos borrarlos [que requiere recorrido] en caso de que tenga como destino el vertice borrado implica O(n^2))
            if(this.contieneVertice(verticeId)){//O(1)
                vertices.remove(verticeId);//O(1)
                Iterator<Arco<T>> iterator = this.obtenerArcos();//O(n)
                while(iterator.hasNext()){//O(n)
                    Arco<T> arco = iterator.next();
                    if(arco.getVerticeDestino() == verticeId){
                        this.borrarArco(arco.getVerticeOrigen(), verticeId); //necesitamos borrar los arcos que otros vertices apuntan a nuestro vertice
                    }
                }
            }
	}

	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {//O(n) (el preguntarle a la lista de arcos si tiene el arco requiere recorrer hasta el final en el peor caso)
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1); //si es que el vertice no existe, retorna null
                if(arcosVertice != null && vertices.get(verticeId2) != null){ //si los vertices existen
                    Arco arco = new Arco(verticeId1, verticeId2, etiqueta); //arco que vamos a agregar
                    if(arcosVertice.contains(arco)){ //si ya existe el arco
                        return;
                    }
                    arcosVertice.add(arco);
                }
	}

	@Override
	public void borrarArco(int verticeId1, int verticeId2) {//O(n) (en el .contains y .remove requiere en el peor de los casos recorrer hasta el final)
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            if(arcosVertice != null){
                Arco arco = new Arco(verticeId1, verticeId2); //instanciamos un arco con las caracteristicas del que queremos eliminar
                if(arcosVertice.contains(arco)){ //si existe el arco buscado
                    arcosVertice.remove(arco); //lo borramos
                }
            }
	}
        
	@Override
	public boolean contieneVertice(int verticeId) {//O(1) 
		return vertices.containsKey(verticeId);//O(1)
	}

	@Override
	public boolean existeArco(int verticeId1, int verticeId2) {//O(n) (porque requerimos saber si la lista de arcos tiene el arco que buscamos, el .contains() en el peor caso tiene que recorrer hasta el final)
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);//O(1)
            if(arcosVertice != null){
                Arco arco = new Arco(verticeId1, verticeId2);//O(1)
                return arcosVertice.contains(arco); //verdadero si esta, falso si no. el arco buscado
            }
            return false; //no existe el vertice 1
	}

	@Override
	public Arco<T> obtenerArco(int verticeId1, int verticeId2) {//O(n) (requerimos saber si el arco existe con el .contains)
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
	public int cantidadVertices() {//O(1)
		return vertices.size();//O(1)
	}

	@Override
	public int cantidadArcos() {//O(n) (aplica a la cantidad de listas de arcos que tenga mi hashmap vertices ya que tengo que recorrer cada una en el while loop)
            int cantidad = 0;
            Iterator<LinkedList<Arco<T>>> iterator = vertices.values().iterator();//obtengo todas las listas de arcos de todos los vertices
            while(iterator.hasNext()){//por cada lista
                LinkedList<Arco<T>> listaArcos = iterator.next(); //obtenemos la lista
                cantidad += listaArcos.size(); //agregamos la cantidad de arcos que tiene
            }
            return cantidad;
	}

	@Override
	public Iterator<Integer> obtenerVertices() {//O(1)
		return vertices.keySet().iterator(); //retornamos un iterator de los id del hashmap. Si no hay vertices, retorna una lista vacia.
	}

	@Override
	public Iterator<Integer> obtenerAdyacentes(int verticeId) {//O(n) (n siendo la cantidad de arcos del vertice, al requerir recorrerlos para obtener los adyacentes)
            LinkedList<Integer> verticesAdyacentes = new LinkedList<>();//lista donde guardamos los vertices adyacentes
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId);
            if(arcosVertice != null){
                Iterator<Arco<T>> iterator = arcosVertice.iterator();
                while(iterator.hasNext()){//por cada arco del vertice
                    Arco<T> arco = iterator.next();
                    verticesAdyacentes.add(arco.getVerticeDestino()); //agregamos el vertice adyacente a la lista
                }
            }
            return verticesAdyacentes.iterator();
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos() {//O(V*a) (v aplica a la cantidad de lista de arcos que haya y a todos los elementos de cada lista que se tienen que recorrer para agregar)
            LinkedList<Arco<T>> arcos = new LinkedList<>();
            Iterator<LinkedList<Arco<T>>> iterator = vertices.values().iterator();
            while(iterator.hasNext()){//por cada lista con arcos
                LinkedList<Arco<T>> listaArcos = iterator.next();
                arcos.addAll(listaArcos);//agregamos los arcos de la lista a la lista arcos
            }
            return arcos.iterator(); //retornamos el iterador
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos(int verticeId) {//O(1) (obtener la lista de arcos de nuestro vertice y luego retornarla no requiere ningun tipo de recorrido)
            LinkedList<Arco<T>> listaArcos = this.vertices.get(verticeId);
            if(listaArcos != null)//si el vertice existe
                return listaArcos.iterator(); //retornamos el iterator
            else //en caso de que no exista el vertice
                return Collections.emptyListIterator(); //tengo que retornar una lista vacía
	}
        
        
        //TEMPLATE PARA JUSTIFICAR LA COMPLEJIDAD DE LOS METODOS
        /**
         * Complejidad: O(1) porque
         * 
         */
}
