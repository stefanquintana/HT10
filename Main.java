import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner; 

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Abrir el archivo
		ArrayList<String> archivo = leerArchivo("guategrafo.txt");
		
		Scanner teclado = new Scanner(System.in);
		
		String op = new String();
		
		while (op.equals("3") == false) {
			//Empezar menú
			System.out.println("----Elija la opción----");
			System.out.println("1. Ruta más corta entre dos ciudades\n2. Modificar las rutas\n3. Salir del programa");
			op = teclado.nextLine();
			if(op.equals("1")) {
				ArrayList<String> paises = llenarPaises(archivo);
				Adyacencia matriz = new Adyacencia(paises.size());
				matriz.llenarMatrizD(archivo, paises);
				matriz.llenarMatrizR();
				matriz.Floyd();
				
				for(int i = 0; i < paises.size(); i++) {
					System.out.println((i+1) + ". " + paises.get(i));
				}
				
				System.out.println();
				
				int M = verEntero("Digite el municipio de salida: ", 1, 5);
				
				for(int i = 0; i < paises.size(); i++) {
					System.out.println((i+1) + ". " + paises.get(i));
				}
				
				int N = verEntero("Digite el municipio a llegar: ", 1, 5);
				
				ArrayList<String> ruta = matriz.mostrarCamino(M-1,N-1, paises);
				
				if(ruta.size() != 0) {
					System.out.println(paises.get(M-1));
					for(String elemento : ruta) {
						System.out.println(elemento);
					}
					System.out.println("La distancia a recorrer es " + matriz.getDistancias()[M-1][N-1] + " km.");
				}
				else {
					System.out.println("En este momento no hay manera de llegar");
				}
				
				
			}
			else
				if(op.equals("2")) {
					
					System.out.println("----¿Qué desea hacer?----");
					System.out.println("1. Bloquear una ruta\n2. Agregar una ruta");
					
					int opcion = verEntero("---->", 1, 2);
					
					ArrayList<String> paises1 = llenarPaises(archivo);
					
					if(opcion == 1) {
						
						for(int i = 0; i < paises1.size(); i++) {
							System.out.println((i+1) + ". " + paises1.get(i));
						}
						
						int m = verEntero("Digite el municipio de salida a remover: ", 1, 5);
						
						for(int i = 0; i < paises1.size(); i++) {
							System.out.println((i+1) + ". " + paises1.get(i));
						}
						
						int n = verEntero("Digite el municipio de llegada a remover: ", 1, 5);
						
						String municipio1 = paises1.get(m-1);
						String municipio2 = paises1.get(n-1);
						
						int index = -1;
						
						for(int i = 0; i < archivo.size(); i++) {
							String[] l = archivo.get(i).split(" ");
							
							if(municipio1.equals(l[0]) && municipio2.equals(l[1])) {
								index = i;
							}
						}
						
						if(index != -1) {
							archivo.remove(index);
							System.out.println("Ruta bloqueada con éxito");
						}
						else {
							System.out.println("Esa ruta no existe actualmente");
						}
						
					}
					if(opcion == 2) {
						
						System.out.println("Escriba el municipio de salida: ");
						String muni1 = teclado.nextLine();
						muni1 = muni1.replace(" ","-");
						
						System.out.println("Escriba el municipio de llegada: ");
						String muni2 = teclado.nextLine();
						muni2 = muni2.replace(" ","-");
						
						int dist = verEntero("Digite la distancia en kilómetros: ",0,10000000);
						String dis = String.valueOf(dist);
						
						//Revisar que no esté ya la ruta en el archivo
						
						int index = -1;
						
						for(int i = 0; i < archivo.size(); i++) {
							String[] l = archivo.get(i).split(" ");
							
							if(muni1.equals(l[0]) && muni2.equals(l[1])) {
								index = i;
							}
						}
						
						if(index == -1) {
							archivo.add(muni1 + " " + muni2 + " " + dis);
							System.out.println("Ruta añadida con éxito");
						}
						else {
							System.out.println("Esa ruta ya existe");
						}
					}
					
				}
				else
					if(op.equals("3")) {
						System.out.println("-----Finalizado-----");
					}
					else {
						System.out.println("Digite correctamente una opción");
					}
		}

	}
	
	/**
	 * Método para recibir archivo y convertir a arrayList
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
	 * Método para hacer la lista de municipios
	 * @param ar: lista de archivos
	 * @return lista de municipios
	 */
	public static ArrayList<String> llenarPaises(ArrayList<String> ar){
		
		ArrayList<String> paises = new ArrayList<String>();
		
		for(String elemento : ar) {
			//Separar cada línea del archivo por sus espacios
			String[] l = elemento.split(" ");
			//Si no está en la lista el país, añadirlo
			if(paises.contains(l[0]) == false) {
				paises.add(l[0]);
			}
			
		}
		return paises;
	}
	
	/**
	 * Programación defensiva para recibir enteros en un rango
	 * @param mensaje a imprimir
	 * @param n1 número mínimo
	 * @param n2 número máximo
	 * @return número digitado correctamente
	 */
	public static int verEntero(String mensaje, int n1, int n2) {
		Scanner leer = new Scanner(System.in);
		int m1=-1;
        do
        {
            try
            {
                System.out.println(mensaje);
                m1=leer.nextInt();
                if(m1 <n1 || m1 > n2)
                {
                	System.out.println("Valor fuera de rango");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Valor no valido, ingrese un valor numérico");
            }
            leer.nextLine();
        }
        while(m1<n1 || m1 > n2);
        
        return m1;
	}

}

