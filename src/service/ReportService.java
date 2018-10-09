package service;

import dao.RecordDAO;
import entity.Record;
import util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportService {

    /**
     * 获取某一天的消费金额
     * @param d
     * @param monthRawData
     * @return
     */
    public int getDaySpend(Date d, List<Record> monthRawData){
        int daySpend = 0;
        for (Record record : monthRawData) {
            if (record.getDate().equals(d))
                daySpend += record.getSpend();
        }
        return daySpend;
    }

    /**
     * 获取一个月的消费记录集合
     * @return
     */
    public List<Record> listThisMonthRecords() {
        RecordDAO recordDAO = new RecordDAO();
        List<Record> result = new ArrayList<>();
        List<Record> monthRawData = recordDAO.listThisMonth();
        Date begin = DateUtil.monthBegin();
        int today = DateUtil.thisMonthTotalDay();
        Calendar calendar = Calendar.getInstance();
        for (int i=0; i<today; i++) {
            Record record = new Record();
            calendar.setTime(begin);
            calendar.add(Calendar.DATE, i);
            Date eachDayOfThisMonth = calendar.getTime();
            int daySpend = getDaySpend(eachDayOfThisMonth, monthRawData);
            record.setSpend(daySpend);
            result.add(record);
        }
        return result;
    }
}
