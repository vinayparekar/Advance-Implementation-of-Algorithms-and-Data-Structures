/* Abhishek Bansal (axb146030)
 * Vinay Parakar (vpp130130)
 * 
 * @author Abhishek Bansal,Vinay Parakar
 * @email-id axb146030@utdallas.edu, vpp130130@utdallas.edu
 * @version 1.0
 * 
 * Arithmetic with large numbers using Linked List
 *
 */

/*
 * The class ArithmeticOperation has the logic for all the Mathematical operations. Each Operation has its own method.
 * It also contains the Main Function, Main Function call the appropriate Method as per the Operation.
 * It has 7 functions, 6 for Operations and 1 is Main.
 */

import java.awt.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class axb146030_vpp130130ArithmeticOperation 
{																							//Begin of Class			
	/*
	 * StrToNum Function Converts a String into Linked List
	 * @param Str : String which is to be converted in Linked list
	 * @return Num : It is the Converted Linked list
	 */
	
		public static LinkedList<Integer> StrToNum(String Str)
		{
				LinkedList<Integer> Num=new LinkedList<Integer>();
				Str=new StringBuffer(Str).reverse().toString();
				for(int Counter=0;Counter<Str.length()-3;Counter++)
				{
					int a=Integer.parseInt(Str.charAt(Counter)+"");
					Num.add(a);
				}
				return Num;
		}
		
	/*
	 * NumToStr Function Converts a LinkedList into String
	 * @param Num : Linkedlist which is to be converted into String
	 * @return Str : It is the Converted String
	 */
		
		public static String NumToStr(LinkedList<Integer> Num)
		{
				String Str="";
				for(int Counter=Num.size()-1;Counter>=0;Counter--)
				{
					Str=Str+Num.get(Counter);
				}
				return Str;
		}
		
	/*
	 * Add Function Adds 2 Number stored as linked list
	 * @param Num1 : It is the 1st number which is to be Added, it is in the form of Linkedlist
	 * @param Num2 : It is the 2nd number which is to be Added, it is in the form of Linkedlist
	 * @return Sum : It is the Sum of 2 numbers which is returned in form of linked list
	 */
		
		public static LinkedList<Integer> Add(LinkedList<Integer> Num1,LinkedList<Integer> Num2)
		{
				int a=0,b=0,Counter=0;
				LinkedList<Integer> Sum= new LinkedList<Integer>();
				while(Counter<Num1.size()&&Counter<Num2.size())
				{
					a=Num1.get(Counter)+Num2.get(Counter)+b;
					b=a/10;
					a=a%10;
					Sum.add(a);
					Counter++;
				}
				if(Counter!=Num1.size())
				{
					while(Counter<Num1.size())
					{
					a=Num1.get(Counter)+b;
					b=a/10;
					a=a%10;
					Sum.add(a);
					Counter++;	
					}
				}
				if(Counter!=Num2.size())
				{
					while(Counter<Num2.size())
					{
					a=Num2.get(Counter)+b;
					b=a/10;
					a=a%10;
					Sum.add(a);
					Counter++;	
					}
				}	
				if(b!=0)
				Sum.add(b);
				return Sum;
		}
		
	/*
	 * Subtract Function Subtracts 2 Number stored as linked list
	 * @param Num1 : It is the 1st number which is to be Subtracted, it is in the form of Linkedlist
	 * @param Num2 : It is the 2nd number which is to be Subtracted, it is in the form of Linkedlist
	 * @return Sub : It is the Subtraction of 2 numbers which is returned in form of linked list
	 */		
		
		public static LinkedList<Integer> Subtract(LinkedList<Integer> Num1,LinkedList<Integer> Num2)
		{
				int a=0,b=0,i=0;
				LinkedList<Integer> Sub= new LinkedList<Integer>();
				if(Num1.size()==Num2.size()&&Num1.getLast()<Num2.getLast())
				{
					Sub.add(0);
					return Sub;
				}
				while(i<Num1.size()&&i<Num2.size())
				{
					if(Num1.get(i)<=Num2.get(i))
					{
						if(Num1.get(i)==0&&b==-1)
							{
								a=(Num1.get(i)+10+b)-Num2.get(i);
								b=-1;
							}
						else if(b==0&&Num1.get(i)==Num2.get(i))
						{
							a=(Num1.get(i)+b)-Num2.get(i);
							b=0;
						}
						else
						{
							a=(Num1.get(i)+b)+10-Num2.get(i);
							b=-1;
						}
					}
					else
					{
						a=Num1.get(i)+b-Num2.get(i);
						b=0;
					}
					Sub.add(a);
					i++;
				}
				if(i<=Num1.size())
				{
					while(i<Num1.size())
					{
						if(Num1.get(i)==0&&b==-1)
							a=(Num1.get(i)+9+b);
						else
							a=(Num1.get(i)+b);
						b=0;
						Sub.add(a);
						i++;	
					}
				}
				if(i<Num2.size()||Sub.get(Sub.size()-1)<0)
				{
					Sub.clear();
					Sub.add(0);
				}	
				return Sub;
		}
		
	/*
	 * Multiply Function Multiplies 2 Number stored as linked list
	 * @param Num1 : It is the 1st number which is to be Multiplied, it is in the form of Linkedlist
	 * @param Num2 : It is the 2nd number which is to be Multiplied, it is in the form of Linkedlist
	 * @return Multiply : It is the Multiplication of 2 numbers which is returned in form of linked list
	 */		
		
		public static LinkedList<Integer> Multiply(LinkedList<Integer> Num1,LinkedList<Integer> Num2)
		{
			LinkedList<Integer> Mul=new LinkedList<Integer>();
			ListIterator<Integer> Num2Iter = Num2.listIterator(0);
	        for (int i = 0; i < (Num2.size()); i++) 
	        {
	            int Num2Digit = Num2Iter.next().intValue();
	            ListIterator<Integer> Num1Iter = Num1.listIterator(0);
	            for (int j = 0; j < Num1.size(); j++) 
	            {
	                int Num1Digit = Num1Iter.next().intValue();
	                int product = Num1Digit * Num2Digit;
	                int shift = i + j;
	                for (int Counter = shift; product != 0; Counter++) 
	                {
	                    int digit = product % 10;
	                    product = product / 10;
	                    int Len = Counter - Mul.size() + 1;
	                    for (int k = 0; k < Len; k++) 
	                    {
	                        Mul.addLast(0);
	                    }
	                    int Position = Counter;
	                    int Cur = Mul.get(Position).intValue();
	                    int Update = Cur + digit;
	                    if (Update >= 10) 
	                    {
	                        Update -= 10;
	                        product++;
	                    }
	                    Mul.set(Position, Update);
	                }
	            }
	        }
	        return Mul;
		}

	/*
	 * Power Function calculates the Power of Number stored as linked list
	 * @param Num1 : It is the 1st number whose power is to be calculated, it is in the form of Linkedlist
	 * @param Num2 : It is the 2nd number which is the power, it is in the form of Linkedlist
	 * @return Multiply : It is the Power of 2 numbers which is returned in form of linked list
	 */			
		
		public static LinkedList<Integer> Power(LinkedList<Integer> Num1,LinkedList<Integer> Num2)
		{	
			 if(Num2.size()==1&&Num2.getFirst()==1)
			 {
				 return Num1;
			 }
			 else
			 {
				LinkedList<Integer> Power=new LinkedList<Integer>();
					int a=0,flag=0;
					for(int i=0;i<Num2.size();i++)
					{
						int Divisor;			
						a=a*10+Num2.get(i);
						if(a==0)
						{
							Power.add(0);
							a=0;
							continue;
						}
						if(a==1&&flag==0)
						{
							flag++;
							continue;
						}
						Divisor=a/2;			
						a=0;
						flag=0;
						Power.add(Divisor);
					}
					if(flag!=0)
						for(int i=0;i<flag;i++)
						{
							Power.add(0);
						}
			 		LinkedList<Integer> temp=new LinkedList<Integer>();
					temp=Power(Num1,Power);
					if(Num2.getLast()%2==0)
					{
						return Multiply(temp, temp);
					}
					else
					{
						temp = Multiply(temp,temp);
						return Multiply(Num1,temp);
					}
			 }
		}
	
	/*
	 * Main Function from where program is Executed
	 * @param args : String array which reads the input from Command line
	 * @variable linenum : It tells the number of line from stdin
	 * @variable Counter : Counts the number of line from stdin
	 * @variable OperandCount : It keeps the count of Operands
	 * @Variable LeftOp : It stores the Position of Left Operand  
	 * @Variable RightOp : It stores the Position of Right Operand  
	 * @Variable LeftToOp : It stores the Position of Operand which is left to the Operator.
	 * @variable Operations : It stores the input same as stdin
	 * @variable AllOperands : a LinkedList Array which stores all the Expressions.
	 * @return Function does not return any value; void
	 */			

		public static void main(String[] args) throws Exception
		{
			
		      int linenum=0,Counter=0,OperandCount=0,LeftOp=0,RightOp=0,LeftToOp=0;
		      String[] Operations = new String[1000];
		      Scanner in = new Scanner(System.in);
		      LinkedList[] AllOperands=new LinkedList[1000];
		      while(linenum!=1001) 
		      {
		    	  linenum = in.nextInt();
		    	  Operations[Counter] = linenum+in.next();
		    	  Counter++;
		      }
		      in.close();
		      char[] ch1=new char[1002];
		      for(int RowCounter=0;RowCounter<Counter-1;RowCounter++)
		      {
		    	  if(Operations[RowCounter].contains("="))
		    	  {
		    		  if(Operations[RowCounter].contains("+"))			
		    		  {
							while(Operations[RowCounter].charAt(3)!=ch1[LeftOp])
							{
								LeftOp++;
							}
							while(Operations[RowCounter].charAt(5)!=ch1[RightOp])
							{
								RightOp++;
							}
							while(Operations[RowCounter].charAt(1)!=ch1[LeftToOp]&& LeftToOp<100)
							{
								LeftToOp++;
							}
							if(LeftToOp==100)
							{
								LinkedList<Integer> a1=Add(AllOperands[LeftOp],AllOperands[RightOp]);
								char ch=Operations[RowCounter].charAt(1);
								AllOperands[OperandCount]=a1;
								ch1[OperandCount]=ch;
								OperandCount++;
							}
							else
							{
								AllOperands[LeftToOp]=Add(AllOperands[LeftOp],AllOperands[RightOp]);
							}
							LeftOp=0;
							RightOp=0;
							LeftToOp=0;
							OperandCount=0;
		    		  }
		    		  else if(Operations[RowCounter].contains("-"))
		    		  {
		    			  while(Operations[RowCounter].charAt(3)!=ch1[LeftOp])
		    			  {
		    				  LeftOp++;
		    			  }
		    			  while(Operations[RowCounter].charAt(5)!=ch1[RightOp])
		    			  {
		    				  RightOp++;
		    			  }
		    			  while(Operations[RowCounter].charAt(1)!=ch1[LeftToOp]&& LeftToOp<100)
		    			  {	
		    				  LeftToOp++;
		    			  }
		    			  if(LeftToOp==100)
		    			  {
		    				  LinkedList<Integer> a1=Subtract(AllOperands[LeftOp],AllOperands[RightOp]);
		    				  char ch=Operations[RowCounter].charAt(1);
		    				  AllOperands[OperandCount]=a1;
		    				  ch1[OperandCount]=ch;
		    				  OperandCount++;
		    			  }
		    			  else	
		    			  {
		    				  AllOperands[LeftToOp]=Subtract(AllOperands[LeftOp],AllOperands[RightOp]);
		    			  }
		    			  LeftOp=0;
		    			  RightOp=0;
		    			  LeftToOp=0;
		    			  OperandCount=0;
		    		  }
		    		  else if(Operations[RowCounter].contains("*"))		
		    		  {
		    			  while(Operations[RowCounter].charAt(3)!=ch1[LeftOp])
		    			  {
		    				  LeftOp++;
		    			  }
		    			  while(Operations[RowCounter].charAt(5)!=ch1[RightOp])
		    			  {
		    				  RightOp++;
		    			  }
		    			  while(Operations[RowCounter].charAt(1)!=ch1[LeftToOp]&& LeftToOp<100)
		    			  {
		    				  LeftToOp++;
		    			  }
		    			  if(LeftToOp==100)
		    			  {
		    				  LinkedList<Integer> a1=Multiply(AllOperands[LeftOp],AllOperands[RightOp]);
		    				  char ch=Operations[RowCounter].charAt(1);
		    				  AllOperands[OperandCount]=a1;
		    				  ch1[OperandCount]=ch;
		    				  OperandCount++;
		    			  }
		    			  else
		    			  {
		    				  AllOperands[LeftToOp]=Multiply(AllOperands[LeftOp],AllOperands[RightOp]);
		    			  }
		    			  LeftOp=0;
		    			  RightOp=0;
		    			  LeftToOp=0;
		    			  OperandCount=0;
		    		  }
		    		  else if(Operations[RowCounter].contains("^"))	
		    		  {
		    			  while(Operations[RowCounter].charAt(3)!=ch1[LeftOp])
		    			  {
		    				  LeftOp++;
		    			  }
		    			  while(Operations[RowCounter].charAt(5)!=ch1[RightOp])
		    			  {
		    				  RightOp++;
		    			  }
		    			  while(Operations[RowCounter].charAt(1)!=ch1[LeftToOp]&& LeftToOp<100)
		    			  {
		    				  LeftToOp++;
		    			  }
		    			  if(LeftToOp==100)
		    			  {
		    				  LinkedList<Integer> a1=Power(AllOperands[LeftOp],AllOperands[RightOp]);
		    				  char ch=Operations[RowCounter].charAt(1);
		    				  AllOperands[OperandCount]=a1;
		    				  ch1[OperandCount]=ch;
		    				  OperandCount++;
		    			  }
		    			  else
		    			  {
		    				  AllOperands[LeftToOp]=Power(AllOperands[LeftOp],AllOperands[RightOp]);
		    			  }
		    			  LeftOp=0;
		    			  RightOp=0;
		    			  LeftToOp=0;
		    			  OperandCount=0;
		    		  }
		    		  else
		    		  {
		    			  LinkedList<Integer> a1=StrToNum(Operations[RowCounter]);
		    			  char ch=Operations[RowCounter].charAt(1);
		    			  AllOperands[OperandCount]=a1;
		    			  ch1[OperandCount]=ch;
		    			  OperandCount++;
		    		  }
		    	  }
		    	  else
		    	  {
		    		  if(Operations[RowCounter].contains("?"))
		    		  {
		    			  while(Operations[RowCounter].charAt(1)!=ch1[LeftOp])
		    			  {
		    				  LeftOp++;
		    			  }
		    			  String Str = NumToStr(AllOperands[LeftOp]);
		    			  int i=Integer.parseInt(Str);
		    			  if(i==0)
		    			  {
		    				  continue;
		    			  }
		    			  else
		    			  {
		    				  RowCounter=Integer.parseInt(Operations[RowCounter].charAt(3)+"")-2;
		    			  }
		    		  }
		    		  else
		    		  {
		    			  while(Operations[RowCounter].charAt(1)!=ch1[LeftOp])
		    			  {
		    				  LeftOp++;
		    			  }
		    			  String Str=NumToStr(AllOperands[LeftOp]);
		    			  System.out.println(Str);
		    			  LeftOp=0;
		    		  }
		    	  }
		    	  LeftOp=0;
		      }
		}
}





