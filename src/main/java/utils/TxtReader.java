package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TxtReader {
    public static String[] convertLinesToArray(String path)
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

        return Arrays.copyOf(lineList.toArray(), lineList.size(), String[].class);
    }
}
