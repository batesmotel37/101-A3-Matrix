Student Name: Kenneth High
username: khigh
hostname: unix_.lt.ucsc.edu

This program takes multiplies matricies together to produce a product matrix. Multiplying two matricies would normally take O(m*n) time, where m is the number of rows is the first factor matrix and n is the number of columns in the second. 

However in a "sparse" matrix (a matrix where most entries are 0), many of these calculations can be skipped as an empty row/column (a row/column with only 0's) will result in 0's in the product matrix. Therefore, by checking for these empty rows/columns in the factor matricies before multiply we can greatly improve the efficency of this program on large sparse matricies.

Sparse.java
   *A program which uses methods from Matrix.java to maniputlate two matricies given by an input file and print out the results to an output file*

Matrix.java
   *A Matrix ADT which generates an array of List ADTs. These Lists hold the non-zero entries in each given row of the matrix.*

List.java
   *A List ADT which allows other programs to generate likedlists of Objects*

MatrixTest.java
   *runs all of the functions from Matrix.java in order to test them and show that they occur without error*

ListTest.java
   *tests that the methods from List.java run without error*

Makefile
   *uses "make" to compile/recompile all .java files in folder.
Creates executable "Sparse"*

README
   *This file a table of contents for the project*
