
public class Lcd {

	static String[][] templateFor = { 
		{ " S ", " - " }, { "|S|", "|S ", " S|" } 
	};

	static int[][] digitMapFor = {
		{ 1, 0, 0, 0, 1 },{ 0, 2, 0, 2, 0 },{ 1, 2, 1, 1, 1 },{ 1, 2, 1, 2, 1 },{ 0, 0, 1, 2, 0 },
		{ 1, 1, 1, 2, 1 },{ 1, 1, 1, 0, 1 },{ 1, 2, 0, 2, 0 },{ 1, 0, 1, 0, 1 },{ 1, 0, 1, 2, 1 }
	};
	
	static int factor, step, line=0,number=0;
	static String digits, template, moo, out="";
	
	public static void main(String[] args) { 
		
		if (args.length==0) return;
		
		if (args[0].equals("-s")) {
			factor = toInt(args[1]);
			digits = args[2];
		} else {
			factor = 2;
			digits = args[1];
		}
		
		for (int height : array(1, factor, 1, factor, 1)) {
			step = 0;
			while (step < height) {
				for (String num : digits.split("")) {
					number = num.length() == 0 ? -1 : toInt(num);
					if (number >= 0) {
						template  = templateFor[line % 2][digitMapFor[number][line]];
						out += replaceAll(factor, replaceAll(factor, template, "-", "-") + " ", "S", " ");
					}
				}
				step++;
				out += "\n";
			}
			line++;
		}
		System.out.println(out);
	}
	
	
	static String replaceAll(int duptime, String... s) { return s[0].replaceAll(s[1], dup(duptime, s[2])); }

	static String dup(int duptime, String s) { for (moo = ""; duptime > 0; duptime--) moo += s; return moo; }

	static int[] array(int... i) { return i; }

	static int toInt(String s) { return Integer.parseInt(s); }
	
}
