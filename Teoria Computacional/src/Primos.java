import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Primos {
	
	public Integer[] getPrimos(int max){
		ArrayList<Integer> numeros= new ArrayList<>();
		if(max>=2)
			numeros.add(2);
		for(int i = 3;i<=max;i+=2){
			boolean primo = true;
			for(int j:numeros){
				if(i%j==0){
					primo = false;
					break;
				}
			}
			if(primo)
				numeros.add(i);
		}
		return numeros.toArray(new Integer[1]);
	}
	
	public void graficar(int max){
		
		Integer[] primos = getPrimos(max); 
		
		int ocurrencias[] = new int[primos.length];
		
		for(int i = 0;i<primos.length;i++){
			for(char c:toBin(primos[i]).toCharArray()){
				if(c=='1')
					ocurrencias[i]++;
			}
		}
		
		JFrame f = new JFrame("Cantidad de 1s");
		f.setSize(300, 300);
		f.setResizable(false);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel p = new JPanel(){
			public void paint(Graphics g){
				super.paint(g);
				float espacio = 280f/(float)(primos.length);
				g.drawLine(10, 240, (int)(10+primos.length*espacio), 240);
				g.drawLine(10, 240, 10, 10);
				int max = 0;
				for(int i = 0;i<primos.length-1;i++){
					g.drawLine((int)(i*espacio+10), 240-ocurrencias[i]*10, (int)((i+1)*espacio+10), 240-ocurrencias[i+1]*10);
					max = (max<ocurrencias[i])?ocurrencias[i]:max;
				}
				g.drawString(""+max, 1, 240-10*max);
				g.drawString("0", 1, 245);
				g.drawString(""+primos[primos.length-1], 270, 255);
			}
		};
		f.setLocationRelativeTo(null);
		p.setBounds(0, 0, 300, 300);
		f.getContentPane().add(p);
		f.setVisible(true);
		
	}
	
	public String toBin(int n){
		return Integer.toBinaryString(n);
	}
	
	public static void main(String args[]){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		FilePrinter p = new FilePrinter("primos.txt");
		
		int max = (int)(Math.random()*1000+1);
		
		System.out.print("Ingrese el maximo a calcular [Enterpara valor aleatorio]: ");
		
		try {
			max = Integer.parseInt(br.readLine());
			if(max>1000){
				System.out.println("Número demasiado grande [Máximo = 1000].");
				return;
			}
		} catch (Exception e) {}
		p.println("Número máximo: "+max);

		p.print("Cadenas binarias cuyo valor es primo: {");
		Primos prim = new Primos();
		Integer[] primos = prim.getPrimos(max);
		for(int i = 0;i<primos.length;i++){
			
			p.print(prim.toBin(primos[i]));
			if(i<primos.length-1)
				p.print(",");
		}
		p.println("}");
		
		p.print("Números primos: {");
		for(int i = 0;i<primos.length;i++){
			
			p.print(primos[i]+"");
			if(i<primos.length-1)
				p.print(",");
		}
		p.println("}");

		prim.graficar(max);
		
	}
}
