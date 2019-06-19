public class Player {
	private int gridsOwned;
	private int num_moves;
	private boolean lost;
	private String color;

	public Player(String color){
		this.gridsOwned = 0;
		this.lost = false;
		this.num_moves = 0;
		this.color = color;
	}
//Basic setters and getters for attributes
	public int getGridsOwned(){
		return this.gridsOwned;
	}

	public void setGridsOwned(int new_gridsOwned){
		this.gridsOwned = new_gridsOwned;
	}

	public void addGridsOwned(int num){
		this.gridsOwned += num;
	}

	public int getNumMoves(){
		return this.num_moves;
	}

	public void addNumMoves(){
		this.num_moves += 1;
	}

	public boolean isLost(){
		return this.lost;
	}

	public void lost(){
		this.lost = true;
	}

	public String getColor(){
		return this.color;
	}
}
