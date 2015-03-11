
/* vinay parekar (vpp130130)
 * 
 * @author Vinay Parekar
 * @email-id vpp130130@utdallas.edu
 * @version 1.0
 * 
 * Code for permutations satisfying precedence constraints
 *
 */


public class Constraint_perm {			// public class to compute permutation with constraints
public static int count;				// To count no permutations with constraints
public static int size,constr,verb;  	// Size of array, constr - no of constraint pair, verb - verbose option
public static int[] constr_array; 		// constraint array for precedence of two values
public static int[] A; 					// Global array to store array values

public static void visit(int v){		// Visit function with verbose input to calculate permutation count and display permutations 
	if(v==0){							// when verbose == o, calculate count alone. 
		count++;						// count increment
	}else if(v>0){						// when verbose > o, calculate count and different permutations.
		for(int k=0; k<size ; k++){
			if(A[k] != 0){
				System.out.print(A[k]);
			}
		}
		count++;						//count increment
		System.out.println();
	}	
}

public static void permute(int[] A,int i,int v){  // calculates different permutations  
	if(i == 0){							// when A[] contains all values for a perticular permutation.
		boolean flag = true;			// flag set to true
		int target;						// to get precedence pair values
		for(int k=0;k<constr*2;k=k+2){	// Loop to iterate through Constraint Array
			target = constr_array[k];	// target set to first value of precedence 
			if(flag == false){ 			// if flag is still set to false then did not follow the last constraint ,do not go visit 
					flag=true;
				return;}
			for(int j= 0;j<size ;j++){	// loop to iterate through A[]
				if(A[j] == target){		// target value found in A[]
					target = constr_array[k+1];		// target value set to next value in same precedence pair
					if(flag == true){				// if flag is true , set it to false
						flag = false;
					}else{							//if flag is false , set it to true
						flag = true;
					}
				}
				
			}
		}
		if(flag == false){ 				// if flag is still set to false then did not follow the last constraint ,do not go visit
			return;}					// return do not visit
		visit(v);						// all constraint passed, can go visit	
	}
	else {
	for(int k=0; k<size ; k++){		// code to generate permutations recursively
		if(A[k] == 0){
			A[k]=i;
			permute(A, i-1,v);
			A[k]=0;
		}
	}
	}
}


public static void main(String[] args) {
	 size = Integer.parseInt(args[0]);					// set Size 
	 constr = Integer.parseInt(args[1]);				// set number of constraint value 
	 verb = Integer.parseInt(args[2]);					// set value of verbose input
	 
	 if(verb < 0 ){										// check for invalid verbose input
		 System.out.println("Invalid verbose input");
		 System.exit(0);
	 }
	 
	 constr_array = new int[constr*2];					// loop to set constraint precedence pair
	 for(int i=0;i<constr*2;i++){
		 constr_array[i] = Integer.parseInt(args[i+3]); 
	 }
	 
	 
	 A= new int[size];
	 for(int i=0; i<size ; i++){						// initialize the A[]
		 A[i] = 0;
	 }
	 
	 long start = System.currentTimeMillis();
	 permute(A,size,verb);								// call to permutation function
	 long last = System.currentTimeMillis();
	 System.out.println("Count:" + count);					// Display count
	 System.out.println("Execution Time:" + (last-start));	// Execution time for the program
	 
}
}
