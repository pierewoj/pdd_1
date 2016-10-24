import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;


public class LocalSensitiveHasher {
	
	private int[][] M;
	
	int genHash(int column, int from, int to, int maxval)
	{
		long res = 31;
		for(int i=from; i<to; i++)
		{
			res = res*314159 + M[i][column];
			res %= maxval;
		}
		return (int) res;
	}
	
	public void Execute(InputStream input, OutputStream output) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(input);
		M = (int[][]) ois.readObject();
		
		TreeSet<Pair> candidatePairs = new TreeSet<Pair>();
		
		for(int band=0; band<5;band++)
		{
			int bandBegin=20 * band;
			int bandEnd = bandBegin + 20;
			
			int[] buckets = new int[1000000];
			for(int i=0;i<buckets.length;i++)
				buckets[i] = -1;
			
			for(int column=0;column<M[0].length;column++)
			{
				int hash = genHash(column, bandBegin, bandEnd, buckets.length);
				if(buckets[hash] == -1)
					buckets[hash] = column;
				else
					candidatePairs.add(new Pair(buckets[hash], column));
			}
			
		}
		
		PrintWriter writer = new PrintWriter(output);
		for(Pair p : candidatePairs)
		{
			writer.println("<"+p.a+","+p.b+">");
		}
		writer.close();
	}	
}
