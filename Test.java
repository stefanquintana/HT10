import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import junit.framework.TestCase;

public class Test extends TestCase{
	
	ArrayList<String> archivo = leerArchivo("guategrafo.txt");
	ArrayList<String> municipios = llenarPaises(archivo);
	
	Adyacencia matriz = new Adyacencia(municipios.size());
	
	/**
	 * M�todo que comprueba que la matriz distancias se llena correctamente
	 */
	public void testLlenar() {
		matriz.llenarMatrizD(archivo,municipios);
		int i = municipios.indexOf("Mixco");
		int j = municipios.indexOf("Antigua");
		assertEquals(30, matriz.getDistancias()[i][j]);
	}
	
	/**
	 * M�todo para comprobar el algoritmo de Floyd
	 */
	public void testFloyd() {
		matriz.llenarMatrizR();
		matriz.Floyd();
		int i = municipios.indexOf("Mixco");
		int j = municipios.indexOf("Guatemala");
		assertEquals(125,matriz.getDistancias()[i][j]);
	}
	/**
	 * Convertir archivo en arrayList
	 * @param nombre del archivo
	 * @return lista
	 */
	public static ArrayList<String> leerArchivo(String nombre) {
		File archivo = null;
		FileReader fr = null;
	    BufferedReader br = null;
	    ArrayList<String> lista = new ArrayList<String>();

	    try {
	    	// Apertura del fichero y creacion de BufferedReader para poder
	        // hacer una lectura comoda (disponer del metodo readLine()).
	        archivo = new File (nombre);
	        fr = new FileReader (archivo);
	        br = new BufferedReader(fr);

	        // Lectura del fichero
	        String linea;
	        while((linea=br.readLine())!=null) {
	        	if(linea.charAt(0) != ' ') {
	        		lista.add(linea);
	        	}
	        	
	        }
	    }
	    
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally{
	    	// En el finally cerramos el fichero, para asegurarnos
	        // que se cierra tanto si todo va bien como si salta 
	        // una excepcion.
	    	try{                    
	    		if( null != fr ){   
	    			fr.close();     
	    		}                  
	    	}catch (Exception e2){
	    		e2.printStackTrace();
	    	}
	    }
		return lista;
	}
	
	/**
	 * Recoge los pa�ses
	 * @param ar lista que da archivo
	 * @return lista de pa�ses
	 */
	public static ArrayList<String> llenarPaises(ArrayList<String> ar){
		
		ArrayList<String> paises = new ArrayList<String>();
		
		for(String elemento : ar) {
			//Separar cada l�nea del archivo por sus espacios
			String[] l = elemento.split(" ");
			//Si no est� en la lista el pa�s, a�adirlo
			if(paises.contains(l[0]) == false) {
				paises.add(l[0]);
			}
			
		}
		return paises;
	}
}
