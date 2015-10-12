
public class AutomataWEBAY extends Automata {

	Nodo web = new Nodo(350, 400, 5);
	Nodo ebay = new Nodo(750, 400, 7);
	
	public AutomataWEBAY() {
		super(new Nodo(150,150,0),120);
		addNodo(new Nodo(350,150,1));
		addNodo(new Nodo(550,150,2));
		addNodo(new Nodo(750,150,3));
		addNodo(new Nodo(150,400,4));
		addNodo(web);
		addNodo(new Nodo(550,400,6));
		addNodo(ebay);
		
		addCondicion("w", 0, 1,20,-170);
		addCondicion("e", 0, 2,100,-152);
		addCondicion("-we", 0, 0,0,-180);
		
		addCondicion("w", 1, 1,0,-180);
		addCondicion("e", 1, 3,100,-160);
		addCondicion("-we", 1, 0,-50,20);
		
		addCondicion("w", 2, 1);
		addCondicion("e", 2, 2,0,-180);
		addCondicion("b", 2, 4,50,-37);
		addCondicion("-web", 2, 0,-100,25);
		
		addCondicion("w", 3, 1,150,-38);
		addCondicion("e", 3, 2);
		addCondicion("b", 3, 5,0,-28);
		addCondicion("-web", 3, 0,200,-33);
		
		addCondicion("w", 4, 1,0,130);
		addCondicion("e", 4, 2,-50,137);
		addCondicion("a", 4, 6,-100,153);
		addCondicion("-wea", 4, 0,0,90);
		
		addCondicion("w", 5, 1,-100,53);
		addCondicion("e", 5, 2,-95,0);
		addCondicion("a", 5, 6,0,-180);
		addCondicion("-wea", 5, 0,300,147);
		
		addCondicion("w", 6, 1,0,45);
		addCondicion("e", 6, 2,-100,50);
		addCondicion("y", 6, 7,0,-180);
		addCondicion("-wey", 6, 0,-100,48);
		
		addCondicion("w", 7, 1,-100,45);
		addCondicion("e", 7, 2,0,45);
		addCondicion("-we", 7, 0,-450,60);
		
		addFinal(5);
		addFinal(7);
	}
	public static void main(String[] args) {
		AutomataWEBAY a = new AutomataWEBAY();
		FilePrinter p = new FilePrinter("webay.txt");
		
		EntradaAutomata e = new EntradaAutomata("Palabras que contienen web o ebay");
		
		if(e.modo==1){
			a.initUI(850, 610);
			a.finalizar();
		}
		int l = 1;
		int posicion = 1;
		boolean is_ebay = false;
		boolean is_web = false;
		String palabra ="";

		for(String linea:e.lineas){
			posicion = 1;
			palabra = "";
			is_ebay = false;
			is_web = false;
			for(char c:linea.toCharArray()){
				if(Character.isLetter(c)){
					Nodo n = a.procesar(c);
					palabra+=c;
					if(n==a.web){
						is_web = true;
					}
					else if(n==a.ebay){
						is_ebay = true;
					}
				}
				else{
					if(is_web){
						p.println("\"web\" encontrada en \""+palabra+"\" en linea "+l+" posición "+(posicion-palabra.length()));
					}
					if(is_ebay){
						p.println("\"ebay\" encontrada en \""+palabra+"\" en linea "+l+" posición "+(posicion-palabra.length()));
					}
					a.reset();
					a.finalizar();
					palabra="";
					is_ebay = false;
					is_web = false;
				}
				posicion++;
			}
			if(is_web){
				p.println("\"web\" encontrada en \""+palabra+"\" en linea "+l+" posición "+(posicion-palabra.length()));
			}
			if(is_ebay){
				p.println("\"ebay\" encontrada en \""+palabra+"\" en linea "+l+" posición "+(posicion-palabra.length()));
			}
			a.reset();
			a.finalizar();
			l++;
		}
		
		p.println("\n\nBúsqueda terminada");
		
	}
}
