package edu.iastate.cs228.hw2;

/**
 *  
 * @author Ethan Kinneer (ekinneer@iastate.edu)
 *
 */

import java.util.Comparator;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later the sorted) sequence. 
 *
 */
public abstract class AbstractSorter
{
	
	protected Point[] points;    // array of points operated on by a sorting algorithm. 
	                             // stores ordered points after a call to sort(). 
	
	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
	                                   // "quicksort". Initialized by a subclass constructor.
		 
	protected Comparator<Point> pointComparator = null;  
	
	
	// Add other protected or private instance variables you may need. 
	


	
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0){
			throw new IllegalArgumentException("pts is null or pts.length is 0");
		}
		points = new Point[pts.length];
		System.arraycopy(pts, 0, points, 0, pts.length);
	}

		
	
	
	

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order == 0, by y-coordinate
	 * if order == 1. Assign the 
     * comparator to the variable pointComparator. 
     *  
	 * 
	 * @param order  0   by x-coordinate 
	 * 				 1   by y-coordinate
	 * 			    
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 1
	 *        
	 */
	public void setComparator(int order) throws IllegalArgumentException
	{
		if (order < 0 || order > 1){
			throw new IllegalArgumentException("Order is less than 0 or greater than 1");
		}
		if (order == 0){
			pointComparator = new Comparator<Point>() {
				@Override
				public int compare(Point o1, Point o2) {
					if (o1.getX() < o2.getX()){
						return -1;
					} else if (o1.getX() == o2.getX()){
						return 0;
					}else{
						return 1;
					}

				}
			};
		}
		if (order == 1){
			pointComparator = new Comparator<Point>() {
				@Override
				public int compare(Point o1, Point o2) {
					if (o1.getY() < o2.getY()){
						return -1;
					} else if (o1.getY() == o2.getY()){
						return 0;
					}else{
						return 1;
					}

				}
			};
		}

	}

	

	/**
	 * Use the created pointComparator to conduct sorting.  
	 * 
	 * Should be protected. Made public for testing. 
	 */
	public abstract void sort(); 
	
	
	/**
	 * Obtain the point in the array points[] that has median index 
	 * 
	 * @return	median point 
	 */
	public Point getMedian()
	{
		return points[points.length/2]; 
	}
	
	
	/**
	 * Copys the array points[] onto the array pts[]. 
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts)
	{
		System.arraycopy(points, 0, pts, 0, points.length);
	}
	

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		Point temp = points[j];
		points[j] = points[i];
		points[i] = temp;

	}	
}
