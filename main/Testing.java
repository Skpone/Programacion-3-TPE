
package main;

import estructuras.Arco;
import estructuras.Grafo;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import servicios.ServicioBFS;
import servicios.ServicioDFS;

public class Testing {
    /**
     * 1:TEST METODOS DEL GRAFO DIRIGIDO PD: está al final porque se cambia la estructura del grafo.
     * 2:TEST SERVICIODFS Y BFS 
     * 3:TEST SERVICIOCAMINOS
     */
    
    public static void main(String args[]) {
        //creamos el grafo
        Grafo grafo = new GrafoDirigido<>();
        
        //creamos los vertices
        grafo.agregarVertice(13);
        grafo.agregarVertice(4);
        grafo.agregarVertice(7);
        grafo.agregarVertice(5);
        grafo.agregarVertice(3);
        grafo.agregarVertice(8);
        grafo.agregarVertice(1);
        grafo.agregarVertice(100);
        grafo.agregarVertice(10);
        grafo.agregarVertice(9);
        
        //creamos los arcos
        grafo.agregarArco(13, 4, null);
        grafo.agregarArco(4, 13, null);
        grafo.agregarArco(4, 3, null);
        grafo.agregarArco(3, 1, null);
        grafo.agregarArco(1, 5, null);
        grafo.agregarArco(5, 13, null);
        grafo.agregarArco(13, 7, null);
        grafo.agregarArco(7, 5, null);
        grafo.agregarArco(5, 7, null);
        grafo.agregarArco(7, 1, null);
        grafo.agregarArco(7, 8, null);
        grafo.agregarArco(4, 5, null);
        grafo.agregarArco(10, 9, null);
        grafo.agregarArco(9, 10, null);
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////     2:TEST SERVICIODFS Y BFS      ////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //instanciamos los servicios
        ServicioDFS servicioDfs = new ServicioDFS(grafo);
        ServicioBFS servicioBfs = new ServicioBFS(grafo);
        
        //SERVICIO DFS
        System.out.println("//RECORRIDO DEL SERVICIO DFS");
        List<Integer> recorridoDfs = servicioDfs.dfsForest();
        System.out.println(Arrays.toString(recorridoDfs.toArray()));
        
        //SERVICIO BFS
        System.out.println("//RECORRIDO DEL SERVICIO BFS");
        List<Integer> recorridoBfs = servicioBfs.bfsForest();
        System.out.println(Arrays.toString(recorridoBfs.toArray()));
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////     2:TEST SERVICIODFS Y BFS      ////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////      3:TEST SERVICIOCAMINOS       ////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////      3:TEST SERVICIOCAMINOS       ////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////// 1:TEST METODOS DEL GRAFO DIRIGIDO ////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //TESTEAR QUE NO SE REPITAN VERTICES
        grafo.agregarVertice(9);//deberia no haber dos vertices "9"
        
        System.out.println("//TODOS LOS VERTICES DEL GRAFO Y CANTIDAD");
        System.out.println("Cantidad de vertices: "+ grafo.cantidadVertices());
        Iterator<Integer> it1 = grafo.obtenerVertices();
        while(it1.hasNext()){
            Integer vertice = it1.next();
            System.out.print(vertice + " ");
        }
        System.out.println("");
        
        //TESTEAR QUE NO SE REPITAN ARCOS
        grafo.agregarArco(7, 5, "soy un arco repetido"); //no deberia haber otro arco igual, ni aunque sean etiquetas diferentes
        //TESTEAR AGREGAR ARCO CUANDO VERTICE ORIGEN NO EXISTE
        grafo.agregarArco(999, 5, null);//no deberia aparecer este arco, ya que vertice 999 no existe
        //TESTEAR AGREGAR ARCO CUANDO VERTICE DESTINO NO EXISTE
        grafo.agregarArco(5, 999, null);//no deberia aparecer este arco, ya que vertice 999 no existe
        
        System.out.println("//TODOS LOS ARCOS DEL GRAFO Y CANTIDAD");
        System.out.println("Cantidad de arcos: "+ grafo.cantidadArcos());
        Iterator<Arco<?>> it2 = grafo.obtenerArcos();
        while(it2.hasNext()){
            Arco<?> arco = it2.next();
            System.out.print(arco + " ");
        }
        System.out.println("");
        
        //BORRAR VERTICE (y por ende en este usa borrarArco tambien)
        grafo.borrarVertice(10);//deberia borrar el vertice, sus arcos y los arcos que lo apuntan
        System.out.println("//BORRAMOS VERTICE 10");
        System.out.println("Cantidad de vertices: "+ grafo.cantidadVertices());
        Iterator<Integer> it3 = grafo.obtenerVertices();
        while(it3.hasNext()){
            Integer vertice = it3.next();
            System.out.print(vertice + " ");
        }
        System.out.println("");
        System.out.println("Cantidad de arcos: "+ grafo.cantidadArcos());
        Iterator<Arco<?>> it4 = grafo.obtenerArcos();
        while(it4.hasNext()){
            Arco<?> arco = it4.next();
            System.out.print(arco + " ");
        }
        System.out.println("");
        
        //CONTIENE VERTICE
        System.out.println("//¿GRAFO CONTIENE VERTICE 10 Y 7?");
        System.out.println("10: "+grafo.contieneVertice(10));
        System.out.println("7: "+grafo.contieneVertice(7));
        
        //EXISTE ARCO
        System.out.println("//¿EN GRAFO EXISTE ARCO 10(9) Y 7(5)?");
        System.out.println("10(9): "+grafo.existeArco(10, 9));
        System.out.println("7(5): "+grafo.existeArco(7, 5));
        
        //OBTENER ARCO
        System.out.println("//OBTENER ARCO 7(5) Y LUEGO 10(9)");
        System.out.println(grafo.obtenerArco(7, 5));
        System.out.println(grafo.obtenerArco(10, 9));
        
        //OBTENER ADYACENTES
        System.out.println("//OBTENER ADYACENTES DE 7");
        Iterator<Integer> it5 = grafo.obtenerAdyacentes(7);
        while(it5.hasNext()){
            Integer vertice = it5.next();
            System.out.print(vertice + " ");
        }
        System.out.println("");
        
        //OBTENER ARCOS DE UN VERTICE
        System.out.println("//OBTENER LOS ARCOS DE 7");
        Iterator<Arco<?>> it6 = grafo.obtenerArcos(7);
        while(it6.hasNext()){
            Arco<?> arco = it6.next();
            System.out.print(arco + " ");
        }
        System.out.println("");
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////// 1:TEST METODOS DEL GRAFO DIRIGIDO ////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
