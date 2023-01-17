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
        BuyableField roskilde = gameController.chosenStreetField("Rødovrevej", player);
        assertEquals("Rødovrevej",roskilde.getName());

    }

    @Test
    void checkIfHouseCanBeSoldEvenly() {
        GameController gameController = new GameController();
        BuyableField field = (BuyableField) GameBoard.getSingleton().getFields()[1];
        BuyableField field2 = (BuyableField) GameBoard.getSingleton().getFields()[3];
        field2.getState().setNumOfHouses(2);
        field.getState().setNumOfHouses(1);
        assertTrue(gameController.canSellOneMoreHouse(field2));
    }
    @Test
    void checkIfHouseCanBeSoldUnevenly() {
        GameController gameController = new GameController();
        BuyableField field = (BuyableField) GameBoard.getSingleton().getFields()[1];
        BuyableField field2 = (BuyableField) GameBoard.getSingleton().getFields()[3];
        field2.getState().setNumOfHouses(2);
        field.getState().setNumOfHouses(1);
        assertFalse(gameController.canSellOneMoreHouse(field));
    }

    @Test
    void checkIfHouseCanBeSoldWhenZero() {
        GameController gameController = new GameController();
        BuyableField field = (BuyableField) GameBoard.getSingleton().getFields()[1];
        BuyableField field2 = (BuyableField) GameBoard.getSingleton().getFields()[3];
        field2.getState().setNumOfHouses(0);
        field.getState().setNumOfHouses(0);
        assertFalse(gameController.canSellOneMoreHouse(field2));
    }

    @Test
    void checkIfHouseCanBeBoughtEvenly(){
        GameController gameController = new GameController();
        BuyableField field = (BuyableField) GameBoard.getSingleton().getFields()[1];
        BuyableField field2 = (BuyableField) GameBoard.getSingleton().getFields()[3];
        Player player = new Player(20000);
        field.getState().setOwner(player);
        field2.getState().setOwner(player);
        field2.getState().setNumOfHouses(0);
        field.getState().setNumOfHouses(0);
        assertTrue(gameController.canBuildOneMoreHouse(field2, player));
    }
    @Test
    void checkIfHouseCanBeBoughtUnevenly() {
        GameController gameController = new GameController();
        BuyableField field = (BuyableField) GameBoard.getSingleton().getFields()[1];
        BuyableField field2 = (BuyableField) GameBoard.getSingleton().getFields()[3];
        Player player = new Player(20000);
        field.getState().setOwner(player);
        field2.getState().setOwner(player);
        field2.getState().setNumOfHouses(1);
        field.getState().setNumOfHouses(0);
        assertFalse(gameController.canBuildOneMoreHouse(field2, player));
    }
    @Test
    void checkIfHouseCanBeBoughtWhenBroke() {
        GameController gameController = new GameController();
        BuyableField field = (BuyableField) GameBoard.getSingleton().getFields()[1];
        BuyableField field2 = (BuyableField) GameBoard.getSingleton().getFields()[3];
        Player player = new Player(0);
        field.getState().setOwner(player);
        field2.getState().setOwner(player);
        field2.getState().setNumOfHouses(0);
        field.getState().setNumOfHouses(0);
        assertFalse(gameController.canBuildOneMoreHouse(field2, player));
    }
    @Test
    void checkIfBankruptyWorks(){
        GameController gameController = new GameController();
        Player player = new Player(2000);
        player.changeBalance(-2000);
        assertFalse(gameController.bankrupty(player));    }
}

