public class Snapshot {

//Defining attributes of SNAPSHOT linked list
	Node_Snap root;
	public int size;

//Constructor
	public Snapshot(){
		this.root = null;
		this.size = 0;
	}

//Function to take a snapshot of the current state
	public void snapshot(int id, Entry entry){
		Node_Snap new_node = new Node_Snap(id, entry);

		if(this.root == null){
			this.root = new_node;
		}else{
			Node_Snap temp = this.root;
			this.root = new_node;
			this.root.next = temp;
		}
		size++;
	}

//Function to return the size of the SNAPSHOT linked list
	public int size(){
		return size;
	}

//Function to print IDs of all the snapshots
	public void getSnapshots(){
		Node_Snap cursor = this.root;
		if(cursor==null){
			System.out.println("no snapshots");
			return;
		}
		while(cursor!=null){
			System.out.println(cursor.id);
			cursor = cursor.next;
		}
	}

//Function to check the existence of key in all the snapshots
	public boolean isKeyThere(String key){
		Node_Snap cursor = this.root;
		if(cursor==null){
			return false;
		}
		while(cursor!=null){
			if(cursor.entry.getValue(key)!=null){
				return true;
			}
			cursor = cursor.next;
		}
		return false;
	}

//Function to remove the key from all the snapshots
	public void removeAllKey(String key){
		Node_Snap cursor = this.root;
		while(cursor!=null){
			if(cursor.entry!=null){
				if(cursor.entry.getValue(key)!=null){
					cursor.entry.remove(key);
				}
			}

			cursor = cursor.next;
		}
	}

//Function to check the existence of snapshot with given ID
	public boolean isSnapThere(int id){
		Node_Snap cursor = this.root;
		if(cursor == null){
			return false;
		}
		while(cursor!=null){
			if(cursor.id == id){
				return true;
			}
			cursor = cursor.next;
		}
		return false;
	}

//Function to remove the snapshot with given ID
	public void remove(int id){
		Node_Snap cursor = this.root;
		if(cursor.id==id && cursor.next == null){
			this.root = null;
			this.size = 0;
			return;
		}else if(cursor.id == id ){
			this.root = cursor.next;
			this.size-=1;
			return;
		}
		while(cursor!=null){
			if(cursor.id > id+1){
				cursor.id -= 1;
			}else if(cursor.id == id+1){
				cursor.id-=1;
				Node_Snap temp = cursor.next.next;
				cursor.next = temp;
				this.size -=1;
				return;
			}
			cursor = cursor.next;
		}
	}

//Function to Implementing the ROLLBACK command
	public Entry rollback(int id){
		Node_Snap cursor = this.root;

		while(cursor!=null){
			if(cursor.id==id){
				return cursor.entry;
			}
			Node_Snap temp = cursor.next;
			this.remove(cursor.id);
			cursor = temp;
		}
		return null;
	}

//Function to get the ENTRY linked list corresponding to given ID
	public Entry get(int id){
		Node_Snap cursor = this.root;

		while(cursor!=null){
			if(cursor.id==id){
				return cursor.entry;
			}
			cursor = cursor.next;
		}
		return null;
	}

}
