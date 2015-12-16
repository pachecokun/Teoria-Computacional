
public class GramaticaPalBin {
	public String palindromo(int largo){
		String res = "P\n";
		String aux = "P";
		for(int  i = 0;i<largo/2;i++){
			char c = Math.random()>0.5?'0':'1';
			aux = aux.replace("P", c+"P"+c);
			res += aux+"\n";
		}
		if(largo%2==1){
			res += aux.replace("P",Math.random()>0.5?"0":"1");
		}
		else{
			res+=aux.replace("P", "");
		}
		return res;
	}
	public static void main(String[] args) {
		System.out.println(new GramaticaPalBin().palindromo(100));
	}
}
