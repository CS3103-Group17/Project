package main;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DataController {
	Queue<String> urlQueue;
	Set<String> historySet;
	FileController fc;
	
	public DataController(){
		urlQueue = new PriorityQueue<String>();
		historySet = new HashSet<String>();
		fc = new FileController("output.txt");
	}
	
	public Queue<String> getQueue(){
		return urlQueue;
	}
	
	private boolean alreadyVisited(String url){
		return historySet.contains(url);
	}
	
	public void addURLToQueue(String url){
		if(!url.contains("http://")){
			url = "http://" + url;
		}
		urlQueue.add(url);
	}
	
	public boolean urlQueueIsEmpty(){
		return urlQueue.isEmpty();
	}
	
	public String getNextURL(){
		while(alreadyVisited(urlQueue.peek())){
			urlQueue.poll();
		}
		
		return urlQueue.poll();
	}
	
	public void addToHistory(String url, long duration){
		historySet.add(url);
		
		String[] splitURL = url.split("/");
		
		String baseURL = splitURL[0]+"//"+splitURL[2];
		
		String log = "Host: "+ baseURL + "\nTime taken in ns: "+duration+"\n";
		fc.writeToFile(log);
	}
}
