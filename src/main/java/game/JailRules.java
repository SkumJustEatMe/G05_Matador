package game;


public class JailRules {

private GameController gameController;

    //Function that takes -1000 from the player balance and let them roll again
public static void PayOutOfJail(Player player){

    player.changeBalance(-1000);

}


    //Function to give an extra turn if you roll equals
public String ExtraTurn(){


}



//Function to force people to pay -1000 to get out of jail (round 3)
public static void ForceOutOfJail(Player player){

    player.changeBalance(-1000);


}





}