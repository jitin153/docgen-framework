package docgenframework.model;

import java.nio.file.Path;

import docgenframework.templateprocessor.TemplateProcessor;

public class DocumentRequest<T> {
	private TemplateEngine templateEngine;
	private Path templateDirectory;
	private String templateName;
	private String templateText;
	private Path outputDirectory;
	private String fileName;
	private String contextName;
	private T data;
	private DocumentType documentType;
	private String processedHtml;
	private String watermark;
	private TemplateProcessor templateProcessor;
	private boolean appendTimestampInFilename;
	
	private DocumentRequest(TemplateEngine templateEngine, Path templateDirectory, String templateName,
			String templateText, Path outputDirectory, String fileName, String contextName, T data,
			DocumentType documentType, String processedHtml, String watermark, TemplateProcessor templateProcessor, boolean appendTimestampInFilename) {
		this.templateEngine = templateEngine;
		this.templateDirectory = templateDirectory;
		this.templateName = templateName;
		this.templateText = templateText;
		this.outputDirectory = outputDirectory;
		this.fileName = fileName;
		this.contextName = contextName;
		this.data = data;
		this.documentType = documentType;
		this.processedHtml = processedHtml;
		this.watermark = watermark;
		this.templateProcessor = templateProcessor;
		this.appendTimestampInFilename = appendTimestampInFilename;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public Path getTemplateDirectory() {
		return templateDirectory;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateText() {
		return templateText;
	}

	public Path getOutputDirectory() {
		return outputDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public String getContextName() {
		return contextName;
	}

	public T getData() {
		return data;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public String getProcessedHtml() {
		return processedHtml;
	}

	public String getWatermark() {
		return watermark;
	}

	public TemplateProcessor getTemplateProcessor() {
		return templateProcessor;
	}
	
	public boolean isAppendTimestampInFilename() {
		return appendTimestampInFilename;
	}

	public static class DocumentRequestBuilder<T> {
		private TemplateEngine templateEngine;
		private Path templateDirectory;
		private String templateName;
		private String templateText;
		private Path outputDirectory;
		private String fileName;
		private String contextName;
		private T data;
		private DocumentType documentType;
		private String processedHtml;
		private String watermark;
		private TemplateProcessor templateProcessor;
		private boolean appendTimestampInFilename;
		
		public DocumentRequestBuilder<T> templateEngine(TemplateEngine templateEngine) {
			this.templateEngine = templateEngine;
			return this;
		}

		public DocumentRequestBuilder<T> templateDirectory(Path templateDirectory) {
			this.templateDirectory = templateDirectory;
			return this;
		}

		public DocumentRequestBuilder<T> templateName(String templateName) {
			this.templateName = templateName;
			return this;
		}

		public DocumentRequestBuilder<T> templateText(String templateText) {
			this.templateText = templateText;
			return this;
		}

		public DocumentRequestBuilder<T> outputDirectory(Path outputDirectory) {
			this.outputDirectory = outputDirectory;
			return this;
		}

		public DocumentRequestBuilder<T> fileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public DocumentRequestBuilder<T> contextName(String contextName) {
			this.contextName = contextName;
			return this;
		}

		public DocumentRequestBuilder<T> data(T data) {
			this.data = data;
			return this;
		}

		public DocumentRequestBuilder<T> documentType(DocumentType documentType) {
			this.documentType = documentType;
			return this;
		}

		public DocumentRequestBuilder<T> processedHtml(String processedHtml) {
			this.processedHtml = processedHtml;
			return this;
		}

		public DocumentRequestBuilder<T> watermark(String watermark) {
			this.watermark = watermark;
			return this;
		}

		public DocumentRequestBuilder<T> templateProcessor(TemplateProcessor templateProcessor) {
			this.templateProcessor = templateProcessor;
			return this;
		}
		
		public DocumentRequestBuilder<T> appendTimestampInFilename(boolean appendTimestampInFilename) {
			this.appendTimestampInFilename = appendTimestampInFilename;
			return this;
		}
		
		public DocumentRequest<T> build() {
			return new DocumentRequest<T>(templateEngine, templateDirectory, templateName, templateText,
					outputDirectory, fileName, contextName, data, documentType, processedHtml, watermark,
					templateProcessor, appendTimestampInFilename);
		}
	}
}
