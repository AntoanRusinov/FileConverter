package coverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author antoan
 *
 *         The program have to be exported as runnable jar file. The jar have to
 *         be available in the same directory where the files are located. Run
 *         as follows: java -jar "jarName"
 *
 */
public class FileFormatConverter {

	public static void main(String args[]) throws Exception {

		convertFormat(new File(args[0]).listFiles(), ".xml", ".csv");

	}

	private static void convertFormat(File[] files, String currentFormat, String newFormat) throws Exception {

		for (File currentFile : files) {

			File newFile = new File(currentFile.getName().replace(currentFormat, newFormat));
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(newFile));

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(currentFile);
			NodeList fields = document.getElementsByTagName("fields in the document");

			String headerColumnNames = "column names separated by some character";
			bufferWriter.append(headerColumnNames);

			for (int k = 0; k < fields.getLength(); k++) {
				Node field = fields.item(k);
				NamedNodeMap namedMap = field.getAttributes();
				Node otherNode = namedMap.getNamedItem("column name that refer to the value");
				String stringNodeValue = otherNode.getTextContent();

				if (k % fields.getLength() == 0) {
					bufferWriter.append("\n");
				}

				bufferWriter.append(stringNodeValue);
			}
			bufferWriter.close();
			System.out.println("File " + newFile.getName() + " transformed... ");
		}
	}

}