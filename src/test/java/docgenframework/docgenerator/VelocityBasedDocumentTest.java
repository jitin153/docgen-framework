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
import docgenframework.templateprocessor.TemplateProcessor;
import docgenframework.templateprocessor.VelocityTemplateProcessor;
import docgenframework.util.DocGenUtil;

public class VelocityBasedDocumentTest {
	
	Student student;

	@Before
	public void setup() {
		Address address = new Address(23, "Test street", "Test city", "Test state");
		student = new Student("Test College of Technology", 12, "Test Student", 89.7, Boolean.TRUE, address);
	}

	@Test
	public void velocityPdfFromTemplateDirectoryTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.templateName("studentReport.vm")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
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
	public void velocityExcelFromTemplateDirectoryTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.templateName("studentReport.vm")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
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
	public void velocityCsvFromTemplateDirectoryTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.templateName("studentReport.vm")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
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
	public void velocityPdfFromProcessedTextTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateText(DocGenUtil.getTemplateText(
						Constants.VELOCITY_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
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
	public void velocityExcelFromProcessedTextTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateText(DocGenUtil.getTemplateText(
						Constants.VELOCITY_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
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
	public void velocityCsvFromProcessedTextTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateText(DocGenUtil.getTemplateText(
						Constants.VELOCITY_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
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
	public void velocityPdfFromTemplateProcessorAndProcessedTextTest() {
		TemplateProcessor processor = new VelocityTemplateProcessor();
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateProcessor(processor)
				.templateText(DocGenUtil.getTemplateText(
						Constants.VELOCITY_TEMPLATE_DIRECTORY + "StudentReportTemplateText.txt"))
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
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
	public void velocityExcelFromTemplateProcessorAndTemplateNameTest() {
		TemplateProcessor processor = new VelocityTemplateProcessor();
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateProcessor(processor)
				.templateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.templateName("studentReport.vm")
				.contextName("data")
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Xls_03")
				.documentType(DocumentType.EXCEL)
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()+".xlsx"));
	}
	
	@Test
	public void velocityCsvFromProcessedHtmlTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.processedHtml(Constants.PROCESSED_TEMPLATE_TEXT)
				.outputDirectory(Paths.get(Constants.VELOCITY_GENERATED_FILE_STORAGE_DIRECTORY))
				.fileName("StudentReport_Csv_03")
				.documentType(DocumentType.CSV)
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		docGen.generateDocumentAndWriteToDisk(req);
		assertTrue(DocGenUtil.isFileExist(req.getOutputDirectory()+"/"+req.getFileName()+".csv"));
	}	
	
	@Test
	public void velocityPdfBytesTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.templateName("studentReport.vm")
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
	public void velocityExcelBytesTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.templateName("studentReport.vm")
				.contextName("data")
				.documentType(DocumentType.EXCEL)
				.watermark("SAMPLE")
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		byte[] bytes = docGen.generateDocumentAndGetBytes(req);
		assertNotNull(bytes);
		assertTrue(bytes.length > 0);
	}
	
	@Test
	public void velocityCsvBytesTest() {
		DocumentRequest<Student> req = new DocumentRequestBuilder<Student>()
				.templateEngine(TemplateEngine.VELOCITY)
				.templateDirectory(Paths.get(Constants.VELOCITY_TEMPLATE_DIRECTORY))
				.templateName("studentReport.vm")
				.contextName("data")
				.documentType(DocumentType.CSV)
				.watermark("SAMPLE")
				.data(student)
				.build();
		DocumentGenerator docGen = DocumentGeneratorFactory.getInstance(req.getDocumentType());
		byte[] bytes = docGen.generateDocumentAndGetBytes(req);
		assertNotNull(bytes);
		assertTrue(bytes.length > 0);
	}
}
