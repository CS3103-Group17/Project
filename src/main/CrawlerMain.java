package main;
import java.io.IOException;
import java.util.Scanner;

public class CrawlerMain {

	private static Scanner reader;

	public static void main(String[] args) throws IOException {
		MainController con = new MainController();
		
		boolean stop = false;
		reader = new Scanner(System.in);
		
		while(!stop){
			System.out.println("Enter a url (Press q to quit): ");
			
			String input = reader.nextLine();
			
			if(input.equals("q")){
				stop = true;
			}
			else if(!input.equals("")){
				con.addURL(input);
			}
			
		}
		
		con.runCrawler();
	}
	
	

}
