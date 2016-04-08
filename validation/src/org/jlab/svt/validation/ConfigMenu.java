package org.jlab.svt.validation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

public class ConfigMenu extends JPanel {

    public JTextPane pane;

    public JMenuBar menuBar;

//    public JToolBar toolBar;

    public ConfigMenu() {
        menuBar = new JMenuBar();
        JMenu cutsMenu = new JMenu("Cuts");
        ActionListener actionPrinter = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pane.getStyledDocument().insertString(
                            0,
                            "Action [" + e.getActionCommand()
                                    + "] performed!\n", null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        JCheckBoxMenuItem singleTrack = new JCheckBoxMenuItem("Single Track");
        singleTrack.addActionListener(actionPrinter);
        JCheckBoxMenuItem type1 = new JCheckBoxMenuItem("Type 1");
        type1.addActionListener(actionPrinter);
        JCheckBoxMenuItem chi2 = new JCheckBoxMenuItem("Chi^2");
        chi2.addActionListener(actionPrinter);
        JCheckBoxMenuItem allCrosses = new JCheckBoxMenuItem("All Crosses");
        allCrosses.addActionListener(actionPrinter);

        cutsMenu.add(singleTrack);
        cutsMenu.add(type1);
        cutsMenu.add(chi2);
        cutsMenu.add(allCrosses);

        menuBar.add(cutsMenu);
        menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

    }

    public static void main(String s[]) {
        ConfigMenu configMenu = new ConfigMenu();
        configMenu.pane = new JTextPane();
        configMenu.pane.setPreferredSize(new Dimension(250, 250));
        configMenu.pane.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JFrame frame = new JFrame("Config Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(configMenu.menuBar);
        frame.getContentPane().add(configMenu.pane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
