
public class PermComb1 {
public static int count;
public static int size;
public static int[] A;

public static void visit(int v){
	for(int k=0; k<size ; k++){
		if(A[k] != 0){
			System.out.print(A[k]);
		}
	}
		count++;
	//System.out.print(" "+count);
	System.out.println();
}
public static void permute(int[] A,int i){
	if(i == 0){
		visit(size);
	}
	else {
	for(int k=0; k<size ; k++){
		if(A[k] == 0){
			A[k]=i;
			//System.out.print(i);
			permute(A, i-1);
			//System.out.println();
			A[k]=0;
		}
	}
	}
}

public static void comb(int[] A,int i, int k){
	if(k == 0)
	{
		visit(3);
	}
	else if(i<k){
		return;
	}else{	
	
			A[i-1]=i;
			comb(A, i-1,k-1);
			A[i-1]=0;
			comb(A,i-1,k);
		}
	
	}



public static void main(String[] args) {
	 //size = Integer.parseInt(args[0]);
	size = 4;
	 A= new int[size];
	 for(int i=0; i<size ; i++){
		 A[i] = 0;
	 }
	 
	 permute(A,size);
	 
/*	 count = 0;
	 for(int i=0; i<size ; i++){
		 A[i] = 0;
	 }
	 System.out.println(" trying out combination now");
	 comb(A,size,2);
	 */
}
}
