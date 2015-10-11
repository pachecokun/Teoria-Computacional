import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AutomataParidad  extends Automata{
	public AutomataParidad(){
		super(new Nodo(150,150,0),-120);
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
		
		EntradaAutomata e = new EntradaAutomata("Cadenas binarias con cantidad de 0s y 1s par");

		AutomataParidad a = new AutomataParidad();

		int l = 1;
		int posicion = 1;
		int procesados = 0; 
		String palabra ="";
		if(e.modo==1){
			a.initUI(500, 500);
		}
		for(String linea:e.lineas){
			posicion = 1;
			palabra = "";
			for(char c:linea.toCharArray()){
				if(Character.isDigit(c)){
					a.procesar(c);
					palabra+=c;
				}
				else{
					if(palabra.length()>0&&a.isFinalizado()){
						System.out.println("\""+palabra+"\" en linea "+l+" posici�n "+(posicion-palabra.length()));
					}
					a.finalizar();
					a.reset();
					palabra="";
				}
				posicion++;
			}
			if(palabra.length()>0&&a.isFinalizado()){
				System.out.println("\""+palabra+"\" en linea "+l+" posici�n "+(posicion-palabra.length()));
			}
			a.finalizar();
			a.reset();
			l++;
		}
		System.out.println("\n\nB�squeda terminada");
		
	}
}
