import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


public class ShingleProcessor {

	public TreeSet<String> Set;
	
	private HashShingleBuilder _builder;
	
	private int _shingleLength;
	
	public ShingleProcessor(HashShingleBuilder builder, int shingleLength)
	{
		_builder = builder;
		_shingleLength = shingleLength;
		Set = new TreeSet<String>();
	}
		
	public void Process(String s)
	{
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			_builder.addChar(ch);
			String shingle = _builder.getShingle();
			if(shingle != null)
				Set.add(shingle);	
		}		
		Set.add(_builder.forceGetShingle());
		_builder.Clean();
	}
	
	public int GetNumShingles()
	{
		return Set.size();
	}

}
