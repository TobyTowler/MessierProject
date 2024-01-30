import javax.swing.text.Style;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class MessierProgram {
    private static PrintStream out;

    public static void main(String[] args) throws FileNotFoundException
    {
        //reading from the file
        MessierCatalogue cat = new MessierCatalogue();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("messier.txt"));
            String str;
            while ((str = reader.readLine()) != null)
            {
                //System.out.println(str);
                try {
                    cat.collection.add(new MessierObject(str));
                }
                catch(Exception e)
                {
                    System.err.println("Incorrect format at line " + str);
                }
            }

        }
        catch(IOException e)
        {
            System.err.println("File not found");
        }
        finally {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.err.println("Reader could not be closed");
                }
            }
        }


        //System.out.println(cat.toString(cat.collection));
    //testing
//        MessierObject d1 = new MessierObject("M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"");
//        System.out.println(cat.collection.get(0).toString());
//        //System.out.println(cat.toString(cat.collection));
//        System.out.println(cat.openAvgMag());
//
//        System.out.println("Furthest glob " + cat.furthestGlobCLust().toString());
//        System.out.println("Lowest sag " + cat.sagLowestDec().toString());
//        System.out.println(cat.collection.get(0).mNumber + "has" + cat.collection.get(0).mag);
//        System.out.println(cat.collection.get(2).mNumber + "has " + cat.collection.get(2).mag);
//        System.out.println(cat.collection.get(0).compareTo(cat.collection.get(2)));
//        System.out.println(cat.magSort());
//        for (int i = 0; i< cat.collection.stream().count();i++)
//        {
//            System.out.println(cat.collection.get(i));
//        }

//        System.out.println(cat.collection.get(41).RAtoRadians());
//        System.out.println(cat.collection.get(42).RAtoRadians());
//        System.out.println(cat.collection.get(41).DetoRadians());
//        System.out.println(cat.collection.get(42).DetoRadians());
//        System.out.println(cat.getAngularDistance(cat.collection.get(41), cat.collection.get(42)));
        //cat.magSort();


        //final questions
        //sort the list by magnitude
        System.out.println("The catalogue arranged in ascending apparent magnitude: ");
        cat.magSort();
        System.out.println(cat.toString(cat.collection));//display the catalogue
        System.out.println("\n");

        System.out.println("The average apparent magnitude of the open clusters is:  " + cat.openAvgMag());
        System.out.println("\n");

        System.out.println("The most distant Globular Cluster is: " + cat.furthestGlobCLust());
        System.out.println("\n");

        System.out.println("The lowest declination object in the Sagittarius constellation is: "+ cat.sagLowestDec());
        System.out.println("\n");

        System.out.println(cat.closestAngDist("m45"));
        System.out.println("\n");



    }
}