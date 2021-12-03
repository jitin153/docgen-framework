package docgenframework.templateprocessor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import docgenframework.factory.TemplateProcessorFactory;
import docgenframework.model.Address;
import docgenframework.model.Constants;
import docgenframework.model.DocumentRequest;
import docgenframework.model.DocumentRequest.DocumentRequestBuilder;
import docgenframework.model.Student;
import docgenframework.model.TemplateEngine;
import docgenframework.util.DocGenUtil;

public class ThymeleafTemplateProcessorTest {
	
	Student student;

	@Before
	public void setup() {
		Address address = new Address(23, "Test street", "Test city", "Test state");
		student = new Student("Test College of Technology", 12, "Test Student", 89.7, Boolean.TRUE, address);
	}

	@Test
	public void processTemplateFromDirectoryTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.THYMELEAF)
				.templateDirectory(Paths.get(Constants.THYMELEAF_TEMPLATE_DIRECTORY))
				.templateName("studentReport.html")
				.contextName("data")
				.data(student)
				.formattedHtml(true)
				.build();
		TemplateProcessor processor = TemplateProcessorFactory.getInstance(req.getTemplateEngine());
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
	
	@Test
	public void processTemplateFromTextTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.THYMELEAF)
				.templateText(DocGenUtil.getTemplateText(
						Constants.THYMELEAF_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.data(student)
				.formattedHtml(false)
				.build();
		TemplateProcessor processor = TemplateProcessorFactory.getInstance(req.getTemplateEngine());
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
	
	@Test
	public void processTemplateFromDirectoryAndBySettingProcessorTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateProcessor(new ThymeleafTemplateProcessor())
				.templateDirectory(Paths.get(Constants.THYMELEAF_TEMPLATE_DIRECTORY))
				.templateName("studentReport.html")
				.contextName("data")
				.data(student)
				.build();
		TemplateProcessor processor = TemplateProcessorFactory.getInstance(TemplateEngine.THYMELEAF);
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
	
	@Test
	public void processTemplateFromTextAndBySettingProcessorTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateProcessor(new ThymeleafTemplateProcessor())
				.templateText(DocGenUtil.getTemplateText(
						Constants.THYMELEAF_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.data(student)
				.build();
		TemplateProcessor processor = TemplateProcessorFactory.getInstance(TemplateEngine.THYMELEAF);
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
	
	@Test
	public void processTemplateFromDirectoryAndByTemplateEngineTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateDirectory(Paths.get(Constants.THYMELEAF_TEMPLATE_DIRECTORY))
				.templateName("studentReport.html")
				.contextName("data")
				.data(student)
				.build();
		TemplateProcessor processor = TemplateProcessorFactory.getInstance(TemplateEngine.THYMELEAF);
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
	
	@Test
	public void processTemplateFromTextAndByTemplateEngineTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateText(DocGenUtil.getTemplateText(
						Constants.THYMELEAF_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.data(student)
				.build();
		TemplateProcessor processor = TemplateProcessorFactory.getInstance(TemplateEngine.THYMELEAF);
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
	
	@Test
	public void processTemplateFromDirectoryAndWithoutSettingTemplateEngineTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateDirectory(Paths.get(Constants.THYMELEAF_TEMPLATE_DIRECTORY))
				.templateName("studentReport.html")
				.contextName("data")
				.data(student)
				.build();
		TemplateProcessor processor = new ThymeleafTemplateProcessor();
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
	
	@Test
	public void processTemplateFromTextAndWithoutSettingTemplateEngineTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateText(DocGenUtil.getTemplateText(
						Constants.THYMELEAF_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.data(student)
				.build();
		TemplateProcessor processor = new ThymeleafTemplateProcessor();
		String text = processor.getProcessedText(req);
		assertNotNull(text);
		assertTrue(text.startsWith("<html>") && text.endsWith("</html>"));
	}
}
