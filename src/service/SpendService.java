package service;

import com.sun.org.apache.regexp.internal.RE;
import dao.RecordDAO;
import entity.Record;
import gui.page.SpendPage;
import util.DateUtil;

import java.util.List;

public class SpendService {
    public SpendPage getSpendPage() {
        RecordDAO recordDAO = new RecordDAO();
        //本月记录
        List<Record> thisMonthRecords = recordDAO.listThisMonth();
        //今日记录
        List<Record> todayRecords = recordDAO.listToday();
        //本月天数
        int thisMonthTotalDay = DateUtil.thisMonthTotalDay();

        int monthSpend = 0;
        int todaySpend = 0;
        int avgSpendPerDay = 0;
        int monthAvailable = 0;
        int dayAvgAvailable = 0;
        int monthLeftDay = 0;
        int usagePercentage = 0;

        //预算
        int monthBudget = new ConfigService().getBudget();

        //统计本月消费
        for (Record record : thisMonthRecords) {
            monthSpend += record.getSpend();
        }

        //统计今日消费
        for (Record record : todayRecords) {
            todaySpend += record.getSpend();
        }

        //计算日均消费
        avgSpendPerDay = monthSpend / thisMonthTotalDay;

        //计算本月可用
        monthAvailable = monthBudget - monthSpend;

        //距离月末
        monthLeftDay = DateUtil.thisMonthLeftDay();

        //计算日均可用
        dayAvgAvailable = monthAvailable / monthLeftDay;

        //计算比例
        usagePercentage = monthSpend * 100 / monthBudget;

        return new SpendPage(monthSpend, todaySpend, avgSpendPerDay, monthAvailable, dayAvgAvailable, monthLeftDay,
                usagePercentage);
    }
}
