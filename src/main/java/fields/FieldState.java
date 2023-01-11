package fields;

import game.Player;

public class FieldState {

    private int numOfHouses;
    public int getNumOfHouses() {return this.numOfHouses;}
    public void setNumOfHouses(int newNumOfHouses) {this.numOfHouses = newNumOfHouses;}
    private Player owner;
    public Player getOwner() {return this.owner;}
    public void setOwner(Player newOwner) {this.owner = newOwner;}
    public boolean hasOwner() {return this.owner != null;}
    private boolean pawned;
    public boolean isPawned() {return this.pawned;}
    public void setPawned(boolean newPawnedBool) {this.pawned = newPawnedBool;}

    public FieldState()
    {
        this.numOfHouses = 0;
        this.owner = null;
        this.pawned = false;
    }
}
