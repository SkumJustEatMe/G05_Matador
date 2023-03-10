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
        String rent = "Leje uden hus: " + ((BuyableField)field).getRent()[0] + " Kr.\\n" + "Leje med 1 hus: " + ((BuyableField)field).getRent()[1] + " Kr.\\n" + "Leje med 2 huse: " + ((BuyableField)field).getRent()[2] + " Kr.\\n" + "Leje med 3 huse: " + ((BuyableField)field).getRent()[3] + " Kr.\\n" + "Leje med 4 huse: " + ((BuyableField)field).getRent()[4] + " Kr.\\n" + "Leje med hotel: " + ((BuyableField)field).getRent()[5] + " Kr.\\n";
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName() + "\\n" + rent);
        gui_field.setSubText("Pris: " + field.getPrice() + " Kr.");
        gui_field.setBackGroundColor(field.getColor());
        this.fields[fieldIndex] = gui_field;
    }

    public void createJailField(int fieldIndex) {
        GUI_Jail gui_field = new GUI_Jail();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName());
        gui_field.setSubText(field.getName());
        fields[fieldIndex] = gui_field;
    }

    public void createBreweryField(int fieldIndex) {
        GUI_Brewery gui_field = new GUI_Brewery();
        Field field = GameBoard.getSingleton().getFields()[fieldIndex];
        String rent = "Leje med 1 bryggeri: " + ((BuyableField)field).getRent()[0] + " Kr.\\n" + "Leje med 2 bryggerier: " + ((BuyableField)field).getRent()[1]+ " Kr.";
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName() + " \\n" + rent);
        gui_field.setSubText("Pris: " + field.getPrice() + " Kr.");
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
        String rent = "Leje med 1 f??rge: " + ((BuyableField)field).getRent()[0] + " Kr.\\n" + "Leje med 2 f??rger: " + ((BuyableField)field).getRent()[1] + " Kr.\\n" + "Leje med 3 f??rger: " + ((BuyableField)field).getRent()[2]+" Kr.\\n" + "Leje med 4 f??rger: " + ((BuyableField)field).getRent()[3] + " Kr.";
        gui_field.setTitle(field.getName());
        gui_field.setDescription(field.getName() + " \\n" + rent);
        gui_field.setSubText("Pris: " + field.getPrice() + " Kr.");
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
        int playerPosition = this.gameController.getPlayers().get(indexOfCurrentPlayer).getPosition();
        GUI_Field newPosition = fields[playerPosition];
        GUI_Player currentPlayer = this.gui_players.get(indexOfCurrentPlayer);
        currentPlayer.getCar().setPosition(newPosition);
    }

    public void refreshPlayerBalance() {
        for (int i = 0; i < this.gameController.getPlayers().size(); i++) {
            this.gui_players.get(i).setBalance(this.gameController.getPlayers().get(i).getBalance());
            }
        }

    public String displayRollDiceButton(String playerName, Die die) {
        if(die.getNumberOfEqualRolls()>=1 && die.getNumberOfEqualRolls() < 3){
            return this.gui.getUserButtonPressed(playerName, playerName + " Sl?? med terningerne igen");
        }else{
        return this.gui.getUserButtonPressed(playerName, playerName + " Sl?? med terningerne");
        }
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
            return gui.getUserButtonPressed(player.getName() + ", Du har siddet i f??ngsel 3 runder. Betal 1000 kr. for at komme ud af f??ngslet", "Betal");
        } else if (player.getBalance() < 1000 && player.getGetOutOfJailFreeCard() == 0) {
            return this.gui.getUserButtonPressed(player.getName() + ", Sl?? 2 ens med terningerne for at komme ud af f??ngslet", "Sl?? terninger");
        } else if (player.getBalance() < 1000 && player.getGetOutOfJailFreeCard() > 0) {
            return gui.getUserButtonPressed(player.getName() + ", V??lg hvordan du vil komme fri: Sl?? 2 ens, eller bruge et ben??dningskort?", "Sl?? terninger", "Ben??dningskort");
        }
        else if (player.getBalance() > 1000 && player.getGetOutOfJailFreeCard() == 0){
            return this.gui.getUserButtonPressed(player.getName() + ", V??lg hvordan du vil komme fri: Sl?? 2 ens, eller betal 1000 kr.","Sl?? terninger", "Betal");
        }
        else{
            return this.gui.getUserButtonPressed(player.getName() + ", V??lg hvordan du vil komme fri:", "Sl?? terninger", "Betal", "Ben??dningskort");
        }

    }

    public String displayUnownedPropertyOptions(Player player, Field field) {
        if (player.getBalance() >= field.getPrice()) {
            return gui.getUserButtonPressed(player.getName() + ", " + field.getName() + " er ikke ejet af nogen, vil du k??be den for " + field.getPrice() + " kr. ?", "Ja tak, betal " + field.getPrice() + " kr.", "Nej tak");
        } else {
            return gui.getUserButtonPressed(player.getName() + ", du har desv??rre ikke r??d til " + field.getName() + ".", "??v");
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
        if(gameController.isAllowedBuildHouses(field.getColor(),field.getState().getOwner()) && field.getState().getNumOfHouses()==0 && field.getType().equals(FieldType.STREET)){
            return gui.getUserButtonPressed(player.getName() + ", du er landet p?? " + field.getName() + " som " + opponent + " ejer. Betal " + rent*2 + "kr. til " + opponent + ".", "??v. Betal " + rent*2 + "kr.");
        }else {
            return gui.getUserButtonPressed(player.getName() + ", du er landet p?? " + field.getName() + " som " + opponent + " ejer. Betal " + rent + "kr. til " + opponent + ".", "??v. Betal " + rent + "kr.");
        }
        }

    public void displayChanceCard (ChanceCard chancecard){
        this.gui.displayChanceCard(chancecard.getText());
    }

    public String showDropDownMenu(Player player, int nrOfEqualRolls) {
        Field[] ownedFields = gameController.getOwnedByPlayer(gameController.getAllStreetFields(GameBoard.getSingleton().getFields()), player);
        Field[] ownedFieldsNotPawned = gameController.getOwnedNotPawnedByPlayer(gameController.getAllBuyableFields(GameBoard.getSingleton().getFields()), player);
        String[] ownedStreetsAsStrings = new String[ownedFields.length];
        String[] ownedStreetsNotPawnedAsStrings = new String[ownedFieldsNotPawned.length];
        String dropdown = null;
        String choice;
        if (ownedFields.length > 0) {
            for (int i = 0; i < ownedFields.length; i++) {
                ownedStreetsAsStrings[i] = ownedFields[i].getName();
            }
            if (ownedFieldsNotPawned.length > 0) {
                for (int i = 0; i < ownedFieldsNotPawned.length; i++) {
                    ownedStreetsNotPawnedAsStrings[i] = ownedFieldsNotPawned[i].getName();
                }
            }
                if (player.getBalance() < 0 && ownedStreetsNotPawnedAsStrings.length > 0) {
                    dropdown = this.gui.getUserSelection(player.getName() + ", du har ikke flere penge i din pengebeholdning, pants??t ejendomme eller s??lg huse p?? dine grunde:", ownedStreetsNotPawnedAsStrings);
                } else if (player.getBalance() < 0 && ownedStreetsNotPawnedAsStrings.length == 0) {
                    this.gui.getUserButtonPressed(player.getName() + ", du er dev??rre g??et fallit", "Okay, ??v");
                    dropdown = "Afslut tur";
                } else {
                    if (nrOfEqualRolls >= 1 && nrOfEqualRolls < 3) {
                        choice = this.gui.getUserButtonPressed(player.getName() + ", vil du adminstere dine grunde, byde p?? en anden spillers grund eller pr??ve at s??lge en af dine grunde til en anden spiller? M??ske bare afslutte din tur?", "Adminstrer grunde", "Byd p?? ejendom", "Pr??v at s??lge ejendom", "Du har sl??et 2 ens, s?? du m?? sl?? igen");
                    } else {
                        choice = this.gui.getUserButtonPressed(player.getName() + ", vil du adminstere dine grunde, byde p?? en anden spillers grund eller pr??ve at s??lge en af dine grunde til en anden spiller? M??ske bare afslutte din tur?", "Adminstrer grunde", "Byd p?? ejendom", "Pr??v at s??lge ejendom", "Afslut tur");
                    }
                    if (choice.equals("Adminstrer grunde")) {
                        dropdown = this.gui.getUserSelection(player.getName() + ", administrer grunde...", ownedStreetsAsStrings);
                    } else if (choice.equals("Byd p?? ejendom")) {
                        dropdown = choice;
                    } else if (choice.equals("Pr??v at s??lge ejendom")) {
                        dropdown = choice;
                    } else {
                        dropdown = "Afslut tur";
                    }
                }
            } else {
                dropdown = "Afslut tur";
            }
            return dropdown;
    }

    public String chooseWhoYouWantToBuy(Player player, String choice, String buyFromOpponent){
        String possibilities = null;
        if(choice.equals("Pr??v at s??lge ejendom")) {
            possibilities = this.gui.getUserButtonPressed("V??lg spilleren du gerne vil pr??ve at s??lge til:", gameController.getAllOpponentNames(player));
        }else if(choice.equals("Byd p?? ejendom")){
            possibilities = this.gui.getUserButtonPressed("V??lg spilleren du gerne vil pr??ve at k??be fra:", gameController.getFieldFromName(buyFromOpponent).getState().getOwner().getName());
        }
        return possibilities;
    }

    public int tryToSellProperty(Player opponent, Player player){
        return this.gui.getUserInteger(player.getName() + " skriv et tilbud til " + opponent.getName() + " (husk at det laveste du har er 50kr.;) :", 0, opponent.getBalance());
    }
    public String soldOrKeptByPlayer(Field field, Player opponent, int bid) {
        String answer;
        if (field.getState().getOwner().equals(opponent)) {
            answer = this.gui.getUserButtonPressed("Tilykke " + opponent.getName() + " du er nu den stolte ejer af " + field.getName() + " for s??lle" + bid + "kr.!", "Fedt tak");
        } else{
            answer = this.gui.getUserButtonPressed(opponent.getName() + " gad ikke k??be din ejendom for det tilbud", "Okay, ??v");
        }
        return answer;
    }
    public String acceptOrDeclineOfferSelling(Player player, Field field, Player opponent, int bid){
        return this.gui.getUserButtonPressed(opponent.getName() + ", vil du acceptere " + player.getName() + "'s tilbud p?? " + bid + "kr. og k??be " + field.getName() +"?", "Ja", "Nej");
    }

    public String soldOrKeptByOpponent(Field field, Player player, int bid, String offerAnswer) {
        String answer = null;
        if(offerAnswer.equals("??v")){
            answer = this.gui.getUserButtonPressed("Du kan desv??rre ikke k??be/s??lge denne grund da der er huse p?? den eller en af de andre i samme kategori",  "Okay, ??v");
        } else if (offerAnswer.equals("Ja")) {
            answer = this.gui.getUserButtonPressed("Tilykke " + player.getName() + " du er nu den stolte ejer af " + field.getName() + " for s??lle" + bid + "kr.!", "Fedt tak");
        }else if(offerAnswer.equals("Nej")){
            answer = this.gui.getUserButtonPressed(field.getState().getOwner().getName() + " gider ikke s??lge sin grund til dig for denne pris!" , "Okay, ??v");
        }
        return answer;
    }
    public int tryToBuyProperty(Player player){
        return this.gui.getUserInteger(player.getName() + ", pr??v at byd p?? grunden og se om modspilleren vil acceptere (husk at det laveste du har er en 50;)): ", 0, player.getBalance());
    }
    public String acceptOrDeclineOfferBuying(Player player, Field field, int bid){
        return this.gui.getUserButtonPressed(field.getState().getOwner().getName() + ", vil du acceptere " + player.getName() + "'s bud p?? " + bid + "kr. for " + field.getName() +"?", "Ja", "Nej");
    }
    public String buyOrSellProperties(String choice, Player player) {
        Field[] ownedFields = gameController.getOwnedByPlayer(gameController.getAllBuyableFields(GameBoard.getSingleton().getFields()), player);
        String[] ownedStreetsAsStrings = new String[ownedFields.length];
            for (int i = 0; i < ownedFields.length; i++) {
                ownedStreetsAsStrings[i] = ownedFields[i].getName();
            }
            String[] opponentFields = gameController.getAllOpponentsFields();
            String chosenProperty =  null;
            if (choice.equals("Byd p?? ejendom") && opponentFields.length>0) {
                chosenProperty = this.gui.getUserSelection(player.getName() + ", v??lg en af modspillernes ejendomme du vil byde p??: ", opponentFields);
            } else if(choice.equals("Pr??v at s??lge ejendom")&& ownedStreetsAsStrings.length>0) {
                chosenProperty = this.gui.getUserSelection(player.getName() + ", v??lg en af dine grunde som du vil s??lge til en modspiller", ownedStreetsAsStrings);
            }else if (choice.equals("Byd p?? ejendom")){
                chosenProperty = this.gui.getUserButtonPressed(player.getName() + " der er ingen ejendomme at byde p??", "Okay, ??v");
            }else if(choice.equals("Pr??v at s??lge ejendom")){
                chosenProperty = this.gui.getUserButtonPressed(player.getName() + " der er ingen ejendomme at s??lge p??", "Okay, ??v");
        }
            return chosenProperty;
    }

    public String buySellHouses(String fieldName, Player player){
        BuyableField field = gameController.chosenStreetField(fieldName,player);
        GUI_Street gui_field = (GUI_Street) gui.getFields()[field.getPosition()];
        String choice;
        String choice2 = null;
        int pawnedFieldPrice = (int) (Math.ceil((field.getPrice()*1.1)/100.0))*100;
        if(player.getBalance()<0){
            if(gameController.canSellOneMoreHouse(field) && gameController.canPawnProperty(field)){

            choice = this.gui.getUserButtonPressed(player.getName() + ", vil du pants??tte ejendommen? Eller s??lge huse?", "Pants??t", "S??lg hus");

            if(choice.equals("Pants??t")){
                choice2 = this.gui.getUserButtonPressed(player.getName() + " er du sikker p?? du vil pants??tte ejendommen?","Pants??t ejendom");
            } else{
                choice2 = this.gui.getUserButtonPressed(player.getName() + " er du sikker p?? du vil s??lge et hus?","S??lg hus");
            }
            }
            else if(gameController.canPawnProperty(field))
            {choice2 =  this.gui.getUserButtonPressed(player.getName() + ", du kan ikke s??lge huse p?? denne grund, vil du pants??tte ejendommen for " + field.getPrice() + "kr. ?","Pants??t ejendom");}
            else if(gameController.canSellOneMoreHouse(field)){
                choice2 =  this.gui.getUserButtonPressed(player.getName() + ", du kan ikke pants??tte denne ejendom, vil du s??lge et hus for " + field.getHousePrice()/2 + "kr. ?","S??lg hus");}
            }
        else{
        if(field.getState().isPawned()) {
            choice = this.gui.getUserButtonPressed("Vil du k??be ejendommen tilbage for " + pawnedFieldPrice + "kr. ? Eller s??lge eller k??be huse?", "K??b ejendom tilbage", "Annuller");
            if(choice.equals("K??b ejendom tilbage") && pawnedFieldPrice < player.getBalance()){
                choice2 = choice;
            } else{
                choice2 = this.gui.getUserButtonPressed("Du har desv??rre ikke r??d til at k??be denne ejendom tilbage." , "Okay, ??v");
            }
        }
        else {
            choice = this.gui.getUserButtonPressed("Vil du pants??tte ejendommen? Eller s??lge eller k??be huse?", "Pants??t ejendom", "S??lg hus", "K??b hus");
        }
        if(choice.equals("Pants??t ejendom") && gameController.canPawnProperty(field)){
            choice2 = this.gui.getUserButtonPressed(player.getName()+ ", hvis du pants??tter ejendommen skal du betale et l??n af p?? 10% for at f?? den igen, forts??t?", "Pants??t ejendom", "Annuller");
        } else if(choice.equals("Pants??t ejendom") && !gameController.canPawnProperty(field)){
            choice2 = this.gui.getUserButtonPressed(player.getName()+ ", du kan desv??rre ikke pants??tte denne ejendom, s??lg husene p?? de andre grunde f??rst!", "Okay, ??v");
        }
        else if(choice.equals("K??b hus") && gameController.isAllowedBuildHouses(field.getColor(),player)){
            if(gameController.canBuildOneMoreHouse(field,player) && player.getBalance() >= field.getHousePrice()){
                choice2 = this.gui.getUserButtonPressed(player.getName()+ ", k??b hus(eller hotel, hvis du har 4 huse) for " + field.getHousePrice() + " kr. eller annuller:", "K??b hus", "Annuller");
                if(choice2.equals("K??b hus") && field.getState().getNumOfHouses()<=3){
                gui_field.setHouses(field.getState().getNumOfHouses()+1);
                }
                else if(choice2.equals("K??b hus") && field.getState().getNumOfHouses()==4){
                    gui_field.setHouses(0);
                    gui_field.setHotel(true);
                }
            }else if(choice2.equals("K??b hus") && field.getState().getNumOfHouses()==5){
                choice2 = this.gui.getUserButtonPressed(player.getName() + ", du har allerede et hotel, du kan ikke k??be flere huse p?? denne grund", "Okay, ??v");
            }else if(!gameController.canBuildOneMoreHouse(field,player) && !field.getState().isPawned()){
                choice2 = this.gui.getUserButtonPressed(player.getName()+ ", du har enten ikke r??d til et hus p?? denne grund ellers bygger du uj??vnt p?? dine grunde, byg et eller 2 huse p?? en af de andre af samme f??r du m?? bygge her.", "Okay, ??v");
            }

        } else if(choice.equals("K??b hus") && !gameController.isAllowedBuildHouses(field.getColor(), player)){
            choice2 = this.gui.getUserButtonPressed(player.getName()+ ", du kan ikke k??be huse p?? denne grund da du enten mangler de resterende grunde af samme farve, en af dem er pantsat ellers har du ikke r??d", "Okay, ??v");
        } else if (choice.equals("S??lg hus") && gameController.canSellOneMoreHouse(field)) {

            choice2 = this.gui.getUserButtonPressed(player.getName() + ", s??lg et hus p?? denne grund til havldelen af den originale v??rdi: " + field.getHousePrice() / 2 + " kr. eller annuller?", "S??lg hus", "Annuller");
            if (choice2.equals("S??lg hus") && field.getState().getNumOfHouses() <= 4) {
                gui_field.setHouses(field.getState().getNumOfHouses()-1);
            } else if (choice2.equals("S??lg hus") && field.getState().getNumOfHouses() == 5) {
                gui_field.setHotel(false);
                gui_field.setHouses(4);
            }
        } else if (choice.equals("S??lg hus") && !gameController.canSellOneMoreHouse(field)) {
            choice2 = this.gui.getUserButtonPressed(player.getName() + ", du har enten ingen huse at s??lge, ellers pr??ver du at s??lge uj??vnt", "Okay, ??v");
        }
        }
        return choice2;
    }

    public void setOwnerAndRent(Player player, int playerIndex){
        GUI_Ownable gui_field = (GUI_Ownable) gui.getFields()[player.getPosition()];
        Field field = GameBoard.getSingleton().getFields()[player.getPosition()];
        if(field.getState().hasOwner()){
            gui_field.setOwnerName(field.getState().getOwner().getName());
            gui_field.setRent(Integer.toString(((BuyableField)field).getRent()[field.getState().getNumOfHouses()]));
            gui_field.setBorder(this.gui_players.get(playerIndex).getCar().getPrimaryColor());
        }
    }

    public void setPawnedGUI(Field field, int indexOfCurrentPlayer){
        GUI_Ownable gui_ownable = (GUI_Ownable) gui.getFields()[field.getPosition()];
        gui_ownable.setBorder(playerColors[indexOfCurrentPlayer], Color.GRAY);
        gui_ownable.setRent("Pantsat: 0 kr.");
    }
    public void setUnpawnedGUI(Field field, int indexOfCurrentPlayer){
        GUI_Ownable gui_ownable = (GUI_Ownable) gui.getFields()[field.getPosition()];
        gui_ownable.setBorder(playerColors[indexOfCurrentPlayer]);
        gui_ownable.setRent(Integer.toString(gameController.getCurrentRent(field)));
    }

    public void updateGUI(Field[] fields, ArrayList<Player> players) {
        for (int j = 0; j < players.size(); j++) {
            if (this.gameController.isBankrupt(this.gameController.getPlayers().get(j))) {
                this.gui_players.get(j).setName(this.gameController.getPlayers().get(j).getName() + " ELLIMINERET");
                this.gui_players.get(j).getCar().setPosition(this.fields[0]);

                }
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].getType().equals(FieldType.BREWERY) || fields[i].getType().equals(FieldType.STREET) || fields[i].getType().equals(FieldType.FERRY)) {
                        GUI_Ownable gui_ownable = (GUI_Ownable) gui.getFields()[i];
                        if (fields[i].getState().hasOwner()) {
                            gui_ownable.setOwnerName(fields[i].getState().getOwner().getName());
                            gui_ownable.setRent(Integer.toString(gameController.getCurrentRent(fields[i])));
                            if (players.get(j).getName().equals(fields[i].getState().getOwner().getName())) {
                                gui_ownable.setBorder(this.gui_players.get(j).getCar().getPrimaryColor());
                                if (fields[i].getState().isPawned()) {
                                    gui_ownable.setRent("Pantsat: 0 kr.");
                                    gui_ownable.setBorder(this.gui_players.get(j).getCar().getPrimaryColor(), Color.GRAY);
                                }
                            }
                        }else{
                            gui_ownable.setOwnerName(null);
                            gui_ownable.setBorder(null);
                            gui_ownable.setRent(null);
                        }
                    }
                }
            }
        }



    public void testButton(ArrayList<Player> players) {
        String test1 = this.gui.getUserButtonPressed("Test det boi", players.get(0).getName() + " Ejer de bl?? grunde", "Lad mig v??re");
        if (test1.equals(players.get(0).getName() + " Ejer de bl?? grunde")) {
            gameController.masterTest(players);
        }
    }


    public void displayWinner(Player player){
        this.gui.getUserButtonPressed("Tillykke " + player.getName() + " du har vundet Matador! Sikke en t??lmodig champ du er!", "Mange tak, Afslut spillet");
        this.gui.close();
    }


    public boolean getGameModeFromUser()
    {
        return this.gui.getUserButtonPressed("V??lg spil type:", "Normal", "Bagl??ns").equals("Bagl??ns");
    }

}

