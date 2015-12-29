
public class Pila<Tipo> {

	private class Nodo{
		Nodo n;
		Tipo val;
		public Nodo(Nodo n, Tipo val) {
			super();
			this.n = n;
			this.val = val;
		}
		
	}
	
	Nodo n = null;
	
	public boolean esVacia(){
		return n == null;
	}
	
	public Tipo top(){
		return n.val;
	}
	
	public void push(Tipo val){
		n = new Nodo(n, val);
	}
	
	public Tipo pop(){
		Tipo r = top();
		n = n.n;
		return r;
	}
}
