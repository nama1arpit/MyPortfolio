import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class AeroDB {

//The Help Statement
	private static final String HELP =
		"BYE   clear database and exit\n"+
		"HELP  display this help message\n"+
		"\n"+
		"LIST KEYS       displays all keys in current state\n"+
		"LIST ENTRIES    displays all entries in current state\n"+
		"LIST SNAPSHOTS  displays all snapshots in the database\n"+
		"\n"+
		"GET <key>    displays entry values\n"+
		"DEL <key>    deletes entry from current state\n"+
		"PURGE <key>  deletes entry from current state and snapshots\n"+
		"\n"+
		"SET <key> <value ...>     sets entry values\n"+
		"PUSH <key> <value ...>    pushes values to the front\n"+
		"APPEND <key> <value ...>  appends values to the back\n"+
		"\n"+
		"PICK <key> <index>   displays value at index\n"+
		"PLUCK <key> <index>  displays and removes value at index\n"+
		"POP <key>            displays and removes the front value\n"+
		"\n"+
		"DROP <id>      deletes snapshot\n"+
		"ROLLBACK <id>  restores to snapshot and deletes newer snapshots\n"+
		"CHECKOUT <id>  replaces current state with a copy of snapshot\n"+
		"SNAPSHOT       saves the current state as a snapshot\n"+
		"\n"+
		"MIN <key>  displays minimum value\n"+
		"MAX <key>  displays maximum value\n"+
		"SUM <key>  displays sum of values\n"+
		"LEN <key>  displays number of values\n"+
		"\n"+
		"REV <key>   reverses order of values\n"+
		"UNIQ <key>  removes repeated adjacent values\n"+
		"SORT <key>  sorts values in ascending order\n"+
		"\n"+
		"DIFF <key> <key ...>   displays set difference of values in keys\n"+
		"INTER <key> <key ...>  displays set intersection of values in keys\n"+
		"UNION <key> <key ...>  displays set union of values in keys\n"+
		"CARTPROD <key> <key ...>  displays set union of values in keys";

	public static void bye() {
		System.out.println("bye");
	}

	public static void help() {
		System.out.println(HELP);
	}

//Function for Wrong Commands
	public static void wrong_comm(){
		System.out.println("Wrong Command!");
	}

	public static void main(String[] args) {
//Making new instance of Snapshot and Entry class
		Snapshot snap = new Snapshot();
		Entry entries = new Entry();

//Initialsing Scanner and taking inputs
		Scanner input = new Scanner(System.in);
		String command = "\n";
		while(!(command.toLowerCase().equals("bye"))){
			//To insure we are getting an empty line after each command
			if(command!="\n"){
				System.out.println();
			}
			System.out.print("> ");
			command = input.nextLine();
			String[] command_word = command.split(" ");

			if (command_word[0].toLowerCase().equals("help")){
				help();

			//Implementing LIST KEYS, LIST ENTRIES and LIST SNAPSHOTS
			}else if(command_word[0].toLowerCase().equals("list")){

				if(command_word.length!=2){  //Checking for correctness of command
					wrong_comm();
				}else if(command_word[1].toLowerCase().equals("keys")){
						entries.getKeys(); //Printing all the keys

				}else if(command_word[1].toLowerCase().equals("entries")){

						entries.getEntries();  //Printing all the entries

				}else if(command_word[1].toLowerCase().equals("snapshots")){

						snap.getSnapshots();  // Printing all the Snapshots

				}else{
					wrong_comm();
				}

			//Impleneting GET command
			}else if(command_word[0].toLowerCase().equals("get")){
				if(command_word.length!=2){
					wrong_comm();
				}else if(entries.getValue(command_word[1])!=null){
					Entry.pprint(entries.getValue(command_word[1])); //Printing values in pretty manner!
					System.out.println();
				}else{
					System.out.println("no such key");
				}

			//Implementing DEL command
			}else if(command_word[0].toLowerCase().equals("del")){
				if(command_word.length!=2){
					wrong_comm();
				}else if(entries.getValue(command_word[1])==null){
					System.out.println("no such key");
				}else{
					entries.remove(command_word[1]);	//Removing the required key-value pair
					System.out.println("ok");
				}

			//Impleneting PURGE command
			}else if(command_word[0].toLowerCase().equals("purge")){
				if(command_word.length!=2){
					wrong_comm();
					continue;
				}else if(entries.getValue(command_word[1])==null && (!snap.isKeyThere(command_word[1]))){ //Checking the existence of the key
					System.out.println("ok");
				}else{
					if(entries.getValue(command_word[1])!=null){
						entries.remove(command_word[1]);  //Removing from current state
					}
					if(snap.isKeyThere(command_word[1])){
						snap.removeAllKey(command_word[1]);  //Removing from all saved snapshots
					}
					System.out.println("ok");
				}


			//Impleneting SET command
			}else if(command_word[0].toLowerCase().equals("set")){
				if (command_word.length < 3){ //Checking correctness
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1])!=null){ //Replacing the current key-value pair as key is already there
					int size = entries.getValue(command_word[1]).size();

					try{

						for (int i = 0 ; i < command_word.length -2 ; i++){
							Integer.parseInt(command_word[i+2]);
						}

					}catch(NumberFormatException e){
						System.out.println("Please input integers in value of the key");
						continue;
					}

					for (int i = 0 ; i < size ; i++){
						entries.getValue(command_word[1]).remove(0);
					}
					for (int i = 0 ; i < command_word.length -2 ; i++){
						entries.getValue(command_word[1]).add(Integer.parseInt(command_word[i+2]));
					}

					System.out.println("ok");
					continue;
				}

				String key = command_word[1];
				ArrayList<Integer> list = new ArrayList<>();
				try{
					for (int i = 2; i < command_word.length ; i++){
					list.add(Integer.parseInt(command_word[i]));
					}
				}catch(NumberFormatException e){
					System.out.println("Please input integers in value of the key");
					continue;
				}
				entries.add(key, list);  //Adding the new key-value pair
				System.out.println("ok");

			//Impleneting PUSH command
			}else if(command_word[0].toLowerCase().equals("push")){
				if (command_word.length < 3){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				//Pushing the value in list
				try{
					for(int i = 2 ; i < command_word.length; i++){
						Integer.parseInt(command_word[i]);
					}
				}catch(NumberFormatException e){
					System.out.println("Please input integers in value of the key");
					continue;
				}

				for(int i = 2 ; i < command_word.length; i++){
					entries.push(command_word[1], Integer.parseInt(command_word[i]));
				}
				System.out.println("ok");

			//Impleneting the APPEND command
			}else if(command_word[0].toLowerCase().equals("append")){
				if (command_word.length < 3){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				try{
					for(int i = 2 ; i < command_word.length; i++){
						Integer.parseInt(command_word[i]);
					}
				}catch(NumberFormatException e){
					System.out.println("Please input integers in value of the key");
					continue;
				}

				for(int i = 2 ; i < command_word.length; i++){
					entries.append(command_word[1], Integer.parseInt(command_word[i]));
				}
				System.out.println("ok");

			//Implementing PICK command
			}else if(command_word[0].toLowerCase().equals("pick")){
				if (command_word.length!=3){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				//Checking consistency of index
				try{
					if(Integer.parseInt(command_word[2]) > entries.getValue(command_word[1]).size() || Integer.parseInt(command_word[2]) < 1){
						System.out.println("index out of range");
						continue;
					}
					System.out.println(entries.getValue(command_word[1]).get(Integer.parseInt(command_word[2])-1));
				}catch(NumberFormatException e){
					System.out.println("Please input integers in index of the key");
					continue;
				}

			//Implementing PLUCK command
			}else if(command_word[0].toLowerCase().equals("pluck")){
				if (command_word.length!=3){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				//Checking consistency of index
				try{
					if(Integer.parseInt(command_word[2]) > entries.getValue(command_word[1]).size() || Integer.parseInt(command_word[2]) < 1){
						System.out.println("index out of range");
						continue;
					}
					//Removing and showing the removed value
					System.out.println(entries.getValue(command_word[1]).get(Integer.parseInt(command_word[2])-1));
					entries.getValue(command_word[1]).remove(Integer.parseInt(command_word[2])-1);
				}catch(NumberFormatException e){
					System.out.println("Please input integers in index of the key");
					continue;
				}


			//Implementing POP command
			}else if(command_word[0].toLowerCase().equals("pop")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				if(entries.getValue(command_word[1]).size() == 0){
					System.out.println("nil");
					continue;
				}
				//Popping out the required key-value pair
				System.out.println(entries.getValue(command_word[1]).get(0));
				entries.getValue(command_word[1]).remove(0);

			//Implementing DROP command
			}else if(command_word[0].toLowerCase().equals("drop")){


				try{
					if(command_word.length!=2){
						wrong_comm();
						continue;
						//Checking the availability of snapshot with given id
					}else if(!snap.isSnapThere(Integer.parseInt(command_word[1]))){
						System.out.println("no such snapshot");
					}else{
						//Removing the required snapshot
						snap.remove(Integer.parseInt(command_word[1]));
						System.out.println("ok");
					}
				}catch(NumberFormatException e){
					System.out.println("Please input integers in id of the snapshot");
					continue;
				}

			//Implementing the ROLLBACK command
			}else if(command_word[0].toLowerCase().equals("rollback")){


				try{
					if(command_word.length!=2){
						wrong_comm();
						continue;
					}else if(!snap.isSnapThere(Integer.parseInt(command_word[1]))){
						System.out.println("no such snapshot");
						continue;
					}else{
						entries = snap.rollback(Integer.parseInt(command_word[1]));
						System.out.println("ok");
					}
				}catch(NumberFormatException e){
					System.out.println("Please input integers in id of the snapshot");
					continue;
				}


			//Implementing the CHECKOUT command
			}else if(command_word[0].toLowerCase().equals("checkout")){


			try{
				if(command_word.length!=2){
					wrong_comm();
					continue;
				}else if(!snap.isSnapThere(Integer.parseInt(command_word[1]))){
					System.out.println("no such snapshot");
					continue;
				//Checking if entry is empty
				}else if(snap.get(Integer.parseInt(command_word[1])) == null){
					entries.root = null;
					entries.size = 0;
					System.out.println("ok");
				}else{
				entries.equates(snap.get(Integer.parseInt(command_word[1])).clone());
				System.out.println("ok");
				}

			}catch(NumberFormatException e){
				System.out.println("Please input integers in id of the snapshot");
				continue;
			}

			//Implementing SNAPSHOT command
			}else if(command_word[0].toLowerCase().equals("snapshot")){
				if(command_word.length!=1){
					wrong_comm();
					continue;
				}
				int id = snap.size();
				Entry temp = new Entry();
				temp.equates(entries.clone());
				snap.snapshot(id+1, temp);
				System.out.println("saved as snapshot " + Integer.toString(id+1));

			//Implementing the MIN command
			}else if(command_word[0].toLowerCase().equals("min")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				//Checking the existence of key
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				System.out.println(Collections.min(entries.getValue(command_word[1])));

			//Implementing MAX command
			}else if(command_word[0].toLowerCase().equals("max")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				System.out.println(Collections.max(entries.getValue(command_word[1])));
			//Implementing SUM command
			}else if(command_word[0].toLowerCase().equals("sum")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				System.out.println(entries.getSum(command_word[1]));

			//Implementing the LEN command
			}else if(command_word[0].toLowerCase().equals("len")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				System.out.println(entries.getValue(command_word[1]).size());

			//Implementing the REV command
			}else if(command_word[0].toLowerCase().equals("rev")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				entries.reverse(command_word[1]);
				System.out.println("ok");

			//Implementing the UNIQ command
			}else if(command_word[0].toLowerCase().equals("uniq")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				entries.uniq(command_word[1]);
				System.out.println("ok");

			//Implementing the SORT command
			}else if(command_word[0].toLowerCase().equals("sort")){
				if (command_word.length!=2){
					wrong_comm();
					continue;
				}
				if(entries.getValue(command_word[1]) == null){
					System.out.println("no such key");
					continue;
				}
				//Sorting the ArrayList
				Collections.sort(entries.getValue(command_word[1]));
				System.out.println("ok");

			//Implementing the DIFF command
			}else if(command_word[0].toLowerCase().equals("diff")){			//Ambiguity in command!...what difference
				if (command_word.length < 3){
					wrong_comm();
					continue;
				}
				int check = 0;
				//Confirming the existence of all keys
				for(int i = 1 ; i < command_word.length ; i++){
					if(entries.getValue(command_word[i])==null){
						check+=1;
					}
				}
				if(check>0){
					System.out.println("no such key");
					continue;
				}

				ArrayList<Integer> temp1 = new ArrayList<>(entries.getValue(command_word[1]));
				temp1 = entries.toDistinct(temp1);
				for(int i = 2 ; i < command_word.length ; i++){
					ArrayList<Integer> temp2 = new ArrayList<Integer>(entries.getValue(command_word[i]));
					temp2 = entries.toDistinct(temp2);
					temp1 = entries.union((entries.diff(temp1, temp2)),(entries.diff(temp2, temp1)));
				}
				Collections.sort(temp1);
				//pretty printing the list
				Entry.pprint(temp1);
				System.out.println();

			//Implementing the INTER command
			}else if(command_word[0].toLowerCase().equals("inter")){    //Ambiguity-what to do about multiple values
				if (command_word.length==1){
					wrong_comm();
					continue;
				}
				//Confirming the existence of all keys
				int check = 0;
				for(int i = 1 ; i < command_word.length ; i++){
					if(entries.getValue(command_word[i])==null){
						check+=1;
					}
				}
				if(check>0){
					System.out.println("no such key");
					continue;
				}

				ArrayList<Integer> temp1 = new ArrayList<>(entries.getValue(command_word[1]));
				temp1 = entries.toDistinct(temp1);
				for(int i = 2 ; i < command_word.length ; i++){
					ArrayList<Integer> temp2 = new ArrayList<Integer>(entries.getValue(command_word[i]));
					temp2 = entries.toDistinct(temp2);
					temp1 = entries.inter(temp1, temp2);
				}
				//Pretty printing the sorted list
				Collections.sort(temp1);
				Entry.pprint(temp1);
				System.out.println();

			//Implementing the UNION command
			}else if(command_word[0].toLowerCase().equals("union")){		//Ambiguity-what to do about multiple values
				if (command_word.length==1 ){
					wrong_comm();
					continue;
				}
				//Confirming the existence of all keys
				int check = 0;
				for(int i = 1 ; i < command_word.length ; i++){
					if(entries.getValue(command_word[i])==null){
						check+=1;
					}
				}
				if(check>0){
					System.out.println("no such key");
					continue;
				}

				ArrayList<Integer> temp1 = new ArrayList<>(entries.getValue(command_word[1]));
				temp1 = entries.toDistinct(temp1);
				for(int i = 2 ; i < command_word.length ; i++){
					ArrayList<Integer> temp2 = new ArrayList<Integer>(entries.getValue(command_word[i]));
					temp2 = entries.toDistinct(temp2);
					temp1 = entries.union(temp1, temp2);
				}
				//Pretty printing the sorted list
				Collections.sort(temp1);
				Entry.pprint(temp1);
				System.out.println();

			//Implementing the CARTPROD command
			}else if(command_word[0].toLowerCase().equals("cartprod")){
				if (command_word.length==1){
					wrong_comm();
					continue;
				}
				//Confirming the existence of all keys
				int check = 0;
				for(int i = 1 ; i < command_word.length ; i++){
					if(entries.getValue(command_word[i])==null){
						check+=1;
					}
				}
				if(check>0){
					System.out.println("no such key");
					continue;
				}

				ArrayList<ArrayList<Integer>> temp1 = new ArrayList<>();
				for(int i = 0 ; i < entries.getValue(command_word[1]).size() ; i++){
					ArrayList<Integer> element = new ArrayList<>();
					element.add(entries.getValue(command_word[1]).get(i));
					temp1.add(element);
				}
				for(int i = 2 ; i < command_word.length ; i++){
					ArrayList<Integer> temp2 = new ArrayList<>(entries.getValue(command_word[i]));
					temp1 = entries.cartprod(temp1, temp2);
				}
				//Pretty printing the list
				System.out.print("[");
				for(int i = 0 ; i < temp1.size() ; i++){
					System.out.print(" ");
					Entry.pprint(temp1.get(i));
				}
				System.out.println(" ]");

			//Implementing the BYE command
			}else if(command.toLowerCase().equals("bye")){
				bye();
				return;

			//Checking for invalid Command
			}else{
				wrong_comm();
			}

		}

	}
}
