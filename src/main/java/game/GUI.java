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

    public GUI(GameController gameController) {
        this.gameController = gameController;
        this.gui_players = new ArrayList<GUI_Player>();
        this.cars = new ArrayList<GUI_Car>();
        this.fields = new GUI_Field[GameBoard.fieldsArray.length];
        populateFields();
        this.gui = new gui_main.GUI(fields, new Color(51, 153, 255));
    }

    public void createStartField(int fieldIndex) {
        GUI_Start gui_field = new GUI_Start();
        Field field = GameBoard.fieldsArray[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createStreetField(int fieldIndex) {
        GUI_Street gui_field = new GUI_Street();
        Field field = GameBoard.fieldsArray[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createJailField(int fieldIndex) {
        GUI_Jail gui_field = new GUI_Jail();
        Field jailField = GameBoard.fieldsArray[fieldIndex];
        gui_field.setTitle(jailField.getName());
        gui_field.setDescription(jailField.getName());
        gui_field.setSubText("");
        fields[fieldIndex] = gui_field;
    }

    public void createBreweryField(int fieldIndex) {
        GUI_Brewery gui_field = new GUI_Brewery();
        Field field = GameBoard.fieldsArray[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createChanceField(int fieldIndex) {
        GUI_Chance gui_field = new GUI_Chance();
        Field field = GameBoard.fieldsArray[fieldIndex];
        gui_field.setDescription(field.getName());
        gui_field.setBackGroundColor(Color.green);
        this.fields[fieldIndex] = gui_field;
    }

    public void createFerryField(int fieldIndex) {
        GUI_Shipping gui_field = new GUI_Shipping();
        Field field = GameBoard.fieldsArray[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createTaxField(int fieldIndex) {
        GUI_Tax gui_field = new GUI_Tax();
        Field field = GameBoard.fieldsArray[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void createRefugeField(int fieldIndex) {
        GUI_Refuge gui_field = new GUI_Refuge();
        Field field = GameBoard.fieldsArray[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }

    public void populateFields() {
        for (int i = 0; i < GameBoard.fieldsArray.length; i++) {
            switch (GameBoard.fieldsArray[i].getType()) {
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
            return gui.getUserButtonPressed("Betal 1000 kr. for at komme ud af fængslet", "Betal");
        } else if (player.getBalance() < 1000) {
            return this.gui.getUserButtonPressed("Slå med terningerne for at komme ud af fængslet", "Slå terninger");
        } else if {
            return gui.getUserButtonPressed("Vælg om du vil slå eller betal for at komme fri", "Slå terninger", "Betal");

        }

    }


    public void displayChanceCard(ChanceCard chancecard) {
        this.gui.displayChanceCard(chancecard.getText());
    }

}
