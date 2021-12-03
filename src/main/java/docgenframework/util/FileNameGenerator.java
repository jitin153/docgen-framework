package docgenframework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import docgenframework.exception.DocumentGeneratorException;
import docgenframework.model.DocumentType;

public class FileNameGenerator {
	private FileNameGenerator() {
		
	}
	public static String generateFileName(String prefix, DocumentType documentType, boolean appendTimestamp) {
		if(prefix.contains(".")) {
			prefix = prefix.split("\\.")[0];
		}
		StringBuilder fileName = new StringBuilder(prefix);
		if(appendTimestamp) {
			fileName = new StringBuilder(prefix).append("_")
					.append(new SimpleDateFormat(DocumentGeneratorConstants.TIMESTAMP).format(new Date()));
		}

		if(null != documentType) {
		switch (documentType) {
		case PDF:
			return fileName.append(".pdf").toString();
		case EXCEL:
			return fileName.append(".xlsx").toString();
		case CSV:
			return fileName.append(".csv").toString();
		case HTML:
			return fileName.append(".html").toString();
		default:
			throw new DocumentGeneratorException("Error while generating the file name!");
		}
		}else {
			throw new DocumentGeneratorException("DocumentType cannot be null.");
		}
	}
}
