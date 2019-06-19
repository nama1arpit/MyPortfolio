import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Atomination {

	// Defining Static Scanner Object
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {

		String command = "";

		// Loop to ask for commands
		while(input.hasNextLine()){

			String[] commands = input.nextLine().split(" ");
			command = commands[0];
			// Implementing HELP command
			if(command.equals("HELP")){
				System.out.println("HELP	displays this help message\n"+
								   "QUIT	quits the current game\n\n"+
								   "DISPLAY	draws the game board in terminal\n"+
								   "START	<number of players> <width> <height> starts the game\n"+
								   "PLACE	<x> <y> places an atom in a grid space\n"+
								   "UNDO	undoes the last move made\n"+
								   "STAT	displays game statistics\n"+
								   "SAVE	<filename> saves the state of the game\n"+
								   "LOAD	<filename> loads a save file\n");
			}else if(command.equals("START")){// Implementing START command
				try{
					if(commands.length > 4){
						System.out.println("Too Many Arguments");
						continue;
					}else if(commands.length < 4){
						System.out.println("Missing Argument");
						continue;
						//Handle Exception
					}else if(Integer.parseInt(commands[3]) < 2 || Integer.parseInt(commands[3]) > 255 || Integer.parseInt(commands[2]) < 2 || Integer.parseInt(commands[2]) > 255 || Integer.parseInt(commands[1]) > 4 || Integer.parseInt(commands[1]) < 2){
						System.out.println("Invalid command arguments");
						continue;
					}else{
						// Initialising Grid and Players to start game
						int num_player = Integer.parseInt(commands[1]);
						int turn = 0;
						int width = Integer.parseInt(commands[2]);
						int  height = Integer.parseInt(commands[3]);
						ArrayList<Player> players = new ArrayList<>();
						Map<List<Integer>, Grid> gridPlane = new HashMap<List<Integer>, Grid>();

						String[] players_name = {"Red", "Green", "Purple", "Blue"};
						String[] players_name_display = {"R","G","P","B"};

						for(int i = 0 ; i < num_player ; i++){
							players.add(new Player(players_name[i]));
						}

						for(int x = 0 ; x < width ; x++){
							for(int y = 0 ; y < height ; y++){
								gridPlane.put(new ArrayList<>(List.of(x,y)), new Grid());
							}
						}

						System.out.println("Game Ready");

						// Noting width, height and number of players in moves_hist list which will help in UNDO and SAVE command
						ArrayList<Byte> moves_hist = new ArrayList<>();
						moves_hist.add(Byte.parseByte(String.valueOf(width)));
						moves_hist.add(Byte.parseByte(String.valueOf(height)));
						moves_hist.add(Byte.parseByte(String.valueOf(num_player)));

						System.out.println("Red's Turn");
						// Invoking start Function
						Atomination.start(players_name, turn, num_player, gridPlane, width, height, players, players_name_display, moves_hist);

					}
				}catch(NumberFormatException e){ //handling Exception for wrong inputs
					System.out.println("Invalid command arguments\n");
					continue;
				}
				// Implementing QUIT command
			}else if(command.equals("QUIT")){
				System.out.println("Bye!");
				input.close();
				break;
				// Implementing DISPLAY command outside the GAME
			}else if(command.equals("DISPLAY")){
				System.out.println("Game Not In Progress");
				continue;
				// Implementing STAT command outside the GAME
			}else if(command.equals("STAT")){
				System.out.println("Game Not In Progress");
				continue;
				// Implementing PLACE command outside the GAME
			}else if(command.equals("PLACE")){
				System.out.println("Game Not In Progress");
				continue;
				// Implementing UNDO command outside the GAME
			}else if(command.equals("UNDO")){
				System.out.println("Game Not In Progress");
				continue;
				// Implementing SAVE command outside the GAME
			}else if(command.equals("SAVE")){
				System.out.println("Game Not In Progress");
				continue;
				// Implementing LOAD command
			}else if(command.equals("LOAD")){
				try(InputStream load_file = new FileInputStream(commands[1])){
					byte[] byte_file = load_file.readAllBytes();
					Atomination.load(byte_file, 1);
				}catch(IOException e){
					System.out.println("Cannot Load Save\n");//Handling Exception if the given File do not exists
					continue;
				}
			}else{//Other Invalid Commands go here
				System.out.println("Invalid Command");
				continue;

			}



		}
	}

	//Load Function to handle loading a game from a file. It also aids in Implementing UNDO Command
	public static void load(byte[] moves, int load){
		// Getting data from moves list and Initialising variables to start new Game with these specifications
		int num_player = moves[2];
		int turn = 0;
		int width = moves[0];
		int  height = moves[1];
		ArrayList<Player> players = new ArrayList<>();
		Map<List<Integer>, Grid> gridPlane = new HashMap<List<Integer>, Grid>();

		String[] players_name = {"Red", "Green", "Purple", "Blue"};
		String[] players_name_display = {"R","G","P","B"};

		for(int i = 0 ; i < num_player ; i++){
			players.add(new Player(players_name[i]));
		}

		for(int x = 0 ; x < width ; x++){
			for(int y = 0 ; y < height ; y++){
				gridPlane.put(new ArrayList<>(List.of(x,y)), new Grid());
			}
		}
		//Placing atoms on gridPlane, so as to start the game where it was left
		for(int i = 0 ; i < (moves.length-3)/4 ; i++ ){
			int x = moves[i*4 + 3];
			int y = moves[i*4 + 4];
			int recursionTimes = 0;
			boolean is_placed = Grid.place(x, y, gridPlane, width, height, turn, players, recursionTimes);
			if(is_placed){
				Player prev_player = players.get(turn%num_player);
				players.get(turn%num_player).addNumMoves();
				turn+=1;
				while(true){
					if((players.get(turn%num_player).getNumMoves() > 0 && players.get(turn%num_player).getGridsOwned() == 0) || (players.get(turn%num_player).isLost() == true)){
						players.get(turn%num_player).lost();
						turn+=1;
					}else{
						break;
					}
				}
				if(players.get(turn%num_player).equals(prev_player)){
					break;
				}
			}
		}

		//Checking if this function is used for LOAD or UNDO Command
		if(load == 1){
			System.out.println("Game Loaded");
		}else if(load == 0){
			System.out.println(players_name[turn%num_player]+"'s Turn");
		}

		ArrayList<Byte> moves_hist = new ArrayList<>();
		for(byte b : moves){
			moves_hist.add(b);
		}
		// Starting the game from middle!
		Atomination.start(players_name, turn, num_player, gridPlane, width, height, players, players_name_display, moves_hist);
	}

	// start function to Implement commands inside the game
	public static void start(String[] players_name, int turn, int num_player, Map<List<Integer>, Grid> gridPlane, int width, int height, ArrayList<Player> players, String[] players_name_display, ArrayList<Byte> moves_hist){
		while(input.hasNextLine()){
			System.out.println();
			// Taking inputs
			String[] commandsAfterStart = input.nextLine().split(" ");
			String commandsAfter = commandsAfterStart[0];
			if(commandsAfter.equals("START")){ //Implementing START command
				System.out.println("Invalid Command");
				continue;
				//Implementing PLACE command
			}else if(commandsAfter.equals("PLACE")){
				try{//Handling Exception for invalid inputs
					if(commandsAfterStart.length != 3){
						System.out.println("Invalid Coordinates");
						continue;
					}else if(Integer.parseInt(commandsAfterStart[1]) < 0 || Integer.parseInt(commandsAfterStart[1]) >= width || Integer.parseInt(commandsAfterStart[2]) < 0 || Integer.parseInt(commandsAfterStart[2]) > height){
						System.out.println("Invalid Coordinates");
						continue;
					}else{
						int x = Integer.parseInt(commandsAfterStart[1]);
						int y = Integer.parseInt(commandsAfterStart[2]);
						int recursionTimes = 0;
						boolean is_placed = Grid.place(x, y, gridPlane, width, height, turn, players, recursionTimes);
						if(is_placed){//Noting the successful moves in moves_hist list to aid SAVE and UNDO command
							moves_hist.add(Byte.parseByte(String.valueOf(x)));
							moves_hist.add(Byte.parseByte(String.valueOf(y)));
							moves_hist.add(Byte.parseByte(String.valueOf(0)));
							moves_hist.add(Byte.parseByte(String.valueOf(0)));
							Player prev_player = players.get(turn%num_player);
							players.get(turn%num_player).addNumMoves();
							turn+=1;
							while(true){
								if((players.get(turn%num_player).getNumMoves() > 0 && players.get(turn%num_player).getGridsOwned() == 0) || (players.get(turn%num_player).isLost() == true)){
								players.get(turn%num_player).lost();
								turn+=1;
								}else{
									break;
								}
							}//To declare the winner
							if(players.get(turn%num_player).equals(prev_player)){
								System.out.println(players_name[turn%num_player] + " Wins!");
								System.exit(0);
							}

							System.out.println(players_name[turn%num_player]+"'s Turn");
						}
					}
				}catch(NumberFormatException e){//Handling Exceptions
					System.out.println("Invalid Coordinates\n");
					continue;
				}
				//HELP command
			}else if(commandsAfter.equals("HELP")){
				System.out.println("HELP displays this help message\n"+
								   "QUIT quits the current game\n"+
								   "DISPLAY draws the game board in terminal\n"+
								   "START <number of players> <width> <height> starts the game\n"+
								   "PLACE <x> <y> places an atom in a grid space\n"+
								   "UNDO undoes the last move made\n"+
								   "STAT displays game statistics\n"+
								   "SAVE <filename> saves the state of the game\n"+
								   "LOAD <filename> loads a save file\n");
								   //STAT command
			}else if(commandsAfter.equals("STAT")){
				for(Player player : players){
					System.out.println("Player "+players_name[players.indexOf(player)] + ":");
					if(player.isLost()){
						System.out.println("Lost");
					}else{
						System.out.println("Grid Count: "+player.getGridsOwned());
					}
					if(!players.get(players.size()-1).equals(player)){
						System.out.println();
					}
				}
				//QUIT command
			}else if(commandsAfter.equals("QUIT")){
				System.out.println("Bye!");
				input.close();
				System.exit(0);
//DISPLAY command
			}else if(commandsAfter.equals("DISPLAY")){
				System.out.println();
				System.out.print("+");
				for(int i = 0 ; i < width-1 ; i++){
					System.out.print("---");
				}
				System.out.println("--+");

				for(int i = 0 ; i < height ; i++){
					for(int j = 0 ; j < width ; j++){
						System.out.print("|");
						if(gridPlane.get(List.of(j,i)).getOwner()!=null){
							System.out.print(players_name_display[players.indexOf(gridPlane.get(List.of(j,i)).getOwner())] + gridPlane.get(List.of(j,i)).getAtomCount());
						}else{
							System.out.print("  ");
						}
					}
					System.out.println("|");
				}

				System.out.print("+");
				for(int i = 0 ; i < width-1 ; i++){
					System.out.print("---");
				}
				System.out.println("--+");
				//UNDO command
			}else if(commandsAfter.equals("UNDO")){
				if(moves_hist.size() == 3){
					System.out.println("Cannot Undo");
					continue;
				}else{
					for(int i = 0 ; i < 4 ; i++){
						moves_hist.remove(moves_hist.size()-1);
					}
					byte[] moves = new byte[moves_hist.size()];
					int i = 0;
					for(Byte b : moves_hist){
						moves[i++] = b.byteValue();
					}//Calling load function
					Atomination.load(moves, 0);
				}
//SAVE command
			}else if(commandsAfter.equals("SAVE")){
				try{
					File f = new File(commandsAfterStart[1]);
					if(f.exists()){//File Already Exists case
						System.out.println("File Already Exists\n");
						continue;
					}
					OutputStream save_file = new FileOutputStream(f);
					byte[] save_file_bytes = new byte[moves_hist.size()];
					int i = 0;
					for(Byte b : moves_hist){
						save_file_bytes[i++] = b.byteValue();
					}
					save_file.write(save_file_bytes);
					save_file.close();
					System.out.println("Game Saved");
				}catch(IOException e){
					System.out.println("Game didn't save. Try again!");
					continue;
				}//Load command
			}else if(commandsAfter.equals("LOAD")){
				System.out.println("Restart Application To Load Save");
				continue;
			}else{
				System.out.println("Invalid Command");
				continue;
			}
		}
	}


}
