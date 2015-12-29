import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

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
	
	JPanel p = new JPanel(){
		public void paint(Graphics gr){
			Graphics2D g = (Graphics2D)gr;
			int w = getWidth();
			int h = getHeight();
			int largocinta = w-10;
			int largosim = largocinta / cinta.length;
			
			for(int i = 0;i<cinta.length;i++){
				g.drawRect(5+largosim*i, 50, largosim, 30);
				g.drawString(cinta[i]+"", 5+largosim*i, 30);
			}
			g.drawLine((int)(5+largosim*(pos+0.5)), 60, (int)(5+largosim*(pos+0.5)), 80);
		}
	};	
	
	public MaquinaTuring(char[] cinta, char q0, char espacio) {
		this.cinta = cinta;
		this.q0 = q0;
		this.q = q0;
		this.espacio = espacio;
		estado = 0;
		pos = 0;
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
	}
	
	public static void main(String[] args) {
		
		MaquinaTuring m = new MaquinaTuring("011".toCharArray(), '0', '-');
		
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
			try {Thread.sleep(500);} catch (InterruptedException e) {}
		}
	}
	
}
