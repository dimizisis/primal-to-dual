public class PrimalProblem {
	
	private String pp;
	private int m;
	private int n;
	private int[][] A;
	private int[] c;
	private int[] b;
	private int minMax;
	private int[] eqin;
	
	public PrimalProblem(String pp) {
		this.pp = pp;
		this.m = this.returnM();
		this.n = this.returnN();
		this.A = this.returnA();
		this.c = this.returnC();
		this.b = this.returnB();
		this.minMax = this.returnProblemType();
		this.eqin = this.returnEqin();
	}

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}

	public int[][] getA() {
		return A;
	}

	public int[] getC() {
		return c;
	}

	public int[] getB() {
		return b;
	}

	public int getMinMax() {
		return minMax;
	}

	public int[] getEqin() {
		return eqin;
	}

	/* Private Methods */ 
	
	private int returnM() {
		
		/* This method returns the number of problem's lines */

		String[] lines = pp.split("\n");
		
		// b vector is always on line 5
		String[] bLine = lines[5].split("\t");
		
		int m=0;
		for(int j=0;j<bLine.length;j++) {
			if (bLine[j].matches(".*\\d+.*"))
				m++;	
		}
		
		return m;
	}
	
	private int returnN() {
		
		/* This method returns the number of variables used */
		
		// Getting lines of pp in string array
		String[] lines = pp.split("\n"); 
		
		// Variables are always on line 2, we separate strings every time we find tab ("\t")
		String[] varLine = lines[2].split("\t");
		
		int n=0;
		// For every variable in variables' line
		for(int j=0;j<varLine.length;j++) {
			// If the string in j position is type of: apphabetic+numeric (i.e. x1), n=n+1
			if (varLine[j].matches("[a-zA-z]?\\d*\\.?\\d+"))
				n++;	
		}  
		
		return n;
	}
	
	private int returnProblemType() {
		
		/* This method returns type of linear problem (-1 for min, 1 for max, 0 for error) */
		
		int type=0;
		
		// First three letters is the type
		for (int i=0;i<pp.length();i++) {
			if (pp.charAt(i)=='(') {
				try {
					if(pp.charAt(i+1) == '-' && pp.charAt(i+2) == '1') {
						type = -1;
						break;
					}
					else if (pp.charAt(i+1) == '1') {
						type = 1;
						break;
					}
				}catch (Exception e) {
					// Do nothing
				}
			}
		}
		
		return type; // 0 for error
 
	}

	private int[] returnB() {
	
	/* This method returns vector b (mx1 vector) */
	
	// The vector, which the method returns
	int b[] = new int[this.getM()];
	
	String[] lines = pp.split("\n");
	
	String[] bLine = lines[5].split("\t");
	
	// k: index of b vector
	int k=0;
	for(int j=0;j<bLine.length;j++) {
		// If bLine in j position is all numeric, we found a b element
		if (bLine[j].matches(".*\\d+.*")) {
			b[k] = Integer.parseInt(bLine[j]);
			k++;
		}	
	}
	
	return b;
}

	private int[] returnC () {
		
		/* This method returns a vector c, which includes the coefficients of variables in function. */
	
	int[] c = new int[this.getN()];
	
	String[] lines = pp.split("\n");
	
	String[] cLine = lines[1].split("\t");
	
	int k=0;
	for(int j=0;j<cLine.length;j++) {
		if (cLine[j].matches(".*\\d+.*")) {
			c[k] = Integer.parseInt(cLine[j]);
			k++;
		}
	}
	
	return c;
}

	private int[][] returnA() {
	
	/* This method returns a (m-1)xn array, which includes the coefficients
	 * for limitations' variables. 
	 */
	
		String[] lines = pp.split("\n");
	
		int[][] A = new int[this.getM()][this.getN()]; // Automatically initialized with zeros
		
		// A is always on line 3
		String[] ALine = lines[3].replaceAll("s.t.", "").split("\t");
		
		int x=0;
		int y=0;
		for(int j=0;j<ALine.length;j++) {
			if (ALine[j].matches(".*\\d+.*")) {
					
				A[x][y] = Integer.parseInt(ALine[j]);
				y++;	
				if (y == getN()) {
					x++;
					y=0;
				}
			}
			
		}
		
	return A;
}

	private int[] returnEqin() {
	
	/* This method returns a m-1 array, which includes math symbols coded
	 * (-1 for <, 0 for =, 1 for >) 
	 */
	
	int eqin[] = new int[getM()];
	
	String[] lines = pp.split("\n");
	
	String[] eqinLine = lines[4].split("\t");
	
	int k=0;
	for(int j=0;j<eqinLine.length;j++) {
		if (eqinLine[j].matches(".*\\d+.*")) {
			eqin[k] = Integer.parseInt(eqinLine[j]);
			k++;
		}
		
	}
	
	return eqin;
}

}
