package b;

public class Distances 
{
	String node;
	int distanceFromOrigin;
	Distances(String node, int distanceFromOrigin)
	{
		this.node=node;
		this.distanceFromOrigin=distanceFromOrigin;
	}
	Distances(String node)
	{
		this.node=node;
	}
	public String getNode()
	{
		return node;
	}

	public int getDistanceOrigin()
	{
		return distanceFromOrigin;
	}

	public void setNode(String node)
	{
		this.node=node;
	}

	public void setDistanceOrigin(int distanceFromOrigin)
	{
		this.distanceFromOrigin=distanceFromOrigin;
	}
	public boolean equals(Object o)
	{
		if(o instanceof Distances)
		{
			Distances p = (Distances) o;
			String node2 = p.getNode();

			if(node==node2)
				return true;
			else
				return false;
		}
		else
			return false;
	}
}
