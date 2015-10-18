import java.util.ArrayList;

public class AutomataND extends Automata{

	String word;
	Trayectoria[] trayectorias = new Trayectoria[1];
	
	public AutomataND(Nodo q0, int angulo) {
		super(q0, angulo);
		trayectorias[0] = new Trayectoria();
		trayectorias[0].addNodo(q0);
	}
	
	public void printTrayectorias(FilePrinter p){
		p.println("Trayectorias posibles: ");
		for(Trayectoria t:trayectorias){
			for (int i = 0; i < t.size(); i++) {
				Nodo n = t.get(i);
				p.print("q"+n.estado);
				if(i+1<t.size()){
					p.print(" -> ");
				}
				else{
					if(t.atascado){
						p.print(" [Atascado]");
					}
					else if(t.getEstado().isFin()&&!t.procesado){
						p.print(" [Final]");
					}
				}
				
			}
			p.println();
		}
	}

	
	public void reset(){
		trayectorias = new Trayectoria[1];
		trayectorias[0] = new Trayectoria();
		trayectorias[0].addNodo(q0);
	}
	
	public boolean isFinalizado(){
		for(Trayectoria t:trayectorias){
			if(t.getEstado().isFin()&&!t.atascado&&!t.procesado){
				return true;
			}
		}
		return false;
	}

	public void procesarND(char c){
		ArrayList<Trayectoria> trayectorias = new ArrayList<>();
		word+=c;
		try{
			
			for(Trayectoria t:this.trayectorias){
				ArrayList<Nodo> tmp = new ArrayList<>();
				if(!t.atascado){
					for(Condicion con:condiciones){
						if(con.n1==t.getEstado()){
							Nodo n = con.procesar(t.getEstado(), c);
							if(n!=null){
								tmp.add(n);
							}
						}
					}
				}
				for(Trayectoria tr:t.getTrayectorias(tmp.toArray(new Nodo[0]))){
					trayectorias.add(tr);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.trayectorias = trayectorias.toArray(new Trayectoria[0]);
	}
	
	class Trayectoria extends ArrayList<Nodo>{
		boolean atascado;
		boolean procesado;
		
		public void addNodo(Nodo n){
			if(n!=null&&!atascado){
				super.add(n);
			}
			else{
				atascado = true;
			}
		}
		
		public Nodo getEstado(){
			return get(size()-1);
		}
		
		public Trayectoria[] getTrayectorias(Nodo[] nodos){
			ArrayList<Trayectoria> aux = new ArrayList<>();
			if(nodos.length==0){
				atascado = true;
			}
			if(atascado){
				aux.add(this);
			}
			if(!procesado&&!atascado){
				for(Nodo n:nodos){
					Trayectoria t = (Trayectoria) this.clone();
					t.addNodo(n);
					aux.add(t);
				}
				procesado = true;
			}
			return aux.toArray(new Trayectoria[0]);
		}
	}
	
	public static void main(String[] args) {
		AutomataND a = new AutomataND(new Nodo(100,100,0),120);
		a.addNodo(new Nodo(200,100,1));
		a.addNodo(new Nodo(300,100,2));
		
		a.addCondicion("-", 0, 0,0,-180);
		a.addCondicion("0", 0, 1,0,-180);
		a.addCondicion("1", 1, 2,0,-180);
		
		a.addFinal(2);
		
		a.initUI(400, 200);
		
		EntradaAutomata e = new EntradaAutomata("Automata no deterministico para detectar cadenas que terminan en 01",false,true);
		
		String palabra = "";
		FilePrinter p = new FilePrinter("binarios_01.txt");

		if(e.entrada==3){
			System.out.println("Cadena generada: "+e.lineas.get(0));
		}
		for(String linea:e.lineas){
			palabra = "";
			a.reset();
			for(char c:linea.toCharArray()){
				if(Character.isDigit(c)){
					a.procesarND(c);
					palabra+=c;
				}
				else{
					if(palabra.length()>0){
						a.printTrayectorias(p);
						if(a.isFinalizado())
							p.println("La cadena "+palabra+" termina en 01");
						else
							p.println("La cadena "+palabra+" NO termina en 01");
					}
					a.reset();
					palabra="";
				}
			}
			if(palabra.length()>0){
				a.printTrayectorias(p);
				if(a.isFinalizado())
					p.println("La cadena "+palabra+" termina en 01");
				else
					p.println("La cadena "+palabra+" NO termina en 01");
			}
		}
		System.exit(0);
	}
	
}
