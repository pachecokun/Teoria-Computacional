import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
		frameT.add(tablero,"North");
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
		System.out.println(jugadas.size());
		for(Trayectoria t:jugadas){
			for(Nodo n:t){
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
		
	}
	
	public static void main(String[] args) {
		AutomataTablero a = new AutomataTablero();
		a.procesarTrayectorias("brbrb");
	}

}

