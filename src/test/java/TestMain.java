import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: jzy
 * @Date: 2023/09/27/23:26
 * @Description:
 */
public class TestMain {
    /**
     * 测试生成表达式函数
     */
    @Test
    public void testexpression(){
        int r=10;
        for (int i = 0; i < 3; i++) {
            String expression = Main.expression(r);
            System.out.println(expression);
        }

    }
    /**
     * 测试逆波兰计算结果函数
     */
    @Test
    public void testbolan(){
        String expression="(8'9/10+5'9/10)+4-2=";
        String bolan = Main.bolan(expression);
        System.out.println(bolan);
    }

    @Test
    public void testjisuan(){
        String i="8";
        String y="5";
        String opeartion="-";
        String jisuan = Main.jisuan(i, y, opeartion);
        System.out.println(jisuan);
    }

    /**
     * 测试一个真分数和整数的计算函数
     */
    @Test
    public void testjisuan1(){
        String i="8'2/3";
        String y="5";
        String opeartion="-";
        String jisuan = Main.jisuan(i, y, opeartion);
        System.out.println(jisuan);
    }

    /**
     * 测试两个真分数的计算函数
     */
    @Test
    public void testjisuan2(){
        String i="8'2/3";
        String y="5'1/2";
        String opeartion="-";
        String jisuan = Main.jisuan(i, y, opeartion);
        System.out.println(jisuan);
    }

    /**
     * 测试生成比较文件函数
     */
    @Test
    public void testcreatGraFile(){
        String exerciseFile="C:\\Users\\17680\\Desktop\\Answer.txt";
        String answerFile="C:\\Users\\17680\\Desktop\\Ans.txt";
        Main.creatGraFile(exerciseFile,answerFile);
    }

    /**
     * 测试化简函数
     */
    @Test
    public void testhuajian(){
        int integer=0;
        int num1=10;
        int num2=6;
        String huajian = Main.huajian(integer, num1, num2);
        System.out.println(huajian);
    }

    /**
     * 测试约分函数
     */
    @Test
    public void yuefen(){
        int num1=10;
        int num2=6;
        int yuefen = Main.yuefen(num1, num2);
        System.out.println(yuefen);
    }

    @Test
    public void testifRepeat(){
        String expression="3+2+1";
        List<String> result=new ArrayList<>();
        result.add("3+2+1");
        boolean b = Main.ifRepeat(expression, result);
        System.out.println(b);
    }

}
