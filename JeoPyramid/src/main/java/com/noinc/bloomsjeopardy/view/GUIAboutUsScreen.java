package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIAboutUsScreen extends JPanel implements MouseListener {
    private JPanel parentPanel;
    private JButton backButton;
    private GUIBrand brand;
    private GameData gameData;

    public GUIAboutUsScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        initializeAboutUsScreen();
    }

    private void initializeAboutUsScreen() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(brand.backgroundIMG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Create the content panel similar to question panel
        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(1000, 600));
        contentPanel.setBackground(brand.black);
        contentPanel.setBorder(BorderFactory.createLineBorder(brand.blue, 8));
        contentPanel.setLayout(new GridBagLayout());

        // Title section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        titlePanel.setBackground(brand.black);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("About Us");
        titleLabel.setFont(brand.CustomFontMedium);
        titleLabel.setForeground(brand.blue);
        titlePanel.add(titleLabel);

        // Content section
        JPanel aboutContentPanel = new JPanel(new BorderLayout());
        aboutContentPanel.setBackground(brand.black);
        aboutContentPanel.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, brand.blue));

        JTextPane aboutTextPane = new JTextPane();
        aboutTextPane.setBackground(brand.black);
        aboutTextPane.setForeground(brand.white);
        aboutTextPane.setFont(brand.CustomFontSmall.deriveFont(16f));
        aboutTextPane.setEditable(false);
        aboutTextPane.setMargin(new Insets(20, 20, 20, 20));
        
        // Center align the text
        StyledDocument doc = aboutTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(center, 1.0f);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        String aboutText = "JEOPYRAMID\n\n" +
                          "A Jeopardy-style educational game based on Bloom's Taxonomy.\n\n" +
                          "This game challenges players with questions across different\n" +
                          "cognitive levels, from basic knowledge recall to complex\n" +
                          "evaluation and synthesis tasks.\n\n" +
                          "Developed as an interactive learning tool to enhance\n" +
                          "critical thinking and knowledge retention.\n\n" +
                          "TEAM MEMBERS\n\n" +
                          "Rolf Garces (Project Manager)\n" +
                          "Sean Bantanos (Backend Developer)\n" +
                          "Jhun Iniego (Backend Developer)\n" +
                          "MacDarren Calimba (Frontend Developer)\n" +
                          "Norman Eulin (Frontend Developer)\n" +
                          "Gian Tongzon (Frontend Developer)\n" +
                          "Jade Petilla (QA Engineer)\n\n" +
                          "© 2025 No Inc. All rights reserved.";
        
        aboutTextPane.setText(aboutText);
        
        // Create scroll pane and customize scrollbars
        JScrollPane scrollPane = new JScrollPane(aboutTextPane);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        scrollPane.setBackground(brand.black);
        scrollPane.setBorder(BorderFactory.createLineBorder(brand.blue, 2));
        scrollPane.getViewport().setBackground(brand.black);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Customize vertical scrollbar
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = brand.blue;
                this.trackColor = brand.black;
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(brand.blue);
                button.setBorder(BorderFactory.createLineBorder(brand.blue));
                return button;
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(brand.blue);
                button.setBorder(BorderFactory.createLineBorder(brand.blue));
                return button;
            }
        });
        
        scrollPane.getVerticalScrollBar().setBackground(brand.black);
        
        aboutContentPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(brand.black);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, brand.blue));
        
        backButton = new JButton("Back");
        backButton.setActionCommand("AboutUs backButton");
        backButton.setPreferredSize(new Dimension(200, 30));
        backButton.setBorderPainted(false);
        backButton.setForeground(brand.white);
        backButton.setBackground(brand.black);
        backButton.setFont(brand.CustomFontMedium);
        backButton.addMouseListener(this);
        buttonPanel.add(backButton);

        // Layout the content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPanel.add(titlePanel, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(aboutContentPanel, gbc);
        
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(buttonPanel, gbc);

        // Add content panel to main panel
        GridBagConstraints mainGBC = new GridBagConstraints();
        mainGBC.gridx = 0;
        mainGBC.gridy = 0;
        mainGBC.anchor = GridBagConstraints.CENTER;
        mainPanel.add(contentPanel, mainGBC);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    public JButton getBackButton() {
        return backButton;
    }

    // Mouse listener methods for hover effects
    @Override
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if (src == backButton) {
            backButton.setBackground(brand.blue);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();
        if (src == backButton) {
            backButton.setBackground(brand.black);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
}