package fields;

import utils.CsvReader;

import java.io.File;
import java.util.Scanner;

public class GameBoard {

    private File gameBoardFile;
    private String[][] fieldsInfo;
    private Field[] FieldList = {
            new Field("Start"),
            new Field("2"),
            new Field("3"),
            new Field("4"),
            new Field("5"),
            new Field("6"),
            new Field("7"),
            new Field("8"),
            new Field("9"),
            new Field("10"),
            new EventField("Visiting jail", Event.VISITJAIL),
            new Field("12"),
            new Field("13"),
            new Field("14"),
            new Field("15"),
            new Field("16"),
            new Field("17"),
            new Field("18"),
            new Field("19"),
            new Field("20"),
            new Field("21"),
            new Field("22"),
            new Field("23"),
            new Field("24"),
            new Field("25"),
            new Field("26"),
            new Field("27"),
            new Field("28"),
            new Field("29"),
            new Field("30"),
            new EventField("Go to jail", Event.GOTOJAIL),
            new Field("32"),
            new Field("33"),
            new Field("34"),
            new Field("35"),
            new Field("36"),
            new Field("37"),
            new Field("38"),
            new Field("39"),
            new Field("40"),
    };

    public GameBoard()
    {
        this.gameBoardFile = CsvReader.read(System.getProperty("user.dir").concat(File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "fields.csv"
                ));
        this.fieldsInfo = CsvReader.convertTo2DArray(this.gameBoardFile.getAbsolutePath());
    }

    private File readCsvFile()
    {
        String pathOfCsvFile = System.getProperty("user.dir").concat(File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "fields.csv"
                );
        return new File(pathOfCsvFile);
    }

    public int getIndexOfJail() {
        int index = 0;
        for (Field field : FieldList)
        {
            if (field instanceof EventField eventField && eventField.getEvent() == Event.VISITJAIL) {
                break;
            }
            index++;
        }
        return index;
    }
    public Field[] getFieldList() { return this.FieldList; }
}