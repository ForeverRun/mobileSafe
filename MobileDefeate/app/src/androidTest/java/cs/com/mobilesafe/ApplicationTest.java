package cs.com.mobilesafe;

import android.content.Context;
import android.test.AndroidTestCase;

import java.util.List;
import java.util.Random;

import cs.com.mobilesafe.Bean.BlackNumberInfo;
import cs.com.mobilesafe.db.dao.BlackNumberDao;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {

    private Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getContext();
    }
    public void testAdd(){
        BlackNumberDao numberDao = new BlackNumberDao(context);
        Random random = new Random();
        for (int i = 0;i < 200;i++){
            Long number = 13022030302l + i;
            numberDao.add(number+"",String.valueOf(random.nextInt(3) + 1));
        }
    }
    public void testFind(){
        BlackNumberDao dao = new BlackNumberDao(context);
        String number = dao.findNumber("13300000004");
        System.out.println(number);
    }

    public void testFindAll(){
        BlackNumberDao dao = new BlackNumberDao(context);
        List<BlackNumberInfo> blackNumberInfos = dao.findAll();
        for (BlackNumberInfo blackNumberInfo : blackNumberInfos) {
            System.out.println(blackNumberInfo.getMode() + "" + blackNumberInfo.getNumber());
        }
    }
}