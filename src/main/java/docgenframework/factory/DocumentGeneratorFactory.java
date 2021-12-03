package docgenframework.factory;

import docgenframework.docgenerator.CSVGenerator;
import docgenframework.docgenerator.DocumentGenerator;
import docgenframework.docgenerator.ExcelGenerator;
import docgenframework.docgenerator.HtmlGenerator;
import docgenframework.docgenerator.PDFGanerator;
import docgenframework.exception.DocumentGeneratorException;
import docgenframework.model.DocumentType;

public class DocumentGeneratorFactory {
	
	private DocumentGeneratorFactory() {}
	
	public static DocumentGenerator getInstance(DocumentType documentType) {
		switch (documentType) {
		case PDF:
			return new PDFGanerator();
		case EXCEL:
			return new ExcelGenerator();
		case CSV:
			return new CSVGenerator();
		case HTML:
			return new HtmlGenerator();
		default:
			throw new DocumentGeneratorException("Error while returning instance!");
		}
	}
}
