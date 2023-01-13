package game;

import chancecards.ChanceCard;
import fields.*;
import gui_fields.*;

import java.awt.*;
import java.util.ArrayList;

public class GUI {
    private gui_main.GUI gui;
    private GameController gameController;
    private GUI_Field[] fields;
    private ArrayList<GUI_Player> gui_players;
    private Color[] playerColors = {
            Color.yellow,
            Color.red,
            Color.blue,
            Color.green,
            Color.cyan,
            Color.magenta
    };
    private ArrayList<GUI_Car> cars;

    public GUI(GameController gameController){
        this.gameController = gameController;
        this.gui_players = new ArrayList<GUI_Player>();
        this.cars = new ArrayList<GUI_Car>();
        this.fields = new GUI_Field[GameBoard.getSingleton().getFields().length];
        populateFields();
        this.gui = new gui_main.GUI(fields, new Color(51, 153, 255));
    }

    public void createStartField(int fieldIndex) {
        GUI_Start gui_field = new GUI_Start();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createStreetField(int fieldIndex) {
        GUI_Street gui_field = new GUI_Street();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        gui_field.setBackGroundColor(field.getColor());
        this.fields[fieldIndex] = gui_field;
    }

    public void createJailField(int fieldIndex) {
        GUI_Jail gui_field = new GUI_Jail();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        fields[fieldIndex] = gui_field;
    }

    public void createBreweryField(int fieldIndex) {
        GUI_Brewery gui_field = new GUI_Brewery();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;

    }

    public void createChanceField(int fieldIndex) {
        GUI_Chance gui_field = new GUI_Chance();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setDescription(field.getName());
        gui_field.setBackGroundColor(Color.green);
        this.fields[fieldIndex] = gui_field;
    }

    public void createFerryField(int fieldIndex) {
        GUI_Shipping gui_field = new GUI_Shipping();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createTaxField(int fieldIndex) {
        GUI_Tax gui_field = new GUI_Tax();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createRefugeField(int fieldIndex) {
        GUI_Refuge gui_field = new GUI_Refuge();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void populateFields()
    {
        Field[] fields = GameBoard.getSingleton().getFields();
        for (int i = 0; i < fields.length; i++)
        {
            switch (fields[i].getType()) {
                case START -> createStartField(i);
                case JAIL -> createJailField(i);
                case FERRY -> createFerryField(i);
                case STREET -> createStreetField(i);
                case CHANCE -> createChanceField(i);
                case TAX -> createTaxField(i);
                case BREWERY -> createBreweryField(i);
                case REFUGE -> createRefugeField(i);
            }
        }
    }

    public void addCarsToBoard() {
        for (int i = 0; i < this.gui_players.size(); i++) {
            this.gui_players.get(i).getCar().setPrimaryColor(this.playerColors[i]);
            this.gui_players.get(i).getCar().setPosition(fields[0]);
        }
    }

    public void addPlayersToBoard(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            this.gui_players.add(new GUI_Player(this.gameController.getPlayers().get(i).getName()));
            this.gui.addPlayer(this.gui_players.get(i));
            this.gui_players.get(i).setBalance(this.gameController.getPlayers().get(i).getBalance());
        }
    }

    public void displayDieRoll(int dieRoll1, int dieRoll2) {
        gui.setDice(dieRoll1, dieRoll2);
    }

    public void moveCarToField(int indexOfCurrentPlayer) {
        this.gui_players.get(indexOfCurrentPlayer).getCar().setPosition(fields[this.gameController.getPlayers().get(indexOfCurrentPlayer).getPosition()]);
    }

    public void displayPlayerBalance() {
        for (int i = 0; i < this.gameController.getPlayers().size(); i++) {
            this.gui_players.get(i).setBalance(this.gameController.getPlayers().get(i).getBalance());
        }
    }

    public String displayRollDiceButton(String playerName) {
        return this.gui.getUserButtonPressed(playerName, "Roll dice");
    }

    public String displayPlayerSelectionButtons() {
        return this.gui.getUserButtonPressed("Select number of players:", "3 Players", "4 Players", "5 Players", "6 Players");
    }

    public String getUserStringInput(int playerIndex) {
        String name = "";
        int playerNumber = playerIndex + 1;
        try {
            name = this.gui.getUserString("Player " + playerNumber + ", please input your name", 1, 12, false);
        } catch (Exception e) {
            getUserStringInput(playerIndex);
        }

        return name;
    }

    public String displayJailOptions(Player player) {
        if (player.getRoundsInJail() == 3) {
            return gui.getUserButtonPressed(player.getName() + ", Du har siddet i fængsel 3 runder. Betal 1000 kr. for at komme ud af fængslet", "Betal");
        } else if (player.getBalance() < 1000 && player.getGetOutOfJailFreeCard() == 0) {
            return this.gui.getUserButtonPressed(player.getName() + ", Slå 2 ens med terningerne for at komme ud af fængslet", "Slå terninger");
        } else if (player.getBalance() < 1000 && player.getGetOutOfJailFreeCard() > 0) {
            return gui.getUserButtonPressed(player.getName() + ", Vælg hvordan du vil komme fri: Slå 2 ens, eller bruge et benådningskort?", "Slå terninger", "Benådningskort");
        }
        else if (player.getBalance() > 1000 && player.getGetOutOfJailFreeCard() == 0){
            return this.gui.getUserButtonPressed(player.getName() + ", Vælg hvordan du vil komme fri: Slå 2 ens, eller betal 1000 kr.","Slå terninger", "Betal");
        }
        else{
            return this.gui.getUserButtonPressed(player.getName() + ", Vælg hvordan du vil komme fri:", "Slå terninger", "Betal", "Benådningskort");
        }

    }

    public String displayUnownedPropertyOptions(Player player, Field field) {
        if (player.getBalance() >= field.getPrice()) {
            return gui.getUserButtonPressed(player.getName() + ", " + field.getName() + " er ikke ejet af nogen, vil du købe den for " + field.getPrice() + " kr. ?", "Ja tak, betal " + field.getPrice() + " kr.", "Nej tak");
        } else {
            return gui.getUserButtonPressed(player.getName() + ", du har desværre ikke råd til " + field.getName() + ".", "Øv");
        }
    }

    public String displayLandingOnOpponentProperty(Player player, Field field){
        int rent = ((BuyableField)field).getRent()[field.getState().getNumOfHouses()];
        String opponent = field.getState().getOwner().getName();
        return gui.getUserButtonPressed(player.getName() + ", du er landet på " + field.getName() + " som " + opponent + " ejer. Betal " + rent + "kr. til " + opponent + ".", "Øv. Betal " + rent + "kr.");
    }

    public void displayChanceCard (ChanceCard chancecard){
        this.gui.displayChanceCard(chancecard.getText());
    }
}

