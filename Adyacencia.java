import java.util.ArrayList;

public class Adyacencia {

	public double[][] distancias;
	public double[][] rutas;
	
	public Adyacencia(int ind) {
		super();
		this.distancias = new double[ind][ind];
		this.rutas = new double[ind][ind];
	}
	
	
	/**
	 * Método que llena la matriz de distancias
	 * @param lista que da el archivo
	 * @param paises lista de municipios
	 */
	public void llenarMatrizD(ArrayList<String> lista, ArrayList<String> paises) {
		
		//Llenar el resto de la matriz con infinitos
		double infinito = Double.POSITIVE_INFINITY;
		//Llenar con infinito
		for(int i = 0; i < distancias.length; i++) {
			for(int j = 0; j < distancias[i].length; j++) {
				//Colocar infinitos
				distancias[i][j] = infinito;
			}
		}
		
		//Llenar los respectivos valores con la distancia
		for(String elemento : lista) {
			String[] l = elemento.split(" ");
			//Buscar indices
			int i = paises.indexOf(l[0]);
			int j = paises.indexOf(l[1]);
			
			//Cambiar la matriz al nuevo valor
			distancias[i][j] = Double.parseDouble(l[2]);
		}
		
		//Llenar la diagonal con 0s
		for(int i = 0; i < distancias.length; i++) {
			distancias[i][i] = 0;
		}
		
	}
	
	/**
	 * Método que llena la matriz de rutas según su columna
	 */
	public void llenarMatrizR() {
		
		double infinito = Double.POSITIVE_INFINITY;
		//Llenar con los indices cada columna
		for(int i = 0; i < rutas.length; i ++) {
			for(int j = 0; j < rutas[i].length; j++) {
				rutas[i][j] = j;
			}
		}
		//Llenar las diagonales con 0 ya que no son validos
		for(int i = 0; i < rutas.length; i++) {
			rutas[i][i] = infinito;
		}
	}
	
	/**
	 * Método para ejecutar algoritmo de Floyd
	 */
	public void Floyd(){
		double infinito = Double.POSITIVE_INFINITY;
        for (int k = 0; k < distancias.length; k++)
        {
            //recorre las filas i
            for (int i = 0; i < distancias.length; i++)
            {
                //recorre las columnas j
                for (int j = 0; j < distancias[i].length; j++)
                {
                    //verifica que no sea infinito
                    if (distancias[i][k] == infinito || distancias[k][j] == infinito)
                        continue;
                   
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]){
                        //se cambia la distancia en la matriz de distancia
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        //se cambia las rutas
                        rutas[i][j] = rutas[i][k];
                    }
                        
                }
            }
        }
    }
	
	/**
	 * Método para mostrar el camino de la ruta más corta
	 * @param m indice del municipio
	 * @param n indice del segundo municipio
	 * @param paises lista de los municipios
	 * @return
	 */
	public ArrayList<String> mostrarCamino(int m, int n, ArrayList<String> paises){
		ArrayList<String> camino = new ArrayList<String>();
		
		double infinito = Double.POSITIVE_INFINITY;
		
		if(distancias[m][n] != 0 && distancias[m][n] != infinito) {
			while(m != n) {
				m = (int)rutas[m][n];
				camino.add(paises.get(m));
			}
		}
		
		return camino;
	}
	
	
	/**
	 * @return the distancias
	 */
	public double[][] getDistancias() {
		return distancias;
	}
	/**
	 * @param distancias the distancias to set
	 */
	public void setDistancias(double[][] distancias) {
		this.distancias = distancias;
	}
	/**
	 * @return the rutas
	 */
	public double[][] getRutas() {
		return rutas;
	}
	/**
	 * @param rutas the rutas to set
	 */
	public void setRutas(double[][] rutas) {
		this.rutas = rutas;
	}
	
	
}
