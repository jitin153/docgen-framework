package docgenframework.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import docgenframework.exception.DocumentGeneratorException;
import docgenframework.factory.TemplateProcessorFactory;
import docgenframework.model.DocumentRequest;
import docgenframework.templateprocessor.TemplateProcessor;

/**
 * 
 * @author Jitin Gangwar
 *
 */
public class DocumentGeneratorHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentGeneratorHelper.class);

	private DocumentGeneratorHelper() {

	}

	public static void writeFile(DocumentRequest documentRequest, byte[] fileContent) {
		if (Objects.nonNull(documentRequest)) {
			String fileName = documentRequest.getFileName();
			if (StringUtils.isNotBlank(fileName)) {
				fileName = FileNameGenerator.generateFileName(fileName, documentRequest.getDocumentType(),
						documentRequest.isAppendTimestampInFilename());
			} else {
				fileName = FileNameGenerator.generateFileName(DocumentGeneratorConstants.FILE_NAME_PREFIX,
						documentRequest.getDocumentType(), documentRequest.isAppendTimestampInFilename());
			}
			if (StringUtils.isNotBlank(documentRequest.getOutputDirectory().toString())) {
				StringBuilder file = new StringBuilder(documentRequest.getOutputDirectory().toString()).append("/")
						.append(fileName);
				try {
					Files.write(new File(file.toString()).toPath(), fileContent);
				} catch (IOException e) {
					LOG.error("Error occurred: {}", e.getMessage());
				}
			} else {
				throw new DocumentGeneratorException("Output directory cannot be null or empty!");
			}
		} else {
			throw new DocumentGeneratorException("DocumentRequest cannot be null.");
		}
	}

	public static String getProcessedText(DocumentRequest request) {
		if (Objects.isNull(request)) {
			throw new DocumentGeneratorException("Request object cannot be null.");
		}
		if (StringUtils.isNotBlank(request.getProcessedHtml())) {
			return request.getProcessedHtml();
		} else if (Objects.nonNull(request.getTemplateProcessor())) {
			return request.getTemplateProcessor().getProcessedText(request);
		} else {
			TemplateProcessor processor = TemplateProcessorFactory.getInstance(request.getTemplateEngine());
			return processor.getProcessedText(request);
		}
	}
}
