import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Parentesis {
	
	String entrada = "";
	String salida = "";
	String esperados = "P";
	int estado = 0;
	
	public Parentesis(String entrada){
		while(entrada.length()>0){
			char c = entrada.charAt(0);
			entrada = entrada.substring(1, entrada.length()+1);
			
			if(salida.length()==0){
				if(c=='('){
					salida("P");
				}
				else{
					System.out.println("Cadena inválida :c");
				}
			}
			else{
				if
			}
		}
	}
	
	public void salida(String s){
		salida += s;
		System.out.println(salida);
	}
	
	String[] evaluar(){
		
	}
	
	
	
	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("\nMétodos de entrada:");
			System.out.println("\n1.Consola");
			System.out.println("2.Aleatorio\n");
			
			int entrada = 0;
			
			while(entrada<1||entrada>2){
				System.out.print("Ingrese la opcion: ");
				try {
					entrada = (Integer.parseInt(br.readLine()));
				} catch (Exception e) {} 
			}
			
			String cadena = "";
			
			if(entrada == 1){
				cadena = br.readLine();
			}
			else{
				int largo = (int)(Math.random()*10001);
				for(int i = 0;i<largo;i++){
					cadena += Math.random()>0.5?'(':')';
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
