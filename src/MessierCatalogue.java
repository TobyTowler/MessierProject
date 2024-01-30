import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MessierCatalogue
{
    public ArrayList<MessierObject> collection = new ArrayList<MessierObject>();
    //ArrayList<MessierObject> sortedCollection =new ArrayList<>();
    //for comparisons, any value will be greater than 0
    MessierObject dummy = new MessierObject("0,0,0,0,0,0,0,0,0");

    public String toString(ArrayList<MessierObject> arr) //outs each line and a new line
    {
        String str = "";
        for(int i = 0; i<arr.stream().count(); i++)
        {
            str += arr.get(i).toString() + "\n";
        }
        return str;
    }

    //https://howtodoinjava.com/java/sort/collections-sort/
    // I used this a framework for this comprartor and the magsort method
    //i retyped and adapted the variable names/paramaters to match my program
    Comparator<MessierObject> compareMag = new Comparator<MessierObject>() {
    @Override
    public int compare(MessierObject o1, MessierObject o2) {
        return o1.mag.compareTo(o2.mag);
    }
};
    public ArrayList<MessierObject> magSort() //sorts in order of apparent magnitude
    {
        Collections.sort(collection, compareMag);
        return collection;

    }


    public double openAvgMag() //displays average magnitude of all open clusters
    {
        double total = 0;
        ArrayList<Double> mags = new ArrayList<Double>();
        for (int i = 0; i<collection.stream().count(); i++)
        {
            if (collection.get(i).type.equalsIgnoreCase("opencluster"))
            {
                mags.add(collection.get(i).mag);
                //System.out.println(mags.stream().count());
            }
        }
        for (int j = 0; j< mags.stream().count(); j++)
        {
            total += mags.get(j);
        }
//        System.out.println(mags.stream().count());
//        System.out.println(total);

        return total/mags.stream().count();
    }

    public MessierObject furthestGlobCLust()
    {
        ArrayList<MessierObject> globs = new ArrayList<>();
        for(int i =0; i<collection.stream().count();i++)
        {
            if(collection.get(i).type.equalsIgnoreCase("globularcluster"))
            {
                globs.add(collection.get(i));
                //System.out.println(collection.get(i));
            }
        }
        MessierObject biggest = dummy;
        for(int j = 0; j<globs.stream().count();j++)
        {
            if(globs.get(j).getDist()>biggest.getDist())
            {
                biggest = globs.get(j);
            }
        }
        return biggest;
    }

    public MessierObject sagLowestDec()
    {
        ArrayList<MessierObject> Sags = new ArrayList<>();
        for(int i =0; i<collection.stream().count();i++)
        {
            if(collection.get(i).constellation.toLowerCase().equals("sagittarius"))
            {
                Sags.add(collection.get(i));
            }

        }
        MessierObject lowestDec = Sags.get(0);
        for(int j =1;j<Sags.stream().count();j++)
        {
            if(Sags.get(j).DetoRadians()<lowestDec.DetoRadians())
            {
                lowestDec = Sags.get(j);
            }
        }
        return lowestDec;
    }

    //https://en.wikipedia.org/wiki/Angular_distance
    public double getAngularDistance(MessierObject m1, MessierObject m2)
    {
        double sin = Math.sin(m1.DetoRadians()) * Math.sin(m2.DetoRadians());
        double cos = Math.cos(m1.DetoRadians()) * Math.cos(m2.DetoRadians());
        double cosRA = Math.cos(m1.RAtoRadians() - m2.RAtoRadians());
        double Angdist = Math.acos(sin + (cos*cosRA));
        double AngdistMins = Angdist*(60 * 180)/Math.PI;
        return AngdistMins;
    }

    public MessierObject closestAngDist(String m) //enter the mnumber wanted e.g. m45
    {
        int pos = 0;
        boolean found = false;
        for(int i = 0; i<collection.stream().count(); i++)
        {
            if(collection.get(i).mNumber.equalsIgnoreCase(m))
            {
                pos = i;
                found = true;
                //System.out.println("found in list");
                break;
            }
        }
        if(!found)
        {
            System.err.println("Object not found");
            return null;
        }

        Double closest = 999999999d;
        Double dist =99999999999d;  //filler values, i was getting errors and 0.0 results with null
        Integer closestObjectPos = null;
        //boolean found1 = false;
        for(int i = 0; i<collection.stream().count(); i++)
        {
            dist = getAngularDistance(collection.get(pos), collection.get(i));
            //System.out.println(dist);
            if(closest ==null && closest!=dist)
            {
                closest = dist;
                //System.out.println("looping");
                //System.out.println(closest);
            }
            if(dist<closest && pos!=i)
            {
                //found1 = true;
                //System.out.println("working");
                closest = dist;
                closestObjectPos = i;
            }
        }
        //if (found1) {
            //System.out.println(closestObjectPos);
            System.out.println("The closest Object to " + collection.get(pos).mNumber + " is " + collection.get(closestObjectPos).mNumber + ", with a distance of "
                    + closest + " arc minutes");
            return collection.get(closestObjectPos);
        //}
//        else
//        {
//            System.out.println("didnt work");
//            return null;
//        }

    }


}
