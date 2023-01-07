package game;

import fields.*;
import fields.GameBoard;
import gui_fields.*;

import java.awt.*;
import java.util.ArrayList;

public class GUI {
    private gui_main.GUI gui;
    private GameController gameController;
    private GameBoard gameBoard;
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
    public GUI(GameBoard gameBoard, GameController gameController){
        this.gameBoard = gameBoard;
        this.gameController = gameController;
        this.gui_players = new ArrayList<GUI_Player>();
        this.cars = new ArrayList<GUI_Car>();
        this.fields = new GUI_Field[gameBoard.getFieldList().length];
        populateFields();
        this.gui = new gui_main.GUI(fields, new Color(51, 153, 255));
    }

    public void createStartField(int fieldIndex){
        GUI_Start gui_field = new GUI_Start();
        Field field = this.gameBoard.getFieldList()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        this.fields[fieldIndex] = gui_field;
    }
    public void createJailField(int fieldIndex){
        GUI_Jail gui_field = new GUI_Jail();
        EventField field = (EventField) this.gameBoard.getFieldList()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        fields[fieldIndex] = gui_field;
    }
    public void createEmptyField(int fieldIndex)
    {
        GUI_Street gui_field = new GUI_Street();
        Field field = this.gameBoard.getFieldList()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText("");
        fields[fieldIndex] = gui_field;
    }
    public void populateFields()
    {
        createStartField(0);

        for (int i = 1; i < 40; i++)
        {
            if (this.gameBoard.getFieldList()[i] instanceof EventField)
            {
                this.createJailField(i);
            }
            else
            {
                this.createEmptyField(i);
            }
        }
    }

    public void addCarsToBoard()
    {
        for (int i = 0; i < this.gui_players.size(); i++)
        {
            this.gui_players.get(i).getCar().setPrimaryColor(this.playerColors[i]);
            this.gui_players.get(i).getCar().setPosition(fields[0]);
        }
    }

    public void addPlayersToBoard(int numberOfPlayers)
    {
        for (int i = 0; i < numberOfPlayers; i++)
        {
            this.gui_players.add(new GUI_Player( this.gameController.getPlayers().get(i).getName()));
            this.gui.addPlayer(this.gui_players.get(i));
            this.gui_players.get(i).setBalance(this.gameController.getPlayers().get(i).getBalance());
        }
    }

    public void displayDieRoll(int dieRoll1, int dieRoll2) {
        gui.setDice(dieRoll1, dieRoll2);
    }

    public void moveCarToField(int indexOfCurrentPlayer){
            this.gui_players.get(indexOfCurrentPlayer).getCar().setPosition(fields[this.gameController.getPlayers().get(indexOfCurrentPlayer).getPosition()]);
    }

    public void displayPlayerBalance() {
        for (int i = 0; i < this.gameController.getPlayers().size(); i++)
        {
            this.gui_players.get(i).setBalance(this.gameController.getPlayers().get(i).getBalance());
        }
    }
    public String displayRollDiceButton(String playerName){
        return this.gui.getUserButtonPressed(playerName, "Roll dice");
    }

    public String displayPlayerSelectionButtons()
    {
        return this.gui.getUserButtonPressed("Select number of players:", "3 Players", "4 Players", "5 Players", "6 Players");
    }

    public String getUserStringInput(int playerIndex)
    {
        String name = "";
        int playerNumber = playerIndex + 1;
        try
        {
            name = this.gui.getUserString("Player " + playerNumber + ", please input your name", 1, 12, false);
        }
        catch(Exception e)
        {
            getUserStringInput(playerIndex);
        }

        return name;
    }
}