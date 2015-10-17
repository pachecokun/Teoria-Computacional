import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EntradaAutomata {
	int modo=0,entrada=0;
	String archivo = null;
	ArrayList<String> lineas = new ArrayList<>();
	
	public EntradaAutomata(String desc,boolean askmodo,boolean binario){
		

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		AutomataParidad a = new AutomataParidad();

		System.out.println(desc);
		System.out.println("Por David Alberto Pacheco Soto\n");
		
		if(askmodo){
		
			System.out.println("1.Modo gráfico");
			System.out.println("2.Modo consola\n");
			
			while(modo<1||modo>2){
				System.out.print("Ingrese la opcion: ");
				try {
					modo = (Integer.parseInt(br.readLine()));
				} catch (Exception e) {} 
			}
		
		}
		
		System.out.println("\nMétodos de entrada:");
		System.out.println("\n1.Consola");
		System.out.println("2.Archivo\n");
		
		while(entrada<1||entrada>2){
			System.out.print("Ingrese la opcion: ");
			try {
				entrada = (Integer.parseInt(br.readLine()));
			} catch (Exception e) {} 
		}
		
		
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
			System.out.print("Ingrese el nombre del archivo :");
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
	}

	public EntradaAutomata(String desc){
		this(desc,true,false);
	}
	public EntradaAutomata(String desc,boolean ask){
		this(desc,ask,false);
	}
}
