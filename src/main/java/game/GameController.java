package game;

import chancecards.*;
import fields.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    private GUI gui;
    private Die die;
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
        this.gui.testButton(getCurrentPlayer());
    }

    public void startGameLoop() {
        while (true) {
            this.gui.updateGUI(GameBoard.getSingleton().getFields(), this.players);
            System.out.println(" ");
            System.out.println("Det er " + getCurrentPlayer().getName() + "'s tur");
            checkJailStatus();
            movePlayer();
            resetEqualDieRolls();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            evaluateFieldAndExecute();
            this.gui.moveCarToField(indexOfCurrentPlayer);
            this.gui.displayPlayerBalance();
            this.managePropertiesOrEndTurn();
            setNextPlayer();
        }
    }

    private void managePropertiesOrEndTurn() {
        String chosenProperty;
        do {
            chosenProperty = this.gui.showDropDownMenu(getCurrentPlayer(), die.getNumberOfEqualRolls());
            if (!chosenProperty.equals("Afslut tur")) {
                String houseDecision = this.gui.buySellHouses(chosenProperty, getCurrentPlayer());
                sellAndBuyHousesAndPawn(houseDecision, chosenProperty, getCurrentPlayer());
                this.gui.displayPlayerBalance();
            }
        } while (!chosenProperty.equals("Afslut tur")) ;
    }

    /**
     * rolls Die object twice and stores the rolls
     */
    private void rollDice() {
        this.currentDieRoll1 = this.die.roll();
        this.currentDieRoll2 = this.die.roll();
        this.sumOfDiceRolls = this.currentDieRoll1 + this.currentDieRoll2;
        System.out.println("du slog " + this.sumOfDiceRolls);
        if (this.die.EqualRolls(currentDieRoll1, currentDieRoll2)) {
            die.incrementNumberOfEqualRolls();
        }
    }

    private void resetEqualDieRolls(){if(!die.EqualRolls(currentDieRoll1,currentDieRoll2)){
    die.resetNumberOfEqualRolls();}
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
                newPosition = currentPosition + this.sumOfDiceRolls - GameBoard.getSingleton().getFields().length;
                getCurrentPlayer().setPosition(newPosition);
                getCurrentPlayer().changeBalance(4000);
            } else {
                newPosition = currentPosition + this.sumOfDiceRolls;
                getCurrentPlayer().setPosition(newPosition);
            }

        }System.out.println("og rykker nu " + sumOfDiceRolls + " felter frem.");
    }

    private boolean hasReachedStartField() {
        return getCurrentPlayer().getPosition() + this.sumOfDiceRolls >= GameBoard.getSingleton().getFields().length;
    }

    private void evaluateFieldAndExecute() {
        Field field = GameBoard.getSingleton().getFields()[getCurrentPlayer().getPosition()];
        switch (field.getType()) {
            case CHANCE, JAIL, TAX -> executeEffect(((EffectField) field).getEffect());
            case STREET, BREWERY, FERRY -> payRentOrBuyProperty(getCurrentPlayer());
        }
    }

    private void executeEffect(Effect effect) {
        if (effect == Effect.JAIL_GOTO) {
            getCurrentPlayer().setPosition(GameBoard.getSingleton().getIndexOfJail());
            getCurrentPlayer().setJailed(true);
            System.out.println(getCurrentPlayer().getName() + " er nu i fængsel");
        } else if (effect == Effect.CHANCE) {
            var card = this.deck.getCard();
            this.gui.displayChanceCard(card);
            if (card instanceof GoToJailCard goToJail) {
                goToJail.execute(getCurrentPlayer());
                System.out.println(getCurrentPlayer().getName() + " er nu i fængsel");
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
        Field currentField = GameBoard.getSingleton().getFields()[player.getPosition()];
        Player opponent = currentField.getState().getOwner();
        if(currentField.getState().hasOwner()) {
            if(!currentField.getState().isPawned()){
            this.gui.displayLandingOnOpponentProperty(player, currentField);
            System.out.println(getCurrentPlayer().getName() + " betaler desværre " + getCurrentRent(currentField) + "kr til " + opponent.getName());
            player.changeBalance(-getCurrentRent(currentField));
            opponent.changeBalance(getCurrentRent(currentField));
        }
        }
        else{
            String buyPropertyOption = this.gui.displayUnownedPropertyOptions(player, currentField);
            if (buyPropertyOption.equals("Ja tak, betal " + currentField.getPrice() + " kr.")) {
                System.out.println(getCurrentPlayer().getName() + " købte " + currentField.getName() + " for " + currentField.getPrice() + "kr");
                player.changeBalance(-currentField.getPrice());
                currentField.getState().setOwner(player);
                this.gui.setOwnerAndRent(getCurrentPlayer(), indexOfCurrentPlayer);
            }
        }
    }

    public int getCurrentRent(Field currentField) {
        Player opponent = currentField.getState().getOwner();
        int currentFieldRent = 0;
        if (currentField.getState().hasOwner()) {
                if (currentField.getType().equals(FieldType.STREET)) {
                    if (isAllowedBuildHouses(currentField.getColor(), opponent) && currentField.getState().getNumOfHouses() == 0) {
                        currentFieldRent = ((BuyableField) currentField).getRent()[currentField.getState().getNumOfHouses()] * 2;
                    } else {
                        currentFieldRent = ((BuyableField) currentField).getRent()[currentField.getState().getNumOfHouses()];
                    }
                } else if (currentField.getType().equals(FieldType.FERRY)) {
                    currentFieldRent = ((BuyableField) currentField).getRent()[GameBoard.getSingleton().getNrOfFerriesOwnedByPlayer(opponent)];
                } else {
                    currentFieldRent = ((BuyableField) currentField).getRent()[GameBoard.getSingleton().getNrOfBreweriesOwnedByPlayer(opponent)];
                }
            }
        return currentFieldRent;
    }

    private int getNumberOfPlayers() {
        String result = (this.gui.displayPlayerSelectionButtons());
        return Integer.parseInt(String.valueOf(result.charAt(0)));
    }

    private void getUserInputToBegin() {
        this.gui.displayRollDiceButton(getCurrentPlayer().getName(), die);
    }

    private void checkJailStatus() {
        if (getCurrentPlayer().isJailed()) {
            String chosenJailOption = this.gui.displayJailOptions(getCurrentPlayer());

            if (chosenJailOption.equals("Slå terninger")) {
                rollDice();
                this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
                if (die.EqualRolls(currentDieRoll1, currentDieRoll2)) {
                    System.out.println(getCurrentPlayer().getName() + "er nu fri fra fængslet");
                    getCurrentPlayer().setJailed(false);
                } else {
                    getCurrentPlayer().incrementRoundsInJail();
                    if (getCurrentPlayer().getRoundsInJail() == 3) {
                        chosenJailOption = this.gui.displayJailOptions(getCurrentPlayer());
                    }
                }
            }
            if (chosenJailOption.equals("Betal")) {
                System.out.println(getCurrentPlayer().getName() + " er nu fri fra fængslet for 1000 kr");
                JailRules.PayOutOfJail(getCurrentPlayer());
                this.gui.displayPlayerBalance();
                if (getCurrentPlayer().getRoundsInJail() != 3) {
                    getUserInputToBegin();
                    rollDice();
                    this.gui.displayDieRoll(this.currentDieRoll1, this.currentDieRoll2);
                }

            }
            if (chosenJailOption.equals("Benådningskort")) {
                System.out.println(getCurrentPlayer().getName() + "er nu fri fra fængslet");
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

    private void sellAndBuyHousesAndPawn(String choice, String fieldName, Player player){
        BuyableField field = chosenField(fieldName,player);
            if(choice.equals("Køb hus") && isAllowedBuildHouses(field.getColor(),player) && canBuildOneMoreHouse(field,player)){
                System.out.println(getCurrentPlayer().getName() + " Købte hus på " + field.getName() + " for " + field.getHousePrice() + "kr");
                field.getState().setNumOfHouses(field.getState().getNumOfHouses()+1);
                player.changeBalance(-field.getHousePrice());
            }
            else if(choice.equals("Sælg hus") && canSellOneMoreHouse(field)) {
                System.out.println(getCurrentPlayer().getName() + "har solgt et hus på " + field.getName() + " for " + field.getHousePrice()/2 + "kr");
            field.getState().setNumOfHouses(field.getState().getNumOfHouses()-1);
            player.changeBalance(field.getHousePrice()/2);
            }
            else if(choice.equals("Pantsæt ejendom") && field.getState().getNumOfHouses()==0){
                System.out.println(getCurrentPlayer().getName() + "har pantsat " + field.getName() + " for " + field.getHousePrice() + "kr");
                field.getState().setPawned(true);
                player.changeBalance(field.getPrice());
                this.gui.setPawnedGUI(field, indexOfCurrentPlayer);
            }else if(choice.equals("Køb ejendom tilbage") && field.getState().isPawned()){
                field.getState().setPawned(false);
                int pawnedFieldPrice = (int) (Math.ceil((field.getPrice()*1.1)/100.0))*100;
                System.out.println(getCurrentPlayer().getName() + " har købt " + field.getName() + " tilbage for " + pawnedFieldPrice + "kr");
                player.changeBalance(-pawnedFieldPrice);
                this.gui.setUnpawnedGUI(field, indexOfCurrentPlayer);
            }
        }
        // should perhaps start off by providing a filtered list containing all the same colored fields
        // matching the index, and be checked if they're owned by the same player.
        // Avoid duplicate array creation of the filtered array.

    public boolean canSellOneMoreHouse(BuyableField field)
    {
        Color colorOfField = field.getColor();
        BuyableField[] streets = this.getStreetsOfSameColor(colorOfField);

        int housesOnIndex = field.getState().getNumOfHouses();
        for (BuyableField street : streets)
        {
            if (housesOnIndex <= 0 || housesOnIndex  < street.getState().getNumOfHouses())
            {
                return false;
            }
        }
        return true;
    }

    public boolean canBuildOneMoreHouse(BuyableField field, Player player)
    {
        Color colorOfField = field.getColor();
        if (!isAllowedBuildHouses(colorOfField, player) || player.getBalance() < field.getHousePrice())
        {
            return false;
        }

        BuyableField[] streets = this.getStreetsOfSameColor(colorOfField);
        int housesOnIndex = field.getState().getNumOfHouses();
        for (BuyableField street : streets)
        {
            if (housesOnIndex >= 5 || housesOnIndex > street.getState().getNumOfHouses())
            {
                return false;
            }
        }
        return true;
    }
    public boolean canPawnProperty(BuyableField field)
    {
        Color colorOfField = field.getColor();
        BuyableField[] streets = this.getStreetsOfSameColor(colorOfField);

        int housesOnIndex = field.getState().getNumOfHouses();
        for (BuyableField street : streets)
        {
            if (housesOnIndex == 0 && housesOnIndex == street.getState().getNumOfHouses())
            {
                return true;
            }
        }
        return false;
    }


    /**
     * checks if all streets of a color are owned by the same player
     * @param color
     * @param player
     * @return
     */
    public boolean isAllowedBuildHouses(Color color, Player player){
        BuyableField[] streets = this.getStreetsOfSameColor(color);
        return areOwnedBySamePlayer(streets, player);
    }

    public BuyableField chosenField(String string, Player player) {
        Field[] ownedfields = getOwnedByPlayer(getAllStreetFields(GameBoard.getSingleton().getFields()), player);
        BuyableField wantedField = null;
        for (int i = 0; i < ownedfields.length; i++) {
            if (ownedfields[i].getName().equals(string)) {
                wantedField = (BuyableField) ownedfields[i];
            }
        }
        return wantedField;
    }

    private BuyableField[] getStreetsOfSameColor(Color color){
        Field[] fields = GameBoard.getSingleton().getFields();
        ArrayList<Field> sameColorFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.getType() == FieldType.STREET && field.getColor().equals(color)) {
                sameColorFields.add(field);
            }
        }
        return Arrays.copyOf(sameColorFields.toArray(), sameColorFields.size(), BuyableField[].class);
    }

    private boolean areOwnedBySamePlayer(BuyableField[] streets, Player player)
    {
        for (BuyableField street : streets)
        {
            if (!street.getState().hasOwner() || !street.getState().getOwner().equals(player))
            {
                return false;
            }
        }
        return true;
    }

    public BuyableField[] getAllStreetFields(Field[] fields){
        ArrayList<BuyableField> streets = new ArrayList<>();
        for (int i =0; i < fields.length; i++)
        {
            if(fields[i].getType()==FieldType.STREET)
                streets.add((BuyableField) fields[i]);
            }
        return Arrays.copyOf(streets.toArray(), streets.size(), BuyableField[].class);
    }
    public BuyableField[] getAllBuyableFields(Field[] fields){
        ArrayList<BuyableField> streets = new ArrayList<>();
        for (int i =0; i < fields.length; i++)
        {
            if(fields[i].getType()==FieldType.STREET||fields[i].getType()==FieldType.BREWERY||fields[i].getType()==FieldType.FERRY)
                streets.add((BuyableField) fields[i]);
        }
        return Arrays.copyOf(streets.toArray(), streets.size(), BuyableField[].class);
    }

    public Field[] getOwnedByPlayer(BuyableField[] fields, Player player){
        ArrayList<BuyableField> streets = new ArrayList<>();
        for (BuyableField field : fields)
        {
            if (field.getState().hasOwner() && field.getState().getOwner().equals(player))
            {
                streets.add(field);
            }
        }

        return Arrays.copyOf(streets.toArray(), streets.size(), BuyableField[].class);
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
    public void masterTest(Player player) {
        BuyableField field = (BuyableField) GameBoard.getSingleton().getFields()[1];
        BuyableField field2 = (BuyableField) GameBoard.getSingleton().getFields()[3];
        BuyableField field3 = (BuyableField) GameBoard.getSingleton().getFields()[5];
        BuyableField field4 = (BuyableField) GameBoard.getSingleton().getFields()[6];
        BuyableField field5 = (BuyableField) GameBoard.getSingleton().getFields()[8];
        BuyableField field6 = (BuyableField) GameBoard.getSingleton().getFields()[9];
        field.getState().setOwner(player);
        field2.getState().setOwner(player);
        field3.getState().setOwner(player);
        field4.getState().setOwner(player);
        field5.getState().setOwner(player);
        field6.getState().setOwner(player);
    }
}