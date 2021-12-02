package docgenframework.factory;

import java.util.Objects;

import docgenframework.exception.DocumentGeneratorException;
import docgenframework.model.TemplateEngine;
import docgenframework.templateprocessor.FreemarkerTemplateProcessor;
import docgenframework.templateprocessor.TemplateProcessor;
import docgenframework.templateprocessor.ThymeleafTemplateProcessor;
import docgenframework.templateprocessor.VelocityTemplateProcessor;

public class TemplateProcessorFactory {

	private TemplateProcessorFactory() {
	}

	public static TemplateProcessor getInstance(TemplateEngine templateEngine) {
		if (Objects.nonNull(templateEngine)) {
			switch (templateEngine) {
			case FREEMARKER:
				return new FreemarkerTemplateProcessor();
			case VELOCITY:
				return new VelocityTemplateProcessor();
			case THYMELEAF:
				return new ThymeleafTemplateProcessor();
			default:
				throw new DocumentGeneratorException("Error while returning instance!");
			}
		} else {
			throw new DocumentGeneratorException("Template engine cannot be null.");
		}
	}
}
