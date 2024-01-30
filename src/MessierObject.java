import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class MessierObject implements Comparable<MessierObject>
{

    String mNumber;
    String NGC;
    String name;
    String type;
    String distance;
    String constellation;
    String magnitude;
    Double mag;
    String ascensionS;     //has to be stored as double later
    String declinationS;   //has to be stored as double later
    String[] fullLine;
    //private Class<? extends MessierObject> ;

    public MessierObject(String line) throws ArrayIndexOutOfBoundsException
    {
        line = line.replaceAll("\\s+","");
        // ignoring commas in quote marks
        //https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
        //i edited the code to take format of the input from the file and fit in with my vairalbe names
        ArrayList<String> fullLine = new ArrayList<String>();
        int start = 0;
        boolean inQuotes = false;
        for (int current = 0; current < line.length(); current++) {
            if (line.charAt(current) == '\"') inQuotes = !inQuotes; // toggle state
            else if (line.charAt(current) == ',' && !inQuotes) {
                fullLine.add(line.substring(start, current));
                start = current + 1;
            }
        }
        fullLine.add(line.substring(start));
        //error checking
        if(fullLine.stream().count() != 9)
        {
            throw new ArrayIndexOutOfBoundsException("Incorrect number of fields");
        }

            mNumber = fullLine.get(0);
            NGC = fullLine.get(1);
            name = fullLine.get(2);
            type = fullLine.get(3);
            distance = fullLine.get(4);
            constellation = fullLine.get(5);
            magnitude = fullLine.get(6);
            mag = Double.parseDouble(magnitude);
            ascensionS = fullLine.get(7);
            declinationS = fullLine.get(8);

    }


    //https://physics.stackexchange.com/questions/224950/how-can-i-convert-right-ascension-and-declination-to-distances#:~:text=%CE%B4%3D(DEd%C2%B1,objects%20in%20the%20southern%20hemisphere.
    public double RAtoRadians()
    {
        int h = ascensionS.indexOf("h");
        int m = ascensionS.indexOf("m");
        int s = ascensionS.indexOf("s");
        String hours = ascensionS.substring(0,h);
        String minutes = ascensionS.substring(h+1,m);
        String seconds = ascensionS.substring(m+1,s);
        //System.out.println(hours);
        //System.out.println(minutes);
        //System.out.println(seconds);
        Double hoursd = Double.parseDouble(hours);
        Double minutesd = Double.parseDouble(minutes);
        Double secondsd = Double.parseDouble(seconds);
        //Double rads = (hoursd + minutesd/60 + secondsd/3600) * 15/360 * 2*Math.PI;
        Double rads = (hoursd*15 + minutesd/4 + secondsd/240) * Math.PI/180;
        return rads;
    }

    public double DetoRadians()
    {
        double rads;
        String sign = declinationS.substring(0,1);
        int degs = declinationS.indexOf("°");
        int mins = declinationS.indexOf("'");
        int secs = declinationS.indexOf('"');
        //System.out.println(degs);
        //System.out.println(mins);
        //System.out.println(secs);
        String degrees = declinationS.substring(0,degs);
        String minutes = declinationS.substring(degs+1,mins);
        String seconds = declinationS.substring(mins+1, secs);
        Double degsd = Double.parseDouble(degrees);
        Double minutesd = Double.parseDouble(minutes);
        Double secondsd = Double.parseDouble(seconds);
        if(sign.equals("-")) {
            degsd = Math.abs(degsd);
            rads = -(degsd + minutesd / 60 + secondsd / 3600) * Math.PI/180;
        }
        else {
            rads = (degsd + minutesd / 60 + secondsd / 3600) * Math.PI/180;
        }
        return rads;

    }

    public int compareTo(MessierObject m)
    {
        return Double.compare(m.mag, mag);
    }

    public String toString()
    {
        return this.mNumber + ", " + this.NGC + ", " + this.name + ", " + this.type + ", " + this.distance
        + ", " + this.constellation + ", " + this.magnitude + ", " + this.ascensionS + ", " +this.declinationS;

    }

    public double getDist()
    {
        double dist;
        if(distance.contains("-"))
        {
            int x = distance.indexOf("-");
            distance = distance.substring(0, x);
        }
        dist = Double.parseDouble(distance);
        return dist;
    }
}
