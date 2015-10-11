import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AutomataParidad  extends Automata{
	public AutomataParidad(){
		//super(estados,transiciones,finales);
		super(new Nodo(150,150,0));
		addNodo(new Nodo(150,300,1));
		addNodo(new Nodo(300,150,2));
		addNodo(new Nodo(300,300,3));
		addCondicion("0", 0,1,-50,-60);
		addCondicion("1", 0,2,50,-150);
		addCondicion("0", 1,0,50,120);
		addCondicion("1", 2,0,-50,30);
		addCondicion("1", 1,3,50,-150);
		addCondicion("0", 2,3,-50,-60);
		addCondicion("0", 3,2,50,120);
		addCondicion("1", 3,1,-50,30);
		addFinal(0);
	}
	
	public static void main(String[] args) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		AutomataParidad a = new AutomataParidad();
		
		System.out.println("Cadenas binarias con cantidad de 0s y 1s par \n");
		System.out.println("Por David Alberto Pacheco Soto\n");
		
		System.out.println("1.Modo gráfico");
		System.out.println("2.Modo consola\n");
		
		int modo = 0;
		
		while(modo<1||modo>2){
			System.out.print("Ingrese la opcion: ");
			try {
				modo = (Integer.parseInt(br.readLine()));
			} catch (Exception e) {} 
		}
		
		int entrada = 0;
		
		System.out.println("\nMétodos de entrada:");
		System.out.println("\n1.Consola");
		System.out.println("2.Archivo\n");
		
		while(entrada<1||entrada>2){
			System.out.print("Ingrese la opcion: ");
			try {
				entrada = (Integer.parseInt(br.readLine()));
			} catch (Exception e) {} 
		}
		
		ArrayList<String> lineas = new ArrayList<>();
		
		if(entrada==1){
			try {
				System.out.print("Ingrese las cadenas a evaluar: ");
				lineas.add(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("El archivo debe estar en la carpeta: "+a.getClass().getResource("").getFile());
			System.out.println("Ingrese el nombre del archivo :");
			String archivo;
			try {
				archivo = br.readLine();
				br = new BufferedReader(new FileReader(new File(archivo)));
				while(br.ready()){
					lineas.add(br.readLine());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		int l = 1;
		int posicion = 1;
		int procesados = 0; 
		String palabra ="";
		
		for(String linea:lineas){
			posicion = 1;
			palabra = "";
			for(char c:linea.toCharArray()){
				if(Character.isDigit(c)){
					if(modo==1){
						a.procesar(c,700,500);
					}
					else{
						a.procesar(c);
					}
					palabra+=c;
				}
				else{
					if(palabra.length()>0&&a.isFinalizado()){
						System.out.println("\""+palabra+"\" en linea "+l+" posición "+(posicion-palabra.length()));
					}
					a.finalizar();
					a.reset();
					palabra="";
				}
				posicion++;
			}
			if(palabra.length()>0&&a.isFinalizado()){
				System.out.println("\""+palabra+"\" en linea "+l+" posición "+(posicion-palabra.length()));
			}
			a.finalizar();
			a.reset();
			l++;
		}
		
		
		AutomataParidad ap = new AutomataParidad();
		for(char c:"001001".toCharArray()){
			ap.procesar(c, 500,500);
		}
		ap.finalizar();
	}
}
