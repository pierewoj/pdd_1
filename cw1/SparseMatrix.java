import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class SparseMatrix {
	private ArrayList<Pair> _pairs;
	public int maxCol = -1;
	
	public SparseMatrix()
	{
		_pairs = new ArrayList<Pair>();
	}
	
	public void add(int row, int col)
	{
		Pair pair = new Pair(row, col);
		_pairs.add(pair);		
		if(col > maxCol)
			maxCol = col;
	}
	
	public int size()
	{
		return _pairs.size();
	}
	
	public void sortAndPrint(OutputStream stream) throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter(stream);
		
		Collections.sort(_pairs);
		int beginLineIndex = _pairs.get(0).a;
		int lineIndex = beginLineIndex;
		
		writer.println(maxCol+1);
		writer.print(lineIndex + " ");
		
		for(Pair pair : _pairs)
		{
			while(lineIndex < pair.a){		
				lineIndex++;
				writer.print('\n');
				writer.print(lineIndex);
				writer.print(' ');				
			}				
			writer.print(pair.b);
			writer.print(' ');
		}
		writer.close();
	}
	
	
}
