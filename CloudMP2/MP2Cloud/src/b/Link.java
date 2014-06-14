package b;

public class Link 
{
	String row;
	int count;
	Link(String row, int count)
	{
		this.row=row;
		this.count=count;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public String getRow()
	{
		return row;
	}
}
