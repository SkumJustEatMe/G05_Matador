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
        String rent = "Leje uden hus: " + ((BuyableField)field).getRent()[0] + " Kr\\n" + "Leje med 1 hus: " + ((BuyableField)field).getRent()[1] + " Kr\\n" + "Leje med 2 huse: " + ((BuyableField)field).getRent()[2] + " Kr\\n" + "Leje med 3 huse: " + ((BuyableField)field).getRent()[3] + " Kr\\n" + "Leje med 4 huse: " + ((BuyableField)field).getRent()[4] + " Kr\\n" + "Leje med hotel: " + ((BuyableField)field).getRent()[5] + " Kr\\n";
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName() + "\\n" + rent);
        gui_field.setSubText("Pris: " + field.getPrice() + " Kr");
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
        int rent;
        if(field.getType().equals(FieldType.STREET)){
            rent = ((BuyableField)field).getRent()[field.getState().getNumOfHouses()];}
        else if(field.getType().equals(FieldType.FERRY)){
            rent = ((BuyableField)field).getRent()[GameBoard.getSingleton().getNrOfFerriesOwnedByPlayer(field.getState().getOwner())];}
        else{
            rent = ((BuyableField)field).getRent()[GameBoard.getSingleton().getNrOfBreweriesOwnedByPlayer(field.getState().getOwner())];
        }
        String opponent = field.getState().getOwner().getName();
        return gui.getUserButtonPressed(player.getName() + ", du er landet på " + field.getName() + " som " + opponent + " ejer. Betal " + rent + "kr. til " + opponent + ".", "Øv. Betal " + rent + "kr.");
    }

    public void displayChanceCard (ChanceCard chancecard){
        this.gui.displayChanceCard(chancecard.getText());
    }

    public String showDropDownMenu(Player player) {
        Field[] ownedFields = gameController.getOwnedByPlayer(gameController.getAllStreetFields(GameBoard.getSingleton().getFields()), player);
        String[] ownedStreetsAsStrings = new String[ownedFields.length];
        String dropdown = null;
        if (ownedFields.length > 0) {
            for (int i = 0; i < ownedFields.length; i++) {
                ownedStreetsAsStrings[i] = ownedFields[i].getName();
            }
            String choice = this.gui.getUserButtonPressed(player.getName() + ", vil du adminstere dine grunde eller afslutte din tur?", "Adminstrer grunde", "Afslut tur");
            if (choice.equals("Adminstrer grunde")) {
                dropdown = this.gui.getUserSelection(player.getName() + ", administrer grunde...", ownedStreetsAsStrings);
            } else {
                dropdown = "Afslut tur";
            }
        } else {
            dropdown = this.gui.getUserButtonPressed("Afslut din tur " + player.getName(), "Afslut tur");
        }
        return dropdown;
    }

    public void buySellHouses(String fieldName, Player player){
        BuyableField field = gameController.chosenField(fieldName,player);
        GUI_Street gui_field = (GUI_Street) gui.getFields()[field.getPosition()];
        String choice = this.gui.getUserButtonPressed("Vil du sælge eller købe huse?", "Sælg", "Køb");
        String choice2;
        if(choice.equals("Køb") && gameController.isAllowedBuildHouses(field.getColor(),player)){
            if(gameController.canBuildOneMoreHouse(field,player) && player.getBalance() >= field.getHousePrice()){
                choice2 = this.gui.getUserButtonPressed(player.getName()+ ", køb hus(eller hotel, hvis du har 4 huse) eller annuller:", "Køb hus", "Annuller");
                if(choice2.equals("Køb hus") && field.getState().getNumOfHouses()<=3){
                gui_field.setHouses(+1);
                }
                else if(choice2.equals("Køb hus") && field.getState().getNumOfHouses()==4){
                    gui_field.setHouses(0);
                    gui_field.setHotel(true);
                }
            } else if(!gameController.canBuildOneMoreHouse(field,player)){
                this.gui.getUserButtonPressed(player.getName()+ ", du har enten ikke råd til et hus på denne grund ellers bygger du ujævnt på dine grunde, byg et eller 2 huse på en af de andre af samme før du må bygge her.", "Okay, øv");
            }
        } else if(choice.equals("Køb") && !gameController.isAllowedBuildHouses(field.getColor(), player)){
            this.gui.getUserButtonPressed(player.getName()+ ", du kan ikke købe huse på denne grund da du mangler de resterende grunde af samme farve", "Okay, øv");
        } else if (choice.equals("Sælg") && gameController.canSellOneMoreHouse(field)) {
            choice2 = this.gui.getUserButtonPressed(player.getName() + ", sælg et hus på denne grund til havldelen af den originale værdi: " + field.getHousePrice() / 2 + " kr. eller annuller?", "Sælg hus", "Annuller");
            if (choice2.equals("Sælg hus") && field.getState().getNumOfHouses() <= 4) {
                gui_field.setHouses(-1);
            } else if (choice2.equals("Sælg hus") && field.getState().getNumOfHouses() == 5) {
                gui_field.setHotel(false);
                gui_field.setHouses(4);
            }
        } else if (choice.equals("Sælg") && !gameController.canSellOneMoreHouse(field)) {
            this.gui.getUserButtonPressed(player.getName() + ", du har enten ingen huse at sælge, ellers prøver du at sælge ujævnt", "Okay, øv");
        }
    }

    private void setOwnerAndRent(Player player){
        GUI_Street gui_field = (GUI_Street) gui.getFields()[player.getPosition()];
        Field field = GameBoard.getSingleton().getFields()[player.getPosition()];
        if(field.getState().hasOwner()){
            gui_field.setOwnerName(field.getState().getOwner().getName());
            gui_field.setRent(Integer.toString(((BuyableField)field).getRent()[field.getState().getNumOfHouses()]));
        }
    }
    /*private void setHousesAndHotels(int fieldIndex){
        GUI_Street gui_field = (GUI_Street) gui.getFields()[fieldIndex];
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        if(field.getState().getNumOfHouses()>0 && field.getState().getNumOfHouses()<5){
            gui_field.setHouses(field.getState().getNumOfHouses());
        }
        if(field.getState().getNumOfHouses()==5){
            gui_field.setHotel(true);
        }
    }*/

    public void updateGui(Player player){
        //setOwnerAndRent(player);
        //setHousesAndHotels(fieldIndex);
    }

    /* public void manageProperties(Player player){
        gui.getUserSelection("Vælg en grund", player.);
    } */
}

