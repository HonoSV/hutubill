package service;

import dao.CategoryDAO;
import dao.RecordDAO;
import entity.Category;
import entity.Record;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CategoryService {
    CategoryDAO categoryDAO = new CategoryDAO();
    RecordDAO recordDAO = new RecordDAO();

    public List<Category> list() {
        List<Category> cs = categoryDAO.list();
        for (Category c : cs){
            List<Record> rs = recordDAO.list(c.getId());
            if (null != rs) {
                c.recordNumber = rs.size();
                System.out.println("有" + c.recordNumber);
            }
            else {
                c.recordNumber = 0;
            }
        }
        Collections.sort(cs, (c1, c2) -> c2.recordNumber - c1.recordNumber);
        return cs;
    }

    public void add(String name){
        Category c = new Category();
        c.setName(name);
        categoryDAO.add(c);
    }

    public void update(int id, String name){
        Category c = new Category();
        c.setId(id);
        c.setName(name);
        categoryDAO.update(c);
    }

    public void delete(int id){
        categoryDAO.delete(id);
    }

    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();
        RecordDAO recordDAO = new RecordDAO();
        List<Category> cs = categoryDAO.list();
        for (Category c : cs) {
            List<Record> rs = recordDAO.list(c.getId());
            System.out.println(rs.size());
        }
    }
}
