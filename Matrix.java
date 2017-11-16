/*
 *
 * Matrix ADT that forms an array of Lists, using the List ADT for Objects. Can
 * return modified matricies using addition, subtraction, scalar multiplication,
 * transposition, and matrix multiplication.
 *
 */

/*
 * Matrix.java
 * A matrix ADT
 */

class Matrix
{

   private int c,count;//used in for loops instead of declaring a "count" each time
   private int rows;//stores the number of rows in the matrix
   private List[] matrix;// the list array which represents the matrix

   private class Entry
   {
      double value;//stores value associated with the entry
      int column;//stores the column no of the this entry; row is stored by array
      //constructer
      Entry(int col, double val)
      {
         column=col;
         value=val;
      }
      /*
 * returns a string which can be printed in format (column, value)
 */
      public String toString()
      {
         return " ("+column+", "+value+")";
      }
      /*
 * checks if Object x is an Entry
 * compares the value of x to the value of this, same with column
 * return whether or not they're equal
 */
      public boolean equals(Object x)
      {
         boolean answer=false;
         Entry e;
         if(x instanceof Entry)
         {
            e=(Entry) x;
            answer=(e.column==this.column)&&(e.value==this.value);
         }
         return answer;
      }
   }
   
   //constructor
   //makes a n x n matrix; assuming n>=1
   Matrix(int n)
   {
      if(n>=1)
      {
         rows=n;
         matrix=new List[n];
         for(int c=0;c<n;c++)
         {
            matrix[c]=new List();
         }
      }
      else
         System.out.printf("ERROR, Matrix not created, need to pass number >=1\n");
   }

   /*
 *
 * Access Functions------------------------------------------------------------
 *
 */

   /*
 * returns a row from the matrix
 */
   private List getRow(int i)
   {
      return matrix[i-1];
   }

   /*
 * returns the number of rows in the matrix
 */
   int getSize()
   {
      return rows;
   }

   /*
 * returns the number of non-zero entries in the Matrix
 */
   int getNNZ()
   {
      int total=0;
      for(c=1;c<=this.getSize();c++)
         total+=this.getRow(c).length();//.length() = no. non-zero entries
      return total;
   }

   /*
 * checks if Object x is a Matrix
 * then it checks the sizes of the matricies, if they're equal
 * checks to see if each list in the matricies are equal
 */
   public boolean equals(Object x)
   {
      boolean answer=false;
      if(x instanceof Matrix)//check that x is a matrix
      {
         Matrix z=(Matrix) x;//cast it as such
         if(z.getSize()==this.getSize())//check that x and this are of the same size
                                        //equal matricies should be of the same size
         {
            for(c=1;c<=this.getSize();c++)//cycle through each row
            {
               answer=this.getRow(c).equals(z.getRow(c));//check each row in this against x
               if(!answer)//if any given set of rows are unequal, kill the loop and return false
               {
                  c=this.rows+1;
               }
            }
         }
      }
      return answer;
   }

   /*
 * 
 * Manipulation Functions------------------------------------------------------
 *
 */

   /*
 * clears all the rows in this matrix
 * making it effectivly a zero matrix
 */
   void makeZero()
   {
      for(c=0;c<getSize();c++)
      {
         matrix[c].clear();
      }
   }

   /*
 * returns a new matrix which has the same entries as this one
 */
   Matrix copy()
   {
      Matrix rtn=new Matrix(this.getSize());//create new matrix
      for(c=1;c<=this.getSize();c++)//cycle through all the rows in this
      {
         List l=this.getRow(c);
         for(l.moveTo(0);l.getIndex()>=0;l.moveNext())//then cycle through each entry in these rows
         {
            Entry e=(Entry) l.getElement();//create new entries with the same data as the ones in this matrix
            rtn.getRow(c).append(new Entry(e.column,e.value));//and append them to the proper place in the new Magrix
         }
      }
      return rtn;
   }

   /*
 * pre-condition: 1<=i<=getSize() && 1<=j<=getSize()
 * the coordinates given must exist withing this matrix
 */
   void changeEntry(int i,int j,double x)
   {
      List L=getRow(i);
      boolean empty_row=L.isEmpty();
      boolean zero=(x==0.0);
      Entry n=new Entry(j,x);
      /*
 * if the entry is not to be changed to a value of zero and the list is empty
 * just add the new entry to the no-longer empty list
 */
      if(!zero&&empty_row)
         L.append(n);
      /*
 * if the entry is to be changed to zero and the row is empty
 * don't do anything as a zero value shouldn't be stored
 */
      /*
 * if the row is not empty, find the entry of which the value must be changed,
 * if the value must be changed to zero, delete the entry
 * otherwise insert the new entry and delete the one which had its old column value
 * if the row doesn't have an entry in the column j, just insert the entry before the next highest column value
 */
      else if(!empty_row)
      {
         for(L.moveTo(0);L.getIndex()>=0;L.moveNext())//cycle through each entry in the row
                                                      //search for the entry with a column value >= the target column (j)
         {
            Entry e=(Entry) L.getElement();
            int col=e.column;
            if(col>=j)
            {
               if(!zero)//if the data to be entered is not zero, insert the new entry right before the target entry (or next highest column value)
                        //if the row already contained a value at the target column value, delete the old entry after insertion
                        //if the data to be inserted is zero, just delete the entry with the target column value
                  L.insertBefore(n);
               if(col==j)
               {
                  L.delete();
               }
               return;
            }
         }
         L.append(n);
      }
      return;
   }

   /*
 * returns a new matrix which is the scalar multiple of this matrix by x
 */
   Matrix scalarMult(double x)
   {
      Matrix rtn=new Matrix(this.getSize());//create a new matrix of the same size as this one
      for(c=1;c<=this.getSize();c++)//cycle through all of the rows in this matrix
      {
         List l=this.getRow(c);
         for(l.moveTo(0);l.getIndex()>=0;l.moveNext())//cycle through each entry in this row
         {
            Entry i=(Entry) l.getElement();
            rtn.changeEntry(c,i.column,x*i.value);//change the corresponding entry in the new Matrix to x*value
         }
      }
      return rtn;
   }

   /*
 * pre-condition: equalSizes(M)
 * returns a new Matrix which is the sum of this matrix and M
 */
   Matrix add(Matrix M)
   {
      equalSizes(M,"add()");//check pre-condition
      Matrix m=M.copy();//create a copy of the passed matrix, this avoids creating erroes if the passed matrix points to this matrix
      Matrix rtn=new Matrix(this.getSize());//create a new matrix that is the same size as the two matricies being added
      for(c=1;c<=this.getSize();c++)//cycle through all the rows in these two matricies
      {
         List this_l=this.getRow(c);
         List that_l=m.getRow(c);
         this_l.moveTo(0);
         that_l.moveTo(0);
         while(this_l.getIndex()>=0&&that_l.getIndex()>=0)//cycle through all the entries in these
         {
            Entry one=(Entry) this_l.getElement();
            Entry two=(Entry) that_l.getElement();
            int col1=one.column;
            int col2=two.column;
            if(col1==col2)//if both rows have entries in the same column, the sum of these entries becomes the value of the corresponding entry in the new matrix
            {
               rtn.changeEntry(c,col1,one.value+two.value);
               this_l.moveNext();//continue cycling
               that_l.moveNext();
            }
            else if(col1>col2)//if one row has an entry in the column while the other has a zero
            {
               rtn.changeEntry(c,col2,two.value);//then the corresponding entry in the new matrix becomes that of the value in the matrix without the zero entry 
               that_l.moveNext();//don't cycle through the other row, because zero entries are not recorded
            }
            else if(col1<col2)
            {
               rtn.changeEntry(c,col1,one.value);
               this_l.moveNext();
            }
         }
         while(this_l.getIndex()>=0)//if one row reaches its final entry before the other
                                    //cycle through the remaining entries in the unended row and copy them to the new matrix
         {
            Entry one=(Entry) this_l.getElement();
            rtn.changeEntry(c,one.column,one.value);
            this_l.moveNext();
         }
         while(that_l.getIndex()>=0)
         {
            Entry two=(Entry) that_l.getElement();
            rtn.changeEntry(c,two.column,two.value);
            that_l.moveNext();
         }
      }
      return rtn;
   }

   /*
 * pre-condition: equalSizes(M)
 * returns a new Matrix which is the difference of this matrix and M
 */
   Matrix sub(Matrix M)
   {
      equalSizes(M,"sub()");//check pre-condition
      /*
 * a-b=a+(-b)
 */
      return this.add(M.scalarMult(-1));
   }

   /*
 * returns a new Matrix that is a transpose of this matrix
 *
 * if matrix A=
 * [1 4 7]
 * [2 5 8]
 * [3 6 9]
 *
 * the transpose of matrix A (A^T) =
 * [1 2 3]
 * [4 5 6]
 * [7 8 9]
 */
   Matrix transpose()
   {
      Matrix rtn=new Matrix(this.getSize());//create new matrix that is the same size as this one
      for(c=1;c<=this.getSize();c++)//cycle through each row in this matrix
      {
         List l=this.getRow(c);
         for(l.moveTo(0);l.getIndex()>=0;l.moveNext())//cycle through each entry in this row
         {
            Entry i=(Entry) l.getElement();
            rtn.changeEntry(i.column,c,i.value);//swap the column and row values of the Entry in this matrix and place the transposed entry into the new matrix
         }
      }
      return rtn;
   }

   /*
 * pre-condition: equalSizes(M)
 * returns a matrix which is the product of this matrix and M
 *
 * A matrix product of the 2x2 matricies A=[a b] & B=[e f]
 *                                         [c d]     [g h]
 * =[a*e+b*g a*f+b*h]
 *  [c*e+d*g c*f+d*h]
 *
 *  or more abstractly
 *  [dot(A.getRow(1),B.transpose.getRow(1))           ...             dot(A.getRow(1),B.transpose.getRow(A.getSize()))]
 *  [ .                                                                                                               ]
 *  [ .                                                                                                               ]
 *  [ .                                                                                                               ]
 *  [dot(A.getRow(A.getSize()),B.transpose.getRow(1)) ... dot(A.getRow(A.getSize()),B.transpose().getRow(A.getSize()))]
 */
   Matrix mult(Matrix M)
   {
      equalSizes(M,"mult()");//checks the pre-condition
      Matrix m=M.copy();
      Matrix rtn=new Matrix(this.getSize());//create a new matrix of the same size as this matrix
      Matrix mt=m.transpose();//transpose the passed matrix
      List NZrows=new List();//list to hold the indicies of all rows in this matrix 
      List cp=new List();//holds the rows of M.transpose which are not empty
                           //these rows are subject to dot() (as zero rows would only yeild a zero entry for rtn, and don't need to be accounted for)
      for(c=1;c<=this.getSize();c++)//cycle through each row of this matrix, and of the transposed M 
      {
         if(!this.getRow(c).isEmpty())//if a row of the matrix is not empty, then note that in the List
         {
            NZrows.append(c);
         }
         if(!mt.getRow(c).isEmpty())
         {
            cp.append(c);
         }
      }
      for(NZrows.moveTo(0);NZrows.getIndex()>=0;NZrows.moveNext())//for each non-empty row in this matrix
      {
         int r=(int)NZrows.getElement();
         for(cp.moveTo(0);cp.getIndex()>=0;cp.moveNext())//and for each non-empty row in M.transpose()
         {
            int cl=(int)cp.getElement();
            double d=dot(this.getRow(r),mt.getRow(cl));//find the dot product of all non-empty rows
            if(d!=0.0)//if it's not zero
            {
               rtn.changeEntry(r,cl,d);//place it in the appropriate place in the new matrix
            }
         }
      }
      return rtn;
   }

   /*
 * returns the dot product of two rows of a matrix
 * used as a helper for mult()
 *
 * The dot product of two matricies A=[a b] and B=[e f]
 *                                    [c d]       [g h]
 * =[a*e+b*f]
 *  [c*g+d*h]
 *
 *  or more abstractly:
 *  [A[0][0]*B[0][0]+A[0][1]*B[0][1]+...+A[0][n-1]*B[0][n-1]+A[0][n]*B[0][n]]
 *  [ .                                                                    ]
 *  [ .                                                                    ]
 *  [ .                                                                    ]
 *  [A[n][0]*B[n][0]+A[n][1]*B[n][1]+...+A[n][n-1]*B[n][n-1]+A[n][n]*B[n][n]]
 */
   private double dot(List a,List b)
   {
      double rtn=0.0;
      a.moveTo(0);
      b.moveTo(0);
      while(a.getIndex()>=0&&b.getIndex()>=0)//dot() is passed two rows of different matricies; cycle through each of the entries in these rows
      {
         Entry one=(Entry) a.getElement();
         Entry two=(Entry) b.getElement();
         int col1=one.column;
         int col2=two.column;
         if(col1==col2)//if the rows each have an entry in the same column; the product of the values at this column is added to the total return value
                       //if either row has a value of zero at a given column, no value from either row is added to the total
         {
            rtn+=one.value*two.value;
            a.moveNext();//and continue down each matrix
            b.moveNext();
         }
         else if(col1>col2)//if one matrix is pointing to a higher column than the other, it stalls so that the other one can catch up
            b.moveNext();
         else if(col1<col2)
            a.moveNext();
      }
      return rtn;
   }

   /*
 * checks whether or not this matrix and M are of equal size
 * prints an error message if they are not and exits gracefully
 *
 * used as a pre-condition by several functions
 */
   private void equalSizes(Matrix M,String method)
   {
      if(this.getSize()!=M.getSize())//if this and M are of unequal size, terminate the program and print error message
      {
         System.out.println("ERROR: "+method+"was passed a matrix with a number of rows/columns inequal to the number of rows/columns in this matrix");
         System.exit(1);
      }
   }

   /*
 * 
 * Other Functions-------------------------------------------------------------
 *
 */

   /*
 * overrides Object's toString()
 * returns a representing the matrix to be printed
 */
   public String toString()
   {
      String rtn="";
      for(c=1;c<=getSize();c++)//cycles through each row in this
      {
         List this_row=getRow(c);
         if(this_row.length()>0)//if the row is not empty
            rtn+=""+c+":"+this_row+'\n';//print it out
      }
      return rtn;
   }
}
