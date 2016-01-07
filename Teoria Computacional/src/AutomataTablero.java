import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class AutomataTablero extends AutomataND {

	int posficha = 0;
	
	JPanel tablero = new JPanel(){
		public void paint(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			g.clearRect(0, 0, getWidth(), getHeight());
			for(int i = 0;i<9;i++){
				g.setColor(i%2==0?Color.black:Color.red);
				g.fillRect(150*(i%3), 150*(i/3), 150, 150);
				if(i==posficha){
					g.setColor(Color.white);
					g.setColor(Color.white);
					g.fillOval(150*(i%3), 150*(i/3), 150, 150);
				}
			}
		}
	};
	
	public AutomataTablero() {
		super(new Nodo(0), 0);
		addNodo(new Nodo(1));
		addNodo(new Nodo(2));
		addNodo(new Nodo(3));
		addNodo(new Nodo(4));
		addNodo(new Nodo(5));
		addNodo(new Nodo(6));
		addNodo(new Nodo(7));
		addNodo(new Nodo(8));
		addFinal(8);

		addCondicion("r", 0,1);
		addCondicion("r", 0,3);
		addCondicion("b", 0,4);

		addCondicion("r", 2,1);
		addCondicion("r", 2,5);
		addCondicion("b", 2,4);

		addCondicion("r", 6,3);
		addCondicion("r", 6,7);
		addCondicion("b", 6,4);
		
		addCondicion("r", 8,5);
		addCondicion("r", 8,7);
		addCondicion("b", 8,4);
		
		addCondicion("r", 1,3);
		addCondicion("r", 1,5);
		addCondicion("b", 1,0);
		addCondicion("b", 1,2);
		addCondicion("b", 1,4);
		
		addCondicion("r", 3,1);
		addCondicion("r", 3,7);
		addCondicion("b", 3,0);
		addCondicion("b", 3,4);
		addCondicion("b", 3,6);
		
		addCondicion("r", 5,1);
		addCondicion("r", 5,7);
		addCondicion("b", 5,2);
		addCondicion("b", 5,4);
		addCondicion("b", 5,8);
		
		addCondicion("r", 7,3);
		addCondicion("r", 7,5);
		addCondicion("b", 7,4);
		addCondicion("b", 7,6);
		addCondicion("b", 7,8);
		
		addCondicion("r", 4,1);
		addCondicion("r", 4,3);
		addCondicion("r", 4,5);
		addCondicion("b", 4,0);
		addCondicion("b", 4,2);
		addCondicion("b", 4,6);
		addCondicion("b", 4,8);
		
		JFrame frameT = new JFrame("Tablero");
		frameT.setSize(460, 475);
		frameT.add(tablero);
		frameT.setResizable(false);
		frameT.setLocationRelativeTo(null);
		frameT.setVisible(true);
		

	}
	
	public void procesarTrayectorias(String tr){

		
		for(char c:tr.toCharArray()){
			procesarND(c);	
		}
		ArrayList<Trayectoria> jugadas= new ArrayList<>();
		for(final Trayectoria t:trayectorias){
			if(t.getEstado().isFin()&&!t.atascado){
				jugadas.add(t);
			}
		}
		System.out.println("Cantidad de jugadas ganadoras: "+jugadas.size());
		
		if(jugadas.size()==0){
			System.out.println("No hay jugadas ganadoras posibles.");
			System.exit(0);
		}
		
		FilePrinter f = new FilePrinter("jugadas.txt");
		
		int animar = (int)(Math.random()*jugadas.size());
		
		for(int  i = 0;i<jugadas.size();i++){
			Trayectoria t = jugadas.get(i);
			for(Nodo n:t){
				f.print(n.estado+",");
				if(i==animar){
					posficha = n.estado;
					tablero.repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			f.println();
		}
		
	}
	
	public static void main(String[] args) {
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("\nMétodos de entrada:");
			System.out.println("\n1.Consola");
			System.out.println("2.Aleatorio\n");
			
			int entrada = 0;
			
			while(entrada<1||entrada>2){
				System.out.print("Ingrese la opción: ");
				try {
					entrada = (Integer.parseInt(br.readLine()));
				} catch (Exception e) {} 
			}
			
			String cadena = "";
			
			if(entrada == 1){
				System.out.print("Ingrese la cadena: ");
				cadena = br.readLine();
			}
			else{
				int largo = (int)(Math.random()*10);
				for(int i = 0;i<largo;i++){
					cadena += Math.random()>0.5?'r':'b';
				}
				System.out.println("Cadena generada: "+cadena);
			}
			
			AutomataTablero a = new AutomataTablero();
			a.procesarTrayectorias(cadena);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}

