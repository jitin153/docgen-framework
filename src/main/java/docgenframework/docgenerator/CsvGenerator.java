package docgenframework.docgenerator;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import docgenframework.exception.DocumentGeneratorException;
import docgenframework.model.DocumentRequest;
import docgenframework.util.DocumentGeneratorConstants;
import docgenframework.util.DocumentGeneratorHelper;

public class CsvGenerator implements DocumentGenerator {
	
	private static final Logger LOG =  LoggerFactory.getLogger(CsvGenerator.class);
	
	/** Generate & return the document as byte array.
	 * @param documentRequest
	 * @return byte[]
	 */
	public byte[] generateDocumentAndGetBytes(DocumentRequest request) {
		return getDocument(DocumentGeneratorHelper.getProcessedText(request));
	}

	/** Generate & write the document to the disk.
	 * @param documentRequest
	 */
	public void generateDocumentAndWriteToDisk(DocumentRequest request) {
		byte[] document = getDocument(DocumentGeneratorHelper.getProcessedText(request));
		DocumentGeneratorHelper.writeFile(request, document);
	}
	
	private byte[] getDocument(String processedText) {
		Document document = Jsoup.parse(processedText);
		Elements tables = document.getElementsByTag(DocumentGeneratorConstants.HTML_TABLE);
		Writer writer = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			writer = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream));
			for (Element table : tables) {
				Elements rows = table.select(DocumentGeneratorConstants.HTML_TABLE_ROW);
				if (!rows.isEmpty()) {
					int i = 0;
					Elements tableHeders = rows.select(DocumentGeneratorConstants.HTML_TABLE_HEADER);
					if (!tableHeders.isEmpty()) {
						writeData(writer, tableHeders);
						i = 1;
					}
					for (; i < rows.size(); i++) {
						Elements cells = rows.get(i).select(DocumentGeneratorConstants.HTML_TABLE_DATA);
						writeData(writer, cells);
					}
				}
				writer.write("\n");
			}
		} catch (Exception e) {
			LOG.error("Error occurred while generating CSV: {}",e.getMessage());
			throw new DocumentGeneratorException("Error while generating CSV.");
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				LOG.error("Error occurred while closing the writer: {}",e.getMessage());
			}
		}
		return byteArrayOutputStream.toByteArray();
	}

	private void writeData(Writer writer, Elements cells) {
		try {
			int cellCounter=0;
			for (Element cell : cells) {
				//writer.write(cell.text().concat(", ")); //--Text without double quotes.
				if(cellCounter < cells.size()-1) {
					writer.write("\""+cell.text()+"\"".concat(", ")); //--Enclose text within double quotes.
				}else {
					writer.write("\""+cell.text()+"\""); //--Enclose text within double quotes.
				}
				cellCounter++;
			}
			writer.write("\n");
		} catch (Exception e) {
			LOG.error("Error occurred while writing CSV cells: {}",e.getMessage());
			throw new DocumentGeneratorException("Error occurred while writing CSV cells.");
		}
	}
}
