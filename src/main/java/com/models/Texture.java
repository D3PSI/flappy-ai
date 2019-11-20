package com.models;

import org.lwjgl.opengl.*;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

/**
 * Defines a texture object
 * @author D3PSI
 */
public class Texture {

    private int id;
    private int width;
    private int height;

    /**
     * Constructor with filename
     * @param filename_ The path to the file on disk
     */
    public Texture(String filename_) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filename_));
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();

            int[] pixels = new int[width * height * 4];
            pixels = bufferedImage.getRGB(
                0, 
                0, 
                width, 
                height, 
                null, 
                0, 
                width);

            ByteBuffer pixBuf = BufferUtils.createByteBuffer(width * height * 4);
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    int pixel = pixels[i * width + j];
                    pixBuf.put((byte)((pixel >> 16) & 0xFF));   // red
                    pixBuf.put((byte)((pixel >> 8) & 0xFF));    // green
                    pixBuf.put((byte)(pixel & 0xFF));           // blue
                    pixBuf.put((byte)((pixel >> 24) & 0xFF));   // alpha
                }
            }

            pixBuf.flip();

            id = GL45.glGenTextures();
            GL45.glBindTexture(GL45.GL_TEXTURE_2D, id);
            GL45.glTexParameterf(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_MIN_FILTER, GL45.GL_NEAREST);
            GL45.glTexParameterf(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_MAG_FILTER, GL45.GL_NEAREST);
            GL45.glTexImage2D(
                GL45.GL_TEXTURE_2D, 
                0, 
                GL45.GL_RGBA, 
                width, 
                height, 
                0, 
                GL45.GL_RGBA, 
                GL45.GL_UNSIGNED_BYTE, 
                pixBuf);
        } catch (IOException e_) {
            e_.printStackTrace();
        }
    }

    /**
     * Binds the texture
     */
    public void bind() {
        GL45.glBindTexture(GL45.GL_TEXTURE_2D, id);
    }

}