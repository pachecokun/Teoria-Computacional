import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MaquinaTuring {
	
	static enum Movimiento{
		IZQUIERDA,
		DERECHA
	}
	
	static class Transicion{
		char estado,nestado,simbolo,nsimbolo;
		Movimiento movimiento;
		public Transicion(char estado, char nestado, char simbolo, char nsimbolo, Movimiento movimiento) {
			super();
			this.estado = estado;
			this.nestado = nestado;
			this.simbolo = simbolo;
			this.nsimbolo = nsimbolo;
			this.movimiento = movimiento;
		}
		
	}
	
	char[] cinta; 
	int estado;
	char q0;
	char q;
	char espacio;
	int pos;
	List<Transicion> transiciones = new ArrayList<>();
	List<Character> finales = new ArrayList<>();
	JScrollPane s;
	
	
	JPanel p = new JPanel(){
		public void paint(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			gr.clearRect(0, 0, getWidth(), getHeight());
			int w = getWidth();
			int h = getHeight();
			int largocinta = w-10;
			int largosim = (int)((float)largocinta / (float)(cinta.length));
			
			for(int i = 0;i<cinta.length;i++){
				g.drawRect(5+largosim*i, 50, largosim, 15);
				g.drawString(cinta[i]+"", 8+largosim*i, 62);
			}
			g.drawLine((int)(5+largosim*(pos+0.5)), 70, (int)(5+largosim*(pos+0.5)), 90);
			g.drawString(q+"", (int)(largosim*(pos+0.5))-3, 90);
			s.getHorizontalScrollBar().setValue(Math.max(0,(int)(largosim*(pos+0.5))-150));
		}
	};	
	
	public MaquinaTuring(char[] cinta, char q0, char espacio) {
		this.cinta = cinta;
		this.q0 = q0;
		this.q = q0;
		this.espacio = espacio;
		estado = 0;
		pos = 0;
		
		
		JFrame f = new JFrame("Máquna de turing");
		
		JPanel p2 = new JPanel();
		p2.setLayout(null);
		
		p.setBounds(0, 0, 150+11*(cinta.length+5), 200);
		
		p2.add(p);
		p2.setPreferredSize(p.getSize());
		
		s = new JScrollPane(p2);
		s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		f.setSize(300, 300);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
	    f.add(s);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setAlwaysOnTop(true);
		f.setVisible(true);
	}	
	
	public void addTransicion(Transicion t){
		transiciones.add(t);
	}
	
	public void addFinal(char f){
		finales.add(f);
	}
	
	public void siguiente(){
		if(estado !=-1){
			
			char c = pos<cinta.length?cinta[pos]:espacio;
			
			
			boolean encontrada = false;
			
			for(Transicion t: transiciones){
				if(t.estado == q && t.simbolo == c){
					if(pos<cinta.length)
						cinta[pos] = t.nsimbolo;
					q = t.nestado;
					pos = pos+(t.movimiento==Movimiento.IZQUIERDA?-1:1);
					if(pos==-1){
						pos = cinta.length-1;
					}
					encontrada = true;
					break;
				}
			}
			if(!encontrada){
				estado = -1;
			}
			else if(finales.contains(q)){
				estado = 1;
			}
			else{
				estado = 0;
			}
		}
		p.repaint();
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
				int largo = (int)(Math.random()*6);
				for(int i = 0;i<largo;i++){
					cadena += Math.random()>0.5?'0':'1';
				}
				System.out.println("Cadena generada: "+cadena);
			}
			
			cadena+="--";

			MaquinaTuring m = new MaquinaTuring(cadena.toCharArray(), '0', '-');
			
			m.addTransicion(new Transicion('0', '1', '0', 'X', Movimiento.DERECHA));
			m.addTransicion(new Transicion('0', '3', 'Y', 'Y', Movimiento.DERECHA));
			m.addTransicion(new Transicion('1', '1', '0', '0', Movimiento.DERECHA));
			m.addTransicion(new Transicion('1', '2', '1', 'Y', Movimiento.IZQUIERDA));
			m.addTransicion(new Transicion('1', '1', 'Y', 'Y', Movimiento.DERECHA));
			m.addTransicion(new Transicion('2', '2', '0', '0', Movimiento.IZQUIERDA));
			m.addTransicion(new Transicion('2', '0', 'X', 'X', Movimiento.DERECHA));
			m.addTransicion(new Transicion('2', '2', 'Y', 'Y', Movimiento.IZQUIERDA));
			m.addTransicion(new Transicion('3', '3', 'Y', 'Y', Movimiento.DERECHA));
			m.addTransicion(new Transicion('3', '4', '-', '-', Movimiento.DERECHA));
			
			m.addFinal('4');
			
			FilePrinter f = new FilePrinter("turing.txt");
			int dt = Math.max((int)((-100f/111)*cadena.length()+(111100f/111)),100);
			
			while(true){
				f.println("Estado: "+m.q+", posicion: "+m.pos+", cinta:"+Arrays.toString(m.cinta));
				if(m.estado==-1){
					if(m.q=='4'){
						f.println("Cadena correcta");
					}
					else{
						f.print("Cadena incorrecta");
					}
					return;
				}
				m.siguiente();
				try {Thread.sleep(dt);} catch (InterruptedException e) {}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
}
