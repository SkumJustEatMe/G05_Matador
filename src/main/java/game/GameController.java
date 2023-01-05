package game;

import fields.*;
import java.util.ArrayList;

public class GameController
{
    private GUI gui;
    private Die die;
    private int currentDieRoll1 = 0;
    private int currentDieRoll2 = 0;
    private int sumOfDiceRoll = 0;
    private GameBoard gameBoard;
    private ArrayList<Player> players;

    public ArrayList<Player> getPlayers() { return this.players; }
    private Player getCurrentPlayer() { return this.players.get(indexOfCurrentPlayer); }
    private int indexOfCurrentPlayer;

    public GameController()
    {
        this.gameBoard = new GameBoard();
        this.gui = new GUI(this.gameBoard, this);
        this.die = new Die();
        this.players = new ArrayList<Player>();
        this.indexOfCurrentPlayer = 0;
    }

    private void addPlayersAndSetPosition(int numberOfPlayers)
    {
        for (int i = 1; i <= numberOfPlayers; i++)
        {
            this.players.add(new Player(0));
        }

        for (Player i : this.players)
        {
            i.setPosition(0);
        }

        setPlayerNames();
        }


    public void run()
    {
        this.initialize();
        this.startGameLoop();
    }

    private void initialize()
    {
        this.addPlayersAndSetPosition(this.getNumberOfPlayers());
        this.gui.addPlayersToBoard(this.players.size());
        this.gui.addCarsToBoard();
    }

    public void startGameLoop()
    {
        while(true)
        {
            getUserInputToBegin();
            rollDice();
            this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
            movePlayer();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            evaluateFieldAndExecute();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            this.gui.displayPlayerBalance();
            setNextPlayer();
        }
    }


    private void rollDice()
    {
        this.currentDieRoll1 = this.die.roll();
        this.currentDieRoll2 = this.die.roll();
        this.sumOfDiceRoll = this.currentDieRoll1 + this.currentDieRoll2;
    }

    private void setNextPlayer()
    {
        if (this.indexOfCurrentPlayer + 1 < this.players.size()) {
            this.indexOfCurrentPlayer++;
        }
        else {
            this.indexOfCurrentPlayer = 0;
        }
    }
    private void movePlayer()
    {
        int currentPosition = getCurrentPlayer().getPosition();

        if (hasReachedStartField())
        {
            getCurrentPlayer().setPosition(currentPosition + this.sumOfDiceRoll - this.gameBoard.getFieldList().length);
            getCurrentPlayer().changeBalance(4000);
        }
        else
        {
            getCurrentPlayer().setPosition(currentPosition + this.sumOfDiceRoll);
        }
    }

    private boolean hasReachedStartField()
    {
        return getCurrentPlayer().getPosition() + this.sumOfDiceRoll >= this.gameBoard.getFieldList().length;
    }

    private void evaluateFieldAndExecute()
    {
        Field field = this.gameBoard.getFieldList()[getCurrentPlayer().getPosition()];
        if (field instanceof EventField event)
        {
            executeEvent(event);
        }
    }

    private void executeEvent(EventField eventField)
    {
       if (eventField.getEvent() == Event.GOTOJAIL) {
            getCurrentPlayer().setPosition(this.gameBoard.getIndexOfJail());
       }
   }

    private int getNumberOfPlayers() {
        String result = (this.gui.displayPlayerSelectionButtons());
        return Integer.parseInt(String.valueOf(result.charAt(0)));
    }

    private void getUserInputToBegin() {
        if (this.gui.displayRollDiceButton(getCurrentPlayer().getName()).equals("Roll dice")) {
            return;
        }
    }

    private void setPlayerNames()
    {
        for (int i = 0; i < this.players.size(); i++)
        {
            this.players.get(i).setName(this.gui.getUserStringInput(i));
        }
    }
}