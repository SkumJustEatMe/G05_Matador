package utils;

import fields.Effect;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public final class CsvReader
{
    private CsvReader(){}

    /**
     * converts a csv file to a 2D array
     * @param path path of file for conversion
     * @return 2D array representation of csv file
     */
    public static String[][] convertTo2DArray(String path)
    {
        ArrayList<String> lineList  = new ArrayList<>();

        try
        {
            Scanner scanner = new Scanner(new File(path));

            while (scanner.hasNextLine())
            {
                String nextLine = scanner.nextLine();
                lineList.add(nextLine);
            }

            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            //should probably display an error message to the user with the path
            e.printStackTrace();
        }

        String[][] convertedArray = new String[lineList.size()][];
        for (int i = 0; i < lineList.size(); i++)
        {
            convertedArray[i] = lineList.get(i).split(",");
        }

        return convertedArray;
    }

    public static Effect NameToEffect(String name)
    {
        return switch (name.toLowerCase()) {
            case "start" -> Effect.START;
            case "indkomstskat" -> Effect.TAX_4000;
            case "prøv lykken" -> Effect.CHANCE;
            case "i fængsel/på besøg" -> Effect.JAIL_VISIT;
            case "fængsel" -> Effect.JAIL_GOTO;
            case "ekstraordinær statsskat" -> Effect.TAX_PERCENTAGE;
            case "parkering" -> Effect.REFUGEE;
            default -> null;
        };
    }
}
