package com.liuzq.watermark.test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.util.StringUtils;
 
//水印图片工具类
public class FontImageUtil {
	/**
	 * 水印内容类
	 */
	public static class Watermark {
		private Boolean enable;
		private String text;
		private String dateFormat;
		private String color;
 
		public Watermark(Boolean enable, String text, String dateFormat, String color) {
			this.enable = enable;
			this.text = text;
			this.dateFormat = dateFormat;
			this.color = color;
		}
 
		public Watermark() {
		}
 
		public Boolean getEnable() {
			return enable;
		}
 
		public void setEnable(Boolean enable) {
			this.enable = enable;
		}
 
		public String getText() {
			return text;
		}
 
		public void setText(String text) {
			this.text = text;
		}
 
		public String getDateFormat() {
			return dateFormat;
		}
 
		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}
 
		public String getColor() {
			return color;
		}
 
		public void setColor(String color) {
			this.color = color;
		}
	}
 
	/**
	 * 生成水印图片
	 * @param watermark
	 * @return
	 */
	public static BufferedImage createWatermarkImage(Watermark watermark) {
		if (watermark == null) {
			watermark = new Watermark();
			watermark.setEnable(true);
			watermark.setText("内部资料");
			watermark.setColor("#C5CBCF");
			watermark.setDateFormat("yyyy-MM-dd HH:mm");
		} else {
			if (StringUtils.isEmpty(watermark.getDateFormat())) {
				watermark.setDateFormat("yyyy-MM-dd HH:mm");
			} else if (watermark.getDateFormat().length() == 16) {
				watermark.setDateFormat("yyyy-MM-dd HH:mm");
			} else if (watermark.getDateFormat().length() == 10) {
				watermark.setDateFormat("yyyy-MM-dd");
			}
			if (StringUtils.isEmpty(watermark.getText())) {
				watermark.setText("内部资料");
			}
			if (StringUtils.isEmpty(watermark.getColor())) {
				watermark.setColor("#C5CBCF");
			}
		}
		String[] textArray = watermark.getText().split("\n");
		Font font = new Font("microsoft-yahei", Font.BOLD, 40);
		Integer width = 400;
		Integer height = 200;
 
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 背景透明 开始
		Graphics2D g = image.createGraphics();
		// 背景透明 结束
		g.setColor(Color.white);
		g.fillRect(0, 0, width , height);
 
		g.setColor(new Color(Integer.parseInt(watermark.getColor().substring(1), 16)));// 设定画笔颜色
		g.setFont(font);// 设置画笔字体
		g.shear(0.1, -0.26);// 设定倾斜度
 
 
		//        设置字体平滑
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
		int y = 50;
		for (int i = 0; i < textArray.length; i++) {
			g.drawString(textArray[i], 0, y);// 画出字符串
			y = y + font.getSize();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(watermark.getDateFormat());
		g.drawString(sdf.format(new Date()), 0, y);// 画出字符串
 
		g.dispose();// 释放画笔
		return image;
 
	}
}