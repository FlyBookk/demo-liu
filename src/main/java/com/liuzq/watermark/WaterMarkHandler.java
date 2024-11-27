package com.liuzq.watermark;

import cn.hutool.core.img.ImgUtil;
import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class WaterMarkHandler extends AbstractSheetWriteHandler {

    private final WaterMark watermark;

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        BufferedImage waterMark = createWatermarkImage(watermark);
        XSSFSheet sheet = (XSSFSheet) writeSheetHolder.getSheet();
        putWaterRemarkToExcel(sheet, waterMark);
    }

    private BufferedImage createWatermarkImage(WaterMark mark) {

        Font font = mark.getFont();
        int width = mark.getWidth();
        int height = mark.getHeight();

        String[] textArray = mark.getContent().split(",");
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // 使用ARGB支持透明度
        Graphics2D g = image.createGraphics();

        try {
            // 设定画笔颜色
            String colorStr = mark.getColor();
            if (colorStr.startsWith("#")) {
                g.setColor(new Color(Integer.parseInt(colorStr.substring(1), 16)));
            } else {
                throw new IllegalArgumentException("Invalid color format: " + colorStr);
            }
            // 设置画笔字体
            g.setFont(font);
            // 设定倾斜度
            g.shear(mark.getShearX(), mark.getShearY());
            // 设置字体平滑
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int y = mark.getYAxis();
            for (String s : textArray) {
                g.drawString(s, 0, y);
                y += font.getSize();
            }
        } finally {
            g.dispose(); // 确保在方法结束时释放资源
        }

        return image;
    }

    /**
     * 为Excel打上水印工具函数
     */
    public static void putWaterRemarkToExcel(XSSFSheet sheet, BufferedImage bufferedImage) {
        //add relation from sheet to the picture data
        XSSFWorkbook workbook = sheet.getWorkbook();
        int pictureIdx = workbook.addPicture(ImgUtil.toBytes(bufferedImage, ImgUtil.IMAGE_TYPE_PNG), org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG);
        String rID = sheet.addRelation(null, XSSFRelation.IMAGES, workbook.getAllPictures().get(pictureIdx))
                .getRelationship().getId();
        //set background picture to sheet
        sheet.getCTWorksheet().addNewPicture().setId(rID);
    }



}

