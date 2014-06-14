package b;

public class Edge 
{
	String next,previous;
	int distance;
	Edge(String next, String previous, int distance)
	{
		this.next=next;
		this.previous=previous;
		this.distance=distance;
	}
	public String getNext()
	{
		return next;
	}
	
	public String getPrevious()
	{
		return previous;
	}
	public int getDistance()
	{
		return distance;
	}
}
