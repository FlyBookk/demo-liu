package com.liuzq.watermark.test;

import com.spire.xls.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class ExcelWatermark {
    public static void main(String[] args)  {

        //加载示例文档
        Workbook workbook = new Workbook();
        workbook.loadFromFile("/Users/wanhua/Documents/waterMark/test.xlsx");

        //设置文本和字体大小
        Font font = new Font("仿宋", Font.PLAIN, 40);
        String watermark = "内部专用";

        for (Worksheet sheet : (Iterable<Worksheet>) workbook.getWorksheets()) {
            //调用DrawText() 方法插入图片
            BufferedImage imgWtrmrk = drawText(watermark, font, Color.pink, Color.white, sheet.getPageSetup().getPageHeight(), sheet.getPageSetup().getPageWidth());

            //将图片设置为页眉
            sheet.getPageSetup().setLeftHeaderImage(imgWtrmrk);
            sheet.getPageSetup().setLeftHeader("&G");

            //将显示模式设置为Layout
            sheet.setViewMode(ViewMode.Layout);
        }

        //保存文档
        workbook.saveToFile("/Users/wanhua/Documents/waterMark/Watermark.xlsx", ExcelVersion.Version2010);
    }
    private static BufferedImage drawText (String text, Font font, Color textColor, Color backColor,double height, double width)
    {
        //定义图片宽度和高度
        BufferedImage img = new BufferedImage((int) width, (int) height, TYPE_INT_ARGB);
        Graphics2D loGraphic = img.createGraphics();

        //获取文本size
        FontMetrics loFontMetrics = loGraphic.getFontMetrics(font);
        int liStrWidth = loFontMetrics.stringWidth(text);
        int liStrHeight = loFontMetrics.getHeight();

        //文本显示样式及位置
        loGraphic.setColor(backColor);
        loGraphic.fillRect(0, 0, (int) width, (int) height);
        loGraphic.translate(((int) width - liStrWidth) / 2, ((int) height - liStrHeight) / 2);
        loGraphic.rotate(Math.toRadians(-45));

        loGraphic.translate(-((int) width - liStrWidth) / 2, -((int) height - liStrHeight) / 2);
        loGraphic.setFont(font);
        loGraphic.setColor(textColor);
        loGraphic.drawString(text, ((int) width - liStrWidth) / 2, ((int) height - liStrHeight) / 2);
        loGraphic.dispose();
        return img;
    }
}