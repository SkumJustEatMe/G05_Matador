package chancecards;

import game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecieveOrPayCardTest {

    @Test
    void CheckIfPlayerBalanceChanges() {
        ChanceCard card = new RecieveOrPayCard(null, 1000);
        Player player = new Player(4000);
        card.execute(player);
        assertEquals(5000, player.getBalance());
    }
}