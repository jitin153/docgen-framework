package docgenframework.docgenerator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import docgenframework.factory.DocumentGeneratorFactory;
import docgenframework.model.Address;
import docgenframework.model.Constants;
import docgenframework.model.DocumentRequest;
import docgenframework.model.DocumentRequest.DocumentRequestBuilder;
import docgenframework.model.DocumentType;
import docgenframework.model.Student;
import docgenframework.model.TemplateEngine;
import docgenframework.templateprocessor.FreemarkerTemplateProcessor;
import docgenframework.templateprocessor.TemplateProcessor;
import docgenframework.util.DocGenUtil;

public class FreemarkerBasedDocumentTest {
	
	Student student;

	@Before
	public void setup() {
		Address address = new Address(23, "Test street", "Test city", "Test state");
		student = new Student("Test College of Technology", 12, "Test Student", 89.7, Boolean.TRUE, address);
	}

	@Test
	public void FreemarkerPdfFromTemplateDirectoryTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.templateName("studentReport.ftl")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Pdf_01")
				.documentType(DocumentType.PDF)
				.watermark("SAMPLE")
				.data(student)
				.appendTimestampInFilename(true)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory(), req.getFileName()));
	}
	
	@Test
	public void FreemarkerExcelFromTemplateDirectoryTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.templateName("studentReport.ftl")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Xls_01")
				.documentType(DocumentType.EXCEL)
				.data(student)
				.appendTimestampInFilename(false)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()+".xlsx"));
	}
	
	@Test
	public void FreemarkerCsvFromTemplateDirectoryTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.templateName("studentReport.ftl")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Csv_01")
				.documentType(DocumentType.CSV)
				.data(student)
				//.appendTimestampInFilename(true)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()+".csv"));
	}
	
	@Test
	public void FreemarkerPdfFromProcessedTextTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateText(DocGenUtil.getTemplateText(
						Constants.FREEMARKER_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Pdf_02.pdf")
				.documentType(DocumentType.PDF)
				.watermark("SAMPLE")
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()));
	}
	
	@Test
	public void FreemarkerExcelFromProcessedTextTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateText(DocGenUtil.getTemplateText(
						Constants.FREEMARKER_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Xls_02.xlsx")
				.documentType(DocumentType.EXCEL)
				.watermark("SAMPLE")
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()));
	}
	
	@Test
	public void FreemarkerCsvFromProcessedTextTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateText(DocGenUtil.getTemplateText(
						Constants.FREEMARKER_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Csv_02.csv")
				.documentType(DocumentType.CSV)
				.watermark("SAMPLE")
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()));
	}
	
	@Test
	public void FreemarkerPdfFromTemplateProcessorAndProcessedTextTest() {
		TemplateProcessor processor = new FreemarkerTemplateProcessor();
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateProcessor(processor)
				.templateText(DocGenUtil.getTemplateText(
						Constants.FREEMARKER_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Pdf_03")
				.documentType(DocumentType.PDF)
				.watermark("SAMPLE")
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()+".pdf"));
	}
	
	@Test
	public void FreemarkerExcelFromTemplateProcessorAndTemplateNameTest() {
		TemplateProcessor processor = new FreemarkerTemplateProcessor();
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateProcessor(processor)
				.templateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.templateName("studentReport.ftl")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Xls_03")
				.documentType(DocumentType.EXCEL)
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()+".xlsx"));
	}
	
	@Test
	public void FreemarkerCsvFromProcessedHtmlTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.processedHtml(Constants.PROCESSED_TEMPLATE_TEXT)
				.outputDirectory(Paths.get(Constants.FREEMARKER_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Csv_03")
				.documentType(DocumentType.CSV)
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()+".csv"));
	}	
	
	@Test
	public void FreemarkerPdfBytesTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.templateName("studentReport.ftl")
				.contextName("data")
				.documentType(DocumentType.PDF)
				.watermark("SAMPLE")
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		byte[] bytes = docGen.generateDocumentAndGetBytes(req);
		assertNotNull(bytes);
		assertTrue(bytes.length > 0);
	}
	
	@Test
	public void FreemarkerExcelBytesTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.templateName("studentReport.ftl")
				.contextName("data")
				.documentType(DocumentType.EXCEL)
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		byte[] bytes = docGen.generateDocumentAndGetBytes(req);
		assertNotNull(bytes);
		assertTrue(bytes.length > 0);
	}
	
	@Test
	public void FreemarkerCsvBytesTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.FREEMARKER)
				.templateDirectory(Paths.get(Constants.FREEMARKER_TEMPLATE_DIRECTORY))
				.templateName("studentReport.ftl")
				.contextName("data")
				.documentType(DocumentType.CSV)
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		byte[] bytes = docGen.generateDocumentAndGetBytes(req);
		assertNotNull(bytes);
		assertTrue(bytes.length > 0);
	}
}
