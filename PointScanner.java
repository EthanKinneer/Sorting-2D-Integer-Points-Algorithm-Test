package edu.iastate.cs228.hw2;

/**
 * 
 * @author Ethan Kinneer (ekinneer@iastate.edu)
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0){
			throw new IllegalArgumentException("pts is null or pts.length is 0");
		}
		points = new Point[pts.length];
		System.arraycopy(pts, 0, points, 0, pts.length);
		sortingAlgorithm = algo;

	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		File f = new File(inputFileName);
		FileInputStream fileInputStream = new FileInputStream(f);
		Scanner scnr = new Scanner(fileInputStream);
		ArrayList<Point> pointArrayList = new ArrayList<>();

		this.sortingAlgorithm = algo;

		while (scnr.hasNextInt()) {
			int x = scnr.nextInt();
			if (scnr.hasNextInt()) {
				pointArrayList.add(new Point(x, scnr.nextInt()));
			} else {
				scnr.close();
				throw new InputMismatchException("There are an odd number of integers");
			}
		}

		points = pointArrayList.toArray(new Point[0]);
		scnr.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param
	 * @return
	 */
	public void scan() throws FileNotFoundException {
		AbstractSorter aSorter = null;
		switch (sortingAlgorithm) {
			case SelectionSort:
				aSorter = new SelectionSorter(points);
				break;
			case InsertionSort:
				aSorter = new InsertionSorter(points);
				break;
			case MergeSort:
				aSorter = new MergeSorter(points);
				break;
			case QuickSort:
				aSorter = new QuickSorter(points);
				break;
		}
			aSorter.setComparator(0); //Sets comparator to X values
			Point.setXorY(true);
			long startTime1 = System.nanoTime();
			aSorter.sort();
			Long endTime1 = System.nanoTime();
			int xMedian = aSorter.getMedian().getX();
			aSorter.setComparator(1); //Sets comparator to Y values
			Point.setXorY(false);
			Long startTime2 = System.nanoTime();
			aSorter.sort();
			Long endTime2 = System.nanoTime();
			scanTime = (endTime1 - startTime1) + (endTime2 - startTime2);
			int yMedian = aSorter.getMedian().getY();
			medianCoordinatePoint = new Point(xMedian, yMedian);
			writeMCPToFile();
	}

	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		return String.format("%14s\t%5d\t%6d", sortingAlgorithm.name(), points.length,scanTime);
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		FileOutputStream output = new FileOutputStream("output.txt");
		PrintWriter writer = new PrintWriter(output);
		writer.println(this);
		writer.close();
	}

}
