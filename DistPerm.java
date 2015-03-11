import java.util.Scanner;


public class DistPerm {
	static int n,v;
	static int[] A;
	static int count;
	
	static void MergeSort(int[] A, int p, int r) {
        if (p < r) {
	    if (r-p > 11) {
                int q = (p+r)/2;
                MergeSort(A, p, q);
                MergeSort(A, q+1, r);
                Merge(A, p, q, r);
            } else {  // Insertion sort
		for(int i=p, j=i; i<r; j=++i) {
		    int ai = A[i+1];
		    while(ai < A[j]) {
			A[j+1] = A[j];
			if (j-- == p) {
			    break;
			}
		    }
		    A[j+1] = ai;
	 	}
	    }
	}
    }

    static void Merge(int[] A, int p, int q, int r) {
	int ls = q-p+1;
	int rs = r-q;
        int[] L = new int[ls];
        int[] R = new int[rs];
        for(int i=p; i<=q; i++) L[i-p] = A[i];
        for(int i=q+1; i<=r; i++) R[i-(q+1)] = A[i];
        
        int i = 0; int j = 0;
        for(int k=p; k<=r; k++) {
	    if ((j>=rs) || ((i<ls) && (L[i] <= R[j])))
		A[k] = L[i++];
	    else
		A[k] = R[j++];
	}
        return;
    }
    
    static void sort(){
        MergeSort(A, 0, n-1);
        
        
        for (int j = 0; j < A.length-1; j++) {
            if(A[j] > A[j+1]) {
		System.out.println("Sorting failed :-(");
		return;
	    }
        }
		System.out.println("Success!");
		//print();
    }
    
    public static void visit(int v){
    	for(int k=0; k<n ; k++){
    		if(A[k] != 0){
    			System.out.print(A[k]); 
    		}
    	}
    		count++;
    	//System.out.print(" "+count);
    	System.out.println();
    }
    
    static void lex(){
    	
    	sort();
    	for(int i=0;i<n;i++){
    		visit(A[i]);
    	}
    	while(true){
    		int j,b;
    		for(j=n; A[j]<A[j+1]; j--){break;}
    		if(j==0){break;}
    		for(b=0; A[j]<A[b]; b++){break;}
    		swap(A,j,b);	
    		reverse(A,j+1,n);
    		//visit();
    	}
    	
    }
    
    static void reverse(int[] A, int j, int n) {
		// TODO Auto-generated method stub
		for(int i=j,l=n; i<n ; i++,l--){
			swap(A,i,l);
		}
	}

	static void swap(int[] A,int a,int b){
    	int temp = A[a];
    	A[a] = A[b];
    	A[b] = temp;
    }
    
    static void print(){
		for(int i=0;i<n;i++){
			//A[i] = sc.nextInt();
			//A[i] = n-i;
			System.out.println(A[i]);
		}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		n = Integer.parseInt(args[0]);
		v = Integer.parseInt(args[1]);
		System.out.print("Enter " + n + "values :");
		Scanner sc = new Scanner(System.in);
		A = new int[n];
		int[] B = new int[n];
		for(int i=0;i<n;i++){
			A[i] = sc.nextInt();
			//A[i] = n-i;
		}
		long start = System.currentTimeMillis();
		lex();
		long last = System.currentTimeMillis();

		System.out.println(last-start);
		System.out.println();
		

		
		
		
	}

}
