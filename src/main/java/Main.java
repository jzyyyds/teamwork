


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: jzy
 * @Date: 2023/09/24/16:49
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        //程序是命令行启动，所以要从args中拿参数，但是拿的时候可能会报数组越界的错误
        //获取参数
        //生成题目的数量
        int n=0;
        //操作数的最大值
        int r=0;
        //给定的文件
        String exerciseFile=null;
        //答案文件
        String answerFile=null;
        //最后输出的文件
        String exportFile="Grade.txt";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "-n":
                    n=Integer.valueOf(args[i+1]);
                    if(n>10000||n<1){
                        System.out.println("对不起，只支持生成1-10000条计算题");
                        return;
                    }
                    i++;
                    break;
                case "-r":
                    r=Integer.valueOf(args[i+1]);
                    if(r<1){
                        System.out.println("对不起，只支持生成大于等于1的数");
                        return;
                    }
                    i++;
                    break;
                case "-e":
                    exerciseFile=args[i+1];
                    i++;
                    break;
                case "-a":
                    answerFile=args[i+1];
                    i++;
                    break;
                default:
                    System.out.println("您输入的指令有误，请重新输入");
                    break;
            }
        }
        if(exerciseFile!=null&&answerFile!=null&&exerciseFile.length()>0&&answerFile.length()>0){
            //进行对比,然后输出到文件中
            creatGraFile(exerciseFile,answerFile);
        }else if(exerciseFile==null&&answerFile==null){
            //进行表达式的生成，并且存储在文件中
            createProblem(n,r);
        }else{
            System.out.println("您的输入有误");
            System.exit(0);
        }
    }


    public static void creatGraFile(String exerciseFile,String answerFile){
        //获取题目文件的答案
        List<String> res=new ArrayList<>();
        String ansFile="C:\\Users\\17680\\Desktop\\Answer.txt";
        try(BufferedReader br= Files.newBufferedReader(Paths.get(ansFile))) {
            String line=null;
            while((line=br.readLine())!=null){
                System.out.println(line);
                if(line.equals("答案")){
                    continue;
                }else {
                    int index = line.indexOf(".");
                    res.add(line.substring(index+1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String re : res) {
            System.out.println(re);
        }
        List<String> ans=new ArrayList<>();
        try(BufferedReader br= Files.newBufferedReader(Paths.get(answerFile))) {
            String line=null;

            while((line=br.readLine())!=null){
                if(line.equals("答案")){
                    continue;
                }else {
                    int index = line.indexOf(".");
                    ans.add(line.substring(index+1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int right=0;
        int wrong=0;
        List<String> rightList=new ArrayList<>();
        List<String> wrongList=new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
            if(res.get(i).equals(ans.get(i))){
                right++;
                rightList.add(String.valueOf(i+1));
            }else{
                wrong++;
                wrongList.add(String.valueOf(i+1));
            }
        }


        File file=new File("C:\\Users\\17680\\Desktop\\Grade.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            if(file.createNewFile()){
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                PrintStream printStream=new PrintStream(file);
                StringBuilder sb=new StringBuilder();
                StringBuilder sb1=new StringBuilder();
                sb.append("Correct:"+right);
                sb.append("(");
                for (String s : rightList) {
                    sb.append(s+",");
                }
                sb.deleteCharAt(sb.toString().length()-1);
                sb.append(")");

                sb1.append("Wrong:"+wrong);
                sb1.append("(");
                for (String s : wrongList) {
                    sb1.append(s+",");
                }
                sb1.deleteCharAt(sb1.toString().length()-1);
                sb1.append(")");
                printStream.println(sb.toString());
                printStream.println(sb1.toString());
                fileOutputStream.close();
                printStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static List<String> generate(int n,int r){
        List<String> result=new ArrayList<>();
        for (int i = 0; i < n;) {
            String expression = expression(r);
            System.out.println("表达式:"+expression);
            //将表达式代入计算得到数据
            String answer = bolan(expression);
            //判断表达式是否重复
            if(ifRepeat(expression,result)||answer==null||answer.length()==0){
               continue;
            }else{
                result.add(expression);
                result.add(answer);
                i++;
            }
        }
        return result;
    }


    public static boolean ifRepeat(String expression,List<String> result){
        if(result.size()==0){
            return false;
        }else{
            for (int i = 0; i < result.size();i+=2 ) {
                if(expression.length()==result.get(i).length()){
                    //说明字符串的长度相等
                    if(expression.equals(result.get(i))){
                        return true;
                    }else{
                        for (int i1 = 0; i1 < expression.length(); i1++) {
                            if(result.get(i).contains(String.valueOf(expression.charAt(i1)))){
                                continue;
                            }else{
                                return false;
                            }
                        }
                    }
                }else{
                    continue;
                }

            }
            return false;
        }

    }


    private static void createProblem(int n,int r){
        List<String> problem=new ArrayList<>();
        List<String> answer=new ArrayList<>();
        List<String> generate = generate(n, r);
        for (int i = 0; i < generate.size(); i++) {
            if(i%2==0){
                problem.add(generate.get(i));
            }else{
                answer.add(generate.get(i));
            }
        }

        creatFile(problem);
        creatAnsFile(answer);

    }
    private static void creatAnsFile(List<String> answer){
        File file=new File("C:\\Users\\17680\\Desktop\\Answer.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            if(file.createNewFile()){
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                PrintStream printStream=new PrintStream(file);
                printStream.println("答案");
                for (int i = 0; i < answer.size(); i++) {
                    printStream.println(i+1+"."+answer.get(i));
                }
                fileOutputStream.close();
                printStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void creatFile(List<String> problem){
        File file=new File("C:\\Users\\17680\\Desktop\\Exercises.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            if(file.createNewFile()){
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                PrintStream printStream=new PrintStream(file);
                printStream.println("学号               姓名：江卓颖              成绩");
                for (int i = 0; i < problem.size(); i++) {
                    printStream.println(i+1+"."+problem.get(i));
                }
                fileOutputStream.close();
                printStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    * 生成表达式
    * */
    public static String expression(int r){
        Random random=new Random();
        String[] operation={"+","-","*","÷","="};
        String[] countOpera=new String[1+random.nextInt(3)];
        String[] countNum=new String[countOpera.length+1];
        String result=new String();
        boolean fenshu=false;

        //生成操作数集合
        for (int i = 0; i < countNum.length; i++) {
            //通过随机来产生整数还是分数
            int trueOrNot=random.nextInt(2);
            if(trueOrNot==0){
                //产生一个整数
                int number = random.nextInt(r+1);
                countNum[i]=String.valueOf(number);
            }else{
                //产生一个分数
                //分母
                int denominator=1+random.nextInt(r+1);
                //分子
                int molecule= random.nextInt(denominator);
                //随机生成一个整数，如2‘2/3这个
                int number=random.nextInt(r+1);

                if(molecule!=0){
                    //要进行最大公约数的约分，化简
                    int yuefen = yuefen(denominator, molecule);
                    denominator/=yuefen;
                    molecule/=yuefen;
                }
                if(number==0&&molecule!=0){
                    countNum[i]=molecule+"/"+denominator;
                    fenshu=true;
                }else if(molecule==0){
                    countNum[i]=String.valueOf(number);
                }else{
                    countNum[i]=number+"'"+molecule+"/"+denominator;
                    fenshu=true;
                }
            }
        }

        //生成运算符集合
        for (int i = 0; i < countOpera.length; i++) {
            if(fenshu){
                //说明有分数，生成加减即可
                countOpera[i]=operation[random.nextInt(2)];
            }else{
                countOpera[i]=operation[random.nextInt(4)];
            }
        }
        //操作数和运算符生成完毕后，就要生成式子
        int num=countNum.length;
        int fun=countOpera.length;
        if(num!=2){
            num=random.nextInt(num);
        }
        for (int i = 0; i < countNum.length; i++) {
            if(i==num&&num<fun){
                result=result+"("+countNum[i]+countOpera[i];
            }else if(i==countNum.length-1&&i==num+1&&num<fun){
                result=result+countNum[i]+")"+"=";
            }else if(i==num+1&&num<fun){
                result=result+countNum[i]+")"+countOpera[i];
            }else if(i==countNum.length-1){
                result=result+countNum[i]+"=";
            }else{
                result=result+countNum[i]+countOpera[i];
            }
        }
        return result;
    }

    public static String bolan(String result){
        Stack<String> stackNum=new Stack<>();
        Stack<String> stackOper=new Stack<>();
        HashMap<String,Integer> hashMap=new HashMap<>();
        hashMap.put("(",0);
        hashMap.put("+",1);
        hashMap.put("-",1);
        hashMap.put("*",2);
        hashMap.put("÷",2);

        for (int i = 0; i < result.length(); ) {
            StringBuilder sb=new StringBuilder();
            char c=result.charAt(i);
            while(Character.isDigit(c)||c=='/'||c=='\''){
                sb.append(c);
                i++;
                c=result.charAt(i);
            }

            if(sb.length()==0){
                //说明不是一个数,是一个运算符
                switch (c){
                    case '(':{
                        stackOper.push(String.valueOf(c));
                        break;
                    }
                    case ')':{
                        //需要将(之后的运算符全部拿出来进行计算
                        String pop = stackOper.pop();
                        while (!stackOper.isEmpty()&&!pop.equals("(")){
                            String pop1 = stackNum.pop();
                            String pop2 = stackNum.pop();
                            //进行计算
                            String res=jisuan(pop2,pop1,pop);
                            if(res==null){
                                return null;
                            }
                            stackNum.push(res);
                            pop=stackOper.pop();
                        }
                        break;
                    }

                    case '=':{
                        String operation;
                        String res=new String();
                        while(!stackOper.isEmpty()){
                            operation=stackOper.pop();
                            String pop = stackNum.pop();
                            String pop1 = stackNum.pop();
                            String jisuan = jisuan(pop1, pop, operation);
                            if(jisuan==null){
                                return null;
                            }
                            if(stackOper.isEmpty()){
                                res=jisuan;
                            }
                            stackNum.push(jisuan);

                        }
                        return res;
                        //break;
                    }
                    default:{
                        //进行优先级的比较然后计算
                        String operation;
                        while (!stackOper.isEmpty()){
                            operation=stackOper.pop();
                            //然后比较两者的优先级大小
                            if(hashMap.get(operation)>=hashMap.get(String.valueOf(c))){
                                String pop = stackNum.pop();
                                String pop1 = stackNum.pop();
                                String jisuan = jisuan(pop1, pop, operation);
                                if(jisuan==null){
                                    return null;
                                }
                                stackNum.push(jisuan);
                            }else{
                                stackOper.push(operation);
                                break;
                            }
                        }
                        stackOper.push(String.valueOf(c));
                        break;
                    }

                }
            }else{
                //是一个操作数
                stackNum.push(sb.toString());
                continue;
            }
            i++;
        }
        return stackNum.pop();
    }

    public static String jisuan(String num1,String num2,String operation){

        String result=null;
        //分数的处理
        List<Integer> list=new ArrayList<>();
        list.add(num1.indexOf('\''));
        list.add(num1.indexOf('/'));
        list.add(num2.indexOf('\''));
        list.add(num2.indexOf('/'));
        if(list.get(1)>0||list.get(3)>0){
            int[] num=new int[3];
            int[] denominator=new int[3];
            int[] molecule=new int[3];
            //处理分数的运算
            //假设第一个数字是分数的话
            if(list.get(1)>0){
                for (int i = 0; i < num1.length(); i++) {
                    if(i<list.get(0)){
                        num[0]=Integer.parseInt(num[0]+String.valueOf(num1.charAt(i)-'0'));
                    }else if(i>list.get(0)&&i<list.get(1)){
                        molecule[0]=Integer.parseInt(molecule[0]+String.valueOf(num1.charAt(i)-'0'));
                    }else if(i>list.get(1)){
                        denominator[0]=Integer.parseInt(denominator[0]+String.valueOf(num1.charAt(i)-'0'));
                    }
                }
            }else{
                num[0]=Integer.parseInt(num1);
                molecule[0]=0;
                denominator[0]=1;
            }
            //假设第二个数是个分数的话
            if(list.get(3)>0){
                for (int i = 0; i < num2.length(); i++) {
                    if(i<list.get(2)){
                        num[1]=Integer.parseInt(num[1]+String.valueOf(num2.charAt(i)-'0'));
                    }else if(i>list.get(2)&&i<list.get(3)){
                        molecule[1]=Integer.parseInt(molecule[1]+String.valueOf(num2.charAt(i)-'0'));
                    }else if(i>list.get(3)){
                        denominator[1]=Integer.parseInt(denominator[1]+String.valueOf(num2.charAt(i)-'0'));
                    }
                }
            }else{
                num[1]=Integer.parseInt(num2);
                molecule[1]=0;
                denominator[1]=1;
            }

            //进行计算
            switch (operation.charAt(0)){
                case '+':{
                    //进行通分，然后再进行化简
                    denominator[2]=denominator[0]*denominator[1];
                    molecule[2]=(num[0]*denominator[0]+molecule[0])*denominator[1]+((num[1]*denominator[1]+molecule[1])*denominator[0]);
                    break;
                }
                case '-':{
                    denominator[2]=denominator[0]*denominator[1];
                    molecule[2]=num[0] * denominator[2] + molecule[0] * denominator[1]
                            - num[1] * denominator[2] - molecule[1] * denominator[0];
                    break;
                }
                default:
                    return null;
            }
            //对最后的结果进行化简
            //最后的表达式
            if(molecule[2]>=denominator[2]&&molecule[2]>0){
                num[2]=molecule[2]/denominator[2];
                molecule[2]=Math.abs(molecule[2]%denominator[2]);
            }else if(molecule[2]<0){
                return null;
            }
            if(molecule[2]!=0){
                String huajian = huajian(num[2], molecule[2], denominator[2]);
                return huajian;
            }else{
                return String.valueOf(num[2]);
            }

        }else{
            //处理整数的运算
            int i = Integer.valueOf(num1);
            int i1 = Integer.valueOf(num2);
            switch (operation.charAt(0)){
                case '+':{
                    result=String.valueOf(i+i1);
                    break;
                }
                case '-':{
                    if(i-i1>=0) {
                        result = String.valueOf(i - i1);
                    }else {
                        return null;
                    }
                    break;
                }
                case '*':{
                    result=String.valueOf(i*i1);
                    break;
                }
                case '÷':{
                   if(i1==0){
                       return null;
                   }else if(i%i1!=0){
                       result=i%i1+"/"+i1;
                       if(i/i1>0){
                           result=i/i1+"'"+result;
                       }
                   }else {
                       result=String.valueOf(i/i1);
                   }
                   break;
                }
            }
        }
        return result;
    }

    /**
     * 计算最大公约数
     * @param num1
     * @param num2
     * @return
     */
    public static int yuefen(int num1,int num2){
        while(true)
        {
            if(num1%num2==0)return num2;
            int temp=num2;
            num2=num1%num2;
            num1=temp;
        }
    }


    public static String huajian(int integer,int num1,int num2){
        //num1是分子，num2是分母
        String result=new String();
            int yuefen = yuefen(num1,num2);
            num1/=yuefen;
            num2/=yuefen;
            if(integer==0&&num1>0){
                result=String.valueOf(num1)+'/'+String.valueOf(num2);
            }else if(integer>0&&num1>0){
                result =String.valueOf(integer)+"'"+String.valueOf(num1)+'/'+String.valueOf(num2);
            }else if(num1==0){
                result=String.valueOf(integer);
            }
            return result;
    }
}
