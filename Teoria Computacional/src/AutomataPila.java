import java.util.ArrayList;
import java.util.List;

public class AutomataPila {

	class Transicion{
		char entrada;
		char estado;
		char top;
		
		char[] ntop;
		char nestado;
		
		public Transicion(char entrada, char estado, char top, char[] ntop, char nestado) {
			this.entrada = entrada;
			this.estado = estado;
			this.top = top;
			this.ntop = ntop;
			this.nestado = nestado;
		}
		
		
	}

	Pila<Character> p = new Pila<>();
	char q;
	char q0;
	char z0;
	List<Transicion> transiciones = new ArrayList<>();
	List<Character> finales = new ArrayList<>();
	int estado = 0;
	
	public void delta(char c){
		boolean encontrado = false;
		for(Transicion t:transiciones){
			if(q==t.estado&&c==t.entrada&&p.top()==t.top){
				q = t.nestado;
				p.pop();
				for(char aux:t.ntop){
					if(aux!='-')
						p.push(aux);
				}
				encontrado = true;
				break;
			}
		}
		if(!encontrado){
			estado = -1;
		}
		else if(finales.contains(q)){
			estado = 1;
		}
		else{
			estado = 0; 
		}
	}
	
	public void addTransicion(Transicion t){
		transiciones.add(t);
	}
	
	public void addFinal(char c){
		finales.add(c);
	}

	public AutomataPila(char q0,char z0) {
		this.q0 = q0;
		this.z0 = z0;
		q=q0;
		p.push(z0);
	}
	
}
