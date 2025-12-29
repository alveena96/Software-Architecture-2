package app;

import persistence.AppRepository;
import ui.MainWindow;
import ui.Ui;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Ui.setNimbus();

        try { AppRepository.get().load("data"); } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
