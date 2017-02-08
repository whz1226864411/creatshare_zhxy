package cn.creat.zhxy.utils.parsecode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;


public class ParseCode {
	private static final int WIDTH = 34;
	private static final int HEIGHT = 42;
	private static final int X1 = 10;
	private static final int X2 = 56;
	private static final int X3 = 94;
	private static final int X4 = 139;
	private static final int X5 = 179;
	private static final int Y1 = 18;
	private static final int Y2 = 28;
	private static final int Y3 = 18;
	private static final int Y4 = 26;
	private static final int Y5 = 17;
	private static String codeFilePath;
	
	static{
//		codeFilePath = "D:/newcode/codelist/";
		codeFilePath = ParseCode.class.getClassLoader().getResource("//").getPath()+"codelist/";
	}
	
	public static String getResultCode(InputStream inputStream) throws
			NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, 
			IOException{
		StringBuilder resultCode = new StringBuilder();
		BufferedImage image2 = Binaryzation.product(inputStream);
		for(int i = 1; i <= 5; i++){
			resultCode.append(getOneCode(i, image2));
		}
		return resultCode.toString();
	}
	
	private static String getOneCode(int noun,BufferedImage image2) 
			throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Class<ParseCode> pClass = ParseCode.class;
		Field fieldX = pClass.getDeclaredField("X"+noun);
		Field fieldY = pClass.getDeclaredField("Y"+noun);
		fieldX.setAccessible(true);
		fieldY.setAccessible(true);
		int x = fieldX.getInt(null);
		int y = fieldY.getInt(null);
		String oneCode = "";
		double result = 0;
		double similarity = 0;
		File[] codeImages = new File(ParseCode.codeFilePath+noun).listFiles();
		BufferedImage image1 = null;
		image2 = image2.getSubimage(x, y, ParseCode.WIDTH, ParseCode.HEIGHT);
		for(File codeImage : codeImages){
			image1 = ImageIO.read(codeImage);
			result = Similarity.getSimilarity(image1, image2);
			if(similarity < result){
				similarity = result;
				String codeName = codeImage.getName();
				int index = codeName.indexOf("-");
				oneCode = codeName.substring(index+1, index+2);
			}
		}
		return oneCode;
	}
	
}
