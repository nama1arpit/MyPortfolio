//Implementing NODE_SNAP for SNAPSHOT linked list
public class Node_Snap{
  public int id;
  public Entry entry;
  public Node_Snap next;

  public Node_Snap(int id, Entry entry){
    this.id = id;
    this.entry = entry;
    this.next = null;
  }
}
