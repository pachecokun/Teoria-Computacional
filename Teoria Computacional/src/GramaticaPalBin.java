import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GramaticaPalBin {
	
	FilePrinter p; 
	
	
	
	public GramaticaPalBin(FilePrinter p) {
		this.p = p;
	}

	public String palindromo(int largo){
		String res = "P\n";
		String aux = "P";
		for(int  i = 0;i<largo/2;i++){
			char c = Math.random()>0.5?'0':'1';
			aux = aux.replace("P", c+"P"+c);
			p.println(aux);
		}
		if(largo%2==1){
			res = aux.replace("P",Math.random()>0.5?"0":"1");
		}
		else{
			res =aux.replace("P", "");
		}
		p.println(res);
		return res;
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

			int largo = -1;
			
			if(entrada == 1){
				while(largo == -1){
					try{
						System.out.print("Ingrese el largo del palíndromo: ");
						largo = Integer.parseInt(br.readLine());
					}catch(Exception e){}
				}
			}
			else{
				largo = (int)(Math.random()*101);
			}

			System.out.println(new GramaticaPalBin(new FilePrinter("palindromo.txt")).palindromo(largo));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
