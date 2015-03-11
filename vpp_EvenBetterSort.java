
import java.io.*;

public class vpp_EvenBetterSort {
	 
	static int n , diff = 0;
    static int MergeSort(int[] A, int[] B, int p, int r) {
        if (p < r) {
	    if (r-p > 11) {
                int q = (p+r)/2;
                int h1,h2;
                h1 = MergeSort(A, B, p, q);
                h2 = MergeSort(A, B, q+1, r);
                if(h1 != h2){
               	  	diff = 1;
               	 	Merge(B,A,p,q,r);
               	 	return (h1 + 1);
                }
                if(h1%2 ==1){Merge(B, A, p, q, r);
                }else{
                Merge(A, B, p, q, r);}
                return (h1 + 1);
                
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
		return 0;
	    }
	} else return 0;
    }

    static void Merge(int[] src, int[] dest, int p, int q, int r) {
        
        int i = p; int j = q+1;
       
        if(diff == 1){
	        for(int k=p; k<=r; k++) {
		    if ((j>r) || ((i<=q) && (src[i] <= dest[j])))
			dest[k] = src[i++];
		    else
			dest[k] = dest[j++];
	        }
	        diff = 0;
        }else{
        		for(int k=p; k<=r; k++) {
        		    if ((j>r) || ((i<=q) && (src[i] <= src[j])))
        			dest[k] = src[i++];
        		    else
        			dest[k] = src[j++];
        	        }
        }
        return;
    }

    public static void main(String[] args) throws IOException{
        n = Integer.parseInt(args[0]);
        int[] A = new int[n];
        int[] B = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = n-i;
        }
        System.out.println();
	long start = System.currentTimeMillis();
    int r = MergeSort(A, B, 0, n-1);
	long last = System.currentTimeMillis();
	if(r%2 == 0){
        for (int j = 0; j < A.length-1; j++) {
            if(A[j] > A[j+1] ) {
		System.out.println("Sorting failed :-(");
		return;
	    }
        }
        }else{
        	 for (int j = 0; j < B.length-1; j++) {
                 if(B[j] > B[j+1] ) {
     		System.out.println("Sorting failed :-(");
     		return;
     		
     		
     	    }
             }
        }
	System.out.println("Success!");
	System.out.println(last-start);
    }
}
