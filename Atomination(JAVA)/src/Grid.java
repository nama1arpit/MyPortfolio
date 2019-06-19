import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Grid {
	private Player owner;
	private int atomCount;

	public Grid(){
		this.owner = null;
		this.atomCount = 0;
	}
//Basic Setters and Getter for attributes
	public Player getOwner(){
		return this.owner;
	}

	public int getAtomCount(){
		return this.atomCount;
	}

	public void setOwner(Player other){
		this.owner = other;
	}

	public void setAtomCount(int new_atomCount){
		this.atomCount = new_atomCount;
	}

	public void addAtomCount(){
		this.atomCount += 1;
	}

//Very Important place funstion
	public static boolean place(int x, int y, Map<List<Integer>, Grid> gridPlane, int width, int height, int turn, ArrayList<Player> players, int recursionTimes){
		Grid imp_grid = gridPlane.get(List.of(x,y));
		//Corner grids
		if((x == 0 || x == width-1) && (y == 0 || y == height-1)){
			if(imp_grid.getOwner()==null){
				imp_grid.setOwner(players.get(turn%(players.size())));
				imp_grid.setAtomCount(1);
				players.get(turn%(players.size())).addGridsOwned(1);
			}else if(imp_grid.getOwner() == players.get(turn%(players.size()))){
				imp_grid.getOwner().addGridsOwned(-1);
				imp_grid.setOwner(null);
				imp_grid.setAtomCount(0);
				Grid.expansion(x, y, width, height, gridPlane, turn, players, recursionTimes);

			}else if((imp_grid.getOwner() != players.get(turn%(players.size())))&&(recursionTimes==0)){
				System.out.println("Cannot Place Atom Here");
				return false;
			}else if((imp_grid.getOwner() != players.get(turn%(players.size())))&&(recursionTimes!=0)){
				imp_grid.getOwner().addGridsOwned(-1);
				imp_grid.setOwner(null);
				imp_grid.setAtomCount(0);
				Grid.expansion(x, y, width, height, gridPlane, turn, players, recursionTimes);

			}
			//Grids on sides excluding corners
		}else if(((x == 0 || x == width-1)&&(y!=0&&y!=height-1))||((y==0 || y==height-1)&&(x!=0&&x!=width-1))){
			if(imp_grid.getOwner() == null){
				imp_grid.setOwner(players.get(turn%(players.size())));
				imp_grid.setAtomCount(1);
				players.get(turn%(players.size())).addGridsOwned(1);
			}else if(imp_grid.getOwner() == players.get(turn%(players.size()))){
				if(imp_grid.getAtomCount() == 1){
					imp_grid.addAtomCount();
				}else if(imp_grid.getAtomCount() == 2){
					imp_grid.getOwner().addGridsOwned(-1);
					imp_grid.setOwner(null);
					imp_grid.setAtomCount(0);
					Grid.expansion(x, y, width, height, gridPlane, turn, players, recursionTimes);
				}

			}else if((imp_grid.getOwner() != players.get(turn%(players.size())))&&(recursionTimes==0)){
				System.out.println("Cannot Place Atom Here");
				return false;

			}else if((imp_grid.getOwner() != players.get(turn%(players.size())))&&(recursionTimes!=0)){
				if(imp_grid.getAtomCount() == 1){
					imp_grid.getOwner().addGridsOwned(-1);
					imp_grid.setOwner(players.get(turn%(players.size())));
					imp_grid.addAtomCount();
					players.get(turn%(players.size())).addGridsOwned(1);
				}else if(imp_grid.getAtomCount() == 2){
					imp_grid.getOwner().addGridsOwned(-1);
					imp_grid.setOwner(null);
					Grid.expansion(x, y, width, height, gridPlane, turn, players, recursionTimes);
				}
			}

		}else{//Grids in the middle
			if(imp_grid.getOwner()==null){
				imp_grid.setOwner(players.get(turn%(players.size())));
				imp_grid.setAtomCount(1);
				players.get(turn%(players.size())).addGridsOwned(1);
			}else if(imp_grid.getOwner() == players.get(turn%(players.size()))){
				if(imp_grid.getAtomCount() == 1){
					imp_grid.addAtomCount();

				}else if(imp_grid.getAtomCount() == 2){
					imp_grid.addAtomCount();

				}else if(imp_grid.getAtomCount() == 3){
					imp_grid.getOwner().addGridsOwned(-1);
					imp_grid.setOwner(null);
					imp_grid.setAtomCount(0);
					Grid.expansion(x, y, width, height, gridPlane, turn, players, recursionTimes);
				}

			}else if((imp_grid.getOwner() != players.get(turn%(players.size())))&&(recursionTimes==0)){
				System.out.println("Cannot Place Atom Here");
				return false;

			}else if((imp_grid.getOwner() != players.get(turn%(players.size())))&&(recursionTimes!=0)){
				if(imp_grid.getAtomCount() == 1){
					imp_grid.getOwner().addGridsOwned(-1);
					imp_grid.setOwner(players.get(turn%(players.size())));
					players.get(turn%(players.size())).addGridsOwned(1);
					imp_grid.addAtomCount();

				}else if(imp_grid.getAtomCount() == 2){
					imp_grid.getOwner().addGridsOwned(-1);
					imp_grid.setOwner(players.get(turn%(players.size())));
					players.get(turn%(players.size())).addGridsOwned(1);
					imp_grid.addAtomCount();

				}else if(imp_grid.getAtomCount() == 3){
					imp_grid.getOwner().addGridsOwned(-1);
					imp_grid.setOwner(null);
					imp_grid.setAtomCount(0);
					Grid.expansion(x, y, width, height, gridPlane, turn, players, recursionTimes);
				}
			}

		}
		return true;
	}

//expansion function to expand owner
	public static void expansion(int x, int y, int width, int height, Map<List<Integer>, Grid> gridPlane, int turn, ArrayList<Player> players, int recursionTimes){
		if(x-1 >= 0 && x-1 < width && y >= 0 && y < height){
			Grid.place(x-1, y, gridPlane, width, height, turn, players, recursionTimes+1);
		}
		if(x+1 >= 0 && x+1 < width && y >= 0 && y < height){
			Grid.place(x+1, y, gridPlane, width, height, turn, players, recursionTimes+1);
		}
		if(x >= 0 && x < width && y-1 >= 0 && y-1 < height){
			Grid.place(x, y-1, gridPlane, width, height, turn, players, recursionTimes+1);
		}
		if(x >= 0 && x < width && y+1 >= 0 && y+1 < height){
			Grid.place(x, y+1, gridPlane, width, height, turn, players, recursionTimes+1);
		}
	}
}
