package org.jlab.svt.validation;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableRender extends JPanel {

    public TableRender() {
        super(new GridLayout(1, 0));

        final JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new MyRenderer());

        table.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                JOptionPane.showInternalMessageDialog(TableRender.this,
                        "Color: " + getTableCellBackground(table, row, col));

                System.out.println("Color: " + getTableCellBackground(table, row, col));
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public Color getTableCellBackground(JTable table, int row, int col) {
        TableCellRenderer renderer = table.getCellRenderer(row, col);
        Component component = table.prepareRenderer(renderer, row, col);
        return component.getBackground();
    }

    class MyRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JTextField editor = new JTextField();
            if (value != null) {
                editor.setText(value.toString());
            }
            editor.setBackground((row % 2 == 0) ? Color.WHITE : Color.YELLOW);
            editor.setForeground((column % 2 != 0) ? Color.RED : Color.BLUE);
//            editor.setSize(200,150);
            table.setRowHeight(21);

            return editor;
        }
    }

    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
        private Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), Boolean.valueOf(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), Boolean.valueOf(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), Boolean.valueOf(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), Boolean.valueOf(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), Boolean.valueOf(false)}
        };
        public final Object[] longValues = {"Jane", "Kathy",
                "None of the above",
                new Integer(20), Boolean.TRUE};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("TableRender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TableRender newContentPane = new TableRender();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
}
