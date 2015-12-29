import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AutomataPilaBin extends AutomataPila {


	public AutomataPilaBin() {
		super('q','z');
		addTransicion(new Transicion('0', 'q', 'z', new char[]{'z','x'},'q'));
		addTransicion(new Transicion('0', 'q', 'x', new char[]{'x','x'},'q'));
		addTransicion(new Transicion('1', 'q', 'x', new char[]{'-'},'p'));
		addTransicion(new Transicion('1', 'p', 'x', new char[]{'-'},'p'));
		addTransicion(new Transicion('-', 'p', 'z', new char[]{'z'},'f'));
		addFinal('f');
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
				int largo = (int)(Math.random()*10001);
				for(int i = 0;i<largo;i++){
					cadena += Math.random()>0.5?'0':'1';
				}
				System.out.println("Cadena generada: "+cadena);
			}

			AutomataPilaBin a = new AutomataPilaBin();
			FilePrinter f = new FilePrinter("binariopila.txt");
			f.println("Entrada: "+cadena);

			for(char c:cadena.toCharArray()){
				f.println("Estado: "+a.q+", tope:"+a.p.top()+", entrada: "+c);
				a.delta(c);
				if(a.estado==-1){
					f.println("Cadena inválida");
					return;
				}
				else if(a.estado == 1){
					f.println("Cadena válida");
					return;
				}
			}
			f.println("Estado: "+a.q+", tope:"+a.p.top()+", entrada: -");
			a.delta('-');
			if(a.estado == 1){
				f.println("Cadena válida");
				return;
			}
			f.println("Cadena inválida");
			return;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
