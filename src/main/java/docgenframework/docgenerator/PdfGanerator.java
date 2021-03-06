package docgenframework.docgenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import docgenframework.exception.DocumentGeneratorException;
import docgenframework.model.DocumentRequest;
import docgenframework.util.DocumentGeneratorConstants;
import docgenframework.util.DocumentGeneratorHelper;

public class PdfGanerator implements DocumentGenerator {
	
	private static final Logger LOG =  LoggerFactory.getLogger(PdfGanerator.class);
	
	/** Generate & return the document as byte array.
	 * @param documentRequest
	 * @return byte[]
	 */
	public byte[] generateDocumentAndGetBytes(DocumentRequest request) {
		return getDocument(request);
	}
	
	/** Generate & write the document to the disk.
	 * @param documentRequest
	 */
	public void generateDocumentAndWriteToDisk(DocumentRequest request) {
		byte[] document = getDocument(request);
		DocumentGeneratorHelper.writeFile(request, document);
	}
	
	// --Call this method when you want to generate pdf with parsing XHTML content into XMLWorkerHelper.
	private byte[] getDocument(DocumentRequest request) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
			document.open();
			XMLWorkerHelper xMLWorkerHelper = XMLWorkerHelper.getInstance();
			String processedtext = DocumentGeneratorHelper.getProcessedText(request);
			InputStream inputStream = new ByteArrayInputStream(processedtext.getBytes(StandardCharsets.UTF_8));
			xMLWorkerHelper.parseXHtml(writer, document, inputStream, Charset.forName(DocumentGeneratorConstants.DEFAULT_ENCODING));
			document.close();
			byte[] bytes = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
			if (null!=request.getWatermark() && request.getWatermark()!="") {
				try {
					byte[] bytesWithWatermak = applyWatermark(bytes, request.getWatermark());					
					return bytesWithWatermak;
				} catch (Exception e) {
					LOG.error("Error occurred: {}",e.getMessage());
					throw new DocumentGeneratorException("Error while creating Pdf!");
				}
			}else {
				return bytes;
			}
		} catch (Exception e) {
			LOG.error("Error occurred: {}",e.getMessage());
			throw new DocumentGeneratorException("Error while creating Pdf!");
		}
	}

	// --Call this method when you want to generate pdf without parsing XHTML content into XMLWorkerHelper.
	/*private byte[] generateDocumentFromProcessedText(DocRequest documentRequest) {
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(documentRequest.getProcessedText());
		renderer.getDocument();
		renderer.layout();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			renderer.createPDF(byteArrayOutputStream);
			if (null!=documentRequest.getWatermark() && documentRequest.getWatermark()!="") {
				return applyWatermark(byteArrayOutputStream.toByteArray(), documentRequest.getWatermark());
			} else {
				return byteArrayOutputStream.toByteArray();
			}
		} catch (Exception e) {
			System.out.println("Error occurred : "+e);
			throw new DocumentGeneratorException("Error while creating Pdf!");
		}
	}*/

	private byte[] applyWatermark(byte[] source, String watermarkValue) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(source);
		int totalPages = reader.getNumberOfPages();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
		Font font = new Font(FontFamily.HELVETICA, 80);
		Phrase phrase = new Phrase(watermarkValue, font);
		for (int i = 1; i <= totalPages; i++) {
			PdfContentByte over = stamper.getOverContent(i);
			over.saveState();
			PdfGState pdfGStae = new PdfGState();
			pdfGStae.setFillOpacity(0.1F);
			over.setGState(pdfGStae);
			ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase, 297, 450, 45);
			over.restoreState();
		}
		stamper.close();
		reader.close();
		return byteArrayOutputStream.toByteArray();
	}
}
