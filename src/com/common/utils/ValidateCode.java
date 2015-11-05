package com.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * 验证码生成工具
 */
public class ValidateCode {

	public static class ValidatecodeInputstream {
		private InputStream inputStream;
		private String validatecode;

		public String getValidatecode() {
			return validatecode;
		}

		public void setValidatecode(String validatecode) {
			this.validatecode = validatecode;
		}

		public InputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}
	}

	public static class ValidatecodeImage {
		private BufferedImage image;
		private String validatecode;

		public BufferedImage getImage() {
			return image;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
		}

		public String getValidatecode() {
			return validatecode;
		}

		public void setValidatecode(String validatecode) {
			this.validatecode = validatecode;
		}
	}

	/**
	 * 生成验证码图片缓存
	 * 
	 * @param seedstring
	 *            验证码种子
	 * @param length
	 *            验证码长度
	 * @param imgwidth
	 *            图片宽度
	 * @param imgheight
	 *            图片长度
	 * @return
	 */
	public static ValidatecodeImage genValidateCodeImage(int length,
			int imgwidth, int imgheight) {
		BufferedImage image = new BufferedImage(imgwidth, imgheight,
				BufferedImage.TYPE_INT_RGB);// 在内存中创建图象
		Graphics2D raphics = (Graphics2D) image.getGraphics();// 获取图形上下文
		Color c = new Color(203, 231, 240);
		raphics.setColor(c);// 设定背景色
		raphics.fillRect(0, 0, imgwidth, imgheight);
		raphics.setFont(new Font("Times New Roman", Font.ITALIC, 28));// 设定字体
																		// style:HANGING_BASELINE
		raphics.setColor(getRandColor(160, 200));// 给定范围获得随机颜色
		Random random = new Random(new Date().getTime()); // 生成随机类
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < 255; i++) {
			int x = random.nextInt(imgwidth);
			int y = random.nextInt(imgheight);
			raphics.drawLine(x, y, x, y);
		}

		String validcode = RandomCode.genRandomCode(length);
		// 取随机产生的认证码(length位数字)
		for (int i = 0; i < length; i++) {
			char code = validcode.charAt(i);
			raphics.setColor(Color.BLACK);// 设置为黑色字体
			raphics.drawString("" + code, 15 * i + 25, 32);
		}
		raphics.dispose(); // 图象生效

		ValidatecodeImage img = new ValidatecodeImage();
		img.setImage(image);
		img.setValidatecode(validcode);
		return img;
	}

	/**
	 * 使用种子生成验证码图片缓存
	 * 
	 * @param length
	 *            验证码长度
	 * @param imgwidth
	 *            图片宽度
	 * @param imgheight
	 *            图片长度
	 * @return
	 */
	public static ValidatecodeImage genValidateCodeImage(String seedstring,
			int length, int imgwidth, int imgheight) {
		BufferedImage image = new BufferedImage(imgwidth, imgheight,
				BufferedImage.TYPE_INT_RGB);// 在内存中创建图象
		Graphics2D raphics = (Graphics2D) image.getGraphics();// 获取图形上下文
		Color c = new Color(203, 231, 240);
		raphics.setColor(c);// 设定背景色
		raphics.fillRect(0, 0, imgwidth, imgheight);
		raphics.setFont(new Font("Times New Roman", Font.ITALIC, 28));// 设定字体
																		// style:HANGING_BASELINE
		raphics.setColor(getRandColor(160, 200));// 给定范围获得随机颜色
		Random random = new Random(new Date().getTime()); // 生成随机类
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < 255; i++) {
			int x = random.nextInt(imgwidth);
			int y = random.nextInt(imgheight);
			raphics.drawLine(x, y, x, y);
		}

		String validcode = RandomCode.genRandomCode(seedstring, length);
		// 取随机产生的认证码(length位数字)
		for (int i = 0; i < length; i++) {
			char code = validcode.charAt(i);
			raphics.setColor(Color.BLACK);// 设置为黑色字体
			raphics.drawString("" + code, 15 * i + 25, 32);
		}
		raphics.dispose(); // 图象生效

		ValidatecodeImage img = new ValidatecodeImage();
		img.setImage(image);
		img.setValidatecode(validcode);
		return img;
	}

	/**
	 * 生成验证码图片输入流
	 * 
	 * @param length
	 *            验证码长度
	 * @param imgwidth
	 *            图片宽度
	 * @param imgheight
	 *            图片长度
	 * @return
	 */
	public static ValidatecodeInputstream genValidateCodeInputStream(
			int length, int imgwidth, int imgheight) {
		return genValidateCodeInputStream(length, imgwidth,
				imgheight);
	}

	/**
	 * 使用种子生成验证码图片输入流
	 * 
	 * @param seedstring
	 *            验证码种子
	 * @param length
	 *            验证码长度
	 * @param imgwidth
	 *            图片宽度
	 * @param imgheight
	 *            图片长度
	 * @return
	 */
	public static ValidatecodeInputstream genValidateCodeInputStream(
			String seedstring, int length, int imgwidth, int imgheight) {
		InputStream inputStream = null;
		ValidatecodeImage img = genValidateCodeImage(seedstring, length,
				imgwidth, imgheight);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
			ImageIO.write(img.getImage(), "JPEG", ios);
			inputStream = new ByteArrayInputStream(bos.toByteArray());
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ValidatecodeInputstream validcodeInputstream = new ValidatecodeInputstream();
		validcodeInputstream.setInputStream(inputStream);
		validcodeInputstream.setValidatecode(img.getValidatecode());
		return validcodeInputstream;
	}

	/**
	 * 随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random(new Date().getTime());
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
