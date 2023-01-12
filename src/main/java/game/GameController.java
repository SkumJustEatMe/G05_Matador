package game;

import chancecards.*;
import fields.*;
import java.util.ArrayList;

public class GameController
{
    private GameBoardState gameBoardState;
    private GUI gui;
    private Die die;

    public int getCurrentDieRoll1() {
        return currentDieRoll1;
    }

    public int getCurrentDieRoll2() {
        return currentDieRoll2;
    }

    private int currentDieRoll1 = 0;
    private int currentDieRoll2 = 0;
    private int sumOfDiceRolls = 0;
    private ArrayList<Player> players;
    private DeckController deck = new DeckController();

    public ArrayList<Player> getPlayers() { return this.players; }
    private Player getCurrentPlayer() { return this.players.get(indexOfCurrentPlayer); }
    private int indexOfCurrentPlayer;

    public GameController()
    {
        this.gameBoardState = new GameBoardState(GameBoard.fieldsArray);
        this.gui = new GUI(this);
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

    /**
     * sets up gamestate and gui prior to running game loop
     */
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
            if (getCurrentPlayer().isJailed()) {
                String chosenJailOption = this.gui.displayJailOptions(getCurrentPlayer());

                if (chosenJailOption.equals("Betal")) {
                    JailRules.PayOutOfJail(getCurrentPlayer());
                    this.gui.displayPlayerBalance();
                    getUserInputToBegin();
                    rollDice();
                    this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);


                } else if (chosenJailOption.equals("Slå terninger")) {
                    rollDice();
                    this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
                    if (die.EqualRolls(currentDieRoll1, currentDieRoll2)) {
                        getCurrentPlayer().setjailed(false);
                    }
                    else{
                        getCurrentPlayer().incrementRoundsInJail();
                    }
                }
                if(getCurrentPlayer().getRoundsInJail()==3){
                    JailRules.PayOutOfJail(getCurrentPlayer());
                    this.gui.displayPlayerBalance();
                    getUserInputToBegin();
                }
            }
            else
            {
                getUserInputToBegin();
                rollDice();
                this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
            }

            movePlayer();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            evaluateFieldAndExecute();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            this.gui.displayPlayerBalance();
            setNextPlayer();
        }
    }

    /**
     * rolls Die object twice and stores the rolls
     */
    private void rollDice()
    {
        this.currentDieRoll1 = this.die.roll();
        this.currentDieRoll2 = this.die.roll();
        this.sumOfDiceRolls = this.currentDieRoll1 + this.currentDieRoll2;
        if (this.die.EqualRolls(currentDieRoll1,currentDieRoll2)){
            die.incrementNumberOfEqualRolls();
        }
    }

    /**
     * increments the index of the player list
     */
    private void setNextPlayer()
    {
        if (!die.EqualRolls(currentDieRoll1,currentDieRoll2) || JailRules.ForceJail(die.getNumberOfEqualRolls())) {
            if (this.indexOfCurrentPlayer + 1 < this.players.size()) {
                this.indexOfCurrentPlayer++;
            } else {
                this.indexOfCurrentPlayer = 0;
            }
            die.resetNumberOfEqualRolls();
        }
    }

    /**
     * moves the current player and checks if it passes start
     */
    private void movePlayer()
    {
        int currentPosition = getCurrentPlayer().getPosition();
        int newPosition = 0;

        if (hasReachedStartField())
        {
            newPosition = currentPosition + this.sumOfDiceRolls - GameBoard.fieldsArray.length;
            getCurrentPlayer().setPosition(newPosition);
            getCurrentPlayer().changeBalance(4000);
        }
        else
        {
            newPosition = currentPosition + this.sumOfDiceRolls;
            getCurrentPlayer().setPosition(newPosition);
        }
    }

    private boolean hasReachedStartField()
    {
        return getCurrentPlayer().getPosition() + this.sumOfDiceRolls >= GameBoard.fieldsArray.length;
    }

    private void evaluateFieldAndExecute()
    {
        Field field = GameBoard.fieldsArray[getCurrentPlayer().getPosition()];
        switch (field.getType())
        {
            case CHANCE, JAIL, TAX -> executeEffect(((EffectField)field).getEffect());
        }
    }

    private void executeEffect(Effect effect)
    {
       if (effect == Effect.JAIL_GOTO) {
            getCurrentPlayer().setPosition(GameBoard.getIndexOfJail());
            getCurrentPlayer().setjailed(true);
       }
       else if (effect == Effect.CHANCE){
        var card = this.deck.getCard();
           this.gui.displayChanceCard(card);
           if(card instanceof GoToJailCard goToJail) {
               goToJail.execute(getCurrentPlayer());
           }
           else if(card instanceof ReceivePerPlayerCard receivePerPlayerCard){
               receivePerPlayerCard.execute(players, indexOfCurrentPlayer);
           }
           else if (card instanceof GetOutOfJailCard getOutOfJailCard){
           getOutOfJailCard.execute(getCurrentPlayer());
           }
           //else if (card instanceof MoveCard moveCard){
           //moveCard.execute(getCurrentPlayer());
           //}
           else if (card instanceof MoveToCard moveToCard) {
           moveToCard.execute(getCurrentPlayer());
           }
           else if(card instanceof RecieveOrPayCard recieveOrPayCard){
           recieveOrPayCard.execute(getCurrentPlayer());
           }
           //else if(card instanceof MatadorCard matadorCard){
           //    matadorCard.execute(getCurrentPlayer());
           //}
           //else if(card instanceof MoveToTypeCard moveToTypeCard){
           //    moveToTypeCard.execute(players, indexOfCurrentPlayer, );
           //}
           //else if (card instanceof PayPerHouseCard payPerHouseCard) {
           //    payPerHouseCard.execute(getCurrentPlayer());
           //}
       }
    }
    private int getNumberOfPlayers() {
        String result = (this.gui.displayPlayerSelectionButtons());
        return Integer.parseInt(String.valueOf(result.charAt(0)));
    }

    private void getUserInputToBegin() {
        this.gui.displayRollDiceButton(getCurrentPlayer().getName());
    }


    /**
     * gui shows field for name input and value is stored in Player
     */
    private void setPlayerNames()
    {
        for (int i = 0; i < this.players.size(); i++)
        {
            this.players.get(i).setName(this.gui.getUserStringInput(i));
        }
    }

}