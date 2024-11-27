package com.liuzq.watermark.test;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.LittleEndianOutput;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//Excel添加水印工具类
public class ExcelWaterMarkUtil {
	private final static Logger logger = LoggerFactory.getLogger(ExcelWaterMarkUtil.class);
	/**
	 *  xlsx 类型添加水印
	 * @param inputFilePath
	 * @param text
	 * @return
	 */
	public static String excelWaterMarkForXlsx(String inputFilePath,String text) {
		FileInputStream is = null;
		FileOutputStream out = null;
		XSSFWorkbook workbook = null;
		ByteArrayOutputStream os = null;
		try {
			//生成水印图片并导出字节流
			BufferedImage image = FontImageUtil.createWatermarkImage(new FontImageUtil.Watermark(true,text, null,null));
			os = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", os);
			//获取excel工作簿
			is = new FileInputStream(inputFilePath);
			workbook = new XSSFWorkbook(is);
			int pictureIdx = workbook.addPicture(os.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
			POIXMLDocumentPart poixmlDocumentPart = workbook.getAllPictures().get(pictureIdx);
			//获取每个Sheet表并插入水印
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				XSSFSheet sheet1 = workbook.getSheetAt(i);
				PackagePartName ppn = poixmlDocumentPart.getPackagePart().getPartName();
				String relType = XSSFRelation.IMAGES.getRelation();
				//add relation from sheet to the picture data
				PackageRelationship pr = sheet1.getPackagePart().addRelationship(ppn, TargetMode.INTERNAL, relType, null);
				//set background picture to sheet
				sheet1.getCTWorksheet().addNewPicture().setId(pr.getId());
			}
			//生成添加水印的excel文件
			File f = new File(inputFilePath);
			String outputFilePath = f.getParent() + File.separator+System.currentTimeMillis()+ f.getName();
			out = new FileOutputStream(outputFilePath);
			workbook.write(out);
			return outputFilePath;
		} catch (Exception e) {
			logger.error("excel文件添加水印异常",e);
		}finally {
			if (is != null){
				try {
					is.close();
				}catch (Exception e){
					logger.error("excel输入文件关闭异常",e);
				}
			}
			if (out != null){
				try {
					out.close();
				}catch (Exception e){
					logger.error("excel输出文件关闭异常",e);
				}
			}
			if (workbook != null){
				try {
					workbook.close();
				}catch (Exception e){
					logger.error("excel工作簿关闭异常",e);
				}
			}
			if (os != null){
				try {
					os.close();
				}catch (Exception e){
					logger.error("水印图片字节流关闭异常",e);
				}
			}
		}
		return "";
	}
 
	/**
	 *  xls 类型添加水印
	 * @param inputFilePath
	 * @param text
	 * @return
	 */
//	public static String excelWaterMarkForXls(String inputFilePath,String text) {
//		FileInputStream is = null;
//		FileOutputStream out = null;
//		HSSFWorkbook workbook = null;
//		ByteArrayOutputStream os = null;
//		//水印图片
//		String tarImgPath = "";
//		try {
//			//生成水印图片并导出字节流
//			BufferedImage image = FontImageUtil.createWatermarkImage(new FontImageUtil.Watermark(true, text, null,null));
//			String fileDirPath = System.getProperties().getProperty("user.dir") + File.separator;
//			File f1 = new File(fileDirPath);
//			String tarimgpath = f1.getParent() + File.separator+System.nanoTime() + text + ".jpg";
//			FileOutputStream outImgStream = new FileOutputStream(tarimgpath);
//			ImageIO.write(image, "jpg", outImgStream);
//
//			//获取每个Sheet表并插入水印
//			byte[] data = getBackgroundBitmapData(tarimgpath); //PNG must not have transparency
//			for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
//				HSSFSheet sheet1 = workbook.getSheetAt(k);
//
//				Field _sheet = HSSFSheet.class.getDeclaredField("_sheet");
//				_sheet.setAccessible(true);
//				InternalSheet internalsheet = (InternalSheet)_sheet.get(sheet1);
//				// get List of RecordBase
//				Field _records = InternalSheet.class.getDeclaredField("_records");
//				_records.setAccessible(true);
//				@SuppressWarnings("unchecked")
//				List<RecordBase> records = (List<RecordBase>)_records.get(internalsheet);
//
//
//
//				// do creating BitmapRecord and ContinueRecords from the data in parts of 8220 bytes
//				BitmapRecord bitmapRecord;
//				List<ContinueRecord> continueRecords = new ArrayList<>();
//				int bytes;
//
//				if (data.length > 8220) {
//					bitmapRecord = new BitmapRecord(Arrays.copyOfRange(data, 0, 8220));
//					bytes = 8220;
//					while (bytes < data.length) {
//						if ((bytes + 8220) < data.length) {
//							continueRecords.add(new ContinueRecord(Arrays.copyOfRange(data, bytes, bytes + 8220)));
//							bytes += 8220;
//						} else {
//							continueRecords.add(new ContinueRecord(Arrays.copyOfRange(data, bytes, data.length)));
//							break;
//						}
//					}
//				} else {
//					bitmapRecord = new BitmapRecord(data);
//				}
//
//				// add the records after PageSettingsBlock
//				int i = 0;
//				for (RecordBase r : records) {
//					if (r instanceof org.apache.poi.hssf.record.aggregates.PageSettingsBlock) {
//						break;
//					}
//					i++;
//				}
//				records.add(++i, bitmapRecord);
//				for (ContinueRecord continueRecord : continueRecords) {
//					records.add(++i, continueRecord);
//				}
//                f1.delete();//删除水印图片
//			}
//			//生成添加水印的excel文件
//			File f = new File(inputFilePath);
//			String outputFilePath = f.getParent() + File.separator+System.currentTimeMillis()+ f.getName();
//			out = new FileOutputStream(outputFilePath);
//			logger.info(outputFilePath);
//			outImgStream.close();
//			boolean res = new File(tarimgpath).delete();
//			workbook.write(out);
//			return outputFilePath;
//		} catch (Exception e) {
//			logger.error("excel文件添加水印异常",e);
//		}finally {
//			if (is != null){
//				try {
//					is.close();
//				}catch (Exception e){
//					logger.error("excel输入文件关闭异常",e);
//				}
//			}
//			if (out != null){
//				try {
//					out.close();
//				}catch (Exception e){
//					logger.error("excel输出文件关闭异常",e);
//				}
//			}
//			if (workbook != null){
//				try {
//					workbook.close();
//				}catch (Exception e){
//					logger.error("excel工作簿关闭异常",e);
//				}
//			}
//			if (os != null){
//				try {
//					os.close();
//				}catch (Exception e){
//					logger.error("水印图片字节流关闭异常",e);
//				}
//			}
//			new File(tarImgPath).delete();
//		}
//		return "";
//	}
	static byte[] getBackgroundBitmapData(String filePath) throws Exception {
 
		//see https://www.openoffice.org/sc/excelfileformat.pdf - BITMAP

		// get file byte data in type BufferedImage.TYPE_3BYTE_BGR
		FileInputStream fio = new FileInputStream(filePath);
		BufferedImage in = ImageIO.read(fio);
		BufferedImage image = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = image.createGraphics();
		graphics.drawImage(in, null, 0, 0);
		graphics.dispose();
 
		// calculate row size (c)
		int rowSize = ((24 * image.getWidth() + 31) / 32) * 4;
 
		ByteArrayOutputStream output = new ByteArrayOutputStream(image.getHeight() * rowSize * 3 + 1024);
 
		// put the record headers into the data
		ByteBuffer header = ByteBuffer.allocate(8 + 12);
		header.order(ByteOrder.LITTLE_ENDIAN);
 
		// Undocumented XLS stuff
		header.putShort((short) 0x09);
		header.putShort((short) 0x01);
		header.putInt(image.getHeight() * rowSize + 12); // Size of image stream
 
		// BITMAPCOREHEADER (a)
		header.putInt(12);
 
		header.putShort((short) image.getWidth());
		header.putShort((short) image.getHeight()); // Use -height if writing top-down
 
		header.putShort((short) 1); // planes, always 1
		header.putShort((short) 24); // bitcount
 
		output.write(header.array());
 
		// Output rows bottom-up (b)
		Raster raster = image.getRaster()
				.createChild(0, 0, image.getWidth(), image.getHeight(), 0, 0, new int[]{2, 1, 0}); // Reverse BGR -> RGB (d)
		byte[] row = new byte[rowSize]; // padded (c)
 
		for (int i = image.getHeight() - 1; i >= 0; i--) {
			row = (byte[]) raster.getDataElements(0, i, image.getWidth(), 1, row);
			output.write(row);
		}
		fio.close();
 
		return output.toByteArray();
	}
 
	static class BitmapRecord extends StandardRecord {
 
		//see https://www.openoffice.org/sc/excelfileformat.pdf - BITMAP
 
		byte[] data;
 
		BitmapRecord(byte[] data) {
			this.data = data;
		}
 
		@Override
		public int getDataSize() {
			return data.length;
		}
 
		@Override
		public short getSid() {
			return (short)0x00E9;
		}
		@Override
		public void serialize(LittleEndianOutput out) {
			out.write(data);
		}
	}
 
	static class ContinueRecord extends StandardRecord {
 
		//see https://www.openoffice.org/sc/excelfileformat.pdf - CONTINUE
 
		byte[] data;
 
		ContinueRecord(byte[] data) {
			this.data = data;
		}
		@Override
		public int getDataSize() {
			return data.length;
		}
		@Override
		public short getSid() {
			return (short)0x003C;
		}
		@Override
		public void serialize(LittleEndianOutput out) {
			out.write(data);
		}
	}
	@Test
	public void test(){
		excelWaterMarkForXlsx("/Users/wanhua/Documents/waterMark/test.xlsx","万华-刘泽权");
	}
}