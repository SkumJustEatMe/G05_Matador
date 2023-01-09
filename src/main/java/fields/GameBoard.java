package fields;

import utils.CsvReader;
import java.io.File;

public class GameBoard {

    private final File gameBoardFile;
    private String[][] fieldsInfo;
    private Field[] FieldList;
    public Field[] getFieldList() { return this.FieldList; }

    public GameBoard()
    {
        this.gameBoardFile = new File(System.getProperty("user.dir").concat(File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "fields.csv"
                ));
        this.fieldsInfo = CsvReader.convertTo2DArray(this.gameBoardFile.getAbsolutePath());
        this.FieldList = new Field[this.fieldsInfo.length - 1];
        PopulateFieldList();
    }

    private void PopulateFieldList()
    {
        //csv header: Name,Position,Type,Price,HousePrice,Rent0,Rent1,Rent2,Rent3,Rent4,Rent5
        for (int i = 1; i < fieldsInfo.length; i++)
        {
            String[] currentRow = this.fieldsInfo[i];
            if (currentRow.length <= 3) {
                this.FieldList[i - 1] = new EffectField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        CsvReader.NameToEffect(currentRow[0]),
                        null);
            } else if (currentRow.length <= 4) {
                this.FieldList[i - 1] = new EffectField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        CsvReader.NameToEffect(currentRow[0]),
                        Integer.parseInt(currentRow[3]));
            } else if (currentRow[4].equals("")) {
                this.FieldList[i - 1] = new BuyableField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        Integer.parseInt(currentRow[3]),
                        null,
                        new int[]{
                                Integer.parseInt(currentRow[5]),
                                Integer.parseInt(currentRow[6])});
            }
            else if (currentRow.length <= 9)
            {
                this.FieldList[i - 1] = new BuyableField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        Integer.parseInt(currentRow[3]),
                        null,
                        new int[]{
                                Integer.parseInt(currentRow[5]),
                                Integer.parseInt(currentRow[6]),
                                Integer.parseInt(currentRow[7]),
                                Integer.parseInt(currentRow[8])});
            }
            else
            {
                this.FieldList[i - 1] = new BuyableField(
                        currentRow[0],
                        Integer.parseInt(currentRow[1]),
                        FieldType.valueOf(currentRow[2].toUpperCase()),
                        Integer.parseInt(currentRow[3]),
                        Integer.parseInt(currentRow[4]),
                        new int[]{
                                Integer.parseInt(currentRow[5]),
                                Integer.parseInt(currentRow[6]),
                                Integer.parseInt(currentRow[7]),
                                Integer.parseInt(currentRow[8]),
                                Integer.parseInt(currentRow[9]),
                                Integer.parseInt(currentRow[10])});
            }
        }
    }

    public int getIndexOfJail() {
        int index = 0;
        for (Field field : FieldList)
        {
            if (field.getType() == FieldType.JAIL && ((EffectField)field).getEffect() == Effect.JAIL_GOTO){
                break;
            }

            index++;
        }
        return index;
    }
}