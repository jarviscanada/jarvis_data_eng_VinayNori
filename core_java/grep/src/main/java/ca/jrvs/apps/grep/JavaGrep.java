package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

    /**
     * Top level search workflow
     *
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a given directory and return all files
     *
     * @param rootDir input directory
     * @return
     * @throws IOException
     */
    List<File> listFiles(String rootDir) throws IOException;

    /**
     * Reads lines from the given file and stores them in a List
     *
     * @param inputFile file to be read
     * @return lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     * @throws IOException
     */
    List<String> readLines(File inputFile) throws IOException;

    /**
     * checks if a line contains the regex pattern (passed by user)
     *
     * @param line input string
     * @return true if there is a match
     * @throws IOException
     */
    boolean containsPattern(String line);

    /**
     * Write lines to a file
     *
     * @param lines matched line
     * @throws IOException if write failed
     */
    void writeToFile(List<String> lines) throws IOException;

    /**
     * Gets root path
     * @return rootPath
     */
    String getRootPath();

    /**
     * Sets root path
     * @param rootPath
     */
    void setRootPath(String rootPath);

    /**
     * Gets regex
     * @return regex
     */
    String getRegex();

    /**
     * Sets regex
     * @param regex
     */
    void setRegex(String regex);

    /**
     * Gets outFile
     * @return outFile
     */
    String getOutFile();

    /**
     * Returns outFile
     * @param outFile
     */
    void setOutFile(String outFile);

}