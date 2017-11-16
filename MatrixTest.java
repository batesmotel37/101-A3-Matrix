//-----------------------------------------------------------------------------
// MatrixTest.java
// A test client for the Matrix ADT.
// Correct output is given in comments at the bottom of the file
//-----------------------------------------------------------------------------
class MatrixTest
{
   private static int row,column,value;
   public static void main(String[] args)
   {
      /*
 * feilds
 */
      Matrix A=new Matrix(3);
      Matrix B=new Matrix(3);
      value=0;
      /*
 * allots values in Matricies, also tests changeEntry(i,j,x)
 */
      for(row=1;row<=3;row++)
      {
         for(column=1;column<=3;column++)
         {
            A.changeEntry(column,row,++value);
            B.changeEntry(column,row,value);
         }
      }
      /*
 * tests .getNNZ(); returns number of non-zero entries
 * printout A to make sure that changeEntry worked (also tests toString())
 */
      System.out.println("A has "+A.getNNZ()+" entries:");
      System.out.println(A);
      System.out.println("B has "+B.getNNZ()+" entries:"+B);
      /*
 * Test .equals()
 */
      System.out.println("A equals B: "+A.equals(B));
      /*
 * Tests .makeZero() and re-tests .equals to make sure that makeZero really does work
 */
      B.makeZero();
      System.out.println("B has "+B.getNNZ()+" entries:\n"+B);
      System.out.println("A equals B:"+B.equals(A));
      /*
 * Tests copy, prints it out to make sure
 */
      B=A.copy();
      System.out.println("B has "+B.getNNZ()+" entries:\n"+B);
      System.out.println("A equals B:"+B.equals(A));
      /*
 * Test scalarMult()
 */
      System.out.println("A*2.0=\n"+A.scalarMult(2.0));
      System.out.println("B*100=\n"+B.scalarMult(100.0));
      /*
 * Test .add() and .sub()
 */
      System.out.println("A+B=\n"+A.add(B));
      System.out.println("A-B=\n"+A.sub(B));
      System.out.println("A-B*0.5=\n"+A.sub(B.scalarMult(0.5)));
      /*
 * Test if .add(),.sub(), and .mult() errors out if passed a matrix of incorrect size
 */
      //System.out.println(A.add(new Matrix(A.getSize()+1)));
      //System.out.println(A.sub(new Matrix(A.getSize()+1)));
      //System.out.println(A.mult(new Matrix(A.getSize()+1)));
      /*
 * Test .transpose()
 */
      System.out.println("A^T:\n"+A.transpose());
      System.out.println("B^T:\n"+B.transpose());
      /*
 * Test .mult() multiplies matricies correctly
 */
      //System.out.println(A.transpose());
      //System.out.println(B);
      System.out.println("A^T*B=\n"+A.transpose().mult(B));
      /*
 * Finally test that mult() runs at a suitable time when used on large, sparse matricies
 * that is to say matricies which have a large size but a small NNZ
 */
      int s=1000000;
      Matrix c=new Matrix(s);
      Matrix d=new Matrix(s);
      value=0;
      int i=s/100;
      /*
 *
 * WARNING!!!!!!-------------------------------------------------------------------------------------------------------------------------------------------------------
 *
 * The method presented below for creating our large matricies will occasionally result in an error out about 50% of the time
 * Just run again and wait for one to work
 * this block of code is just for testing mult(); we don't need to make the 1000x1000 matricies perfectly everytime, just often enough to test
 */
      for(row=1;row<=100;row++)
      {
         for(column=1;column<=50;column++)
         {
            int r=(int)(row*Math.random()*i)+1;
            int col=(int)(column*Math.random()*i)+1;
            c.changeEntry(r,col,value+Math.random()*1000);
            d.changeEntry(r,col,value+Math.random()*1000);
         }
      }
      System.out.println("c: has "+c.getNNZ()+"non-zero entries\n"+c);
      System.out.println("d: has "+d.getNNZ()+"\n"+d);
      System.out.println("c*d=\n"+c.mult(d));
   }
}
/*output from this program
 *
 * A has 9 entries:
 * 1: (1, 1.0)  (2, 4.0)  (3, 7.0) 
 * 2: (1, 2.0)  (2, 5.0)  (3, 8.0) 
 * 3: (1, 3.0)  (2, 6.0)  (3, 9.0) 
 *
 * B has 9 entries:1: (1, 1.0)  (2, 4.0)  (3, 7.0) 
 * 2: (1, 2.0)  (2, 5.0)  (3, 8.0) 
 * 3: (1, 3.0)  (2, 6.0)  (3, 9.0) 
 *
 * A equals B: true
 * B has 0 entries:
 *
 * A equals B:false
 * B has 9 entries:
 * 1: (1, 1.0)  (2, 4.0)  (3, 7.0) 
 * 2: (1, 2.0)  (2, 5.0)  (3, 8.0) 
 * 3: (1, 3.0)  (2, 6.0)  (3, 9.0) 
 *
 * A equals B:true
 * A*2.0=
 * 1: (1, 2.0)  (2, 8.0)  (3, 14.0) 
 * 2: (1, 4.0)  (2, 10.0)  (3, 16.0) 
 * 3: (1, 6.0)  (2, 12.0)  (3, 18.0) 
 *
 * B*100=
 * 1: (1, 100.0)  (2, 400.0)  (3, 700.0) 
 * 2: (1, 200.0)  (2, 500.0)  (3, 800.0) 
 * 3: (1, 300.0)  (2, 600.0)  (3, 900.0) 
 *
 * A+B=
 * 1: (1, 2.0)  (2, 8.0)  (3, 14.0) 
 * 2: (1, 4.0)  (2, 10.0)  (3, 16.0) 
 * 3: (1, 6.0)  (2, 12.0)  (3, 18.0) 
 *
 * A-B=
 *
 * A-B*0.5=
 * 1: (1, 0.5)  (2, 2.0)  (3, 3.5) 
 * 2: (1, 1.0)  (2, 2.5)  (3, 4.0) 
 * 3: (1, 1.5)  (2, 3.0)  (3, 4.5) 
 *
 * A^T:
 * 1: (1, 1.0)  (2, 2.0)  (3, 3.0) 
 * 2: (1, 4.0)  (2, 5.0)  (3, 6.0) 
 * 3: (1, 7.0)  (2, 8.0)  (3, 9.0) 
 *
 * B^T:
 * 1: (1, 1.0)  (2, 2.0)  (3, 3.0) 
 * 2: (1, 4.0)  (2, 5.0)  (3, 6.0) 
 * 3: (1, 7.0)  (2, 8.0)  (3, 9.0) 
 *
 * A^T*B=
 * 1: (1, 14.0)  (2, 32.0)  (3, 50.0) 
 * 2: (1, 32.0)  (2, 77.0)  (3, 122.0) 
 * 3: (1, 50.0)  (2, 122.0)  (3, 194.0) 
 */
