import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Parentesis {
	
	
	public static void verificarparentesis(String entrada,FilePrinter p){
		
		String salida = "B";
		String[][]producciones = {{"B","(RB",""},{"R",")","(RR"}};
		
		p.println("Largo de entrada: "+entrada.length());
		
		p.println("Entrada: "+entrada);
		while(true){
			p.println(salida);
			
			char esperado;
			String siguiente;
			
			if(salida.contains("R")||salida.contains("B")){
				if(!salida.contains("R")||salida.indexOf('R')>salida.indexOf('B')){
					esperado = salida.charAt(salida.indexOf('B'));
				}
				else{
					esperado = salida.charAt(salida.indexOf('R'));
				}
			}
			else{
				p.println("--Cadena correcta");
				return;
			}
			
			if(entrada.isEmpty()){
				siguiente = "";
			}
			else{
				siguiente = entrada.substring(0,1);
				entrada = entrada.substring(1, entrada.length());
			}
			
			
			boolean encontrado = false;
			for(String[] produccion:producciones){
				for(int i = 1;i<produccion.length;i++){
					if(
						esperado==produccion[0].charAt(0)&&
						((siguiente.isEmpty()&&produccion[i].isEmpty())||!siguiente.isEmpty()&&produccion[i].startsWith(siguiente))
					){
						salida = salida.replaceFirst(esperado+"",produccion[i]);
						encontrado = true;
						break;
					}
				}
				if(encontrado){
					break;
				}
			}
			if(!encontrado){
				p.print("Cadena incorrecta");
				return;
			}
			//try {Thread.sleep(100);} catch (InterruptedException e) {}
		}
	}
		
	
	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("\nMétodos de entrada:");
			System.out.println("\n1.Consola");
			System.out.println("2.Aleatorio\n");
			
			int entrada = 0;
			
			while(entrada<1||entrada>2){
				try {
					//Imprime ingrese la opcion deseada
					System.out.print("Ingrese la opcion deseada: ");
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
					cadena += Math.random()>0.5?'(':')';
				}
				System.out.println("Cadena generada: "+cadena);
			}
			
			verificarparentesis(cadena,new FilePrinter("parentesis.txt"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
