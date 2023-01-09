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
        String[] lineArray = TxtReader.convertLinesToArray(path);

        String[][] convertedArray = new String[lineArray.length][];
        for (int i = 0; i < lineArray.length; i++)
        {
            convertedArray[i] = lineArray[i].split(",");
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
