import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {


        String[] fileNames = getUserInput();

        Decathlon decathlon = processFile(fileNames[0]);

        toXML(fileNames[1], decathlon);

    }

    static String[] getUserInput() {
        //Default I/O values
        String inputName = "Decathlon_input.txt";
        String outputName = "Decathlon.xml";

        String[] array = new String[2];
        Scanner in = new Scanner(System.in);
        System.out.print("Write the name of the Input file (leave empty to use default): ");
        String tmp = in.nextLine();
        if (!tmp.isEmpty())
            array[0] = tmp;
        else
            array[0] = inputName;

        System.out.print("Write the name of the Output file (leave empty to use default): ");
        tmp = in.nextLine();
        if (!tmp.isEmpty())
            array[1] = tmp;
        else
            array[1] = outputName;

        return array;
    }

    static Decathlon processFile(String inputName) {
        Decathlon decathlon = new Decathlon();
        List<Competitor> competitors = new ArrayList<>();

        try {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader(inputName));
            // read the first line from the text file
            String fileRead = br.readLine();

            // List used to sum TotalScore
            List<Double> tempScores;

            // Other variables of Decathlon formulas
            double[] A = new double[]{25.4347, 0.14354, 51.39, 0.8465, 1.53775, 5.74352, 12.91, 0.2797, 10.14, 0.03768};
            double[] B = new double[]{18, 220, 1.5, 75, 82, 28.5, 4, 100, 7, 480};
            double[] C = new double[]{1.81, 1.4, 1.05, 1.42, 1.81, 1.92, 1.1, 1.35, 1.08, 1.85};

            // loop until all lines are read
            while (fileRead != null) {
                Competitor competitor = new Competitor();
                tempScores = new ArrayList<>();

                String[] tokenize = fileRead.split(";");

                // Calculate score
                for (int i = 1; i < tokenize.length; i++) {
                    if (i == 1 || i == 5 || i == 6)
                        tempScores.add(formulaTimeEvent(A[i - 1], B[i - 1], C[i - 1], Double.valueOf(tokenize[i])));
                    else if (i == 2 || i == 4 || i == 8)
                        tempScores.add(formulaDistanceEvent(A[i - 1], B[i - 1], C[i - 1], Double.valueOf(tokenize[i]) * 100));
                    else if (i == 10) {
                        String[] time = tokenize[i].split("\\.");
                        tempScores.add(formulaTimeEvent(A[i - 1], B[i - 1], C[i - 1], toSeconds(time)));
                    } else
                        tempScores.add(formulaDistanceEvent(A[i - 1], B[i - 1], C[i - 1], Double.valueOf(tokenize[i])));
                }

                // Calculate TotalScore
                int sum = 0;
                for (Double i : tempScores) {
                    sum += i;
                }
                competitor.setTotalScore(sum);
                competitor.setInputData(Arrays.copyOfRange(tokenize, 1, tokenize.length));
                competitor.setName(tokenize[0]);

                competitors.add(competitor);

                // if end of file reached
                fileRead = br.readLine();
            }

            // Putting Competitors to map so it would look a bit nicer in XML
            toMap(competitors, decathlon);

            // close file stream
            br.close();
            // handle exceptions
        } catch (FileNotFoundException fnfe) {
            System.out.println("file not found");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return decathlon;
    }

    static void toMap(List<Competitor> competitors, Decathlon decathlon) {
        sortCompetitors(competitors);
        Map<String, Competitor> allParticipantsTemp = new TreeMap<>();

        for (int i = 0; i < competitors.size(); i++) {
            if (i != competitors.size() - 1 && competitors.get(i).getTotalScore().compareTo(competitors.get(i + 1).getTotalScore()) == 0) {
                competitors.get(i).setPlace((i + 1) + "-" + (i + 2));
                allParticipantsTemp.put((i + 1) + ".", competitors.get(i));
            } else if (i != 0 && competitors.get(i).getTotalScore().compareTo(competitors.get(i - 1).getTotalScore()) == 0) {
                competitors.get(i).setPlace(i + "-" + (i + 1));
                allParticipantsTemp.put((i + 1) + ".", competitors.get(i));
            } else {
                competitors.get(i).setPlace(String.valueOf(i + 1));
                allParticipantsTemp.put(String.valueOf(i + 1), competitors.get(i));
            }
        }

        decathlon.setAllParticipants(allParticipantsTemp);
    }

    static void toXML(String outputName, Decathlon decathlon) {

        try {
            File file = new File(outputName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Decathlon.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(decathlon, file);
            jaxbMarshaller.marshal(decathlon, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    // For timed events 1, 5, 6, 10
    static double formulaTimeEvent(double A, double B, double C, double P) {
        return A * Math.pow(B - P, C);
    }

    // For distance related events 2, 3, 4, 7, 8, 9
    static double formulaDistanceEvent(double A, double B, double C, double P) {
        return A * Math.pow(P - B, C);
    }

    static double toSeconds(String[] time) {
        int size = time.length;

        int minutes = size > 0 ? Integer.parseInt(time[0]) : 0;
        int seconds = size > 1 ? Integer.parseInt(time[1]) : 0;
        int milliseconds = size > 2 ? Integer.parseInt(time[2].trim()) : 0;
        seconds += minutes * 60 + milliseconds / 1000;
        return (double) seconds;
    }

    // Sort
    static void sortCompetitors(List<Competitor> competitors) {
        Collections.sort(competitors, (s1, s2) -> s2.getTotalScore().compareTo(s1.getTotalScore()));
    }

}
