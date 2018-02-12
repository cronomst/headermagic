
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HeaderMagic
{

    public static void main(String[] args)
    {
        HeaderMagic headerMagic = new HeaderMagic();
        
        // Let's pretend we've already parsed this data out of the command line
        // and input file
        
        // These are the headers that we would find in our input file
        String[] inputHeaders = {"Title", "Author", "PageCount", "WordCount"};
        
        // These are the headers that would be in our command line and are therefore
        // expected in the output
        String[] outputHeaders = {"Title", "Author", "Language", "PageCount"};
        
        // These are the individual lines of our input file (minus the header part)
        String[] inputFileLines = {
            "Storm Front,Jim Butcher,300,10000",
            "Dune,Frank Herbert,400,11000"
        };
        
        // Do the thing!
        String actualOutput = headerMagic.start(inputHeaders, outputHeaders, inputFileLines);
        
        
        // Now that we've done the thing, I've created a sort of unit test to
        // validate that the output generated matches what we expect it to look like
        
        // This represents what, when everything goes to plan, the output
        // should be.
        String expectedOutput = "Title,Author,Language,PageCount\n"
                + "Storm Front,Jim Butcher,,300\n"
                + "Dune,Frank Herbert,,400\n";
        
        System.out.println("Expected Output:\n" + expectedOutput);
        System.out.println("Actual Output:\n" + actualOutput);
        
        // Check if our actual output matches our expected output
        if (expectedOutput.equals(actualOutput)) {
            System.out.println("PASS: Output matches expected value.");
        } else {
            System.out.println("FAIL: Output does NOT match expected value.");
        }

    }

    public String start(String[] inputHeaders, String[] outputHeaders, String[] inputFileLines)
    {
        // Look! You can still have your ArrayList! Now it's a list of Maps.
        // This will eventually be our input file data with easily accessible
        // associations between headers and their values for each line
        ArrayList<Map<String, String>> inputFileData = new ArrayList<>();
        
        // Just a quick way to join elements of an array together with a delimiter
        String output = String.join(",", outputHeaders) + "\n";
        
        // For each input line in our file, parse the data into a map of header/value
        // pairs.
        for (String inputFileLine : inputFileLines) {
            inputFileData.add(this.parseLine(inputFileLine, inputHeaders));
        }
        
        // Now create a line of output for each item in our list
        for (Map<String, String> headerValues : inputFileData) {
            output+= this.createLine(headerValues, outputHeaders) + "\n";
        }
        
        return output;
    }
    
    /**
     * Parses a comma-delimited list and maps the corresponding headers and
     * values based on their indexed position.
     * 
     * @param line
     * @param inputHeaders
     * @return 
     */
    private Map<String,String> parseLine(String line, String[] inputHeaders)
    {
        HashMap<String,String> lineData = new HashMap<>();
        
        String[] lineFields = line.split(",");
        for (int position=0; position<lineFields.length; position++) {
            lineData.put(inputHeaders[position], lineFields[position]);
        }
        return lineData;
    }
    
    /**
     * Creates a line of our output
     * @param headerValues
     * @param outputHeaders
     * @return 
     */
    private String createLine(Map<String, String> headerValues, String[] outputHeaders)
    {
        String output = "";
        
        // For each of our desired output header values, we want to look up
        // the corresponding value (if it exists) associated with this line
        // of data
        for (int i=0; i<outputHeaders.length; i++) {
            String value = headerValues.get(outputHeaders[i]);
            // If we don't have a corresponding value, just use a blank instead
            output += (value == null ? "" : value);
            
            // Only append a comma if this is not the last item on the line
            if (i<outputHeaders.length-1) {
                output+= ",";
            }
        }
        return output;
    }
}
