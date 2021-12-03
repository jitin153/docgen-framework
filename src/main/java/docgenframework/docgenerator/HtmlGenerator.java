package docgenframework.docgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import docgenframework.model.DocumentRequest;
import docgenframework.util.DocumentGeneratorHelper;

public class HtmlGenerator implements DocumentGenerator {
	
	private static final Logger LOG =  LoggerFactory.getLogger(HtmlGenerator.class);
	
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
		return processedText.getBytes();
	}
}
