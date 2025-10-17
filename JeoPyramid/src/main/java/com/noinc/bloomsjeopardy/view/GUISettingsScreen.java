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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.noinc.bloomsjeopardy.data.GameData;
import com.noinc.bloomsjeopardy.utils.SoundManager;

public class GUISettingsScreen extends JPanel implements MouseListener {
    private JPanel parentPanel;
    private JButton backButton;
    private GUIBrand brand;
    private GameData gameData;

    public GUISettingsScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        initializeSettingsScreen();
    }

    private void initializeSettingsScreen() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(brand.backgroundIMG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Create the content panel that sizes to fit content
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(brand.black);
        contentPanel.setBorder(BorderFactory.createLineBorder(brand.blue, 8));
        contentPanel.setLayout(new GridBagLayout());

        // Title section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        titlePanel.setBackground(brand.black);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(brand.CustomFontMedium);
        titleLabel.setForeground(brand.blue);
        titlePanel.add(titleLabel);

        // Content section (empty for now)
        JPanel settingsContentPanel = new JPanel(new BorderLayout());
        settingsContentPanel.setBackground(brand.black);
        settingsContentPanel.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, brand.blue));

        // Add audio settings
        JPanel audioSettingsPanel = createAudioSettingsPanel();
        settingsContentPanel.add(audioSettingsPanel, BorderLayout.CENTER);

        // Back button section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(brand.black);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, brand.blue));
        
        backButton = new JButton("Back");
        backButton.setActionCommand("Settings backButton");
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
        contentPanel.add(settingsContentPanel, gbc);
        
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

    private JPanel createAudioSettingsPanel() {
        JPanel audioPanel = new JPanel(new GridBagLayout());
        audioPanel.setBackground(brand.black);
        audioPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Audio Settings Header
        JLabel audioHeaderLabel = new JLabel("Audio Settings");
        audioHeaderLabel.setFont(brand.CustomFontSmall);
        audioHeaderLabel.setForeground(brand.blue);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        audioPanel.add(audioHeaderLabel, gbc);

        // Master Volume
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel masterVolumeLabel = new JLabel("Master Volume:");
        masterVolumeLabel.setFont(brand.CustomFontExtraSmall);
        masterVolumeLabel.setForeground(brand.white);
        audioPanel.add(masterVolumeLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSlider masterVolumeSlider = new JSlider(0, 100, 80);
        styleSlider(masterVolumeSlider);
        masterVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = masterVolumeSlider.getValue() / 100.0f;
                SoundManager.getInstance().setMasterVolume(volume);
            }
        });
        audioPanel.add(masterVolumeSlider, gbc);

        // Background Music Volume
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel bgMusicLabel = new JLabel("Background Music:");
        bgMusicLabel.setFont(brand.CustomFontExtraSmall);
        bgMusicLabel.setForeground(brand.white);
        audioPanel.add(bgMusicLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSlider bgMusicSlider = new JSlider(0, 100, 80);
        styleSlider(bgMusicSlider);
        bgMusicSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = bgMusicSlider.getValue() / 100.0f;
                SoundManager.getInstance().setVolume("background_music.wav", volume);
            }
        });
        audioPanel.add(bgMusicSlider, gbc);

        // Sound Effects Volume
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel sfxLabel = new JLabel("Sound Effects:");
        sfxLabel.setFont(brand.CustomFontExtraSmall);
        sfxLabel.setForeground(brand.white);
        audioPanel.add(sfxLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JSlider sfxSlider = new JSlider(0, 100, 80);
        styleSlider(sfxSlider);
        sfxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = sfxSlider.getValue() / 100.0f;
                // Apply to common sound effects
                SoundManager.getInstance().setVolume("intro.wav", volume);
                SoundManager.getInstance().setVolume("correct1.wav", volume);
                SoundManager.getInstance().setVolume("wrong1.wav", volume);
            }
        });
        audioPanel.add(sfxSlider, gbc);

        // Mute All Audio Checkbox
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JCheckBox muteAllCheckbox = new JCheckBox("Mute All Audio");
        muteAllCheckbox.setBackground(brand.black);
        muteAllCheckbox.setForeground(brand.white);
        muteAllCheckbox.setFont(brand.CustomFontExtraSmall);
        muteAllCheckbox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean muted = muteAllCheckbox.isSelected();
                if (muted) {
                    SoundManager.getInstance().stopBackgroundMusic();
                    SoundManager.getInstance().stopAll();
                    masterVolumeSlider.setEnabled(false);
                    bgMusicSlider.setEnabled(false);
                    sfxSlider.setEnabled(false);
                } else {
                    SoundManager.getInstance().playBackgroundMusic();
                    masterVolumeSlider.setEnabled(true);
                    bgMusicSlider.setEnabled(true);
                    sfxSlider.setEnabled(true);
                }
            }
        });
        audioPanel.add(muteAllCheckbox, gbc);

        // Disable Intro Sound Checkbox
        gbc.gridy = 5;
        JCheckBox disableIntroCheckbox = new JCheckBox("Disable Intro Sound");
        disableIntroCheckbox.setBackground(brand.black);
        disableIntroCheckbox.setForeground(brand.white);
        disableIntroCheckbox.setFont(brand.CustomFontExtraSmall);
        // Note: This would require storing the preference and checking it in GameEngine
        audioPanel.add(disableIntroCheckbox, gbc);

        return audioPanel;
    }

    private void styleSlider(JSlider slider) {
        slider.setBackground(brand.black);
        slider.setForeground(brand.blue);
        slider.setOpaque(true);
        slider.setFocusable(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(false);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        
        // Set preferred size to make it easier to interact with
        slider.setPreferredSize(new Dimension(200, 30));
        
        // Enable focus so it can be clicked and dragged
        slider.setRequestFocusEnabled(true);
        
        // Add mouse listener for click-to-position functionality
        slider.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Calculate the value based on click position
                int sliderWidth = slider.getWidth();
                int clickX = e.getX();
                
                // Calculate the percentage of where the click occurred
                double percentage = (double) clickX / sliderWidth;
                
                // Clamp percentage between 0 and 1
                percentage = Math.max(0.0, Math.min(1.0, percentage));
                
                // Calculate the new value based on slider's range
                int range = slider.getMaximum() - slider.getMinimum();
                int newValue = slider.getMinimum() + (int) (percentage * range);
                
                // Set the new value
                slider.setValue(newValue);
            }
        });
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