package game;


public class Player {

    private int getOutOfJailFreeCard = 0;
    public void setGetOutOfJailFreeCard(int getOutOfJailFreeCard) {
        this.getOutOfJailFreeCard = getOutOfJailFreeCard;
    }
    public int getGetOutOfJailFreeCard(){return getOutOfJailFreeCard;}

    private int balance;

    private String name;

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public int getBalance() { return this.balance; }

    /**
     * Adds amount to player balance
     * @param balanceChange
     * Amount to be added
     */

    public void changeBalance(int balanceChange) {
        balance += balanceChange;
    }
    private int position;
    public int getPosition() { return this.position; }
    public void setPosition(int newPosition){ this.position = newPosition; }

    public Player(int startingBalance){
        this.balance = startingBalance;
    }

}
