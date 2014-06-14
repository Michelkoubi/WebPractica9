package b;


import java.io.IOException;

public class Main 
{
	static String givenURL1="www.japan.com";
	static String givenURL2="www.spain.com";
    public static void main(String[] args) throws IOException 
    {
    	
    	MapReduceGraph mrg= new MapReduceGraph();
    	
    	//For calculating the average and the minimum distance
    	mrg.exampleGraph();
    	mrg.shortestPathAlgorithm(givenURL1,givenURL2);
    	mrg.calculateAverage();
    	
    	
    	//For reading and parsing the files
    	//mrg.createGraph();
    }
}
