import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class AtominationTest{
    @Test
    public void gridConstructionTest(){//For construction of grid
        Grid testGrid = new Grid();
        assertTrue(testGrid.getOwner() == null);
        assertTrue(testGrid.getAtomCount() == 0);
    }

    @Test
    public void playerConstructionTest(){//For construction of player
        Player testPlayer = new Player("Green");
        assertTrue(testPlayer.getColor().equals("Green"));
        assertTrue(testPlayer.getGridsOwned() == 0);
        assertTrue(testPlayer.isLost() == false);
        assertTrue(testPlayer.getNumMoves() == 0);
    }

    @Test
    public void gridSetOwnerTest(){//Test for set owner function
        Grid testGrid = new Grid();
        Player testPlayer = new Player("Purple");
        testGrid.setOwner(testPlayer);
        assertEquals(testPlayer, testGrid.getOwner());
        assertEquals(testPlayer.getColor(), testGrid.getOwner().getColor());
    }

    @Test
    public void gridAddAndSetAtomCountTest(){//Test for add and set atom count function
        Grid testGrid = new Grid();
        assertTrue(testGrid.getAtomCount() == 0);
        testGrid.addAtomCount();
        assertTrue(testGrid.getAtomCount() == 1);
        testGrid.setAtomCount(4);
        assertTrue(testGrid.getAtomCount() == 4);
    }

    @Test
    public void playerSetAndAddGridCountTest(){//Test for set and add grid count
        Player testPlayer = new Player("Blue");
        assertTrue(testPlayer.getGridsOwned() == 0);
        testPlayer.setGridsOwned(4);
        assertTrue(testPlayer.getGridsOwned() == 4);
        testPlayer.addGridsOwned(2);
        assertTrue(testPlayer.getGridsOwned() == 6);
    }

    @Test
    public void playerLostAndAddNumMovesTest(){//Test for lost and add num moves function
        Player testPlayer = new Player("Blue");
        assertTrue(testPlayer.isLost() == false);
        testPlayer.lost();
        assertTrue(testPlayer.isLost() == true);
        assertTrue(testPlayer.getNumMoves() == 0);
        testPlayer.addNumMoves();
        assertTrue(testPlayer.getNumMoves() == 1);
    }

    @Test
    public void gridCornerNullOwnerThenAddOwnerTest(){//Test for corner grid
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < 10 ; i++){
            for(int j = 0 ; j < 6 ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 0, gridPlane, 10, 6, turn, players, 0);
        Grid testGrid = gridPlane.get(List.of(0,0));

        assertTrue(isPlaced == true);
        assertTrue(testGrid.getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 1);
        assertTrue(testGrid.getOwner().equals(players.get(0)));
    }

    @Test
    public void gridCornerSomeOwnerThenAddSameOwnerTest(){//Test for corner grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced == true);
        turn+=1;
        boolean isPlaced2 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced2 == true);
        turn+=1;
        boolean isPlaced3 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced3 == true);
        Grid testGrid = gridPlane.get(List.of(0,0));

        assertTrue(testGrid.getAtomCount() == 0);
        assertTrue(players.get(0).getGridsOwned() == 2);
        assertTrue(testGrid.getOwner() == null);
        assertTrue(gridPlane.get(List.of(0,1)).getAtomCount() == 2);
        assertTrue(gridPlane.get(List.of(0,1)).getOwner().equals(players.get(0)));
    }

    @Test
    public void gridCornerSomeOwnerThenAddDiffOwnerTest(){//Test for corner grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced == true);
        turn+=1;
        boolean isPlaced2 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        Grid testGrid = gridPlane.get(List.of(0,0));

        assertTrue(isPlaced2 == false);
        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 1);
        assertTrue(players.get(1).getGridsOwned() == 0);
    }

    @Test
    public void gridCornerSomeOwnerThenAddDiffOwnerAfterExpansionTest(){//Test for corner grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced == true);
        turn+=1;
        assertTrue(gridPlane.get(List.of(0,1)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,1)).getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 1);
        boolean isPlaced2 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced2 == true);
        turn+=1;
        boolean isPlaced3 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced3 == true);
        turn+=1;
        boolean isPlaced4 = Grid.place(1, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced4 == true);
        turn+=1;
        boolean isPlaced5 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced5 == true);
        turn+=1;

        Grid testGrid = gridPlane.get(List.of(0,0));

        assertTrue(testGrid.getOwner() == null);
        assertTrue(testGrid.getAtomCount() == 0);
        assertTrue(players.get(1).getGridsOwned() == 0);
        assertTrue(players.get(0).getGridsOwned() == 4);
        assertTrue(gridPlane.get(List.of(0,1)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,1)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(0,2)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,2)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,1)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(1,1)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,0)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(1,0)).getAtomCount() == 2);
    }

    @Test
    public void gridSideSomeOwnerThenAddOtherOwnerTest(){//Test for SIDE grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced == true);
        turn+=1;
        boolean isPlaced2 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        Grid testGrid = gridPlane.get(List.of(0,1));

        assertTrue(isPlaced2 == false);
        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 1);
        assertTrue(players.get(1).getGridsOwned() == 0);
    }

    @Test
    public void gridSideSomeOwnerThenAddSameOwnerTest(){//Test for SIDE grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced == true);
        turn+=1;
        boolean isPlaced2 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced2 == true);
        turn+=1;
        boolean isPlaced3 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced3 == true);
        turn+=1;

        Grid testGrid = gridPlane.get(List.of(0,1));

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 2);
        assertTrue(players.get(1).getGridsOwned() == 1);
        assertTrue(gridPlane.get(List.of(0,0)).getOwner().equals(players.get(1)));
    }

    @Test
    public void gridSideSomeOwner2ThenAddSameOwnerTest(){//Test for SIDE grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced == true);
        turn+=1;
        boolean isPlaced2 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced2 == true);
        turn+=1;
        boolean isPlaced3 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced3 == true);
        turn+=1;
        boolean isPlaced4 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced4 == true);
        assertTrue(gridPlane.get(List.of(1,1)).getOwner().equals(players.get(1)));
        assertTrue(gridPlane.get(List.of(1,1)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,1)).getOwner().getGridsOwned() == 2);
        turn+=1;
        boolean isPlaced5 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced5 == true);
        turn+=1;

        Grid testGrid = gridPlane.get(List.of(0,1));

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 4);
        assertTrue(gridPlane.get(List.of(0,2)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(0,2)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(1,1)).getAtomCount() == 2);
        assertTrue(gridPlane.get(List.of(1,1)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(1,0)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,0)).getOwner().equals(players.get(0)));
    }

    @Test
    public void gridSideSomeOwner2ThenAddOtherOwnerAfterExpansionTest(){//Test for SIDE grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced == true);
        turn+=1;
        boolean isPlaced2 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced2 == true);
        turn+=1;
        boolean isPlaced3 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced3 == true);
        turn+=1;
        boolean isPlaced4 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced4 == true);
        turn+=1;

        Grid testGrid = gridPlane.get(List.of(0,1));

        assertTrue(testGrid.getOwner() == null);
        assertTrue(testGrid.getAtomCount() == 2);
        assertTrue(players.get(0).getGridsOwned() == 0);
        assertTrue(players.get(1).getGridsOwned() == 4);
        assertTrue(gridPlane.get(List.of(0,0)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(0,0)).getOwner().equals(players.get(1)));
        assertTrue(gridPlane.get(List.of(0,2)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(0,2)).getOwner().equals(players.get(1)));
        assertTrue(gridPlane.get(List.of(1,0)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,0)).getOwner().equals(players.get(1)));
        assertTrue(gridPlane.get(List.of(1,1)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,1)).getOwner().equals(players.get(1)));
    }

    @Test
    public void gridMiddleNullOwnerThenAddSomeOwnerTest(){//Test for middle grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced == true);

        Grid testGrid = gridPlane.get(List.of(1,1));

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 1);
    }

    @Test
    public void gridMiddleSomeOwnerThenAddSameOwnerTest(){//Test for middle grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced == true);
        boolean isPlaced2 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced2 == true);
        boolean isPlaced3 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced3 == true);

        Grid testGrid = gridPlane.get(List.of(1,1));

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 2);
        assertTrue(players.get(0).getGridsOwned() == 1);

        boolean isPlaced4 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced4 == true);
        boolean isPlaced5 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced5 == true);

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 3);
        assertTrue(players.get(0).getGridsOwned() == 1);

        boolean isPlaced6 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced6 == true);
        boolean isPlaced7 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced7 == true);

        assertTrue(testGrid.getOwner() == null);
        assertTrue(testGrid.getAtomCount() == 0);
        assertTrue(players.get(0).getGridsOwned() == 4);
    }

    @Test
    public void gridMiddleSomeOwnerThenAddDiffOwnerTest(){//Test for middle grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced == true);
        boolean isPlaced2 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        assertTrue(isPlaced2 == false);

        Grid testGrid = gridPlane.get(List.of(1,1));

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 1);
        assertTrue(players.get(1).getGridsOwned() == 0);
    }

    @Test
    public void gridMiddleSomeOwnerThenAddDiffOwnerExpansion1Test(){//Test for middle grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced == true);
        boolean isPlaced2 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced2 == true);
        boolean isPlaced3 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced3 == true);
        boolean isPlaced4 = Grid.place(1, 2, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced4 == true);
        boolean isPlaced5 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced5 == true);

        Grid testGrid = gridPlane.get(List.of(1,1));

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 2);
        assertTrue(gridPlane.get(List.of(0,0)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,0)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(0,2)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,2)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,2)).getOwner().equals(players.get(1)));
        assertTrue(gridPlane.get(List.of(1,2)).getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 3);
        assertTrue(players.get(1).getGridsOwned() == 1);
    }

    @Test
    public void gridMiddleSomeOwnerThenAddDiffOwnerExpansion2Test(){//Test for middle grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced == true);
        boolean isPlaced2 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced2 == true);
        boolean isPlaced3 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced3 == true);
        boolean isPlaced4 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced4 == true);
        boolean isPlaced5 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced5 == true);

        Grid testGrid = gridPlane.get(List.of(1,1));

        assertTrue(testGrid.getOwner().equals(players.get(0)));
        assertTrue(testGrid.getAtomCount() == 3);
        assertTrue(gridPlane.get(List.of(0,0)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,0)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(0,2)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,2)).getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 3);
        assertTrue(players.get(1).getGridsOwned() == 0);
    }

    @Test
    public void gridMiddleSomeOwnerThenAddDiffOwnerExpansion3Test(){//Test for middle grid
        int width = 10;
        int height = 6;
        Map<List<Integer>, Grid> gridPlane = new HashMap<>();
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height ; j++){
                gridPlane.put(new ArrayList<>(List.of(i, j)), new Grid());
            }
        }
        Player testPlayer1 = new Player("Red");
        Player testPlayer2 = new Player("Green");
        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);
        int turn = 0;

        boolean isPlaced = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced == true);
        boolean isPlaced2 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced2 == true);
        boolean isPlaced3 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced3 == true);
        boolean isPlaced4 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced4 == true);
        boolean isPlaced5 = Grid.place(0, 0, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced5 == true);
        boolean isPlaced6 = Grid.place(1, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced6 == true);
        boolean isPlaced7 = Grid.place(0, 1, gridPlane, width, height, turn, players, 0);
        turn+=1;
        assertTrue(isPlaced7 == true);

        Grid testGrid = gridPlane.get(List.of(1,1));

        assertTrue(testGrid.getOwner() == null);
        assertTrue(testGrid.getAtomCount() == 0);
        assertTrue(gridPlane.get(List.of(0,1)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,1)).getAtomCount() == 2);
        assertTrue(gridPlane.get(List.of(1,0)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(1,0)).getAtomCount() == 2);
        assertTrue(gridPlane.get(List.of(0,2)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(0,2)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(1,2)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(1,2)).getAtomCount() == 1);
        assertTrue(gridPlane.get(List.of(2,1)).getOwner().equals(players.get(0)));
        assertTrue(gridPlane.get(List.of(2,1)).getAtomCount() == 1);
        assertTrue(players.get(0).getGridsOwned() == 5);
        assertTrue(players.get(1).getGridsOwned() == 0);
    }

}


//javac -cp ".;C:\Users\NM\Desktop\UNI_ACAD\INFO1113\Assignment2\Atomination\lib\junit-4.12.jar;C:\Users\NM\Desktop\UNI_ACAD\INFO1113\Assignment2\Atomination\lib\hamcrest-core-1.3.jar" AtominationTest.java

//java -cp ".;C:\Users\NM\Desktop\UNI_ACAD\INFO1113\Assignment2\Atomination\lib\junit-4.12.jar;C:\Users\NM\Desktop\UNI_ACAD\INFO1113\Assignment2\Atomination\lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore AtominationTest
