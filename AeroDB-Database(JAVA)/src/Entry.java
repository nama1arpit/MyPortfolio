import java.util.ArrayList;

public class Entry{

//Defining attributes of ENTRY linked list
	Node root;
	public int size;

//Constructor
	public Entry(){
		this.root = null;
		this.size = 0;
	}

//Function to clone an instance of ENTRY linked list
	public Entry clone(){
		Node cursor = this.root;
		Entry temp = new Entry();
		if(this.root == null){
			return temp;
		}
		Node new_node_first = new Node(cursor.key, cursor.value);
		temp.root = new_node_first;
		temp.size+=1;
		Node cursor_copy = temp.root;
		while(cursor.next!=null){
			cursor = cursor.next;
			Node new_node_copy = new Node(cursor.key, cursor.value);
			cursor_copy.next = new_node_copy;
			temp.size+=1;
			cursor_copy = cursor_copy.next;
		}
		return temp;
	}

//Function to add a node in ENTRY linked list
	public void add(String key, ArrayList<Integer> value){
		Node new_node = new Node(key, value);

		if(this.root==null){
			this.root = new_node;
		}else{
			Node temp = this.root;
			this.root = new_node;
			this.root.next = temp;
		}
		size+=1;
	}

//Function to get the Arraylist stored in NODE with given key
	public ArrayList<Integer> getValue(String key){
		Node cursor = this.root;

		if(cursor==null){
			return null;
		}
		while((cursor!=null) && (!cursor.key.equals(key))){
				cursor = cursor.next;
		}
		if(cursor==null){
			return null;
		}else{
			return cursor.value;
		}
	}

//Function to give size of ENTRY linked list
	public int size(){
		return size;
	}

//Function to print all the keys in present state
	public void getKeys(){
		Node cursor = this.root;
		if(cursor==null){
			System.out.println("no keys");
			return;
		}
		while(cursor!=null){
			System.out.println(cursor.key);
			cursor = cursor.next;
		}
	}

//Function to print all entries in pretty manner
	public void getEntries(){
		Node cursor = this.root;
		if(cursor==null){
			System.out.println("no entries");
			return;
		}
		while(cursor!=null){
			System.out.print(cursor.key + " ");
			pprint(cursor.value);
			System.out.println();
			cursor = cursor.next;
		}
	}

//Function to remove the NODE with given key
	public void remove(String key){
		Node cursor = this.root;

		if(cursor.key.equals(key) && cursor.next==null){
			this.root = null;
			this.size = 0;
			return;
		}else if(cursor.key.equals(key)){
			this.root = cursor.next;
			this.size-=1;
			return;
		}
		while((cursor!=null) && !(cursor.next.key.equals(key))){
			cursor = cursor.next;
		}

		Node temp = cursor.next.next;
		cursor.next = temp;
		this.size-=1;
		return;

	}

//Function to append a value to ArrayList corresponding to key
	public void append(String key, int val){
		Node cursor = this.root;

		while((cursor!=null) && (!cursor.key.equals(key))){
				cursor = cursor.next;
		}

			cursor.value.add(val);
			return;
		
	}

//Function to PUSH a value in ArrayList corresponding to key
	public void push(String key, int val){
		Node cursor = this.root;

		if(cursor==null){
			System.out.println("no such key");
			return;
		}
		while((cursor!=null) && (!cursor.key.equals(key))){
				cursor = cursor.next;
		}
		if(cursor==null){
			System.out.println("no such key");
			return;
		}else{
			cursor.value.add(0, val);
			return;
		}
	}

//Function to get the sum of the values in the given ArrayList
	public int getSum(String key){
		int sum = 0;
		ArrayList<Integer> temp = this.getValue(key);
		for(int i = 0; i < temp.size(); i++){
			sum += temp.get(i);
		}
		return sum;
	}

//Function to get the reversed ArrayList corresponding to given key
	public void reverse(String key){
		ArrayList<Integer> temp = new ArrayList<>(this.getValue(key));

		int size = temp.size();
		for(int i = 0 ; i < size ; i++){
			this.getValue(key).add(0, temp.get(i));
			this.getValue(key).remove(size);
		}
	}

//Function to Implementing the UNIQ command
	public void uniq(String key){
		ArrayList<Integer> temp = this.getValue(key);
		for(int i = 0; i < temp.size()-1 ; i++ ){
			if(temp.get(i)==temp.get(i+1)){
				temp.remove(i);
				i-=1;
			}
		}
		return;
	}

//Function to determine the symmetric difference of two sets
	public ArrayList<Integer> diff(ArrayList<Integer> val1, ArrayList<Integer> val2){
		ArrayList<Integer> temp = new ArrayList<>(val1);
		for(int i = 0 ; i < val2.size() ; i++){
			for(int j = 0 ; j < val1.size() ; j++){
				if (val1.get(j)==val2.get(i)){
					temp.remove(val1.get(j));
				}
			}
		}
		return temp;
	}

//Function to determine the Intersection of two sets
	public ArrayList<Integer> inter(ArrayList<Integer> val1, ArrayList<Integer> val2){
		ArrayList<Integer> temp = new ArrayList<>();
		for(int i = 0 ; i < val1.size() ; i++){
			for(int j = 0 ; j < val2.size() ; j++){
				if(val1.get(i)==val2.get(j)){
					temp.add(val1.get(i));
				}
			}
		}
		return temp;
	}

//Function to determine the Union of two sets
	public ArrayList<Integer> union(ArrayList<Integer> val1, ArrayList<Integer> val2){

		int check = 0;
		for(int i = 0 ; i < val1.size() ; i++){
			check = 0;
			for(int j = 0 ; j < val2.size() ; j++){
					if (val1.get(i)==val2.get(j)){
						check = 1;
					}
				}
				if (check!=1){
					val2.add(val1.get(i));
				}
			}
		return val2;
	}

//Function to determine the Cartesian Product of two sets
	public ArrayList<ArrayList<Integer>> cartprod(ArrayList<ArrayList<Integer>> val1, ArrayList<Integer> val2){
		ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
		for(int i = 0 ; i < val1.size() ; i++){
			for(int j = 0 ; j < val2.size() ; j++){
				ArrayList<Integer> element = new ArrayList<>(val1.get(i));
				element.add(val2.get(j));
				temp.add(element);
			}
		}
		return temp;
	}

//PrettyPrint: Function to print a list in a 'pretty' manner
	public static void pprint(ArrayList<Integer> entry){
		System.out.print("[");
		for(int i = 0 ; i < entry.size() ; i++){
			System.out.print(entry.get(i));
			if (i != entry.size()-1){
				System.out.print(" ");
			}
		}
		System.out.print("]");
	}

//Function to convert an ArrayList to set with distinct elements
	public ArrayList<Integer> toDistinct(ArrayList val){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0 ; i < val.size() ; i++){
			if(!temp.contains(val.get(i))){
				temp.add((Integer)val.get(i));
			}
		}
		return temp;
	}

//Function to equate two ENTRY linked list
	public void equates(Entry entry){
		this.root = entry.root;
		this.size = entry.size;
	}

}
