package gui.listener;

import gui.panel.BackupPanel;
import gui.panel.ConfigPanel;
import gui.panel.MainPanel;
import service.ConfigService;
import util.MysqlUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BackupListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        BackupPanel p = BackupPanel.instance;
        String mysqslPath = new ConfigService().get(ConfigService.mysqlPath);
        if (0 == mysqslPath.length()) {
            JOptionPane.showMessageDialog(p, "备份前请事先配置mysql的路径");
            MainPanel.instance.workingPanel.show(ConfigPanel.instance);
            ConfigPanel.instance.tfMysqlPath.grabFocus();
            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("hutubill.sql"));
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".sql");
            }

            @Override
            public String getDescription() {
                return ".sql";
            }
        });
        int returnVal = fc.showSaveDialog(p);
        File file = fc.getSelectedFile();

        if (returnVal==JFileChooser.APPROVE_OPTION){
            if (!file.getName().toLowerCase().endsWith(".sql"))
                file = new File(file.getParent(), file.getName()+".sql");
            try{
                MysqlUtil.backup(mysqslPath, file.getAbsolutePath());
                JOptionPane.showMessageDialog(p,"备份成功,备份文件位于:\r\n"+file.getAbsolutePath());
            }catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(p, "备份失败\r\n,错误:\r\n"+ex.getMessage());
            }
        }
    }
}
