package docgenframework.docgenerator;

import docgenframework.model.DocumentRequest;

public interface DocumentGenerator {
	public byte[] generateDocumentAndGetBytes(DocumentRequest request);
	public void generateDocumentAndWriteToDisk(DocumentRequest request);
}
