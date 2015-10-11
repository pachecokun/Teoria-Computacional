import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AutomataIng extends Automata{

	public AutomataIng(){
		super(new Nodo(100,200,0));
		addNodo(new Nodo(250,200,1));
		addNodo(new Nodo(400,200,2));
		addNodo(new Nodo(550,200,3));
		addCondicion("[iI]", 0,1,0,-180);
		addCondicion("[nN]", 1,2,0,-180);
		addCondicion("[iI]", 1,1,0,-180);
		addCondicion("[Gg]", 2,3,0,-180);
		addCondicion("[iI]", 2,1,90,-50);
		addCondicion("[iI]", 3,1,70,-23);
		addCondicion("[a-zA-Z&&[^iI]]", 0,0,0,-180);
		addCondicion("[a-zA-Z&&[^iInN]]", 1,0,-70,32);
		addCondicion("[a-zA-Z&&[^iIgG]]", 2,0,-120,34);
		addCondicion("[a-zA-Z&&[^iI]]", 3,0,150,-30);
		addFinal(3);
	}
	
	public static void main(String args[]){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		AutomataIng a = new AutomataIng();
		
		System.out.println("Palabras que terminan con ing \n");
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
				if(Character.isLetter(c)){
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
		
	}
}
