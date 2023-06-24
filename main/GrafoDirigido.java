package main;

import java.util.Iterator;
import estructuras.Grafo;
import estructuras.Arco;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;

public class GrafoDirigido<T> implements Grafo<T> {

    private HashMap<Integer, LinkedList<Arco<T>>> vertices;

    public GrafoDirigido() {
        vertices = new HashMap<>();
    }

    /**
     * Complejidad: O(1), usamos un metodo del HashMap
     */
    @Override
    public void agregarVertice(int verticeId) {
        vertices.putIfAbsent(verticeId, new LinkedList());
    }

    /**
     * Complejidad: Es O(A^2), (A siendo la cantidad total de arcos en el grafo)
     * porque se deben obtener todos los arcos y recorrerlos, luego por cada arco,
     * si cumple con la condición, borrarlo (.borrarArco() == O(n)).
     */
    @Override
    public void borrarVertice(int verticeId) {
        if (this.contieneVertice(verticeId)) {//O(1)
            vertices.remove(verticeId);//O(1)
            Iterator<Arco<T>> iterator = this.obtenerArcos();//O(n)
            while (iterator.hasNext()) {//O(n)
                Arco<T> arco = iterator.next();
                if (arco.getVerticeDestino() == verticeId) {
                    //necesitamos borrar los arcos donde otros vertices apuntan a nuestro vertice
                    this.borrarArco(arco.getVerticeOrigen(), verticeId);//O(n)
                }
            }
        }
    }

    /**
     * Complejidad: O(a) porque requerimos saber si el arco ya existe en la
     * lista de arcos del vertice, el peor caso requiere recorrer "a"; la
     * cantidad de arcos pertenecientes al vertice 1.
     */
    @Override
    public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
        if (this.contieneVertice(verticeId1) && this.contieneVertice(verticeId2)) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            Arco arco = new Arco(verticeId1, verticeId2, etiqueta); //arco que vamos a agregar
            if (arcosVertice.contains(arco)) { //si ya existe el arco
                return;
            }
            arcosVertice.add(arco);
        }
    }

    /**
     * Complejidad: Es O(a) (a siendo la cantidad de arcos pertenecientes al vertice 1),
     * porque implica recorrer hasta el final en el peor caso
     * para eliminar el arco. (.remove()).
     */
    @Override
    public void borrarArco(int verticeId1, int verticeId2) {
        if (this.contieneVertice(verticeId1)) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            Arco arco = new Arco(verticeId1, verticeId2); //instanciamos un arco con las caracteristicas del que queremos eliminar
            arcosVertice.remove(arco);
        }
    }

    /**
     * Complejidad: O(1), usamos un metodo del HashMap
     */
    @Override
    public boolean contieneVertice(int verticeId) {
        return vertices.containsKey(verticeId);
    }

    /**
     * Complejidad: Es O(a) (a siendo la cantidad de arcos pertenecientes al vertice 1),
     * porque en el metodo contains() requerimos saber si el arco existe dento de la lista de arcos del vertice.
     * La LinkedList tiene que en el peor caso recorrer todos sus elementos.
     */
    @Override
    public boolean existeArco(int verticeId1, int verticeId2) {
        if (this.contieneVertice(verticeId1)) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            Arco arco = new Arco(verticeId1, verticeId2);
            return arcosVertice.contains(arco); //verdadero si esta, falso si no.
        }
        return false; //no existe el vertice 1
    }

    /**
     * Complejidad: Es O(a) (a siendo la cantidad de arcos pertenecientes al vertice 1),
     * porque en el metodo indexOf() para encontrar en índice en el peor caso se deben recorrer todos los arcos,
     * lo mismo para el get, ya que es una LinkedList.
     */
    @Override
    public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
        if (this.contieneVertice(verticeId1)) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId1);
            Arco arco = new Arco(verticeId1, verticeId2); //este arco se usa para encontrar el original
            int idArco = arcosVertice.indexOf(arco);//buscamos la posicion del original
            if (idArco != -1) {//si existe
                return arcosVertice.get(idArco); //lo buscamos y retornamos
            }
        }
        return null; //no existe el vertice 1 o el arco no existe
    }

    /**
     * Complejidad: O(1), pedir el tamaño de una lista no requiere ningun tipo
     * de recorrido (por ejemplo).
     */
    @Override
    public int cantidadVertices() {
        return vertices.size();
    }

    /**
     * Complejidad: O(V) V siendo la cantidad total de vertices que tengamos en nuestro HashMap,
     * debemos recorrer las listas preguntando por su tamaño (O(1)).
     */
    @Override
    public int cantidadArcos() {
        int cantidad = 0;
        Iterator<LinkedList<Arco<T>>> iterator = vertices.values().iterator();//obtengo todas las listas de arcos de todos los vertices
        while (iterator.hasNext()) {//por cada lista
            LinkedList<Arco<T>> listaArcos = iterator.next();
            cantidad += listaArcos.size(); //agregamos la cantidad de arcos que tiene
        }
        return cantidad;
    }

    /**
     * Complejidad: O(1), aplicamos O(1) de complejidad a cualquier metodo del
     * HashMap
     */
    @Override
    public Iterator<Integer> obtenerVertices() {
        return vertices.keySet().iterator(); //retornamos un iterator de los id del Hashmap. Si no hay vertices, retorna una lista vacia.
    }

    /**
     * Complejidad: O(a) 'a' siendo la cantidad de arcos del vertice, al requerir
     * recorrerlos para obtener los adyacentes del vertice.
     */
    @Override
    public Iterator<Integer> obtenerAdyacentes(int verticeId) {
        LinkedList<Integer> verticesAdyacentes = new LinkedList<>();//lista donde guardamos los vertices adyacentes
        if (this.contieneVertice(verticeId)) {
            LinkedList<Arco<T>> arcosVertice = vertices.get(verticeId);
            Iterator<Arco<T>> iterator = arcosVertice.iterator();
            while (iterator.hasNext()) {//por cada arco del vertice
                Arco<T> arco = iterator.next();
                verticesAdyacentes.add(arco.getVerticeDestino()); //agregamos el vertice adyacente a la lista
            }
        }
        return verticesAdyacentes.iterator();
    }

    /**
     * Complejidad: Es O(V*a) ('V' siendo la cantidad de vertices en el grafo y 'a' la cantidad de arcos pertenecientes a cada vertice),
     * porque tenemos que recorrer cada lista de arcos por cada vertice de nuestro grafo, además de agregar los arcos a la lista, ya que
     * se trata de una LinkedList.
     */
    @Override
    public Iterator<Arco<T>> obtenerArcos() {
        LinkedList<Arco<T>> arcos = new LinkedList<>();
        Iterator<LinkedList<Arco<T>>> iterator = vertices.values().iterator();
        while (iterator.hasNext()) {//por cada lista con arcos
            LinkedList<Arco<T>> listaArcos = iterator.next();
            arcos.addAll(listaArcos);//agregamos los arcos de la lista a la lista arcos
        }
        return arcos.iterator();
    }

    /**
     * Complejidad: Es O(1) (tratandose del id del vertice),
     * porque obtener la LinkedList desde el HashMap y llamar
     * su iterator no varia por el tamaño de la entrada.
     */
    @Override
    public Iterator<Arco<T>> obtenerArcos(int verticeId) {
        if (this.contieneVertice(verticeId))//si el vertice existe
        {
            return this.vertices.get(verticeId).iterator();
        } else //en caso de que no exista el vertice
        {
            return Collections.emptyListIterator(); //tengo que retornar una lista vacía
        }
    }
}
