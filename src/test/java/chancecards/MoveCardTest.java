package chancecards;

import game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveCardTest {

    @Test
    void CheckIfCardMovesPlayer() {
        MoveCard card = new MoveCard(null,2);
        Player player = new Player(4000);
        player.setPosition(30);
        card.execute(player, false);
        assertEquals(32, player.getPosition());
    }
}