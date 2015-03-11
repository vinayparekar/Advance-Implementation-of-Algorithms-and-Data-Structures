import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * 
 * 
 * @author AMIT KUMAR AND DEEPAK NEGI
 * 
 * This class implements various  function like insert, find, delete,Find Min, Find Max, price hike, price Range.
 *
 */


public class dxn130730_axk131930_P6_Execute {

	public static dxn130730_axk131930_P6_RedBlack<Long,Row>  DataTree =new dxn130730_axk131930_P6_RedBlack<Long,Row> ();

	public static HashMap<String,dxn130730_axk131930_P6_RedBlack<Double,Row>> DataHash = new HashMap<String,dxn130730_axk131930_P6_RedBlack<Double,Row>>();

	public static Double TotalSum = 0.0;

	/*****************************************************************************
	 *  Test client
	 *****************************************************************************/
	public static void main(String[] args) { 
		dxn130730_axk131930_P6_Execute ex = new dxn130730_axk131930_P6_Execute();

		ex.processInput();



		System.out.println("TotalSum: "+TotalSum);




	}

	/**
	 * Method process the input entered by the user from the console.
	 * Stores each execution command.
	 */
	public  void processInput(){

		ArrayList<String> data = readinput();
		long start = System.currentTimeMillis();
		System.out.println("Start Time: "+start);

		for (int i=0; i< data.size() ;i++ ){
			//	System.out.println("Data: "+ data.get(i));
			TokenizeString(data.get(i),data);

		}
		long last = System.currentTimeMillis();
		System.out.println("End Time: "+last);
		System.out.println("TotalTimeTaken: "+ (last-start)+ "ms");

	}

	/**
	 * Method process the input entered by the user from the console.
	 * Stores each execution command.
	 */
	public  ArrayList<String> readinput() {

		ArrayList<String> inputString = new ArrayList<String>();

		Scanner scanIn = new Scanner(System.in);


		while (scanIn.hasNextLine()){
			String data  = scanIn.nextLine();
			if(data.equals("")){
				break;
			}
			if (data.charAt(0) != '#'){
				inputString.add(data);				
			}

		}
		scanIn.close();

		return inputString;

	}


	/**
	 * Tokenize each input string to get command and input parameters for the each function like find, delete,FindMaxPrice,FindMinPrice etc
	 * @param string
	 * @param data
	 */
	public  void TokenizeString (String string, ArrayList<String> data){

		String Token [] = string.split("[ ]+");

		if(Token[0].equals("Insert")){

			String str_name ="";

			for (int i=3; i<Token.length-1;i++){

				str_name =str_name+" "+Token[i];

			}

			TotalSum=TotalSum+ Insert(Long.parseLong(Token[1]),Double.parseDouble(Token[2]),str_name);

		}
		// gets the parameter for Find  functioanlity .
		else if(Token[0].equals("Find")){

			TotalSum=TotalSum+Find(Long.parseLong(Token[1]));




		}
		// gets the parameter for Delete  functionality .
		else if(Token[0].equals("Delete")){


			TotalSum=TotalSum+Delete(Long.parseLong(Token[1]));


		}


		// gets the parameter for FindMaxPrice  functionality .
		else if(Token[0].equals("FindMaxPrice")){


			TotalSum=TotalSum+findMaxPrice(Token[1]);


		}
		// gets the parameter for FindMinPrice  functionality .
		else if(Token[0].equals("FindMinPrice")){


			TotalSum=TotalSum+findMinPrice(Token[1]);


		}
		// gets the parameter for PriceHike  functionality .
		else if(Token[0].equals("PriceHike")){


			TotalSum=TotalSum+PriceHike(Long.parseLong(Token[1]), Long.parseLong(Token[2]), Double.parseDouble(Token[3]));


		}

		// gets the parameter for FindPriceRange  functionality .
		else if(Token[0].equals("FindPriceRange")){


			TotalSum=TotalSum+findPriceRange(Token[1], Double.parseDouble(Token[2]) , Double.parseDouble(Token[3]));


		}




	}


	/**
	 * Function implements insertion of Elements into RedBlack Tree. 
	 * Takes input id, price and name and return 0 or 1 if record already exist  with given id.
	 *
	 */
	public int Insert(long id,	Double price, String name){

		Row row= new Row(id, name, price);


		int temp= insertHashTree(name,row,price,DataTree.put(id, row,true));

		return temp;


	}

	/** 
	 * Insert data into  HashMap which are sorted according to the price value.
	 * @param name
	 * @param row
	 * @param price
	 * @param exist
	 * @return
	 */

	public int insertHashTree(String name, Row row, Double price,int exist){

		if(exist != 0){

			String [] name_split = name.split(" ");

			for (int i=1; i<name_split.length; i++){

				if(DataHash.containsKey(name_split[i])){

					dxn130730_axk131930_P6_RedBlack<Double,Row> rb=	DataHash.get(name_split[i]);
					rb.put(price, row,false);
				}
				else {
					dxn130730_axk131930_P6_RedBlack<Double,Row> rb = new dxn130730_axk131930_P6_RedBlack<Double,Row>();
					rb.put(price, row,false);
					DataHash.put(name_split[i], rb);
				}

			}

		}
		return exist;
	}



	/**
	 * Find the id in a tree and returns price of the id if  found  else 0.0
	 * 
	 * @param id
	 * @return
	 */

	public Double Find(long id){
		Row row =DataTree.get(id);
		if(row != null){
			Double temp = row.getPrice();

			return temp;
		}
		else {

			return 0.0;
		}

	}

	/**
	 * Deletes the id  from the Tree and HashMap.
	 * 
	 * @param id
	 * @return
	 */
	public long Delete(long id)
	{

		Row row =DataTree.delete(id);

		if(row != null){
			String []str = row.getName().split("[ ]+");


			long sum= 0;

			for(int i=1;i<str.length;i++){

				if(str[i] != "" && str[i] != " " ){

					sum= sum+ Long.parseLong(str[i]);

				}
			}

			deleteHashTree(row.getName(),row.getId(),row.getPrice());

			return sum;
		}
		else {

			return 0L;
		}

	}







	public void deleteHashTree(String name, long id, Double price) {
		// TODO Auto-generated method stub

		String delims = "[ ]+";
		String []name_part = name.split(delims);

		for (int i=0; i< name_part.length ;i++){
			if(name_part[i].length() > 0 ){

				dxn130730_axk131930_P6_RedBlack<Double,Row> tree = DataHash.get(name_part[i]);

				tree.delete(price,id);  
			}
		}

	}

	/**
	 * Find min price for a given str which matches the name of the element.
	 * @param str = name substring 
	 * @return the price of the element
	 */
	public Double findMinPrice(String str){

		dxn130730_axk131930_P6_RedBlack<Double,Row> tree =DataHash.get(str);
		Row row = tree.min();
		if(row ==null){

			return 0.0;
		}

		return row.getPrice();
	}


	/**
	 * Find max price for a given str which matches the name of the element.
	 * @param str = name substring 
	 * @return the price of the element
	 */


	public Double findMaxPrice(String str){

		dxn130730_axk131930_P6_RedBlack<Double,Row> tree =DataHash.get(str);

		Row row = tree.max();
		if(row == null){

			return 0.0;
		}

		return row.getPrice();


	}

	/**
	 * Increases price of by increase_percent of  all the elements between ids low_id and high_id
	 * @param low_id
	 * @param high_id
	 * @param increase_percent
	 * @return
	 */


	public Double PriceHike(long low_id, long high_id, Double increase_percent)
	{ 



		Double number = DataTree.inRange(low_id,high_id,increase_percent);

		return number;



	}

	/**
	 * Find price range 
	 * @param str
	 * @param low
	 * @param high
	 * @return
	 */

	public int findPriceRange(String str, Double low , Double high){

		dxn130730_axk131930_P6_RedBlack<Double,Row> tree =DataHash.get(str);
		int number = tree.inRange(low,high);

		return number;

	}






}


class  Row{


	private long id ; 
	private String name ;
	private Double price ;

	public Row(long id, String name, Double price) {
		this.id=id;
		this.name =name;
		this.price=price;

	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}





}
