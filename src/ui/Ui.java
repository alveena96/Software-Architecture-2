package ui;

import javax.swing.*;
import java.awt.*;

public final class Ui {
    private Ui() {}

    public static void setNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception ignored) {}
    }

    public static void style(JTable t) {
        t.setRowHeight(26);
        t.setFillsViewportHeight(true);
        t.setAutoCreateRowSorter(true);
    }

    public static JPanel padded(JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        p.add(c, BorderLayout.CENTER);
        return p;
    }
}
