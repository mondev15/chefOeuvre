package fr.enac.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * FileReader is the class used to read the files
 */
public class FileReader {

	/**
	 * List of all the lines of a file
	 */
    private LinkedList<String> lines = new LinkedList<String>();

    /**
     * Reads the file
     * 
     * @param fileName the name of the file to be read
     */
    public FileReader(String fileName) {
        try {
            File f = new File(fileName);
            java.io.FileReader fr = new java.io.FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            try {
                String line = br.readLine();

                while (line != null) {
                    lines.addFirst(line);
                    line = br.readLine();
                }

                br.close();
                fr.close();
            } catch (IOException exception) {
                System.out.println("Erreur lors de la lecture : " + exception.getMessage());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Le fichier n'a pas été trouvé");
        }
    }
    
    /**
     * Returns the next line of the file
     */
    public String nextLine() {
        if (lines.size() > 0) {
            return lines.pollLast();
        } else
            return null;
    }

    /**
     * Returns the total number of lines
     */
    public int lineNumber() {
        return lines.size();
    }
}
