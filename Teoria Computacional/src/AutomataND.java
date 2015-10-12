import java.util.ArrayList;

public class AutomataND extends Automata{

	String word;
	Nodo [] estados = new Nodo[1];
	
	public AutomataND(Nodo q0, int angulo) {
		super(q0, angulo);
		estados[0]=q0;
	}

	
	public void reset(){
		estados = new Nodo[1];
		estados[0] = q0;
	}
	
	public boolean isFinalizado(){
		for(Nodo n:estados){
			if(n.isFin()){
				return true;
			}
		}
		return false;
	}

	public void procesarND(char c){
		ArrayList<Nodo> nodos = new ArrayList<>();
		word+=c;
		try{
			for(Nodo e:estados){
				ArrayList<Nodo> tmp = new ArrayList<>();
				for(Condicion con:condiciones){
					Nodo n = con.procesar(e, c);
					if(n!=null){
						tmp.add(n);
					}
				}
				for (Nodo nodo : tmp) {
					if(!nodos.contains(nodo))
						nodos.add(nodo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		estados = nodos.toArray(new Nodo[0]);
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
		
		EntradaAutomata e = new EntradaAutomata("Automata no deterministico para detectar cadenas que terminan en 01",false);
		
		String palabra = "";
		FilePrinter p = new FilePrinter("binarios_01.txt");
		
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
				if(a.isFinalizado())
					p.println("La cadena "+palabra+" termina en 01");
				else
					p.println("La cadena "+palabra+" NO termina en 01");
			}
		}
		
	}
	
}
