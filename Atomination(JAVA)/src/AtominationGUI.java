import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AtominationGUI extends PApplet {

    private Map<List<Integer>, Grid> gridPlane;
    private int turn;
    private ArrayList<Player> players;
    private boolean finish;

    public AtominationGUI() {//Constructor
        this.gridPlane = new HashMap<List<Integer>, Grid>();
        for(int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 6 ; j++){
                gridPlane.put(new ArrayList<>(List.of(i,j)), new Grid());
            }
        }
        this.turn = 0;
        this.players =  new ArrayList<Player>();
        players.add(new Player("Red"));
        players.add(new Player("Green"));
        this.finish = false;
    }

    public void setup() {//Basic Setup
        frameRate(100);
        surface.setTitle("Atomination");
    }

    public void settings() {
        /// DO NOT MODIFY SETTINGS
        size(640, 384);
    }

    public void draw() {//Loading appropriate images 60 times a sec using gridPlane!
        PImage grid = loadImage("C:/Users/NM/Desktop/UNI_ACAD/INFO1113/Assignment2/Atomination/assets/tile.png");
        for(int i = 0 ; i < 10 ; i++){
            for(int j = 0 ; j < 6 ; j++){
                image(grid, i*grid.width, j*grid.height);
                if(gridPlane.get(List.of(i, j)).getOwner() != null){
                    if(gridPlane.get(List.of(i, j)).getOwner().getColor().equals("Red")){
                        if(gridPlane.get(List.of(i, j)).getAtomCount() == 1){
                            image(loadImage("C:/Users/NM/Desktop/UNI_ACAD/INFO1113/Assignment2/Atomination/assets/red1.png"), i*grid.width, j*grid.height);
                        }else if(gridPlane.get(List.of(i, j)).getAtomCount() == 2){
                            image(loadImage("C:/Users/NM/Desktop/UNI_ACAD/INFO1113/Assignment2/Atomination/assets/red2.png"), i*grid.width, j*grid.height);
                        }else if(gridPlane.get(List.of(i, j)).getAtomCount() == 3){
                            image(loadImage("C:/Users/NM/Desktop/UNI_ACAD/INFO1113/Assignment2/Atomination/assets/red3.png"), i*grid.width, j*grid.height);
                        }
                    }else if(gridPlane.get(List.of(i, j)).getOwner().getColor().equals("Green")){
                        if(gridPlane.get(List.of(i, j)).getAtomCount() == 1){
                            image(loadImage("C:/Users/NM/Desktop/UNI_ACAD/INFO1113/Assignment2/Atomination/assets/green1.png"), i*grid.width, j*grid.height);
                        }else if(gridPlane.get(List.of(i, j)).getAtomCount() == 2){
                            image(loadImage("C:/Users/NM/Desktop/UNI_ACAD/INFO1113/Assignment2/Atomination/assets/green2.png"), i*grid.width, j*grid.height);
                        }else if(gridPlane.get(List.of(i, j)).getAtomCount() == 3){
                            image(loadImage("C:/Users/NM/Desktop/UNI_ACAD/INFO1113/Assignment2/Atomination/assets/green3.png"), i*grid.width, j*grid.height);
                        }
                    }
                }
            }
        }
        if(this.finish == true){
            System.exit(0);
        }
    }

    public void mouseClicked(){//Handling a mouse click
        int x = mouseX/64;
        int y = mouseY/64;
        boolean placed = Grid.place(x, y, gridPlane, 10, 6, this.turn, this.players, 0);
        if(placed){
            Player prev_player = players.get(this.turn%2);
            players.get(this.turn%2).addNumMoves();
            this.turn+=1;
            while(true){
                if((players.get(this.turn%2).getNumMoves() > 0 && players.get(this.turn%2).getGridsOwned() == 0) || (players.get(this.turn%2).isLost() == true)){
                players.get(this.turn%2).lost();
                this.turn+=1;
                }else{
                    break;
                }
            }
            if(players.get(this.turn%2).equals(prev_player)){
                this.finish = true;
            }
        }
    }

    public static void go() {
        AtominationGUI.main("AtominationGUI");

    }

    public static void main(String[] args){
        go();

    }

}
