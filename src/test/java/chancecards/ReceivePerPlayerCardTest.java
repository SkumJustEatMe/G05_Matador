package chancecards;

import game.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReceivePerPlayerCardTest {

    @org.junit.jupiter.api.Test
    void checkPlayerReceivesMoney() {
        ArrayList<game.Player> players = new ArrayList<>();
        players.add(new Player(4000));
        players.add(new Player(4000));
        players.add(new Player(4000));
        int playerindex = 0;
        ReceivePerPlayerCard card = new ReceivePerPlayerCard(null, 500);
        card.execute(players, playerindex);
        assertEquals(5000, players.get(0).getBalance());
    }

    @org.junit.jupiter.api.Test
    void checkPlayerPaysMoney(){
        ArrayList<game.Player> players = new ArrayList<>();
        players.add(new Player(4000));
        players.add(new Player(4000));
        players.add(new Player(4000));
        int playerindex = 0;
        ReceivePerPlayerCard card = new ReceivePerPlayerCard(null, 500);
        card.execute(players, playerindex);
        assertEquals(3500, players.get(1).getBalance());
        assertEquals(3500, players.get(2).getBalance());
    }

}