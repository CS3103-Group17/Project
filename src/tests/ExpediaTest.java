package tests;

import api.hotels.ExpediaCrawler;

public class ExpediaTest {

	public static void main(String[] args) {
		ExpediaCrawler myExpedia = new ExpediaCrawler();
		myExpedia.getHotelsTest("Seoul");
		System.out.println(myExpedia.generateHTML());

	}

}
