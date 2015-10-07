
public class RegexBin {
	public static void main(String args[]){
		
		int largo = (int)(Math.random()*1001);
		
		String alfabeto = (Math.random()>0.5)?"0":"1";
		
		Lenguaje l = new Lenguaje(alfabeto);
		
		FilePrinter p = new FilePrinter("regex.txt");
		
		p.println("\u03A3={"+alfabeto+"}");
		p.println();
		
		for(int i = 0; i<=largo;i++){
			p.print("\u03A3^"+i+"={");
			String [] palabras = l.getPalabras(i);
			for(int j = 0; j<palabras.length ; j++ ){
				if(palabras[j].length() ==0)
					p.print("\u03B5");
				else
					p.print(palabras[j]);
				if(j+1<palabras.length)
					p.print(",");
			}
			p.println("}");
			p.println();
		}
	}
}
