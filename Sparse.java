/*
 *
 * Sparse.java takes an input from a file from which two matricies are taken.
 * These matricies are then printed to an output file along with the scalar
 * multiple of the first, their sum, double the first, their difference, the
 * difference of the first with itself (a zero matrix), the transposition of 
 * the first, their product and the square of the second. 
 *
 * It requires two inputs from the command line. one input file which is 
 * formated to have three blocks of numbers seperated by one blank line each,
 * the first line details the number of rows of the two square matricies, the 
 * number of non-zero entries in each matrix respectivly. After one blank line,
 * it details the row, column and value of each non-zero entry, one entry per 
 * line for the first matrix. After, another blank line, the same is done for
 * the second matrix.
 *
 */

/*
 * Sparse.java
 * uses Matrix.java's ADT
 * which uses List.java's ADT
 */

import java.util.Scanner;
import java.io.*;

class Sparse
{

   private static int c;

   /*
 * Pre-condition: two commandline arguements must be given
 * an input file and an output file
 * main accepts two files as arguements and converts the contents of the first
 * into two square matricies. These matricies are then operated on and the
 * and the results are printed to the output file.
 */
   public static void main(String[] args) throws IOException
   {
      /*
 * check if there are not exactly two commandline args
 */
      if(args.length!=2)
      {
         System.out.println("ERROR: Sparse needs exactly two commandline arguements");
         System.exit(1);
      }
      Scanner in=new Scanner(new File(args[0]));//accepts input
      PrintWriter output=new PrintWriter(new FileWriter(args[1]));//writes to output file
      String[] first_line=in.nextLine().split("\\s");//accepts the first line of input, will be used to format the two matricies
      int rows=Integer.parseInt(first_line[0]);
      int NNZa=Integer.parseInt(first_line[1]);
      int NNZb=Integer.parseInt(first_line[2]);
      Matrix one=new Matrix(rows);//set up the two square matricies
      Matrix two=new Matrix(rows);
      String throw_away=in.nextLine();//gets rid of the blank line
      createMatrixRows(one,NNZa,in);//allots first matrix
      throw_away=in.nextLine();
      createMatrixRows(two,NNZb,in);//allots second matrix
/*
      String[] line;
      for(int c=0;c<first_line[1];c++)
      {
         line=in.nextLine().split('\\s');
         one.changeEntry(line[0],line[1],line[2]);
      }
      throw_away=in.nextLine();
      for(c=0;c<first_line[2];c++)
      {
         line=in.nextLine().split('\\s');
         two.changeEntry(line[0],line[1],line[2]);
      }
*///replaced with createMatrixRows()
      output.println("A has "+one.getNNZ()+" non-zero entries:\n"+one);
      output.println("B has "+two.getNNZ()+" non-zero entries:\n"+two);
      output.println("(1.5)*A =\n"+one.scalarMult(1.5));
      output.println("A+B =\n"+one.add(two));
      output.println("A+A =\n"+one.add(one));
      output.println("B-A =\n"+two.sub(one));
      output.println("A-A =\n"+one.sub(one));
      output.println("Transpose(A) =\n"+one.transpose());
      output.println("A*B =\n"+one.mult(two));
      output.println("B*B =\n"+two.mult(two));
      in.close();//close input/output files and exit program
      output.close();
   }

   /*
 * Helper method
 * takes an input file (of type Scanner) and reads line by line reformating the lines into a reformatted matrix
 * This is sepearated since the input files given for the Sparse project give two matricies in an input file
 * and I didn't feel like writing out this loop twice
 */
   private static void createMatrixRows(Matrix m,int nnz,Scanner input)
   {
      String[] line;
      for(c=0;c<nnz;c++)
      {
         line=input.nextLine().split("\\s");//splits the line which consists of three numbers split by a space into an array
         m.changeEntry(Integer.parseInt(line[0]),Integer.parseInt(line[1]),Double.parseDouble(line[2]));//passes the split line to changeEntry where it can be used
      }
   }
}
