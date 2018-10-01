package startup;

import gui.frame.MainFrame;
import gui.panel.MainPanel;
import gui.panel.SpendPanel;
import util.GUIUtil;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class BootStrap {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        GUIUtil.useLNF();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                MainFrame.instance.setVisible(true);
                MainPanel.instance.workingPanel.show(SpendPanel.instance);
            }
        });
    }
}
