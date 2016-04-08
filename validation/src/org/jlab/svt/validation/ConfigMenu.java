package org.jlab.svt.validation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

public class ConfigMenu extends JPanel {

    public JTextPane pane;
    public JMenuBar menuBar;
    private JCheckBoxMenuItem singleTrackCheckBox;
    private JCheckBoxMenuItem type1CheckBox;
    private JCheckBoxMenuItem chi2CheckBox;
    private JCheckBoxMenuItem allCrossesCheckBox;

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
        singleTrackCheckBox = new JCheckBoxMenuItem("Single Track");
        singleTrackCheckBox.addActionListener(actionPrinter);
        type1CheckBox = new JCheckBoxMenuItem("Type 1");
        type1CheckBox.addActionListener(actionPrinter);
        chi2CheckBox = new JCheckBoxMenuItem("Chi^2");
        chi2CheckBox.addActionListener(actionPrinter);
        allCrossesCheckBox = new JCheckBoxMenuItem("All Crosses");
        allCrossesCheckBox.addActionListener(actionPrinter);

        cutsMenu.add(singleTrackCheckBox);
        cutsMenu.add(type1CheckBox);
        cutsMenu.add(chi2CheckBox);
        cutsMenu.add(allCrossesCheckBox);

        menuBar.add(cutsMenu);
        menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

    }

    public JCheckBoxMenuItem getSingleTrackCheckBox() {
        return singleTrackCheckBox;
    }

    public JCheckBoxMenuItem getType1CheckBox() {
        return type1CheckBox;
    }

    public JCheckBoxMenuItem getChi2CheckBox() {
        return chi2CheckBox;
    }

    public JCheckBoxMenuItem getAllCrossesCheckBox() {
        return allCrossesCheckBox;
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
