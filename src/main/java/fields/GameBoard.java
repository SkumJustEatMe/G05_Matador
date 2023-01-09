package fields;

import utils.CsvReader;
import java.io.File;

public class GameBoard {

    private final File gameBoardFile;
    private String[][] fieldsInfo;
    private Field[] FieldList;

    public GameBoard()
    {
        this.gameBoardFile = new File(System.getProperty("user.dir").concat(File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "fields.csv"
                ));
        this.fieldsInfo = CsvReader.convertTo2DArray(this.gameBoardFile.getAbsolutePath());
        PopulateFieldList();
    }

    private void PopulateFieldList()
    {
        //csv header: Name,Position,Type,Price,HousePrice,Rent0,Rent1,Rent2,Rent3,Rent4,Rent5
        for (int i = 1; i < fieldsInfo.length; i++)
        {
            String[] currentCsvField = this.fieldsInfo[i];
            switch (currentCsvField[2])
            {
                case "start":
                    this.FieldList[i-1] = new StartField(currentCsvField[0], Integer.parseInt(currentCsvField[1]), FieldType.START);
                case "street":
                    this.FieldList[i-1] = new StreetField(currentCsvField[0], Integer.parseInt(currentCsvField[1]), FieldType.STREET, Integer.parseInt(currentCsvField[3]), );
            }
        }
    }

    public int getIndexOfJail() {
        int index = 0;
        for (Field field : FieldList)
        {
            if (field.getEffect() == FieldEffect.JAIL_VISIT) {
                break;
            }

            index++;
        }
        return index;
    }
    public Field[] getFieldList() { return this.FieldList; }
}