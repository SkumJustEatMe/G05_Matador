package chancecards;

import fields.FieldType;
import game.Player;
import utils.TxtReader;

import java.io.File;
import java.util.ArrayList;

public class DeckController {

    private final String chanceCardFile;
    private String[] cardInfo;

    private FieldType FERRY = FieldType.FERRY;

    private ArrayList<ChanceCard> cards;

    public ChanceCard getCard() {
        return this.cards.get((int) (Math.random() * 46));
    }

    public DeckController() {
        this.chanceCardFile = System.getProperty("user.dir").concat(File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "chancecards.txt");
        this.cardInfo = TxtReader.convertLinesToArray(this.chanceCardFile);
        this.cards = new ArrayList<>();
        this.cards.add(new PayPerPropertyCard(cardInfo[0],500,2000));
        this.cards.add(new PayPerPropertyCard(cardInfo[0],500,2000));
        this.cards.add(new PayPerPropertyCard(cardInfo[1],800,2300));
        this.cards.add(new RecieveOrPayCard(cardInfo[2],-1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[3],-300));
        this.cards.add(new RecieveOrPayCard(cardInfo[4],-200));
        this.cards.add(new RecieveOrPayCard(cardInfo[5], -3000));
        this.cards.add(new RecieveOrPayCard(cardInfo[6], -3000));
        this.cards.add(new RecieveOrPayCard(cardInfo[7], -1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[8], -200));
        this.cards.add(new RecieveOrPayCard(cardInfo[9], -1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[10], -200));
        this.cards.add(new RecieveOrPayCard(cardInfo[11], -2000));
        this.cards.add(new RecieveOrPayCard(cardInfo[12], 500));
        this.cards.add(new RecieveOrPayCard(cardInfo[13], 500));
        this.cards.add(new RecieveOrPayCard(cardInfo[14], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[15], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[16], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[17], 3000));
        this.cards.add(new RecieveOrPayCard(cardInfo[18], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[19], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[20], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[21], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[22], 1000));
        this.cards.add(new RecieveOrPayCard(cardInfo[23],  200));
        this.cards.add(new MatadorCard(cardInfo[24], 40000 ));
        this.cards.add(new ReceivePerPlayerCard(cardInfo[25], 200 ));
        this.cards.add(new ReceivePerPlayerCard(cardInfo[26], 500));
        this.cards.add(new ReceivePerPlayerCard(cardInfo[27], 500));
        this.cards.add(new MoveToCard(cardInfo[28], 0));
        this.cards.add(new MoveToCard(cardInfo[29], 0));
        this.cards.add(new MoveCard(cardInfo[30], 3));
        this.cards.add(new MoveCard(cardInfo[31], -3));
        this.cards.add(new MoveCard(cardInfo[32], -3));
        this.cards.add(new MoveToCard(cardInfo[33], 11));
        this.cards.add(new MoveToTypeCard(cardInfo[34], FERRY, 2));
        this.cards.add(new MoveToTypeCard(cardInfo[35], FERRY, 2));
        this.cards.add(new MoveToCard(cardInfo[36],15));
        this.cards.add(new MoveToCard(cardInfo[37], 24));
        this.cards.add(new MoveToCard(cardInfo[38], 32));
        this.cards.add(new MoveToTypeCard(cardInfo[39], FERRY, 1));
        this.cards.add(new MoveToCard(cardInfo[40], 19));
        this.cards.add(new MoveToCard(cardInfo[41], 39));
        this.cards.add(new GetOutOfJailCard(cardInfo[42]));
        this.cards.add(new GetOutOfJailCard(cardInfo[43]));
        this.cards.add(new GoToJailCard(cardInfo[44], 10));
        this.cards.add(new GoToJailCard(cardInfo[45], 10));
    }
    public int getOutOfJailCards(ArrayList<Player> players){
        int sum = 0;
        for(int i = 0; i<=players.size(); i++){
            sum =+ players.get(i).getGetOutOfJailFreeCard();
        }
        return sum;
    }

}


