package dao;

import entity.Config;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConfigDAO {
    public int getTotal(){
        int total = 0;
        try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
            String sql = "select count(*) from config";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()){
                total = rs.getInt(1);
            }
            System.out.println("total:" + total);
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }

    public void add(Config config){
        String sql = "insert into config values(null,?,?)";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setString(1, config.getKey());
            ps.setString(2, config.getValue());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                int id = rs.getInt(1);
                config.setId(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int id){
        String sql = "delete from config where id = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, id);
            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Config config){
        String sql = "update config set key_=?, value=? where id = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setString(1, config.getKey());
            ps.setString(2, config.getValue());
            ps.setInt(3,config.getId());
            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Config get(int id){
        Config config = null;
        String sql = "select * from config where id = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                config = new Config();
                config.setId(id);
                config.setKey(rs.getString("key_"));
                config.setValue(rs.getString("value"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return config;
    }

    public List<Config> list(int start, int count){
        List<Config> cs = null;
        String sql = "select * from config limit ?,?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cs = new ArrayList<>();
                Config config = new Config();
                config.setId(rs.getInt("id"));
                config.setKey(rs.getString("key_"));
                config.setValue(rs.getString("value"));
                cs.add(config);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return cs;
    }

    public List<Config> list(){
        return list(0, Short.MAX_VALUE);
    }

    public Config getByKey(String key){
        Config config = null;
        String sql = "select * from config where key_ = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                config = new Config();
                config.setId(rs.getInt("id"));
                config.setKey(key);
                config.setValue(rs.getString("value"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return config;
    }

    public static void main(String[] args) {
        System.out.println(Short.MAX_VALUE);
    }
}
