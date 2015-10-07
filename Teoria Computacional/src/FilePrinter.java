import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FilePrinter {
	PrintWriter pw;
	public FilePrinter(String file){
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void print(String s){
		System.out.print(s);
		pw.print(s);
		pw.flush();
	}
	public void println(String s){
		System.out.println(s);
		pw.println(s);
		pw.flush();
	}
	public void println(){
		System.out.println();
		pw.println("");
		pw.flush();
	}
}
