/*
 *
 *    List ADT that takes the form of a doubly linked-list for Objects. Includes a
 *    "cursor" which can travel both ways through the list, and can be "undefined" 
 *    (That is to say not on the list). The list has a front node and a back node,
 *    the first and last nodes in the list, respectivly. The index of the list spans
 *    0 to n-1, with n being the length of the list.    
 *
 */

   /*
 *  List.java
 *  An Object list ADT
 *
 *  Adapted from List.java submitted with asg1
 */

class List
{

   private class Node
   {
      // Fields
      Object data;
      Node prev;
      Node next;
      // Constructor
      Node(Object data) 
      { 
         this.data=data; 
         prev=null; 
         next=null; 
      }
      /*
 *
 * (Unchanged from Queue.java)
 *
 * Overrides Object's toString
 * returns a string verstion of the Object data held by a node
 */
      public String toString() 
      { 
         return data.toString(); 
      }
      /*
 * Overrides Object's equals
 * checks that if x is an instance of Node
 * if they're equal
 */
      public boolean equals(Object x)
      {
         boolean answer=false;
         if(x instanceof Node)
         {
            Node n=(Node) x;
            answer=n.equals(this);
         }
         return answer;
      }
   }

   // Fields
   private Node front;
   private Node back;
   private int length;
   private int index;//the number of nodes into the list that the  "cursor" is
   private Node cursor;// the cursor which points at nodes in the list

   // Constructor
   List() 
   { 
      front=null; 
      back=null;
      length=0;
      cursor=null;
      index=-1; 
   }

   /*
 *
 * Access Method
 *
 */

   /*
 * returns length of list
 */
   int length()
   {
      return length;
   }

   /*
 * Returns the index of the cursor
 * if the cursor is unidentified; return -1
 */
   int getIndex()
   {
      if(cursor==null)
         index=-1;
      return index;
   }

   /*
 * Pre-condition: length()>0
 * Return the element at the front of the list [front.data]
 */
   Object front()
   {
      if(isEmpty())
         throw new RuntimeException("ERROR: Access Function front(); list is empty");
      return front.data;
   }

   /*
 * Pre-codition: length()>0
 * return element at the back of the list [back.data]
 */
   Object back()
   {
      if(isEmpty())
         throw new RuntimeException("ERROR: Access Function back(); list is empty");
      return back.data; 
   }

   /*
 * Pre-codition: length()>0
 * Returns the element at the place in the list pointed to by index/cursor
 */
   Object getElement()
   {
      if(isEmpty())
         throw new RuntimeException("ERROR: Access Function getElement(); list is empty");
      return cursor.data;
   }

   /*
 * returns true if this is an empty queue, false otherwise
 */
   boolean isEmpty() 
   { 
      return length==0; 
   }

   /*
 *
 * Manipulation Methods
 *
 */

   /*
 * empties list; deletes all nodes and sets length to 0
 */
   void clear()
   {
      front=null;
      back=null;
      cursor=null;
      index=-1;
      length=0;
   }

   /*
 * Points the cursor to the a node on the list at index i
 * if the list is shorter that i, cursor becomes "undefined" (null)
 * if the index i is negative (re: not on the queue)
 */
   void moveTo(int i)
   {
      if(length<i||i<0)//check if list is too short or if i would make the cursor "undef"
      {
         cursor=null;
         index=-1;
      }
      else
      {
         /*
       * cycles from front of list to the index specified by i and leaves the cursor
       * pointing at that node
       */
         cursor=front;
         index=0;
         for(int c=0;c<i;c++)
         {
            cursor=cursor.next;
            index++;
         }
      }
   }

   /*
 * The cursor moves one node toward the front of the list and the index is subtracted by 1
 * if the cursor is already pointing at the front of the list, the cursor becomes 
 * "undefined" (ie: null) and the index=-1
 *
 * equivalent to moveTo(getIndex-1)
 */
   void movePrev()
   {
      moveTo(getIndex()-1);
   }

   /*
 * The cursor moves one node toward the front of the list and the index is increased by 1
 * if the cursor is already pointing at the back of the list, the cursor becomes 
 * "undefined" (ie: null) and the index=-1
 * 
 * equivalent to moveTo(getIndex+1)
 */
   void moveNext()
   {
      moveTo(getIndex()+1);
   }

   /*
 * Inserts data before front
 */
   void prepend(Object data)
   {
      Node node=new Node(data);
      if(front!=null)
      {
         front.prev=node;
         node.next=front;
         front=node;
         length++;
         if(cursor!=null)
            index++;
      }
      else
      {
         front=node;
         back=node;
         length=1;
      }
   }

   /*
 * Inserts data after back
 */
   void append(Object data)
   {
      Node node=new Node(data);
      if(back==null)
      {
         front=node;
         back=node;
         length=1;
      }
      else
      {
         node.prev=back;
         back.next=node;
         back=node;
         length++;
      }
   }

   /*
 * Pre-condition:length>0 && index>=0
 * Inserts data before the cursor
 */
   void insertBefore(Object data)
   {
      if(isEmpty()||index<0)
         throw new RuntimeException("ERROR: Access Function insertBefore(); list is empty or cursor is undefined");
/*
      else if(length==1)
      {
         front=null;
         back=null;
         prepend(data);
      }*/
      if(cursor==front)
         prepend(data);
      else
      {
         Node node=new Node(data);
         node.next=cursor;
         node.prev=cursor.prev;
         cursor.prev.next=node;
         cursor.prev=node;
         length++;
      }
   }

   /*
 * Pre-condition:length>0 && index>=0
 * Inserts data after the cursor
 */
   void insertAfter(Object data)
   {
      if(isEmpty()||index<0)
         throw new RuntimeException("ERROR: Access Function insertAfter(); list is empty or cursor is undefined");
/*
       else if(length==1)
       {
          front=null;
          back=null;
          prepend(data);
    }*/
      if(cursor==back)
         append(data);
      else
      {
         Node node=new Node(data);
         node.prev=cursor;
         node.next=cursor.next;
         cursor.next.prev=node;
         cursor.next=node;
         length++;
      }
   }

   /*
 * Pre-condition: length>0
 * deletes the front element of the list
 */
   void deleteFront()
   {
      if(isEmpty())
         throw new RuntimeException("ERROR: Access Function deleteFront(); list is empty");
      if(length()>1)
      {
         front=front.next;
         front.prev=null;
      }
      else
      {
         front=null;
         back=null;
      }
      length--;
   }

   /*
 * Pre-condition: length>0
 * delete the back element of the list
 */
   void deleteBack()
   {
      if(isEmpty())
         throw new RuntimeException("ERROR: Access Function deleteFront(); list is empty");
      if(length()>1)
      {
         back=back.prev;
         back.next=null;
      }
      else
      {
         front=null;
         back=null;
      }
      length--;
   }

   /*
 * Pre-condition: length>0, index>=0
 * deletes the element pointed at by the cursor and makes cursor undefined
 */
   void delete()
   {
      if(isEmpty()||index<0)
         throw new RuntimeException("ERROR: Access Function delete(); list is empty or cursor is undefined");
      if(getIndex()==0)
         deleteFront();
      else if(getIndex()==length()-1)
         deleteBack();
      else
      {
         cursor.prev.next=cursor.next;
         cursor.next.prev=cursor.prev;
         cursor=null;
         index=-1;
         length--;
      }
   }

   /*
 *
 * Other Functions
 *
 */

   /* 
 *
 * (Unchanged from Queue.java)
 *
 * Overrides Object's toString
 * returns a string consisting of each element in the List seperated by one space
 */
   public String toString(){
      String str = "";
      for(Node N=front; N!=null; N=N.next){
         str += N.toString() + " ";
      }
      return str;
   }

   /*
 * checks if Object x is a List
 * then if the lengths of the Lists are equal (just to make it run faster)
 * then compares each Node using Node's equals function
 */
   public boolean equals(Object x)
   {
      boolean answer=false;
      if(x instanceof List)
      {
         List l=(List) x;
         if(l.length()==this.length())
         {
            int holding_index=this.getIndex();
            l.moveTo(0);
            this.moveTo(0);
            while(l.getIndex()>=0)
            {
//               System.out.print(this.getElement()+"  "+l.getElement()+'\n');
               answer=this.getElement().equals(l.getElement());
//System.out.println(answer);
               if(!answer)
               {
                  l.moveTo(-1);
               }
               else
               {
                  l.moveNext();
                  this.moveNext();
               }
            }
            this.moveTo(holding_index);
         }
      }
      return answer;
   }
}
