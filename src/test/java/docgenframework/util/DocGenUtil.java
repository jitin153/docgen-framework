package docgenframework.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import docgenframework.model.Constants;
import docgenframework.model.DocumentType;

public class DocGenUtil {
	public static String getTemplateText(String path) {
		String templateText = "";
		try {
			templateText = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			System.out.println(e);
		}
		return templateText;
	}

	public static boolean isFileExist(String filePath) {
		return new File(filePath).exists();
	}

	public static boolean isFileExist(Path folderPath, String filename) {
		File folder = new File(folderPath.toString());
		if (folder.isDirectory()) {
			for (String name : folder.list()) {
				if (name.startsWith(filename)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void writeFileFromByteArray(DocumentType documentType, String outputDirectory, String filenameWithoutExtension, byte[] fileContent) {
		String filenameWithExtension = FileNameGenerator.generateFileName(filenameWithoutExtension, documentType, false);
		try {
			Files.write(new File(outputDirectory+"/" + filenameWithExtension).toPath(), fileContent);
		} catch (IOException e) {
			System.out.println("Error occurred : " + e);
		}
	}
}
