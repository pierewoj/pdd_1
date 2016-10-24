class Pair implements Comparable<Pair>
{
	Integer a;
	Integer b;
	
	public Pair(int a, int b)
	{
		this.a = a;
		this.b = b;
	}
			
	public int compareTo(Pair p) {
		int first = a.compareTo(p.a);
		if(first==0)
			return b.compareTo(p.b);
		return first;
	}		
}