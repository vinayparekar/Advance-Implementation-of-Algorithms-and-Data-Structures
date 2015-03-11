 public class LinkedList {

	 	public char variable; 
	 	public ListNode node;
	 	
		public class ListNode {
          private int data;
          private ListNode next = null;
  
          public ListNode() {
        	//this.data = 0 ;
            this.next = null;
          }

          public ListNode(int inputData) {
            this.data = inputData;
            this.next = null;
          }

          public int getData() {
            return data;
          }

          public void setData(int data) {
            this.data = data;
          }

          public ListNode getNext() {
            return next;
          }

          public void setNext(ListNode next) {
            this.next = next;
          }
          
          public int listLength(ListNode lnode){
        	  int length = 0;
        	  while(lnode != null){
        		  length++;
        		  lnode = lnode.next;
        	  }
        	  return length;
          }
        } 
		
        public char getVariable() {
			return variable;
		}


		public void setVariable(char variable) {
			this.variable = variable;
		}
		
		public ListNode getNode() {
			return node;
		}
		
		public void setNode(ListNode node) {
			this.node = node;
		}
          
		public LinkedList(){
			variable = ' ';
			node = null;
		}
		
          public LinkedList(String temp_input){
        	  setVariable(temp_input.charAt(0));
        	  setNode(strToNum(temp_input.substring(2)));
        	  
          }
          
          public ListNode strToNum(String temp_String){
        	  ListNode Head=null,prev=null;
        	  ListNode ln;
        	  for(int i=0;i<temp_String.length();i++){
        		  ln = new ListNode();
        		  ln.setData(Character.getNumericValue((temp_String.charAt(i))));
        		  
        		  ln.setNext(prev);
        		  prev = ln;
        		  Head = ln;
        	  }
        	  
        	  return Head;
          }
          
          public String numToStr(){
        	  String str = "";
        	  ListNode lnode = node;
        	  
        	  while(lnode != null){
        		  str = lnode.getData() + str;
        		  System.out.print(lnode.getData() + "-->");
        		  lnode = lnode.getNext();
        	  }
        	  System.out.println();
        	  return str;
          }
          
          public LinkedList add(LinkedList ll1,LinkedList ll2){
        	LinkedList ll = new LinkedList();
        	ListNode Head = null,new_node = null;
        	ListNode ln = new ListNode() ;
        	ListNode ln1 = ll1.getNode();
        	ListNode ln2 = ll2.getNode();
        	int carry = 0;
        	
        	 while(ln1 != null || ln2 != null){
        		 

        		 if(Head == null){
        			 Head = ln;
        		 }else{
            		 new_node = new ListNode();
            		 ln.setNext(new_node);
            		 ln = new_node;
        		 }
        		 
        		 if(ln1 != null){
        			 ln.data = ln.data + ln1.data+carry;
        			 ln1=ln1.getNext();
        			 carry = 0;
        		 }
        		 if(ln2 != null){
        			 ln.data = ln.data + ln2.data+carry;
        			 ln2=ln2.getNext();
        			 carry = 0;
        		 }

        		 carry = ln.data / 10;
        		 ln.data = ln.data % 10;

        	 }
        	 if(carry > 0){
        		 ln.data = carry; 
        	 }
        	 ll.setNode(Head);
        	
        	return ll;  
          }
          
          public LinkedList sub(LinkedList ll1,LinkedList ll2){
          	LinkedList ll = new LinkedList();
          	ListNode Head=null,new_node=null;
          	ListNode ln = new ListNode();
          	ListNode ln1 = ll1.getNode();
          	ListNode ln2 = ll2.getNode();
          	int carry = 0;
          	
          	if(ln1.listLength(ln1) < ln2.listLength(ln2)){
          		ln.data = 0;
          		ll.setNode(ln);
          		return ll;
          	}
          	
          	 while(ln1 != null || ln2 != null){
          		 
        		 if(Head == null){
        			 Head = ln;
        		 }else{
            		 new_node = new ListNode();
            		 ln.setNext(new_node);
            		 ln = new_node;
        		 }
          		 
          		 if(ln1 != null){
          			 if(carry == 1){
          				ln.data = ln.data + ln1.data - 1; 
          				carry = 0;
          			 }else{
          				 ln.data = ln.data + ln1.data;
          			 }
          			 ln1=ln1.getNext();
          		 }
          		 
          		 if(ln2 != null ){
          			 if(ln.data < ln2.data){
          				 ln.data = 10 + ln.data - ln2.data;
          				 carry = 1;
          			 }else{
          				 ln.data = ln.data - ln2.data;
          			 }
          			 ln2=ln2.getNext(); 
          		 	}
          		// System.out.print(ln.data + "==>");
          	 	}
          	 
          	 if(carry > 0){
           		ln.data = 0;
           		ll.setNode(ln);
           		return ll;
          		 
          	 }
          	 ll.setNode(Head);
          	
          	return ll;  
            }
          
          public static void main(String[] args) {
        	  String[] input = new String[args.length];
      		String[] input_line = new String[args.length];
      		 LinkedList[] ll= new LinkedList[args.length];
      		 int list_count = 0;
      		 
      		System.out.println("argument length : "+args.length);
      		for(int i = 0;i<args.length;i=i+2) {
      		     
      			 input_line[i] = args[i];
      			 input[i] = args[i+1];
      			
      			
      		    // System.out.println(input_line[i] + "   " + input[i]);
      		     
      		     if(input[i].contains("+")){
      		    	char chr =input[i].charAt(0);
      		    	char ch =input[i].charAt(2);
      		    	char ch1=input[i].charAt(4);
      		    	int first=-1,second=1,add_var=-1;
      		    	for(int j=0;j<list_count;j++){

  		    		 if(ll[j].variable == ch){
  		    			 first = j;
  		    		 }
  		    		 if( ll[j].variable == ch1){
  		    			 second = j;
  		    		 }
  		    		 if(ll[j].variable == chr){
  		    			 add_var = j;
  		    		 } 
      		    	}
      		    	if(add_var == -1){
      		    		ll[list_count] = new LinkedList();
      		    		add_var = list_count;
      		    		list_count++;
      		    	}
      		    	
      		    	ll[add_var]=ll[first].add(ll[first],ll[second]);
      		    	ll[add_var].setVariable(chr);
      		    	
      		     }else if(input[i].contains("-")){
      		    	 
      		    	char chr =input[i].charAt(0);
      		    	char ch =input[i].charAt(2);
      		    	char ch1=input[i].charAt(4);
      		    	int first=-1,second=-1,sub_var=-1;
      		    	for(int j=0;j<list_count;j++){

  		    		 if(ll[j].variable == ch){
  		    			 first = j;
  		    		 }
  		    		 if( ll[j].variable == ch1){
  		    			 second = j;
  		    		 }
  		    		 if(ll[j].variable == chr){
  		    			 sub_var = j;
  		    		 } 
      		    	}
      		    	if(sub_var == -1){
      		    		ll[list_count] = new LinkedList();
      		    		sub_var = list_count;
      		    		list_count++;
      		    	}
      		    	ll[sub_var]=ll[first].sub(ll[first],ll[second]);
      		    	ll[sub_var].setVariable(chr);
      		    	 
      		     }else if(input[i].contains("^")){
      		    	 
      		     }else if(input[i].contains("*")){
      		    	 
      		     }else if(input[i].contains("?")){
      		    	 
      		     }else if(input[i].contains("=")){ 
      		    	
      		    	ll[list_count] = new LinkedList(input[i]);
      		    	list_count++;
      		    	 
      		     }else{
      		    	
      		    	 char ch= input[i].charAt(0);
      		    	
      		    	 for(int j=0;j<list_count;j++){
      		    		 if(ll[j].getVariable() == ch){
      		    			 String str = ll[j].numToStr();
      		    			 System.out.println(str);
      		    		 }
      		    	 }
      		    	 
      		     }
      		     
      		}
		}
          
      }
