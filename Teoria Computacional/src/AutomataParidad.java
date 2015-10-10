public class AutomataParidad  extends Automata{
	public AutomataParidad(){
		//super(estados,transiciones,finales);
		super(new Nodo(150,150,0));
		addNodo(new Nodo(150,300,1));
		addNodo(new Nodo(300,150,2));
		addNodo(new Nodo(300,300,3));
		addCondicion("0", 0,1,-50);
		addCondicion("1", 0,2,-50);
		addCondicion("0", 1,0,50);
		addCondicion("1", 2,0,50);
		addCondicion("1", 1,3,50);
		addCondicion("0", 2,3,50);
		addCondicion("0", 3,2,-50);
		addCondicion("1", 3,1,-50);
		addFinal(0);
	}
	
	public static void main(String[] args) {
		AutomataParidad ap = new AutomataParidad();
		for(char c:"001001".toCharArray()){
			ap.procesar(c, true);
		}
	}
}
