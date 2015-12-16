
public class Lel {
	public static byte f1(byte a, byte b, byte c, byte d, byte e){
		return (byte) (~(a^c^e)|(a&b&~c&e)|(~a&~c&d&~e));
	}
	public static byte f2(byte a, byte b, byte c, byte d, byte e){
		return (byte) ((a|c|~e)&(~a|~c|e)&(~a|~b|c|e)&(a|~c|e)&(~a|b|c|e));
	}
	public static void main(String[] args) {
		for(byte a = 0;a<2;a++){
			for(byte b = 0;b<2;b++){
				for(byte c = 0;c<2;c++){
					for(byte d = 0;d<2;d++){
						for(byte e= 0;e<2;e++){
							System.out.println(f1(a, b, c, d, e)+" "+f2(a, b, c, d, e));
						}	
					}	
				}	
			}	
		}
	}
}
