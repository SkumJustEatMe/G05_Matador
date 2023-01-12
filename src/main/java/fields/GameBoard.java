package fields;

import utils.CsvReader;
import java.awt.*;
import java.io.File;

public class GameBoard {

    private static final GameBoard gameBoard = new GameBoard();
    public static GameBoard getSingleton() {return GameBoard.gameBoard;}
    private final String[][] fieldsInfo;
    private final Field[] fields;
    public Field[] getFields() {return this.fields;}


    private GameBoard()
    {
        String gameBoardFilePath = System.getProperty("user.dir").concat(File.separator +
                "src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "fields.csv"
        );
        this.fieldsInfo = CsvReader.convertTo2DArray(gameBoardFilePath);
        this.fields = populateFieldsArray();
    }

    private Field[] populateFieldsArray()
    {
        //csv header: Name,Position,Type,Color,Price,HousePrice,Rent0,Rent1,Rent2,Rent3,Rent4,Rent5
        Field[] fields = new Field[this.fieldsInfo.length - 1];
        for (int i = 1; i < fieldsInfo.length; i++)
        {
            String[] currentRow = fieldsInfo[i];
            FieldType currentType = FieldType.valueOf(currentRow[2].toUpperCase());

            switch (currentType)
            {
                default:
                case START:
                case CHANCE:
                case JAIL:
                case TAX:
                case REFUGE:
                    fields[i - 1] = new EffectField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        CsvReader.NameToEffect(currentRow[0]),
                        null,
                        currentType == FieldType.TAX ? Integer.parseInt(currentRow[4]) : null);
                        break;
                case BREWERY:
                    fields[i - 1] = new BuyableField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        null,
                        Integer.parseInt(currentRow[4]),
                        null,
                        new int[]{
                                Integer.parseInt(currentRow[6]),
                                Integer.parseInt(currentRow[7])});
                        break;
                case FERRY:
                    fields[i - 1] = new BuyableField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        null,
                        Integer.parseInt(currentRow[4]),
                        null,
                        new int[]{
                                Integer.parseInt(currentRow[6]),
                                Integer.parseInt(currentRow[7]),
                                Integer.parseInt(currentRow[8]),
                                Integer.parseInt(currentRow[9])});
                        break;
                case STREET:
                    fields[i - 1] = new BuyableField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        Color.getColor(currentRow[3]),
                        Integer.parseInt(currentRow[4]),
                        Integer.parseInt(currentRow[5]),
                        new int[]{
                                Integer.parseInt(currentRow[6]),
                                Integer.parseInt(currentRow[7]),
                                Integer.parseInt(currentRow[8]),
                                Integer.parseInt(currentRow[9]),
                                Integer.parseInt(currentRow[10]),
                                Integer.parseInt(currentRow[11])});
                        break;
            }
        }
        return fields;
    }

    public int getIndexOfJail() {
        int index = 0;
        for (Field field : fields)
        {
            if (field.getType() == FieldType.JAIL && ((EffectField)field).getEffect() == Effect.JAIL_VISIT){
                break;
            }

            index++;
        }
        return index;
    }
}