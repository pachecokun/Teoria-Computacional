import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;


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
	}
	
	public boolean isFinalizado(){
		for(Nodo i:finales){
			if(estado==i){
				return true;
			}
		}
		return false;
	}
	
	public void procesar(char c){
		try{
			boolean encontrado = false;
			System.out.print(c+":"+estado.estado+"->");
			for(Condicion con:condiciones){
				Nodo n = con.procesar(estado, c);
				if(n!=null){
					encontrado = true;
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
	}
	
	public void procesar(char c, boolean draw){
		
		if(frame == null){
			frame = new JFrame();
			p = new JPanel(){
				public void paint(Graphics g){
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
		procesar(c);
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
				
		public Condicion(String s, Nodo n1, Nodo n2,int radio) {
			super();
			this.patron = Pattern.compile(s);
			this.n1 = n1;
			this.n2 = n2;
			this.radio = radio;
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
			if(n1==n2){
				g.drawArc(n1.x-40, n1.y-40, 40, 40, 0, 360);
			}
			else if(radio==0){
				g.drawLine(n1.x, n1.y, n2.x, n2.y);
			}
			else{
				g.draw(new QuadCurve2D.Float(n1.x, n1.y, (n1.x+n2.x)/2,(n1.y+n2.y)/2-radio, n2.x, n2.y));

				Graphics2D g2d = (Graphics2D)g;
				AffineTransform original = g.getTransform();
				//g.translate(n2.x, n2.y);
				//g.rotate(-Math.atan(radio/(n1.x+n2.x)/2));
				g.fillOval(-25, -5, 10, 10);
				g.transform(original);
			}
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
				g.setColor(Color.green);
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