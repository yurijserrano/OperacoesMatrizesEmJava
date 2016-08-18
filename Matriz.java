/**
 * 
 */
package org.mack.an2.ativ1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Yuri Serrano
 *
 */

public class Matriz {
	
	private double[][] m;
	private int n;

	public Matriz() {
		n=0;
		m=null;
	}
	
	public Matriz(int n) {
		m = new double[n][n];	// nao inicializada
		this.n = n;
	}
	
	public Matriz(double[][] mm) {
		n = mm.length;
		m = new double[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				m[i][j]=mm[i][j];
			}
		}
	}
	
	public Matriz identidade(int n) {
		m = new double[n][n];
		this.n = n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				m[i][j]=(i!=j)?0.0:1.0;
			}
		}
		return this;
	}
	
	public String toString() {
		String res = "";
		for (int i = 0; i < n; i++) {
			res += "| ";
			for (int j = 0; j < n; j++) {
				res += m[i][j];
				if (j!=n-1) res += "\t";
			}
			res += " |%n";
		}
		return res;
	}
	
	public Matriz read(String dataFile) {
		try {
			DataInputStream in = new DataInputStream(new
			        BufferedInputStream(new FileInputStream(dataFile)));
			n = in.readInt();
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					m[i][j]=in.readDouble();
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	/**
	 * Cofator da matriz
	 * @param i	número da linha
	 * @param j número da coluna
	 * @return cofator
	 */
	public double coFator(int i, int j) {
		Matriz cm = new Matriz(n-1);
		for (int ii=0; ii < n-1; ii++)
			for (int jj=0; jj < n-1; jj++) {
				cm.m[i][j]=m[(ii<i)?ii:ii+1][(jj<j)?jj:jj+1];
			}
		return((((i+j)/2)==1)?-1:1)*det();
	}
	
	public double det() {
		if (n < 1) {
			// should throw an exception but by now i'm not
			System.out.println("Determinante com n<1?!");
			return 0.0;
		}
		if (n==1) return m[0][0];
		// should look for best line or column but no time
		double res=0.0;
		for (int i=0; i<n; i++) {
			res += m[i][0]*coFator(i, 0);
		}
		return res;
	}

	public Matriz decompLU(Matriz l) {
		l.identidade(n);
		for (int j = 0; j < n-1; j++) {
			for (int i = j+1; i < n; i++) {
				l.m[j][i]=m[i][j]/m[j][j];
				m[i][j]=0.0;
				for (int k = j+1; k < n; k++) {
					m[i][k] = m[i][k] - l.m[j][i]*m[j][k];
				}
			}
		}
		return this;
	}
}
