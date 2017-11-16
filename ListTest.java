//-----------------------------------------------------------------------------
//  ListTest.java
//  A test client for the new modified List ADT. Use this to test your list module. The
//  correct output is given below.
//-----------------------------------------------------------------------------

//Modified from ListClient.java supplied by Prof. Tantalo
public class ListTest{
   public static void main(String[] args){
      List A = new List();
      List B = new List();
      
      for(int i=1; i<=20; i++){
         A.append(i);
         B.append(i);
      }
      System.out.println(A);
      System.out.println(B);
      System.out.println(A.length());
      System.out.println(B.length());
/*
      for(A.moveTo(0); A.getIndex()>=0; A.moveNext()){
         System.out.print(A.getElement()+" ");//+A.getIndex()+"\n");
      }
      System.out.println();
      for(B.moveTo(B.length()-1); B.getIndex()>=0; B.movePrev()){
         System.out.print(B.getElement()+" ");
      }
      System.out.println();
*/      
//      List C = A.copy();
      System.out.println(A.equals(B));
      B.clear();
      System.out.println(B);
      System.out.println(B.length());
      System.out.println(A.equals(B));
      for(int i=1;i<=20;i++)
      {
         B.prepend(i);
      }
      System.out.println(B);
      System.out.println(B.length());
      System.out.println(A.equals(B));
//      System.out.println(B.equals(C));
//      System.out.println(C.equals(A));
      //System.out.println(A);
      A.moveTo(0);
      A.delete();
      A.moveTo(A.length()-1);
      A.delete();
//      A.deleteFront();
      //A.deleteBack();
      A.moveTo(5);
      A.insertBefore(100);
      A.moveTo(15);
      A.insertAfter(100);
      A.moveTo(10);
      A.delete();
      //A.moveTo(19);
      //A.delete();
      System.out.println(A);
      A.clear();
      System.out.println(A);
      System.out.println(A.length());
   }
}

// Output of this program:
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
// 20
// 20
// true
//
// 0
// false
// 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
// 20
// false
// 2 3 4 100 5 6 7 8 9 11 12 13 14 15 100 16 17 18 19
// 
// 0
