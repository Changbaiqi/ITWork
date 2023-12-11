package com.cbq.it.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class ImageUtils {

    public static void printImageBinary(String filePath){
        try {
            URL resource = ImageUtils.class.getClassLoader().getResource(filePath);
            BufferedImage read = ImageIO.read(resource);

            int width = read.getWidth();
            int height = read.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(read, 0, 0, null);
            graphics.dispose();


            int threshold = 128;
            //用于存储图片的二值化数组
            double data[][] = new double[height][width];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    Color color = new Color(bufferedImage.getRGB(x, y));
                    double gray = (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() *0.114)/255.0;
                    data[y][x] = gray;
                }
                System.out.println(Arrays.toString(data[y]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] readImageBinaryResource(String filePath){
        try {
            URL resource = ImageUtils.class.getClassLoader().getResource(filePath);
            BufferedImage read = ImageIO.read(resource);

            int width = read.getWidth();
            int height = read.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(read, 0, 0, null);
            graphics.dispose();

            int threshold = 128;
            //用于存储图片的二值化数组
            double data[][] = new double[height][width];
            double res[][] = new double[1][height*width];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    //获取图片指定点的rgb色彩
                    //int rgb = read.getRGB(x,y);
                    Color color = new Color(bufferedImage.getRGB(x, y));
                    //int binaryPixel = gray < threshold ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                    //binaryImage.setRGB(x,y,binaryPixel)
                    double gray = (0.299*color.getRed() + 0.587*color.getGreen() + 0.114*color.getBlue())/255.0;
                    data[y][x] = gray;
                    res[0][y*width+x] = gray;
                }
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用于获取一个图片的矩形数据特征的
     * @param filePath
     * @return
     */
    public static double[][] readImageMatrixBinary(String filePath){
        try {
            BufferedImage read = ImageIO.read(new File(filePath));

            int width = read.getWidth();
            int height = read.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(read, 0, 0, null);
            graphics.dispose();

            int threshold = 128;
            //用于存储图片的二值化数组
            double res[][] = new double[height][width];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    Color color = new Color(bufferedImage.getRGB(x, y));
                    double gray = (0.299*color.getRed() + 0.587*color.getGreen() + 0.114*color.getBlue())/255.0;
                    res[y][x] = gray;
                }
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static double[][] readImageBinary(String filePath){
        try {
            BufferedImage read = ImageIO.read(new File(filePath));

            int width = read.getWidth();
            int height = read.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(read, 0, 0, null);
            graphics.dispose();


            int threshold = 128;
            //用于存储图片的二值化数组
            double res[][] = new double[1][height*width];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    //获取图片指定点的rgb色彩
                    //int rgb = read.getRGB(x,y);
                    Color color = new Color(bufferedImage.getRGB(x, y));
                    //int binaryPixel = gray < threshold ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                    //binaryImage.setRGB(x,y,binaryPixel)
                    double gray = (0.299*color.getRed() + 0.587*color.getGreen() + 0.114*color.getBlue())/255.0;
                    res[0][y*width+x] = gray;
                }
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
