package dao;

import entity.Record;
import util.DBUtil;
import util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordDAO {

    public int getTotal(){
        int total = 0;
        try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
            String sql = "select count(*) from record";
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()){
                total = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }

    public void add(Record record){
        String sql = "insert into record values(null, ?, ?, ?, ?)";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, record.getSpend());
            ps.setInt(2, record.getCid());
            ps.setString(3, record.getComment());
            ps.setDate(4, DateUtil.util2sql(record.getDate()));
            ps.execute();
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()){
//                int id  = rs.getInt("id");
//                record.setId(id);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Record record){
        String sql = "update into record set spend=?,cid=?,comment=?,date=? where id = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, record.getSpend());
            ps.setInt(2, record.getCid());
            ps.setString(3, record.getComment());
            ps.setDate(4, DateUtil.util2sql(record.getDate()));
            ps.setInt(5, record.getId());
            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int id){
        String sql = "delete form record where id = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Record get(int id){
        Record record = null;
        String sql = "select * from record where id = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                record = new Record();
                record.setId(id);
                record.setSpend(rs.getInt("spend"));
                record.setCid(rs.getInt("cid"));
                record.setComment(rs.getString("comment"));
                record.setDate(rs.getDate("date"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return record;
    }

    public List<Record> list(int start, int count){
        List<Record> records = null;
        String sql = "select * from record limit ?,?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                records = new ArrayList<>();
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setSpend(rs.getInt("spend"));
                record.setCid(rs.getInt("cid"));
                record.setComment(rs.getString("comment"));
                record.setDate(rs.getDate("date"));
                records.add(record);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  records;
    }

    public List<Record> listAll(){
        return list(0,Short.MAX_VALUE);
    }

    public List<Record> list(int cid){
        List<Record> records = new ArrayList<>();
        String sql = "select * from record where cid = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setSpend(rs.getInt("spend"));
                record.setCid(rs.getInt("cid"));
                record.setComment(rs.getString("comment"));
                record.setDate(rs.getDate("date"));
                records.add(record);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  records;
    }

    public List<Record> list(Date date){
        List<Record> records = new ArrayList<>();
        String sql = "select * from record where date = ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setDate(1, DateUtil.util2sql(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setSpend(rs.getInt("spend"));
                record.setCid(rs.getInt("cid"));
                record.setComment(rs.getString("comment"));
                record.setDate(rs.getDate("date"));
                records.add(record);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return records;
    }

    public List<Record> listToday(){
        return list(DateUtil.today());
    }

    public List<Record> list(Date start, Date end){
        List<Record> records = new ArrayList<>();
        String sql = "select * from record where date >= ? and date <= ?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setDate(1, DateUtil.util2sql(start));
            ps.setDate(2, DateUtil.util2sql(end));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Record record = new Record();
                record.setId(rs.getInt("id"));
                record.setSpend(rs.getInt("spend"));
                record.setCid(rs.getInt("cid"));
                record.setComment(rs.getString("comment"));
                record.setDate(rs.getDate("date"));
                records.add(record);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return records;
    }

    public List<Record> listThisMonth(){
        return list(DateUtil.monthBegin(), DateUtil.monthEnd());
    }

}
