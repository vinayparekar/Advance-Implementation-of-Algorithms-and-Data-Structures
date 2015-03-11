import java.awt.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Demo 
{
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
	public static String NumToStr(LinkedList<Integer> Num)
	{
			String Str="";
			for(int i=Num.size()-1;i>=0;i--)
			{
				Str=Str+Num.get(i);
			}
			return Str;
	}
	public static LinkedList<Integer> Add(LinkedList<Integer> L1,LinkedList<Integer> L2)
	{
			int a=0,b=0,i=0;
			LinkedList<Integer> Sum= new LinkedList<Integer>();
			while(i<L1.size()&&i<L2.size())
			{
				a=L1.get(i)+L2.get(i)+b;
				b=a/10;
				a=a%10;
				Sum.add(a);
				i++;
			}
			if(i!=L1.size())
			{
				while(i<L1.size())
				{
				a=L1.get(i)+b;
				b=a/10;
				a=a%10;
				Sum.add(a);
				i++;	
				}
			}
			if(i!=L2.size())
			{
				while(i<L2.size())
				{
				a=L2.get(i)+b;
				b=a/10;
				a=a%10;
				Sum.add(a);
				i++;	
				}
			}	
			if(b!=0)
			Sum.add(b);
			return Sum;
		}
		public static LinkedList<Integer> Subtract(LinkedList<Integer> L1,LinkedList<Integer> L2)
		{
			int a=0,b=0,i=0;
			LinkedList<Integer> Sub= new LinkedList<Integer>();
			if(L1.size()==L2.size()&&L1.getLast()<L2.getLast())
			{
				Sub.add(0);
				return Sub;
			}
			
			
			while(i<L1.size()&&i<L2.size())
			{
				if(L1.get(i)<=L2.get(i))
				{
					if(L1.get(i)==0&&b==-1)
						{
							a=(L1.get(i)+10+b)-L2.get(i);
							b=-1;
						}
					else if(b==0&&L1.get(i)==L2.get(i))
					{
						a=(L1.get(i)+b)-L2.get(i);
						b=0;
					}
					else
					{
						a=(L1.get(i)+b)+10-L2.get(i);
						b=-1;
					}
				}
				else
				{
					a=L1.get(i)+b-L2.get(i);
					b=0;
				}
				Sub.add(a);
				i++;
			}
			if(i<=L1.size())
			{
				while(i<L1.size())
				{
					if(L1.get(i)==0&&b==-1)
						a=(L1.get(i)+9+b);
					else
						a=(L1.get(i)+b);
				b=0;
				Sub.add(a);
				i++;	
				}
			}
			if(i<L2.size()||Sub.get(Sub.size()-1)<0)
			{
				Sub.clear();
				Sub.add(0);
			}	
			return Sub;
		}
		
		public static LinkedList<Integer> Multiply(LinkedList<Integer> L1,LinkedList<Integer> L2)
		{
			 LinkedList<Integer> answer=new LinkedList<Integer>();
			 ListIterator<Integer> operand2Iter = L2.listIterator(0);
	        for (int i = 0; i < (L2.size()); i++) 
	        {
	            int operand2Digit = operand2Iter.next().intValue();
	            ListIterator<Integer> operand1Iter = L1.listIterator(0);
	            for (int j = 0; j < L1.size(); j++) 
	            {
	                int operand1Digit = operand1Iter.next().intValue();
	                int product = operand1Digit * operand2Digit;
	                int shiftAmount = i + j;
	                
	                
	                for (int insertPositionFromRight = shiftAmount; product != 0; insertPositionFromRight++) 
	                {
	                    int digit = product % 10;
	                    product = product / 10;
	                    int numNodesNeeded = insertPositionFromRight - answer.size() + 1;
	                    for (int k = 0; k < numNodesNeeded; k++) 
	                    {
	                        answer.addLast(0);
	                    }
	                    int resultIndex = insertPositionFromRight;
	                    int curVal = answer.get(resultIndex).intValue();
	                    int newVal = curVal + digit;
	                    if (newVal >= 10) 
	                    {
	                        newVal -= 10;
	                        product++;
	                    }
	                    answer.set(resultIndex, newVal);
	                }
            }
        }

        return answer;
	}

		
		

		public static LinkedList<Integer> Power(LinkedList<Integer> L1,LinkedList<Integer> L2)
		{	
			 if(L2.size()==1&&L2.getFirst()==1)
			 {
				 return L1;
			 }
			 else
			 {
				LinkedList<Integer> Power=new LinkedList<Integer>();
					int a=0,flag=0;
					for(int i=0;i<L2.size();i++)
					{
						int Divisor;				
						
						a=a*10+L2.get(i);
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
					temp=Power(L1,Power);
					if(L2.getLast()%2==0)
					{
						return Multiply(temp, temp);
					}
					else
					{
						temp = Multiply(temp,temp);
						return Multiply(L1,temp);
					}

			 }
		}

		public static void main(String[] args) throws Exception
	{
		
	      int linenum=0,Counter=0,OperandCount=0,LeftOp=0,RightOp=0,LeftToOp=0;
	      String[] Operations = new String[100];
	      Scanner in = new Scanner(System.in);
	      LinkedList[] AllOperends = new LinkedList[1000];
	      
	      LinkedList[] Q=new LinkedList[1010];
	      char[] ch1=new char[1002];

	      
	   
	      while(linenum!=1001) 
	      {
	    	  linenum = in.nextInt();
	    	  Operations[Counter] = linenum+in.next();
	    	  Counter++;
	      }


		
		for(int RowCounter=0;Operations[RowCounter]!=null;RowCounter++)
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
								LinkedList<Integer> a1=Add(Q[LeftOp],Q[RightOp]);
								char ch=Operations[RowCounter].charAt(1);
								Q[OperandCount]=a1;
								ch1[OperandCount]=ch;
								OperandCount++;

							}
						else
						{
							Q[LeftToOp]=Add(Q[LeftOp],Q[RightOp]);
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
							LinkedList<Integer> a1=Subtract(Q[LeftOp],Q[RightOp]);
							char ch=Operations[RowCounter].charAt(1);
							Q[OperandCount]=a1;
							ch1[OperandCount]=ch;
							OperandCount++;

						}
					else
					{
						Q[LeftToOp]=Subtract(Q[LeftOp],Q[RightOp]);
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
							LinkedList<Integer> a1=Multiply(Q[LeftOp],Q[RightOp]);
							char ch=Operations[RowCounter].charAt(1);
							Q[OperandCount]=a1;
							ch1[OperandCount]=ch;
							OperandCount++;

						}
					else
					{
						Q[LeftToOp]=Multiply(Q[LeftOp],Q[RightOp]);
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
							LinkedList<Integer> a1=Power(Q[LeftOp],Q[RightOp]);
							char ch=Operations[RowCounter].charAt(1);
							Q[OperandCount]=a1;
							ch1[OperandCount]=ch;
							OperandCount++;

						}
					else
					{
						Q[LeftToOp]=Power(Q[LeftOp],Q[RightOp]);
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
					Q[OperandCount]=a1;
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
					String Str = NumToStr(Q[LeftOp]);
					int i=Integer.parseInt(Str);
					if(i==0)
					{
						continue;
					}
					else
					{
						RowCounter=Integer.parseInt(Operations[RowCounter].charAt(3)+"")-2;
						//System.out.println("ROW"+RowCounter);
					}
					
					
					
				}
				else
				{
					while(Operations[RowCounter].charAt(1)!=ch1[LeftOp])
					{
						LeftOp++;
					}
					String Str=NumToStr(Q[LeftOp]);
					System.out.println(Str);
					LeftOp=0;
				}
				
			}
			LeftOp=0;
			
		}
		
		
		
/*		
		String Str="x=100";
		LinkedList<Integer> L=StrToNum(Str);
		LinkedList<Integer> L1=StrToNum("x=64");
		LinkedList<Integer> Sum,Sub;
		Sum=Add(L,L1);
 		Str=NumToStr(Sum);
		System.out.println(Str);
		Sub=Subtract(L,L1);
 		Str=NumToStr(Sub);
		System.out.println(Str);
		Multiply(L, L1);
		
		
		System.out.println("**");
		L1=StrToNum("x=100");
		Str=NumToStr(L1);
		Str=new StringBuffer(Str).reverse().toString();
		System.out.println(Str);
		L1=StrToNum("  "+Str);
		
		System.out.println("****");
		LinkedList<Integer> P= new LinkedList<Integer>();
		P=Power(L, L1);
		
		String PString="";
		System.out.println("NO");
		PString=NumToStr(P);
		System.out.println("***");
		System.out.println(PString);
		*/
	}
	
}





