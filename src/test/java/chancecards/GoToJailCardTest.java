package chancecards;

import game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoToJailCardTest {

    @Test
    void checkIfPlayerIsMovedToJail() {
        ChanceCard card = new GoToJailCard(null);
        Player player = new Player(4000);
        card.execute(player);
        assertEquals(player.getPosition(), 10);
    }
    @Test
    void CheckIfPlayerStatusIsChanged(){
        ChanceCard card = new GoToJailCard(null);
        Player player = new Player(4000);
        card.execute(player);
        assertTrue(player.isJailed());
    }

}