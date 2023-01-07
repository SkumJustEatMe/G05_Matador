package fields;

import fields.effects.JailEffect;
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

    }

    public int getIndexOfJail() {
        int index = 0;
        for (Field field : FieldList)
        {
            if (field.getType() == FieldType.JAIL  && ((JailField)field).getEvent() == JailEffect.VISITJAIL) {
                break;
            }

            index++;
        }
        return index;
    }
    public Field[] getFieldList() { return this.FieldList; }
}