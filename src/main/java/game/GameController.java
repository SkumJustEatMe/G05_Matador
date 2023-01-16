package game;

import chancecards.*;
import fields.*;

import java.awt.*;
import java.util.ArrayList;

import static fields.GameBoard.getSingleton;

public class GameController {
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

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    private Player getCurrentPlayer() {
        return this.players.get(indexOfCurrentPlayer);
    }

    private int indexOfCurrentPlayer;

    public GameController() {
        this.gui = new GUI(this);
        this.die = new Die();
        this.players = new ArrayList<Player>();
        this.indexOfCurrentPlayer = 0;
    }

    private void addPlayersAndSetPosition(int numberOfPlayers) {
        for (int i = 1; i <= numberOfPlayers; i++) {
            this.players.add(new Player(30000));
        }

        for (Player i : this.players) {
            i.setPosition(0);
        }

        setPlayerNames();
    }


    public void run() {
        this.initialize();
        this.startGameLoop();
    }

    /**
     * sets up gamestate and gui prior to running game loop
     */
    private void initialize() {
        this.addPlayersAndSetPosition(this.getNumberOfPlayers());
        this.gui.addPlayersToBoard(this.players.size());
        this.gui.addCarsToBoard();
    }

    public void startGameLoop() {
        while (true) {
            checkJailStatus();
            movePlayer();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            evaluateFieldAndExecute();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            this.gui.displayPlayerBalance();
            //this.gui.updateGui(getCurrentPlayer());
            setNextPlayer();
        }
    }

    /**
     * rolls Die object twice and stores the rolls
     */
    private void rollDice() {
        this.currentDieRoll1 = this.die.roll();
        this.currentDieRoll2 = this.die.roll();
        this.sumOfDiceRolls = this.currentDieRoll1 + this.currentDieRoll2;
        if (this.die.EqualRolls(currentDieRoll1, currentDieRoll2)) {
            die.incrementNumberOfEqualRolls();
        }
    }

    /**
     * increments the index of the player list
     */
    private void setNextPlayer() {
        if (!die.EqualRolls(currentDieRoll1, currentDieRoll2) || JailRules.ForceJail(die.getNumberOfEqualRolls())) {
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
    private void movePlayer() {
        int currentPosition = getCurrentPlayer().getPosition();
        int newPosition = 0;

        if (!getCurrentPlayer().isJailed()) {
            if (hasReachedStartField()) {
                newPosition = currentPosition + this.sumOfDiceRolls - getSingleton().getFields().length;
                getCurrentPlayer().setPosition(newPosition);
                getCurrentPlayer().changeBalance(4000);
            } else {
                newPosition = currentPosition + this.sumOfDiceRolls;
                getCurrentPlayer().setPosition(newPosition);
            }
        }
    }

    private boolean hasReachedStartField() {
        return getCurrentPlayer().getPosition() + this.sumOfDiceRolls >= getSingleton().getFields().length;
    }

    private void evaluateFieldAndExecute() {
        Field field = getSingleton().getFields()[getCurrentPlayer().getPosition()];
        switch (field.getType()) {
            case CHANCE, JAIL, TAX -> executeEffect(((EffectField) field).getEffect());
            case STREET, BREWERY, FERRY -> payRentOrBuyProperty(getCurrentPlayer());
        }
    }

    private void executeEffect(Effect effect) {
        if (effect == Effect.JAIL_GOTO) {
            getCurrentPlayer().setPosition(getSingleton().getIndexOfJail());
            getCurrentPlayer().setJailed(true);
        } else if (effect == Effect.CHANCE) {
            var card = this.deck.getCard();
            this.gui.displayChanceCard(card);
            if (card instanceof GoToJailCard goToJail) {
                goToJail.execute(getCurrentPlayer());
            } else if (card instanceof ReceivePerPlayerCard receivePerPlayerCard) {
                receivePerPlayerCard.execute(players, indexOfCurrentPlayer);
            } else if (card instanceof GetOutOfJailCard getOutOfJailCard) {
                getOutOfJailCard.execute(getCurrentPlayer());
            } else if (card instanceof MoveCard moveCard) {
                moveCard.execute(getCurrentPlayer());
            } else if (card instanceof MoveToCard moveToCard) {
                moveToCard.execute(getCurrentPlayer());
            } else if (card instanceof RecieveOrPayCard recieveOrPayCard) {
                recieveOrPayCard.execute(getCurrentPlayer());
            } else if (card instanceof MatadorCard matadorCard) {
                matadorCard.execute(getCurrentPlayer());
            } else if (card instanceof MoveToTypeCard moveToTypeCard) {
                moveToTypeCard.execute(getCurrentPlayer());
            } else if (card instanceof PayPerHouseCard payPerHouseCard) {
                payPerHouseCard.execute(getCurrentPlayer());
            }
        }
    }

    private void payRentOrBuyProperty(Player player) {
        Field currentField = getSingleton().getFields()[player.getPosition()];
        Player opponent = currentField.getState().getOwner();
        int currentFieldRent;
        if (currentField.getState().hasOwner()) {
            if (currentField.getState().getOwner() != getCurrentPlayer()) {
                if (currentField.getType().equals(FieldType.STREET)) {
                    currentFieldRent = ((BuyableField) currentField).getRent()[currentField.getState().getNumOfHouses()];
                } else if (currentField.getType().equals(FieldType.FERRY)) {
                    currentFieldRent = ((BuyableField) currentField).getRent()[GameBoard.getSingleton().getNrOfFerriesOwnedByPlayer(opponent)];
                } else {
                    currentFieldRent = ((BuyableField) currentField).getRent()[GameBoard.getSingleton().getNrOfBreweriesOwnedByPlayer(opponent)];
                }
                this.gui.displayLandingOnOpponentProperty(player, currentField);
                player.changeBalance(-currentFieldRent);
                opponent.changeBalance(currentFieldRent);
            }
        }
        else {
                String buyPropertyOption = this.gui.displayUnownedPropertyOptions(player, currentField);
                if (buyPropertyOption.equals("Ja tak, betal " + currentField.getPrice() + " kr.")) {
                    player.changeBalance(-currentField.getPrice());
                    currentField.getState().setOwner(player);
            }
        }
    }

    private int getNumberOfPlayers() {
        String result = (this.gui.displayPlayerSelectionButtons());
        return Integer.parseInt(String.valueOf(result.charAt(0)));
    }

    private void getUserInputToBegin() {
        this.gui.displayRollDiceButton(getCurrentPlayer().getName());
    }

    private void checkJailStatus() {
        if (getCurrentPlayer().isJailed()) {
            String chosenJailOption = this.gui.displayJailOptions(getCurrentPlayer());

            if (chosenJailOption.equals("Slå terninger")) {
                rollDice();
                this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
                if (die.EqualRolls(currentDieRoll1, currentDieRoll2)) {
                    getCurrentPlayer().setJailed(false);
                } else {
                    getCurrentPlayer().incrementRoundsInJail();
                    if (getCurrentPlayer().getRoundsInJail() == 3) {
                        chosenJailOption = this.gui.displayJailOptions(getCurrentPlayer());
                    }
                }
            }
            if (chosenJailOption.equals("Betal")) {
                JailRules.PayOutOfJail(getCurrentPlayer());
                this.gui.displayPlayerBalance();
                if (getCurrentPlayer().getRoundsInJail() != 3) {
                    getUserInputToBegin();
                    rollDice();
                    this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
                }

            }
            if (chosenJailOption.equals("Benådningskort")) {
                getCurrentPlayer().setJailed(false);
                getCurrentPlayer().setGetOutOfJailFreeCard(-1);
                getUserInputToBegin();
                rollDice();
                this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
            }
            if (getCurrentPlayer().isJailed() == false) {
                getCurrentPlayer().resetRoundsInJail();
            }
        } else {
            getUserInputToBegin();
            rollDice();
            this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
        }
    }

    private void sellAndBuyHouses() {
    }

    private void checkIfBuyHousesPossible() {
        Field field = GameBoard.getSingleton().getFields()[getCurrentPlayer().getPosition()];
        int i = 0;
        int nrOfSameColorsOwned = 0;
        Field searchedField = GameBoard.getSingleton().getFields()[i];
        Color searchedColor = GameBoard.getSingleton().getFields()[i].getColor();
        for (i = 0; i < GameBoard.getSingleton().getFields().length; i++) {
            if (field.getColor() == searchedColor)
                nrOfSameColorsOwned++;
        }
        if (nrOfSameColorsOwned == GameBoard.getSingleton().getNrOfSameColor(field.getColor())) {
            int minNrOfHouses = 5;
            Field leastHouses;
            for (i = 0; i < GameBoard.getSingleton().getFields().length; i++) {
                if (searchedColor == field.getColor()) {
                    if (searchedField.getState().getNumOfHouses() < minNrOfHouses) {
                        minNrOfHouses = searchedField.getState().getNumOfHouses();
                    }
                }

            }
        }
    }


            /**
             * gui shows field for name input and value is stored in Player
             */
            private void setPlayerNames ()
            {
                for (int i = 0; i < this.players.size(); i++) {
                    this.players.get(i).setName(this.gui.getUserStringInput(i));
                }
            }
        }