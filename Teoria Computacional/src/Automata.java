import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Automata {
	Nodo q0;
	Nodo estado =null;
	ArrayList<Condicion>condiciones = new ArrayList<>();
	ArrayList<Nodo> finales = new ArrayList<>();
	boolean finalizado = false;
	boolean fin = false;
	ArrayList<Nodo> nodos = new ArrayList<>();
	JFrame frame;
	JPanel p;
	String palabra="";
	
	
	public Automata(Nodo q0,int angulo){
		nodos.add(q0);
		this.q0=q0;
		q0.setInicial(true, angulo);
		this.estado = q0;
	}

	public void addNodo(Nodo n){
		nodos.add(n);
	}

	public void addFinal(int n){
		nodos.get(n).setFinal(true);
	}
	
	public void addCondicion(String s, int n1, int n2){
		condiciones.add(new Condicion(s, nodos.get(n1), nodos.get(n2),0,0));
	}
	public void addCondicion(String s, int n1, int n2,int h,int a){
		condiciones.add(new Condicion(s, nodos.get(n1), nodos.get(n2),h,a));
	}
	
	public void reset(){
		if(estado!=null)
			estado.setActual(false);
		estado = q0;
		palabra="";
		fin = false;
		if(p!=null){
			p.repaint();
		}
	}
	
	public boolean isFinalizado(){
		return estado!=null&&estado.isFin();
	}
	
	public Nodo procesar(char c){
		Condicion cn = null;
		Nodo e0 = estado;
		palabra+=c;
		if(frame!=null){
			if(estado!=null){
				e0.setActual(true);
				p.repaint();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				e0.setActual(false);
			}
		}
		
		try{
			boolean encontrado = false;
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
		
		if(frame!=null){
			if(cn!=null){
				cn.setActual(true);
				p.repaint();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cn.setActual(false);
				p.repaint();
			}
			if(estado!=null){
				estado.setActual(true);
				p.repaint();
			}
		}
		
		return estado;
	}
	
	public void initUI(int w,int h){
		frame = new JFrame();
		p = new JPanel(){

			public void paint(Graphics g){
				g.clearRect(0, 0, getWidth(), getHeight());
				Font f = g.getFont();
				if(fin)
					if(estado!=null&&estado.isFin())
						g.setColor(Color.green);
					else
						g.setColor(Color.red);
				g.setFont(new Font("Arial",Font.BOLD,50));
				g.drawString(palabra, 20, 50);
				g.setFont(f);
				for(Condicion con:condiciones){
					con.draw(g);
				}
				for(Nodo n:nodos){
					n.paint(g);
				}
			}
		};
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(p);
		frame.setSize(w,h);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setState(JFrame.ICONIFIED);
		frame.setState(JFrame.NORMAL);
	}
	
	public void finalizar(){
		fin = true;
		if(p!=null){
			p.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class Condicion{
		Nodo n1;
		Nodo n2;
		int radio;
		int angulo;
		boolean actual;
		char permitido;
		String no_permitidos=null;
		String desc;
		
		public Condicion(String s, Nodo n1, Nodo n2,int radio,int angulo) {
			super();
			if(s.startsWith("-")){
				no_permitidos = s.substring(1);
				desc = "\u03A3 \\ {";
				for(int i = 0;i<no_permitidos.length();i++){
					desc+=no_permitidos.charAt(i);
					if(i<no_permitidos.length()-1){
						desc+=",";
					}
				}
				desc+="}";
			}
			else{
				permitido = s.charAt(0);
				desc =""+permitido;
			}
			
			
			this.n1 = n1;
			this.n2 = n2;
			this.radio = radio;
			this.angulo = angulo;
		}
		
		public void setActual(boolean actual) {
			this.actual = actual;
		}

		public Nodo procesar(Nodo estado,char c){
			c = Character.toLowerCase(c);
			if(estado==n1&&((no_permitidos!=null&&no_permitidos.indexOf(c)==-1)||c==permitido)){
				return n2;
			}
			else{
				return null;
			}
		}
		
		
		public void draw(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			Font f = g.getFont();
			Font f2 = new Font(f.getFontName(), Font.BOLD, f.getSize()+7);
			if(actual){
				g.setStroke(new BasicStroke(2f));
				g.setFont(f2);
				g.setColor(Color.BLUE);
			}
			else{
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(1f));
			}
			if(n1==n2){
				g.drawArc(n1.x-40, n1.y-40, 40, 40, 0, 360);
				g.drawString(desc, n1.x-50, n2.y-40);
			}
			else if(radio==0){
				g.drawLine(n1.x, n1.y, n2.x, n2.y);
				if(Math.abs(n1.x-n2.x)>Math.abs(n1.y-n2.y))
					g.drawString(desc, (n1.x+n2.x)/2, (n1.y+n2.y)/2-5);
				else
					g.drawString(desc, (n1.x+n2.x)/2+5, (n1.y+n2.y)/2);
			}
			else{
				if(Math.abs(n1.x-n2.x)>Math.abs(n1.y-n2.y)){
					g.draw(new QuadCurve2D.Float(n1.x, n1.y, (n1.x+n2.x)/2,(n1.y+n2.y)/2-radio, n2.x, n2.y));
					g.drawString(desc, (n1.x+n2.x)/2,(n1.y+n2.y)/2-(int)(radio*0.60));
				}
				else{
					g.draw(new QuadCurve2D.Float(n1.x, n1.y, (n1.x+n2.x)/2-radio,(n1.y+n2.y)/2, n2.x, n2.y));
					g.drawString(desc, (n1.x+n2.x)/2-(int)(radio*0.6),(n1.y+n2.y)/2);
				}
			}
			AffineTransform original = g.getTransform();
			g.translate(n2.x, n2.y);
			g.rotate(Math.toRadians(angulo));
			int pxs []= {20,30,30}; 
			int pys []= {0,10,-10}; 
			g.fillPolygon(pxs,pys,3);
			g.setTransform(original);
			g.setFont(f);
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(1f));
			
		}
		
	}

	public static class Nodo{
		int x,y,estado;
		boolean fin;
		boolean actual;
		boolean inicial = false;
		int ainicial = 0;
		
		public void setInicial(boolean inicial,int angulo) {
			this.inicial = inicial;
			ainicial = angulo;
		}
		
		public boolean isFin() {
			return fin;
		}
		
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
		public void paint(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			
			if(inicial){
				AffineTransform original = g.getTransform();
				g.translate(x, y);
				g.rotate(Math.toRadians(ainicial));
				int pxs []= {20,30,30}; 
				int pys []= {0,10,-10}; 
				g.drawLine(50, 0, 10, 0);
				g.fillPolygon(pxs,pys,3);
				g.setTransform(original);
			}
			
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