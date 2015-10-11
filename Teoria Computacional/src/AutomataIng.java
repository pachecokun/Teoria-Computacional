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
		super(new Nodo(100,200,0),120);
		addNodo(new Nodo(250,200,1));
		addNodo(new Nodo(400,200,2));
		addNodo(new Nodo(550,200,3));
		addCondicion("i", 0,1,0,-180);
		addCondicion("n", 1,2,0,-180);
		addCondicion("i", 1,1,0,-180);
		addCondicion("g", 2,3,0,-180);
		addCondicion("i", 2,1,90,-50);
		addCondicion("i", 3,1,70,-23);
		addCondicion("-i", 0,0,0,-180);
		addCondicion("-in", 1,0,-70,32);
		addCondicion("-ig", 2,0,-120,34);
		addCondicion("-i", 3,0,150,-30);
		addFinal(3);
	}
	
	public static void main(String args[]){

		AutomataIng a = new AutomataIng();
		EntradaAutomata e = new EntradaAutomata("Palabras que terminan con ing");
		
		int l = 1;
		int posicion = 1;
		int procesados = 0; 
		String palabra ="";
		if(e.modo==1){
			a.initUI(700, 400);
		}
		for(String linea:e.lineas){
			posicion = 1;
			palabra = "";
			for(char c:linea.toCharArray()){
				if(Character.isLetter(c)){
					a.procesar(c);
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

		System.out.println("\n\nBúsqueda terminada");
	}
}
