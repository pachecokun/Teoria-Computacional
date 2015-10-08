import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;

//Comentario salvaje aparecio
//Comentario salvaje aparecio
//ola k ase

public abstract class Automata {
	/*int estado = 0;
	Transicion[]transiciones;
	int[] finales;
	boolean finalizado = false;
	int[] estados;
	int posiciones[][];
	*/
	Nodo q0;
	Nodo estado =null;
	ArrayList<Condicion>condiciones = new ArrayList<>();
	ArrayList<Nodo> finales = new ArrayList<>();
	boolean finalizado = false;
	ArrayList<Nodo> nodos = new ArrayList<>();
	JFrame frame;
	JPanel p;
	String palabra="";
	
	
	public Automata(Nodo q0){
		nodos.add(q0);
		this.q0=q0;
		this.estado = q0;
	}

	public void addNodo(Nodo n){
		nodos.add(n);
	}

	public void addFinal(int n){
		nodos.get(n).setFinal(true);
	}
	
	public void addCondicion(String s, int n1, int n2){
		condiciones.add(new Condicion(s, nodos.get(n1), nodos.get(n2),0));
	}
	public void addCondicion(String s, int n1, int n2,int h){
		condiciones.add(new Condicion(s, nodos.get(n1), nodos.get(n2),h));
	}
	
	public void reset(){
		estado = q0;
		palabra="";
	}
	
	public boolean isFinalizado(){
		for(Nodo i:finales){
			if(estado==i){
				return true;
			}
		}
		return false;
	}
	
	public Condicion procesar(char c){
		Condicion cn = null;
		palabra+=c;
		try{
			boolean encontrado = false;
			System.out.print(c+":"+estado.estado+"->");
			for(Condicion con:condiciones){
				Nodo n = con.procesar(estado, c);
				if(n!=null){
					encontrado = true;
					cn = con;
					estado = n;
					break;
				}
			}
			if(!encontrado){
				estado = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(estado==null?-1:estado.estado);
		return cn;
	}
	
	public void procesar(char c, boolean draw){
		
		if(frame == null){
			frame = new JFrame();
			p = new JPanel(){
				public void paint(Graphics g){
					g.clearRect(0, 0, getWidth(), getHeight());
					g.drawString(palabra, 50, 50);
					for(Condicion con:condiciones){
						con.draw(g);
					}
					for(Nodo n:nodos){
						n.paint(g);
					}
				}
			};
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.add(p);
			frame.setVisible(true);
		}
		if(estado!=null){
			estado.setActual(true);
			p.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			estado.setActual(false);
		}
		Condicion con = procesar(c);
		System.out.println(con);
		if(con!=null){
			con.setActual(true);
			p.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con.setActual(false);
			p.repaint();
		}
		if(estado!=null){
			estado.setActual(true);
			p.repaint();
		}
		
	}
	
	
	public static class Condicion{
		Pattern patron;
		Nodo n1;
		Nodo n2;
		int radio;
		boolean actual;
				
		public Condicion(String s, Nodo n1, Nodo n2,int radio) {
			super();
			this.patron = Pattern.compile(s);
			this.n1 = n1;
			this.n2 = n2;
			this.radio = radio;
		}
		
		public void setActual(boolean actual) {
			this.actual = actual;
		}

		public Nodo procesar(Nodo estado,char c){
			if(estado==n1&&patron.matcher(""+c).matches()){
				return n2;
			}
			else{
				return null;
			}
		}
		
		public void draw(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			if(actual){
				g.setStroke(new BasicStroke(2f));
				g.setColor(Color.BLUE);
			}
			else{
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(1f));
			}
			if(n1==n2){
				g.drawArc(n1.x-40, n1.y-40, 40, 40, 0, 360);
			}
			else if(radio==0){
				g.drawLine(n1.x, n1.y, n2.x, n2.y);
			}
			else{
				if(Math.abs(n1.x-n2.x)>Math.abs(n1.y-n2.y)){
					g.draw(new QuadCurve2D.Float(n1.x, n1.y, (n1.x+n2.x)/2,(n1.y+n2.y)/2-radio, n2.x, n2.y));
				}
				else{
					g.draw(new QuadCurve2D.Float(n1.x, n1.y, (n1.x+n2.x)/2-radio,(n1.y+n2.y)/2, n2.x, n2.y));
				}
			}
			AffineTransform original = g.getTransform();
			g.translate(n2.x, n2.y);
			g.rotate(Math.atan((n1.y-n2.y)/(n2.x-n2.x)));
			g.fillOval(-25, -5, 10, 10);
			g.setTransform(original);
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(1f));
		}
		
	}

	public static class Nodo{
		int x,y,estado;
		boolean fin;
		boolean actual;
		
		public Nodo(int estado) {
			super();
			this.estado = estado;
		}
		
		public void setFinal(boolean fin){
			this.fin = fin;
		}
		
		public void setActual(boolean b){
			this.actual = b;
		}
		
		public Nodo(int x, int y, int estado) {
			super();
			this.x = x;
			this.y = y;
			this.estado = estado;
		}
		public void paint(Graphics g){
			if(actual)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.white);
			g.fillOval(x-20, y-20, 40, 40);
			g.setColor(Color.black);
			if(fin)
				g.drawOval(x-17, y-17, 34, 34);
			g.drawOval(x-20, y-20, 40, 40);
			g.drawString("q"+estado, x-8,y);
		}
	}
	
}