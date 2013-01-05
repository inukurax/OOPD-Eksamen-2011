package model.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import model.Position;


/**
 * A parser of a Tron configuration for text files.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class TextParser implements IParser
{
    private File file;
	private static int  width;
	private int height;
	private int scaleFactor;
	private Collection<LineSegment> lineSegments = new ArrayList<LineSegment>();
	private Collection<Position> startPositions = new ArrayList<Position>();

    /**
     * Constructs a new TextParser to work on the specified file.
     * 
     * @throws  FileNotFoundException   if the file does not exist
     * @param   file    The file to parse.
     */
    public TextParser(File file) throws FileNotFoundException
    {
        this.file = file;
        
        if (!file.isFile())
            throw new FileNotFoundException("The specified file does not exist.");
    }
    
    /**
     * Constructs a new TextParser to work on the file with the specified path
     * 
     * @throws  FileNotFoundException    If the specified path is not a file
     * @param   fileName    The path to the file.
     */
    public TextParser(String fileName) throws FileNotFoundException
    {
        this(new File(fileName));
    }

	/* (non-Javadoc)
	 * @see model.config.IParser#getConfiguration()
	 */
	@Override
	public Configuration getConfiguration() throws ParserException {
		try {
			 Scanner scan = new Scanner(file);
		     String regex =  "(\\d+)\\s*(\\d+)\\s*(\\d+)";
			 String regexLine = "(\\d+)\\s*(\\d+)\\s*(\\d+)\\s*(\\d+)";
			 String regexPlayer =  "player:\\s*(\\d+)\\s*(\\d+)";

			 Pattern pattern = Pattern.compile(regex);
			 Pattern patternLine = Pattern.compile(regexLine);
			 Pattern patternPlayer = Pattern.compile(regexPlayer);


			while (scan.hasNextLine()) {
				if (scan.hasNextInt()) { 
					scan.findInLine(pattern);
					MatchResult result = scan.match();
					width = Integer.parseInt(result.group(1));
					height = Integer.parseInt(result.group(2));
					scaleFactor = Integer.parseInt(result.group(3));					
				}
				if (scan.hasNext("line:")) {
					if (scan.findInLine(patternLine) != null) {
						MatchResult result = scan.match();
						int x1 = Integer.parseInt(result.group(1));
						int y1 = Integer.parseInt(result.group(2));
						int x2 = Integer.parseInt(result.group(3));
						int y2 = Integer.parseInt(result.group(4));
						lineSegments.add(new LineSegment(x1, y1, x2, y2));	
					}
				}
				if (scan.hasNext("player:")) {
					if (scan.findInLine(patternPlayer) != null) {
						MatchResult result = scan.match();
						int x1 = Integer.parseInt(result.group(1));
						int y1 = Integer.parseInt(result.group(2));
						startPositions.add(new Position(x1, y1));	
					}
				}
				if (scan.hasNextLine())
				scan.nextLine();
			}
			scan.close();
			return new Configuration(width, height, scaleFactor,
					lineSegments, startPositions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
