package docgenframework.templateprocessor;

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import docgenframework.exception.DocumentGeneratorException;
import docgenframework.model.DocumentRequest;
import docgenframework.util.DocumentGeneratorConstants;

public class VelocityTemplateProcessor implements TemplateProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(VelocityTemplateProcessor.class);

	public String getProcessedText(DocumentRequest request) {
		Template template;
		if (null != request.getTemplateText()) {
			template = this.processTemplateFromString(request.getTemplateText());
		} else if (null != request.getTemplateDirectory() && request.getTemplateName() != "") {
			template = this.processTemplateFromFile(request.getTemplateDirectory().toString()+"/", request.getTemplateName());
		} else {
			throw new DocumentGeneratorException("Some required properties were null or empty!");
		}
		String processedText = this.processTemplate(template, request.getContextName(), request.getData());
		return request.isFormattedHtml() ? Jsoup.parse(processedText).html() : processedText;
	}

	private Template processTemplateFromFile(String templateDirectory, String templateName) {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		StringBuilder velocityTemplate = new StringBuilder(templateDirectory).append(templateName);
		return velocityEngine.getTemplate(velocityTemplate.toString());
	}

	private Template processTemplateFromString(String templateText) {
		RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
		StringReader stringReader = new StringReader(templateText);
		SimpleNode simpleNode;
		Template template = new Template();
		try {
			simpleNode = runtimeServices.parse(stringReader, DocumentGeneratorConstants.DEFAULT_TEMPLATE_NAME);
			template.setRuntimeServices(runtimeServices);
			template.setData(simpleNode);
			template.initDocument();
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
		return template;
	}

	private String processTemplate(Template template, String contextName, Object data) {
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put(contextName, data);

		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		return stringWriter.toString();
	}
}
