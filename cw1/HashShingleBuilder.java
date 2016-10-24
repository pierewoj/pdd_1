import java.util.Objects;


public class HashShingleBuilder{

	private int _size;
	private StringBuilder _builder;
	private long _maxHash;
	private int _hashLen;
	
	public void Clean()
	{
		_builder = new StringBuilder();
	}
	
	public HashShingleBuilder(int size, int hashLen)
	{
		_size = size;
		_builder = new StringBuilder();
		_hashLen = hashLen;
		_maxHash = 1;
		for(int i=0;i<8*hashLen;i++)
		{
			_maxHash *= 2;
		}
			
	}
	
	public void addChar(Character c)
	{			
		if(_builder.length() == _size)
			_builder.deleteCharAt(0);
		_builder.append(c);
	
	}
	
	public String getShingle()
	{
		if(_builder.length() == _size)
		{
			return forceGetShingle();		
		}
		return null;		
	}

	public String forceGetShingle() {
		if(_hashLen <= 0)
			return _builder.toString();
		
		long hash = Math.floorMod(_builder.toString().hashCode(), _maxHash);
		return Objects.toString(hash);
	}
}
