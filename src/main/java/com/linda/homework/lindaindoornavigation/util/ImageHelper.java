package com.linda.homework.lindaindoornavigation.util;

import java.awt.Graphics2D;

import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import sun.misc.BASE64Decoder;


public class ImageHelper {

    // 项目根目录路径
    public static final String path = System.getProperty("user.dir");

    /**
     * 生成缩略图 <br/>
     * 保存:ImageIO.write(BufferedImage, imgType[jpg/png/...], File);
     *
     * @param source
     *            原图片
     * @param width
     *            缩略图宽
     * @param height
     *            缩略图高
     * @param b
     *            是否等比缩放
     * */
    public static BufferedImage thumb(BufferedImage source, int width,
                                      int height, boolean b) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) width / source.getWidth();
        double sy = (double) height / source.getHeight();

        if (b) {
            if (sx > sy) {
                sx = sy;
                width = (int) (sx * source.getWidth());
            } else {
                sy = sx;
                height = (int) (sy * source.getHeight());
            }
        }

        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width,
                    height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(width, height, type);
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 读取JPEG图片
     * @param filename 文件名
     * @return BufferedImage 图片对象
     */
    public static BufferedImage readJPEGImage(String filename)
    {
        try {
            InputStream imageIn = new FileInputStream(new File(filename));
            // 得到输入的编码器，将文件流进行jpg格式编码
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
            // 得到编码后的图片对象
            BufferedImage sourceImage = decoder.decodeAsBufferedImage();

            return sourceImage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 读取JPEG图片
     * @param filename 文件名
     * @return BufferedImage 图片对象
     */
    public static BufferedImage readPNGImage(String filename)
    {
        try {
            File inputFile = new File(filename);
            BufferedImage sourceImage = ImageIO.read(inputFile);
            return sourceImage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将二进制字节流转化为图片
     * @return
     */
    public static BufferedImage readImage(byte[] picData) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(picData);
        return ImageIO.read(in);
    }

    public static byte[] readPNGBinary(BufferedImage image, String format){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, format, out);
        } catch (IOException e) {
            return null;
        }
        return out.toByteArray();
    }

    /**
     * 灰度值计算
     * @param pixels 像素
     * @return int 灰度值
     */
    public static int rgbToGray(int pixels) {
        // int _alpha = (pixels >> 24) & 0xFF;
        int _red = (pixels >> 16) & 0xFF;
        int _green = (pixels >> 8) & 0xFF;
        int _blue = (pixels) & 0xFF;
        return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
    }

    /**
     * 计算数组的平均值
     * @param pixels 数组
     * @return int 平均值
     */
    public static int average(int[] pixels) {
        float m = 0;
        for (int i = 0; i < pixels.length; ++i) {
            m += pixels[i];
        }
        m = m / pixels.length;
        return (int) m;
    }

    public static byte[] base64tobyteArr(String imgStr) throws IOException {
        if (imgStr == null) {
            return new byte[0];
        }
        BASE64Decoder decoder = new BASE64Decoder();
        // 解密
        byte[] b = decoder.decodeBuffer(imgStr);
        // 处理数据
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return b;
    }
}
