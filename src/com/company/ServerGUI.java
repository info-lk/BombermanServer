package com.company;

import com.company.network.SimpleServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by winterj on 26.03.2014.
 */
public class ServerGUI extends Thread implements ActionListener{
    private JButton startButton;
    private JButton freezeButton;
    private JButton editorModeButton;
    private JButton closeButton;
    private JPanel mainPanel;
    private JTextArea textArea1;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTextField wallC;
    private JTextField dWallC;
    private JButton searchForCLientsButton;
    private JFrame frame;
    SimpleServer server;


    public ServerGUI(SimpleServer server) {
        this.server = server;
        frame = new JFrame("Server");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 600));
        startButton.addActionListener(this);
        closeButton.addActionListener(this);
        freezeButton.addActionListener(this);
        spinner1.setValue(16);
        spinner2.setValue(16);
        System.out.println("hier");
    }

    public void printConsole(String s) {
        textArea1.append(s + "\n");
    }

    public int getWidth() {
        return (Integer) spinner1.getValue();
    }

    public int getHeight() {
        return (Integer) spinner2.getValue();
    }

    public double getWallChance() {
        return Double.parseDouble(wallC.getText());
    }

    public double getDestructableWallChance() {
        return Double.parseDouble(dWallC.getText());
    }

    public void redrawText() {
        textArea1.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent aE) {
        if(aE.getSource() == startButton) {
            server.start();
        }

        if(aE.getSource() == freezeButton) {
            server.freeze();
        }

        if(aE.getSource() == closeButton) {
            server.shutDown();
            System.exit(1);
        }
        if(aE.getSource() == searchForCLientsButton) {
            server.runLobby();
        }
    }

    public void run() {
        frame.setVisible(true);
    }
}
