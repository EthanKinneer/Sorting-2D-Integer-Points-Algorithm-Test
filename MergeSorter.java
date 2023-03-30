package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Ethan Kinneer (ekinneer@iastate.edu)
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array
	 *
	 *
	 */
	private void mergeSortRec(Point[] pts)
	{
		if (pts.length <= 1) { // Base case / Quit
			return;
		} else { // Split array
			int midpoint = pts.length / 2;
			Point[] leftPoints = new Point[midpoint];
			Point[] rightPoints = new Point[pts.length - leftPoints.length];

			System.arraycopy(pts, 0, leftPoints, 0, leftPoints.length); //Populate left points

			System.arraycopy(pts, leftPoints.length, rightPoints, 0, rightPoints.length); //Populate right points

			mergeSortRec(leftPoints);
			mergeSortRec(rightPoints);

			Point[] sortedPoints = merge(leftPoints, rightPoints); //After recursion merge all points back together

			System.arraycopy(sortedPoints, 0, pts, 0, pts.length); //Copy sortedPoints to pts
		}
	}

	private Point[] merge(Point[] leftPoints, Point[] rightPoints)
	{
		int i = 0; //Index for leftPoints
		int j = 0; //Index for rightPoints
		int k = 0; //Index for mergedPoints

		Point[] mergedPoints = new Point[leftPoints.length + rightPoints.length];

		//Compare left and right array and sort each into mergedPoints until one array has been looped all through
		while (i < leftPoints.length && j < rightPoints.length) {
			if (pointComparator.compare(leftPoints[i], rightPoints[j]) < 0) {
				mergedPoints[k] = leftPoints[i];
				i++;
				k++;
			} else {
				mergedPoints[k] = rightPoints[j];
				j++;
				k++;
			}
		}

		//Loop through leftover points in the left array
		while (i < leftPoints.length) {
			mergedPoints[k] = leftPoints[i];
			i++;
			k++;
		}

		//Loop through leftover points in the right array
		while (j < rightPoints.length) {
			mergedPoints[k] = rightPoints[j];
			j++;
			k++;
		}
		return mergedPoints;
	}

	
	// Other private methods if needed ...

}
