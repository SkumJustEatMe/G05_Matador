package game;

import fields.BuyableField;
import fields.GameBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void chosenField() {
        GameController gameController = new GameController();
        Player player = new Player(4000);
        GameBoard.getSingleton().getFields()[1].getState().setOwner(player);
        BuyableField roskilde = gameController.chosenField("Rødovrevej", player);
        assertEquals("Rødovrevej",roskilde.getName());

    }
}