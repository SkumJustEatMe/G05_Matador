package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public final class CsvReader
{
    private CsvReader(){}

    public static String[][] convertTo2DArray(String path)
    {
        ArrayList<String> lineList  = new ArrayList<String>();

        Scanner scanner;
        try
        {
            scanner = new Scanner(new File(path));
            scanner.useDelimiter("\n");

            while (scanner.hasNextLine())
            {
                String nextLine = scanner.nextLine();
                lineList.add(nextLine);
            }

            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String[][] convertedArray = new String[lineList.size()][];
        for (int i = 0; i < lineList.size(); i++)
        {
            convertedArray[i] = lineList.get(i).split(",");
        }


        return convertedArray;
    }

    public static File read(String path)
    {
        return new File(path);
    }
}
