package chancecards;

import game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveCardTest {

    @Test
    void CheckIfCardMovesPlayer() {
        ChanceCard card = new MoveCard(null,2);
        Player player = new Player(4000);
        player.setPosition(30);
        card.execute(player);
        assertEquals(32, player.getPosition());

    }
}