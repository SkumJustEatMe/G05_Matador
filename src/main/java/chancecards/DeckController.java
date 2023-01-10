package chancecards;

import fields.FieldType;
import utils.TxtReader;

import java.io.File;

public class DeckController {

    private final String chanceCardFile;
    private String[] cardInfo;

    private FieldType FERRY = FieldType.FERRY;

    private ChanceCard[] cards = new ChanceCard[46];

    public ChanceCard getCard() {
        return this.cards[(int) (Math.random() * 46)];
    }

    public DeckController() {
        this.chanceCardFile = System.getProperty("user.dir").concat(File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "fields.csv");
        this.cardInfo = TxtReader.convertLinesToArray(this.chanceCardFile);

        this.cards[0] = new PayPerPropertyCard(cardInfo[0],500,2000);
        this.cards[1] = new PayPerPropertyCard(cardInfo[1],800,2300);
        this.cards[2] = new RecieveOrPayCard(cardInfo[2],-1000);
        this.cards[3] = new RecieveOrPayCard(cardInfo[3],-300);
        this.cards[4] = new RecieveOrPayCard(cardInfo[4],-200);
        this.cards[5] = new RecieveOrPayCard(cardInfo[5], -3000);
        this.cards[6] = new RecieveOrPayCard(cardInfo[6], -3000);
        this.cards[7] = new RecieveOrPayCard(cardInfo[7], -1000);
        this.cards[8] = new RecieveOrPayCard(cardInfo[8], -200);
        this.cards[9] = new RecieveOrPayCard(cardInfo[9], -1000);
        this.cards[10] = new RecieveOrPayCard(cardInfo[10], -200);
        this.cards[11] = new RecieveOrPayCard(cardInfo[11], -2000);
        this.cards[12] = new RecieveOrPayCard(cardInfo[12], 500);
        this.cards[13] = new RecieveOrPayCard(cardInfo[13], 500);
        this.cards[14] = new RecieveOrPayCard(cardInfo[14], 1000);
        this.cards[15] = new RecieveOrPayCard(cardInfo[15], 1000);
        this.cards[16] = new RecieveOrPayCard(cardInfo[16], 1000);
        this.cards[17] = new RecieveOrPayCard(cardInfo[17], 3000);
        this.cards[18] = new RecieveOrPayCard(cardInfo[18], 1000);
        this.cards[19] = new RecieveOrPayCard(cardInfo[19], 1000);
        this.cards[20] = new RecieveOrPayCard(cardInfo[20], 1000);
        this.cards[21] = new RecieveOrPayCard(cardInfo[21], 1000);
        this.cards[22] = new RecieveOrPayCard(cardInfo[22], 1000);
        this.cards[23] = new RecieveOrPayCard(cardInfo[23],  200);
        this.cards[24] = new MatadorCard(cardInfo[24], 40000 );
        this.cards[25] = new ReceivePerPlayerCard(cardInfo[25], 200 );
        this.cards[26] = new ReceivePerPlayerCard(cardInfo[26], 500);
        this.cards[27] = new ReceivePerPlayerCard(cardInfo[27], 500);
        this.cards[28] = new MoveToCard(cardInfo[28], 0);
        this.cards[29] = new MoveToCard(cardInfo[29], 0);
        this.cards[30] = new MoveCard(cardInfo[30], 3);
        this.cards[31] = new MoveCard(cardInfo[31], -3);
        this.cards[32] = new MoveCard(cardInfo[32], -3);
        this.cards[33] = new MoveToCard(cardInfo[33], 11);
        this.cards[34] = new MoveToTypeCard(cardInfo[34], FERRY, 2);
        this.cards[35] = new MoveToTypeCard(cardInfo[35], FERRY, 2);
        this.cards[36] = new MoveToCard(cardInfo[36],15);
        this.cards[37] = new MoveToCard(cardInfo[37], 24);
        this.cards[38] = new MoveToCard(cardInfo[38], 32);
        this.cards[39] = new MoveToTypeCard(cardInfo[39], FERRY, 1);
        this.cards[40] = new MoveToCard(cardInfo[40], 19);
        this.cards[41] = new MoveToCard(cardInfo[41], 39);
        this.cards[42] = new GetOutOfJailCard(cardInfo[42]);
        this.cards[43] = new GetOutOfJailCard(cardInfo[43]);
        this.cards[44] = new GoToJailCard(cardInfo[44], 10);
        this.cards[45] = new GoToJailCard(cardInfo[45], 10);





    }

    private void PopulateDeckDescription(){
        }
    }


}
