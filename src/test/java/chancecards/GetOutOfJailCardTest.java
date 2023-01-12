package chancecards;

import game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetOutOfJailCardTest {

    @Test
    void CheckIfPlayerGetCardInHand() {
        ChanceCard card = new GetOutOfJailCard(null);
        Player player = new Player(4000);
        card.execute(player);
        assertEquals(1, player.getGetOutOfJailFreeCard());
    }
}