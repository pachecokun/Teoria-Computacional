import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


public class Lenguaje {

	String alfabeto;
	PrintWriter pw;

	//Funcion consructora
	public Lenguaje(String alfabeto){

		this.alfabeto = "";
		//Quitamos repeticiones
		for(char c:alfabeto.toCharArray()){
			if(this.alfabeto.indexOf(c)==-1)
				this.alfabeto+=c;
		}

	}

	//Funcion que calcula el total de cadenas posibles
	public long total(int largo){
		return (long)Math.pow(alfabeto.length(), largo);
	}

	public long total(int min, int max){
		long  t = 0;
		for(int i = min;i<=max;i++)
			t += (long)Math.pow(alfabeto.length(), i);
		return t;
	}


	//Funcion que devuelve un arreglo de palabras de determinado largo
	public String[] getPalabras(int largo){
		int posiciones [] = new int[largo];
		String palabras[] = new String[(int)total(largo)];
		for(int j = 0;j<total(largo);j++){
			palabras[j] = "";
			for(int n = 0;n<largo;n++){
				if(j%Math.pow(alfabeto.length(), n) == 0){
					posiciones[n] = (posiciones[n]+1)%alfabeto.length();
				}
				palabras[j] += alfabeto.charAt(posiciones[n]);
			}
		}
		return palabras;
	}
	

	//Funcion que crea archivo donde guardar
	public void crearArchivo(String s){
		try{
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(s),StandardCharsets.UTF_8));
		}catch(Exception e){}
	}

	//Imprime en archivo y en linea de comandos
	public void printfile(String s){
		System.out.print(s);
		pw.print(s);
		pw.flush();
	}


	//Imprime en archivo y en linea de comandos
	public void printlnfile(String s){
		System.out.println(s);
		pw.println(s);
		pw.flush();
	}

	public static void main(String args[]){
		try {
			//Solicitaos el alfabeto
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Ingrese un alfabeto: ");
			String input = br.readLine();

			Lenguaje l = new Lenguaje(input);
			l.crearArchivo("lenguaje.txt");

			//Solicitamos largo de cadena
			System.out.print("Ingrese el largo maximo de cadena (enter para aleatorio): ");

			//Generamos largo aleatorio
			int largo = (int)(Math.random()*1000);
			try{
				//Solicitamos largo al usuario, 
				//si pulsa enter, se conserva el aleatorio
				largo = Integer.parseInt(br.readLine());
			}
			catch(Exception e){
				l.printlnfile("Largo aleatorio: "+largo);
				l.printlnfile("");
			}

			//Imprimimos alfabeto
			l.printfile("\u03A3 = {");
			for(int i = 0;i<l.alfabeto.length();i++){
				l.printfile(l.alfabeto.charAt(i)+"");
				if(i<l.alfabeto.length()-1)
					l.printfile(",");
			}
			l.printlnfile("}");
			l.printlnfile("");

			l.printlnfile("Total de cadenas: "+l.total(0,largo));
			l.printlnfile("");

			//Imprimimos palabras
			for(int i = 0; i<=largo;i++){
				l.printfile("\u03A3^"+i+"={");
				String [] palabras = l.getPalabras(i);
				for(int j = 0; j<palabras.length ; j++ ){
					if(palabras[j].length() ==0)
						l.printfile("\u03B5");
					else
						l.printfile(palabras[j]);
					if(j+1<palabras.length)
						l.printfile(",");
				}
				l.printlnfile("}");
				l.printlnfile("");
			}

		} catch (Exception e) {}
	}
}