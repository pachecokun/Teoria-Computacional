import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AutomataND extends Automata{

	Nodo [] estados;
	
	Executor pool = Executors.newCachedThreadPool();
	
	Thread threadpool;
	
	public AutomataND(Nodo q0) {
		super(q0);
	}
	
	public void coincidencia(Condicion c){
		
	}
	
	public boolean isFinalizado(){
		for(Nodo n:estados){
			if(n.isFin()){
				return true;
			}
		}
		return false;
	}

	public void procesarCaracter(char c){
		ArrayList<Nodo> nodos = new ArrayList<>();
		palabra+=c;
		try{
			for(Condicion con:condiciones){
				for(Nodo e:estados){
					Nodo n = con.procesar(e, c);
					if(n!=null){
						nodos.add(n);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		estados = nodos.toArray(new Nodo[0]);
	}
	
	public static class ThreadAutomata{
		public Nodo [] estados;
	}
	
}
