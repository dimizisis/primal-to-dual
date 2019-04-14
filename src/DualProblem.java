import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;

public class DualProblem {
	
	private PrimalProblem pp;
	private int eqin[];
	private int[] transposedB;
	private int n;
	private int m;
	private int minMax;
	private int[][] transposedA;
	private int[] c;
	private String[] dualVariables;
	
	
	public DualProblem(PrimalProblem pp) {
		
		this.pp = pp;
		this.n = this.returnN();
		this.m = this.returnM();
		this.eqin = this.returnEqin();
		this.minMax = this.returnMinMax();
		this.c = this.returnC();
		this.transposedB = this.returnBTransposed();
		this.transposedA = this.returnATransposed();
		this.dualVariables = this.returnDualVariables();
	}
	
	/* Public Methods */
	
	public int[] getEqin() {
		return eqin;
	}

	public int getMinMax() {
		return minMax;
	}

	public int[][] getTransposedA() {
		return transposedA;
	}

	public String[] getDualVariables() {
		return dualVariables;
	}
	
	public int[] getTransposedB() {
		return transposedB;
	}
	
	public int getN() {
		return n;
	}

	public int getM() {
		return m;
	}
	
	public int[] getC() {
		return c;
	}
	
	public void writeFile(String output) { 
		
		/* This method writes dual problem to text file */
		
		int[][] At = getTransposedA();
		String[] bw = new String[getM()];
		String[] dualVariables = getDualVariables();
		int[] eqin = getEqin();
		int type = getMinMax();
		int[] c = getC();
		int[] b = getTransposedB();
		
		for (int i=0;i<getM();i++)
			bw[i] = "";
		
		
		File outFile = new File(output+"\\dual_problem.txt");
		try {
	    FileWriter writer = new FileWriter(outFile, true);
	    
		if (type == 1)
			writer.write("max \t");
		else if (type == -1)
			writer.write("min \t");
	
		// Creating dual function
		for (int i=0;i<getM();i++) {
			if (i>0) {
				if (b[i] != 0 && b[i] != 1 && b[i] != -1) {
					if (b[i] < 0)
						bw[i] += "-\t" + String.valueOf(Math.abs(b[i])) + dualVariables[i] + "\t";
					else
						bw[i] += "+\t" + String.valueOf(Math.abs(b[i])) + dualVariables[i] + "\t";
				}
				else if (b[i] == 0)
					bw[i] += "";
				else if (b[i] == 1)
					bw[i] += "+\t" + dualVariables[i] + "\t";
				else if (b[i] == -1)
					bw[i] += "-\t" + dualVariables[i] + "\t";
			}
			
			else {
				
				if (b[i] != 0 && b[i] != 1 && b[i] != -1) {
					if (b[i] < 0)
						bw[i] += "-" + String.valueOf(Math.abs(b[i])) + dualVariables[i] + "\t";
					else
						bw[i] += String.valueOf(Math.abs(b[i])) + dualVariables[i] + "\t";	
				}
				else if (b[i] == 0)
					bw[i] += "";
				else if (b[i] == 1)
					bw[i] += dualVariables[i] + "\t";
				else if (b[i] == -1)
					bw[i] += "-" + dualVariables[i] + "\t";
			}
		}
		
		// Writting to file dual function
		for (int i=0;i<getM();i++)
			writer.write(bw[i]+" ");
		
		// Initialization of dual limitations
		String[][] aw = new String[getN()][getM()];
		for (int i=0;i<getN();i++) {
			for(int j=0;j<getM();j++) {
				aw[i][j] = "";
			}
		}
		
		// Creating the limitations
		for (int i=0;i<getN();i++) {
			for(int j=0;j<getM();j++) {
				if (j>0) {
					if (At[i][j] != 0 && At[i][j] != 1 && At[i][j] != -1) {
						if (At[i][j] < 0)
							aw[i][j] += "-\t" + String.valueOf(Math.abs(At[i][j])) + dualVariables[j] + "\t";
						else
							aw[i][j] += "+\t" + String.valueOf(Math.abs(At[i][j])) + dualVariables[j] + "\t";
					}
					else if (At[i][j] == 0)
						aw[i][j] += "";
					else if (At[i][j] == 1)
						aw[i][j] += "+\t" + dualVariables[j] + "\t";
					else if (At[i][j] == -1)
						aw[i][j] += "-\t" + dualVariables[j] + "\t";
				}
				
				else if (j==0){
					
					if (At[i][j] != 0 && At[i][j] != 1 && At[i][j] != -1) {
						if (At[i][j] < 0)
							aw[i][j] += "-" + String.valueOf(Math.abs(At[i][j])) + dualVariables[j] + "\t";
						else
							aw[i][j] += String.valueOf(Math.abs(At[i][j])) + dualVariables[j] + "\t";	
					}
					else if (At[i][j] == 0)
						aw[i][j] += "";
					else if (At[i][j] == 1)
						aw[i][j] += dualVariables[j] + "\t";
					else if (At[i][j] == -1)
						aw[i][j] += "-" + dualVariables[j] + "\t";
				}
				
				if (j==getM()-1) {
					
					if (eqin[i] == 1)
						aw[i][j] += ">= \t" + c[i];
					else if (eqin[i] == 0)
						aw[i][j] += "= \t" + c[i];
					else
						aw[i][j] += "<= \t" + c[i];
					
				}
			}
		}
		
		// Writing to file limitations
		writer.write(System.lineSeparator());
		
		writer.write("s.t.\t");
		
		for (int i=0;i<getN();i++) {
			for(int j=0;j<getM();j++) {
				writer.write(aw[i][j]);
			}
			
			writer.write(System.lineSeparator());
			writer.write("\t");
		} 
		
			writer.write(System.lineSeparator());
			writer.write("    \t");
			
			writer.flush();
		    writer.close();
		    
		    int dialogResult = JOptionPane.showConfirmDialog(null, "Conversion complete! Open dual_problem.txt?", "Success!", JOptionPane.YES_NO_OPTION);
			
		    if(dialogResult == JOptionPane.YES_OPTION)
		    	Desktop.getDesktop().edit(outFile); 		
		
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error in tracing output folder! Check path & try again.", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/* Private methods */
	
	private int returnM() {
		
		/* This method return m from lp. */
		
		return pp.getM();
	}
	
	private int returnN() {
		
		/* This method return n from lp. */
		
		return pp.getN();
	}

	private int returnMinMax() {
		
		/* This method returns problem type of dual problem. */
		
		if (pp.getMinMax() == -1) {
			return 1;
		}
		else
			return -1;
	
	}
	
	private int[] returnBTransposed() {
		
		/* This method return b vector from lp. */
		
		return pp.getB();
	}
	
	private int[] returnC() {
		
		/* This method return c vector from lp. */
		
		return pp.getC();
	}
	
	private int[] returnEqin() {
		
		/* This method returns the eqin vector for dual problem. */
		
		int[] dualEqin = new int[getN()];
		
		for(int i=0;i<getN();i++)
			dualEqin[i] = -1;
		
		return dualEqin;
		
	}
	
	private int[][] returnATransposed() {
		
		/* This method returns matrix A transposed */
		
		return transposeMatrix(pp.getA());
		
	}
	
	private String[] returnDualVariables() {
		
		/* This method returns a String array, which contains the dual variables (w1, w2, ..., wm) */
		
		int m = pp.getM();
		String[] dualVariables = new String[m];
		
		// w1, w2, ..., Wm
		for(int i=0;i<m;i++)
			dualVariables[i] = "w"+String.valueOf(i+1);
		
		return dualVariables;
	}
	
	private static int[][] transposeMatrix(int[][] matrix)
	{
		
		/* This method transposes a given matrix */
		
	    int m = matrix.length;
	    int n = matrix[0].length;

	    int[][] transposedMatrix = new int[n][m];

	    for(int i=0;i<n;i++)
	    {
	        for(int j=0;j<m;j++)
	        {
	            transposedMatrix[i][j] = matrix[j][i];
	        }
	    }

	    return transposedMatrix;
	}
	
}