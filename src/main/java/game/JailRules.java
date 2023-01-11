package game;


import fields.GameBoard;

public class JailRules {



    //Function that takes -1000 from the player balance and let them roll again
public static void PayOutOfJail(Player player){

    player.changeBalance(-1000);
    player.setjailed(false);

}

//Function to force people to pay -1000 to get out of jail (round 3)
public static void ForceOutOfJail(Player player){

    player.changeBalance(-1000);
    player.setjailed(false);
}

//if you roll equals 3 times in a row, force Jail

public static boolean ForceJail(int numberofequalrolls){
    return numberofequalrolls == 3;
}


}
