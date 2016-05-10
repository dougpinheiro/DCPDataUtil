package dougpinheiro.dcp.datautil.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DataZip {

	private static final int BUFFER = 1024; 

	public static byte[] zip(File file, boolean compressed) throws IOException {
		byte[] result = null;
		
			System.out.println(file.getAbsoluteFile().getName());
			
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			
			ZipOutputStream zos = new ZipOutputStream(bao);
			zos.setComment("Teste");
			
			if(compressed){
				zos.setMethod(ZipOutputStream.DEFLATED);
				zos.setLevel(9);				
			}else{
				zos.setMethod(ZipOutputStream.STORED);
				zos.setLevel(0);				
			}
			
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zos.putNextEntry(zipEntry);
			
			FileInputStream fis = new FileInputStream(file);

			byte data[] = new byte[BUFFER];
			int count = 0;

			while((count = fis.read(data, 0,	BUFFER)) != -1) {
				zos.write(data, 0, count);
			}
			
			fis.close();
			zos.flush();
			zos.close();
			
			result = bao.toByteArray();
		
		return result;
	}

	public static void writeZipFile(File fileIn, boolean compressed) throws IOException {
		
		System.out.println(fileIn.getAbsoluteFile().getName());
		
		FileOutputStream fos = new FileOutputStream(new File(fileIn.getAbsoluteFile()+".zip"));
		
		ZipOutputStream zos = new ZipOutputStream(fos);
				
		ZipEntry zipEntry = new ZipEntry(fileIn.getName());
		zos.putNextEntry(zipEntry);

		if(compressed){
			zos.setMethod(ZipOutputStream.DEFLATED);
			zos.setLevel(9);				
		}else{
			zos.setMethod(ZipOutputStream.STORED);	
			zos.setLevel(0);
		}
		
		FileInputStream fis = new FileInputStream(fileIn);
		
		byte data[] = new byte[BUFFER];
		int count = 0;
		
		while((count = fis.read(data, 0,	BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		
		fis.close();
		zos.flush();
		zos.close();
		
	}
	
	public static void main(String[] args) {
		
		try {
			DataZip.zip(new File("<file path>"), true);
			DataZip.writeZipFile(new File("<file path>"), true);
			DataZip.writeZipFile(new File("<file path>"), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
