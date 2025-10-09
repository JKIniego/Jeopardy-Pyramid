package com.noinc.bloomsjeopardy.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIBrand {
    BufferedImage gameIconIMG, backgroundIMG, titleIMG, titleInGameIMG, brickIMG, heartFullIMG, heartEmptyIMG, menuIMG, neuralBrainIMG;
    Color blue, lightBlue, darkBlue, red, green, white, black, gray; 
    Font CustomFontLarge, CustomFontFinalScore, CustomFontMedium, CustomFontSmall, CustomFontSmaller, CustomFontExtraSmall;
    ImageIcon titleGIF, titleInGameGIF, gameOverGIF;

    public GUIBrand(){
        blue = new Color(56, 182, 255);
        darkBlue = new Color(0, 34, 73);
        lightBlue = new Color(192, 230, 252);
        red = new Color(255, 49, 49);
        green = new Color(13, 255, 0);
        white = new Color(255,255,255);
        black = new Color(0,0,0);
        gray = new Color(70,70,70);

        try {
            var fontStream = getClass().getResourceAsStream("/Assets/Fonts/PressStart2P-Regular.ttf");
            if (fontStream == null) {
                throw new IOException("Font file not found in resources!");
            }
            Font CustomFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            CustomFontLarge = CustomFont.deriveFont(Font.PLAIN, 65);
            CustomFontMedium = CustomFont.deriveFont(Font.PLAIN, 27);
            CustomFontSmall = CustomFont.deriveFont(Font.PLAIN, 16);
            CustomFontSmaller = CustomFont.deriveFont(Font.PLAIN, 14);
            CustomFontExtraSmall = CustomFont.deriveFont(Font.PLAIN, 12);
            CustomFontFinalScore = CustomFont.deriveFont(Font.PLAIN, 40);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        try {
            backgroundIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/background.jpg"));
            titleIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/title.png"));
            titleInGameIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/titleInGame.png"));
            brickIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/brick.png"));
            heartFullIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/heart-full.png"));
            heartEmptyIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/heart-empty.png"));
            menuIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/menu.png"));
            gameIconIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/game-icon.png"));
            neuralBrainIMG = ImageIO.read(getClass().getResourceAsStream("/Assets/Images/neural-brain.png"));

            titleIMG = resizeImage(titleIMG, 800, 150);
            titleInGameIMG = resizeImage(titleInGameIMG, 450, 80);
            brickIMG = resizeImage(brickIMG, 170, 53);
            heartFullIMG = resizeImage(heartFullIMG, 20, 20);
            heartEmptyIMG = resizeImage(heartEmptyIMG, 20, 20);
            menuIMG = resizeImage(menuIMG, 20, 20);
            gameIconIMG = resizeImage(gameIconIMG, 250, 200);
            neuralBrainIMG = resizeImage(neuralBrainIMG, 200, 200);

            titleGIF = new ImageIcon(getClass().getResource("/Assets/Images/jeopardy-animation.gif"));
            titleInGameGIF = new ImageIcon(getClass().getResource("/Assets/Images/jeopardy-animation2.gif"));
            gameOverGIF = new ImageIcon(getClass().getResource("/Assets/Images/game-over.gif"));
            
            titleGIF = resizeGIF(titleGIF, 800, 150);
            titleInGameGIF = resizeGIF(titleInGameGIF, 500, 100);
            gameOverGIF = resizeGIF(gameOverGIF, 900, 150);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public ImageIcon resizeGIF(ImageIcon icon, int width, int height) {
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaled);
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    public void buttonHighlight(JButton button){
        button.setFont(CustomFontMedium);
        button.setForeground(white);
        button.setBackground(black);
        button.setBorder(BorderFactory.createLineBorder(blue, 5));
        button.setContentAreaFilled(true);
        button.setFocusable(false);
        button.setBorderPainted(true);
    }
    public void buttonTransparent(JButton button){
        button.setFont(CustomFontMedium);
        button.setForeground(white);
        button.setBackground(blue);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorderPainted(false);
    }

}

