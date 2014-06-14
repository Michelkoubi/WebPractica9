package b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class MapReduceGraph
{
	int count, countOfLinks, countValues, countAppear, countReadFile, changePath;
	float average;
	String parseFile, relativePath, url1;
	String searchString= "Honduras";
	String urlString= "url";//"{{cite web|url"
	String globalPath="/Users/miguel/Documents/workspace/files_directory";
	boolean notStop=true;
	BufferedReader reader;
	ArrayList<String> files=new ArrayList<String>(); 
	ArrayList<String> filesRelativePaths=new ArrayList<String>();
	ArrayList<Link> links= new ArrayList<Link>();
	ArrayList<String> urlsSpain= new ArrayList<String>();
	ArrayList<String> urlsUsa= new ArrayList<String>();
	ArrayList<String> urlsItaly= new ArrayList<String>();
	ArrayList<String> urlsJapan= new ArrayList<String>();
	ArrayList<String> urlsGermany= new ArrayList<String>();
	ArrayList<String> urlsFrance= new ArrayList<String>();
	//int[][] graph = new int[rows][columns];
	HashMap<String, ArrayList<String>> UrlHashMappings = new HashMap<String, ArrayList<String>>();
	ArrayList<Edge> edges=new ArrayList<Edge>();
	ArrayList<Distances> distances=new ArrayList<Distances>();
	ArrayList<Distances> distancesBefore=new ArrayList<Distances>();
	ArrayList<String> valuesInit =new ArrayList<String>();
	ArrayList<String> valuesIteration =new ArrayList<String>();
	ArrayList<String> newValues =new ArrayList<String>();
	ArrayList<String> tempNewValues =new ArrayList<String>();
	ArrayList<String> urlsInFiles =new ArrayList<String>();
	
	
	public void createGraph()
	{
		//Script to look for the urls
		final File folder = new File(globalPath);
		listFilesForFolder(folder);
		openReadFiles();
		for(int i=0;i<filesRelativePaths.size();i++)
		{
			System.out.println(filesRelativePaths.get(i)+" ");
		}
		System.out.println("Number of files read: "+ countReadFile);
		System.out.println("Number of times that "+searchString+" appears :"+ countAppear);
	}
	
	//As input it receives the graph with the links , int[][] graph
	public float calculateAverage()
	{
		Iterator<String> keySetIterator = UrlHashMappings.keySet().iterator();

		while(keySetIterator.hasNext())
		{
		  String key = keySetIterator.next();
		  ArrayList<String> values=UrlHashMappings.get(key);
		  countValues+=values.size();
		}
	    //System.out.println("Number of Values: "+countValues);
		//System.out.println("size: " +UrlHashMappings.size());
		average=countValues/UrlHashMappings.size();
		System.out.println("average: "+average);
	    return average;
	}
	
	//Getting the txt files
	public void listFilesForFolder(final File folder) 
	{
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) 
	        {
	            relativePath+="/"+fileEntry.getName();
	            //System.out.println(relativePath);
	            listFilesForFolder(fileEntry);
	            
	        } else {
	            parseFile=fileEntry.getName();
	            String substring=parseFile.substring(parseFile.indexOf(".")+1,parseFile.indexOf(".")+4);
	            if(substring.equals("txt"))
	            {
	            	//I create the relative paths in order to read them afterwards 
	            	createRelativePaths(relativePath+"/"+parseFile);
	            	files.add(parseFile);
	            }
	        	
	        	//I reset the relative paths
	        	relativePath="";
	        }
	    }
	}
	public void openReadFiles() 
	{
		for(int i=0;i<files.size();i++)
		{
			try {
				reader = new BufferedReader(new FileReader(globalPath+filesRelativePaths.get(i)));
				String line = null;
				String cutUrl=null;
				String textFile="";
				while ((line = reader.readLine()) != null) 
				{
					/*
					if(line.contains(searchString))
					{
						countAppear++;
						System.out.println("file where it was founded: "+filesRelativePaths.get(i));
					}*/
					textFile+=line;
					//System.out.println(textFile);
				}
				//PARSING THE URLS
				if(textFile.contains(urlString))
				{

					String tokens;
				     StringTokenizer st = new StringTokenizer(textFile);
				     while (st.hasMoreTokens()) 
				     {
				         	tokens=st.nextToken();
							if(tokens.contains(urlString))
							{
								int indexStart=tokens.indexOf(urlString)+4;
								int indexEnd=tokens.lastIndexOf("/");
								if(indexStart>0 && indexEnd>0)
									cutUrl=tokens.substring(indexStart,indexEnd);

								if(indexEnd!=0)
								{
									System.out.println("Urlfounded: "+cutUrl+"file "+filesRelativePaths.get(i));
								}
									
							}
				     }
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//System.out.println("file not found");
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			}
			countReadFile++;
		}
	
	}
	public void createRelativePaths(String path) 
	{
		filesRelativePaths.add(path);

	}
	public void exampleGraph()
	{
		//UrlMappings.put("File name", every url);
		urlsSpain.add("www.japan.com");
		urlsSpain.add("www.usa.com");
		urlsSpain.add("www.france.com");
		UrlHashMappings.put("spain", urlsSpain);
		
		urlsUsa.add("www.japan.com");
		urlsUsa.add("www.spain.com");
		urlsUsa.add("www.italy.com");
		UrlHashMappings.put("usa", urlsUsa);
		
		urlsItaly.add("www.japan.com");
		urlsItaly.add("www.germany.com");
		UrlHashMappings.put("italy", urlsItaly);
		
		urlsJapan.add("www.usa.com");
		urlsJapan.add("www.italy.com");
		UrlHashMappings.put("japan", urlsJapan);
		
		urlsSpain.add("www.france.com");
		urlsGermany.add("www.spain.com");
		urlsGermany.add("www.italy.com");
		UrlHashMappings.put("germany", urlsGermany);
		
		urlsFrance.add("www.spain.com");
		urlsFrance.add("www.germany.com");
		UrlHashMappings.put("france", urlsFrance);
	}
	public void shortestPathAlgorithm(String url1,String url2)//Hay que pasar el elemento para iterar que al principio es 0
	{
			this.url1=url1;
			valuesInit=initMapReduce(url1);
			mapReduceDijkstra(valuesInit);
			
			//ITERATION
			do{
				notStop=hasNotChanged(distancesBefore,distances);
				for(int m=0;m<newValues.size();m++)
				{
					if(newValues.get(m).equals(url1))
					{
						newValues.remove(m);
					}
				}

				mapReduceDijkstra(newValues);
			}while(notStop);
			
			//I print out the list of distances
			for(int j=0;j<distances.size();j++)
			{
				System.out.println("distances:"+distances.get(j).getNode()+","+distances.get(j).getDistanceOrigin());
			}
			
			//check for the url2 in distances
			for(int j=0;j<distances.size();j++)
			{
				if(distances.get(j).getNode().equals(url2))
				{
					System.out.println("The distance from "+url1+" to "+url2+" is "+distances.get(j).getDistanceOrigin());
				}
			}

	}
	public void mapReduceDijkstra(ArrayList<String> valuesIteration_)
	{
		ArrayList<String> valuesIteration =new ArrayList<String>();
			//I clear the nodes of the past iteration
		valuesIteration.addAll(valuesIteration_);
		newValues.clear();
			//MAP
			for(int k=0;k<valuesIteration.size();k++)
			{
				for(int l=0;l<edges.size();l++)
				{
					//if the url is in the edges list, then you have to increase the distance
					if(valuesIteration.get(k).equals(edges.get(l).getNext()))
					{
						String keyFind=cut(valuesIteration.get(k));
						tempNewValues= UrlHashMappings.get(keyFind);
						newValues.addAll(tempNewValues);
						
						for(int m=0;m<tempNewValues.size();m++)
						{
							if(tempNewValues.get(m).equals(url1))
							{
								tempNewValues.remove(m);
							}
							if(m<tempNewValues.size())
							edges.add(new Edge(tempNewValues.get(m),valuesIteration.get(k),edges.get(l).getDistance()+1));
						}
					}

				}
			}

			distancesBefore=distances;
			//REDUCE
			addDistances();
	}
	
	public ArrayList<String> initMapReduce(String url1)
	{
		//the starting point distance is 0
		distances.add(new Distances(url1,0));
		
		ArrayList<String> values =new ArrayList<String>();
		String url1Key=cut(url1);
		values= UrlHashMappings.get(url1Key);//it gives back the values
		for(int i=0;i<values.size();i++)
		{
			edges.add(new Edge(values.get(i),url1,1));
			distances.add(new Distances(edges.get(i).getNext(),edges.get(i).getDistance()));
		}
		return values;
	}
	/*
	 * To see if there have been any changes in the distances array
	 */
	public boolean hasNotChanged(ArrayList<Distances> distancesBefore_ ,ArrayList<Distances> distances_)
	{
		String compareDistancesBefore="";
		String compareDistances="";
		for(int i=0;i<distancesBefore_.size();i++)
		{
			compareDistancesBefore+=distancesBefore_.get(i).getDistanceOrigin();
			
		}
		for(int j=0;j<distances_.size();j++)
		{
			compareDistances+=distances_.get(j).getDistanceOrigin();
			
		}

		if(compareDistancesBefore.equals(compareDistances))
			notStop=false;
		
		return notStop;
	}
	/*
	 * For example, to get usa from www.usa.com
	 */
	public String cut(String url)
	{
		String urlCut=url.substring(url.indexOf("www.")+4,url.indexOf(".com"));
		return urlCut;
	}
	public void addDistances()
	{
		for(int i=0;i<edges.size();i++)
		{
			for(int j=0;j<distances.size();j++)
			{
				//if it exists
				if(distances.contains(new Distances(edges.get(i).getNext())))
				{
					if(edges.get(i).getDistance()<distances.get(j).getDistanceOrigin() && distances.get(j).getNode().equals(edges.get(i).getNext()))
					{
						//change the distance
						distances.get(j).setDistanceOrigin(edges.get(i).getDistance());
					}
				}
				else
				{
					//if it does not exist I create it
					distances.add(new Distances(edges.get(i).getNext(),edges.get(i).getDistance()));	
				}
			}
		}
	}
}
