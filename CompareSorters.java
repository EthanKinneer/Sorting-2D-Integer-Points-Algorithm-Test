package edu.iastate.cs228.hw2;

/**
 *  
 * @author Ethan Kinneer (ekinneer@iastate.edu)
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{
		PointScanner[] scanners = new PointScanner[4];
		int trialCounter = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		System.out.println("Trial " + trialCounter + ": ");
		int input = sc.nextInt();
		while (input != 3){
			if (input == 1){
				System.out.print("Enter number of random points: ");
				int randInput = sc.nextInt();
				Point[] randomPoints = generateRandomPoints(randInput, new Random());
				scanners[0] = new PointScanner(randomPoints, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(randomPoints, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(randomPoints, Algorithm.MergeSort);
				scanners[3] = new PointScanner(randomPoints, Algorithm.QuickSort);
			} else if (input == 2) {
				System.out.println("Points from a file");
				System.out.print("Enter the file name: ");
				String fileInput = sc.next();
				scanners[0] = new PointScanner(fileInput, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileInput, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileInput, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileInput, Algorithm.QuickSort);
			}

			System.out.println("Algorithm\t Size\t time(ns)");
			System.out.println("---------------------------------");
			for (PointScanner scanner:scanners) {
				scanner.scan();
				System.out.println(scanner.stats());
			}
			System.out.println("---------------------------------\n");
			trialCounter++;
			System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
			System.out.print("Trial " + trialCounter + ": ");
			input = sc.nextInt();
		}
		sc.close();
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		if (numPts < 1){
			throw new IllegalArgumentException("numPts is less than 1");
		}
		Point[] randomPoints = new Point[numPts];
		for (int i = 0; i < numPts; i++) {
			randomPoints[i] = new Point(rand.nextInt(101)-50, rand.nextInt(101)-50);
		}

		return randomPoints;
	}
	
}
