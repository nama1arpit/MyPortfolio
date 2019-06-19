import java.util.ArrayList;

//Implementing NODE for ENTRY linked list
public class Node{
	public String key;
	public ArrayList<Integer> value;
	public Node next;

	public Node(String key, ArrayList<Integer> value){
		this.key = key;
		this.value = value;
		this.next = null;
	}
}
