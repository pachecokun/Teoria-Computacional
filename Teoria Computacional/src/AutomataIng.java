import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AutomataIng extends Automata{

	public AutomataIng(){
		//super(estados,transiciones,finales);
		super(new Nodo(50,300,0));
		addNodo(new Nodo(150,300,1));
		addNodo(new Nodo(250,300,2));
		addNodo(new Nodo(350,300,3));
		addCondicion("[a-zA-Z&&[^iI]]", 0,0);
		addCondicion("[a-zA-Z&&[^iInN]]", 1,0,50);
		addCondicion("[a-zA-Z&&[^iIgG]]", 2,0,60);
		addCondicion("[a-zA-Z&&[^iI]]", 3,0,150);
		addCondicion("[iI]", 0,1);
		addCondicion("[nN]", 1,2);
		addCondicion("[iI]", 1,1);
		addCondicion("[Gg]", 2,3);
		addCondicion("[iI]", 2,1,90);
		addCondicion("[iI]", 3,1,70);
		addFinal(3);
	}
	
	
	ArrayList<Nodo> nodos = new ArrayList<>();
	ArrayList<Trayectoria> trayectorias = new ArrayList<>();
	/*
	public void diagrama(){
		JFrame jf = new JFrame("*ing");
		JPanel p = new JPanel(){
			public void paint(Graphics g){
				for(Trayectoria t: trayectorias){
					t.draw(g);
				}
				for(Nodo n:nodos){
					g.setColor(Color.white);
					g.fillOval(n.px-20, n.py-20, 40, 40);
					g.setColor(Color.black);
					g.drawOval(n.px-20, n.py-20, 40, 40);
					g.drawString("q"+n.estado, n.px-8,n.py);
				}
				g.drawArc(10, 10, 100, 100, 50, 30);
			}
		};
		
		jf.setSize(500, 500);
		jf.getContentPane().add(p);
		jf.setVisible(true);
	}
	*/
	public static void main(String args[]){
				
		AutomataIng a = new AutomataIng();

		for(char c :"inging2".toCharArray()){
			a.procesar(c,true);
		}//a.diagrama();
	}
	
	/*static class Nodo{
		int px;
		int py;
		int estado;
		public Nodo(int px, int py, int estado) {
			super();
			this.px = px;
			this.py = py;
			this.estado = estado;
		}
		
	}*/
	static class Trayectoria{
		Nodo n1,n2;
		char c;
		int radio=0;
		
		public Trayectoria(Nodo n1, Nodo n2, char c, int radio) {
			super();
			this.n1 = n1;
			this.n2 = n2;
			this.c = c;
			this.radio = radio;
		}

		/*public void draw(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			if(radio==0){
				g.drawLine(n1.px, n1.py, n2.px, n2.py);
			}
			else{
				g.draw(new QuadCurve2D.Float(n1.px, n1.py, (n1.px+n2.px)/2, radio, n2.px, n2.py));
				
			}
		}*/
		
	}
}
