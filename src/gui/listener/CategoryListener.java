package gui.listener;

import entity.Category;
import gui.panel.CategoryPanel;
import service.CategoryService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        CategoryPanel p = CategoryPanel.instance;
        JButton b = (JButton) e.getSource();

        if (p.bAdd == b){
            String name = JOptionPane.showInputDialog(null);
            if (null == name)
                return;
            if (0 == name.length()){
                JOptionPane.showMessageDialog(p, "分类名称不能为空");
                return;
            }
            new CategoryService().add(name);
        }

        if (p.bEdit == b){
            Category c = p.getSelectCategory();
            int id = c.getId();
            String name = JOptionPane.showInputDialog("修改分类名称", c.getName());
            if (null == name)
                return;
            if (0 == name.length()){
                JOptionPane.showMessageDialog(p, "分类名称不能为空");
                return;
            }
            new CategoryService().update(id, name);
        }

        if (p.bDelete == b){
            Category c = p.getSelectCategory();
            if (0 != c.recordNumber) {
                JOptionPane.showMessageDialog(p, "本分类下有消费记录存在，不能删除");
                return;
            }
            if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(p, "确认要删除？"))
                return;

            int id = c.getId();
            new CategoryService().delete(id);
        }

        p.updateData();
    }
}
