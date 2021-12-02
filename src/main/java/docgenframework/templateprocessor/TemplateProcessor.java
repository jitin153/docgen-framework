package docgenframework.templateprocessor;

import docgenframework.model.DocumentRequest;

public interface TemplateProcessor {
	public String getProcessedText(DocumentRequest request);
}
