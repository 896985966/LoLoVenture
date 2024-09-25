package com.nbteam;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static com.nbteam.LoLoVenture.gameView;
import static com.nbteam.LoLoVenture.score;

public class MiniGames {


    // 猜数字
    // 当骰子数为1时进入该游戏
    /*
    负责人：李尚哲
    功能
        后台生成在一定范围的随机数字

        玩家通过键盘输入猜测的数字与计算机随机生成的数字进行对比
        玩家输入后显示结果并输入Y进入下一个回合（继续投骰子）

        1-3随机难度
        分值：20 30 50
        系统根据难度分别生成1-9,1-99,1-999数字给玩家猜，猜错有提示大了还是小了，提示次数依次为2,4,6

        根据游戏情况，给当前用户score加分
    参数：void
    返回值：
    */
    public static void guessNumber() {
        delayedPrint("跟随着掷出的骰子的数字指引，你来到了标号为2的神秘星球。");
        delayedPrint("在一块神秘的田野中你发现了一篇蕴含宇宙初始能量的麦田");
        delayedPrint("你低头看到一把镰刀插在田野中央，想要释放并收集尽可能多的宇宙能量");
        delayedPrint("你需要猜测麦田所表达的数字并用手上的镰刀在麦田中将你猜测的数字割出来");
        delayedPrint("注意，你的机会是有限的");
        delayedPrint("请注意你只有两次机会");
        int random = (int)(Math.random() * 10);
        int i=2;
        while(i>0){
            System.out.println("输入您猜测的数字(范围是1-10）：");
            Scanner mySca = new Scanner(System.in);
            int num = mySca.nextInt();
            if(num > random){
                i--;
                System.out.println("你猜错了，目标数字比你猜测的小");
            } else if(num < random){
                i--;
                System.out.println("你猜错了，目标数字比你猜测的大");
            } else {
                System.out.println("恭喜你猜对了，获得宇宙能量");
                break;
            }
        }
        System.out.println("随后你离开了神秘星球");

    }

   // 猜大小
    // 当骰子数为2时进入该游戏
    /*
    负责人：王涛
    功能
        键盘输入大/小

        玩家通过键盘输入大或者小与电脑随机生成数字所在规定区间进行对比
        玩家输入后显示结果并输入Y进入下一个回合（继续投骰子）

        固定难度
        分值35
        系统随机生成两个1到5的数字，若相加为6-10则为大若为1-5则为小，玩家输入大小判断输赢

        根据游戏情况，给当前用户score加分
    参数：void

    返回值：void
    */
   public static void guessSize() {
       delayedPrint("你穿越到了葫芦星球。");
       delayedPrint("这个星球是由一个大葫芦跟一个小葫芦组成的。");
       delayedPrint("在其中的一块葫芦里，有我们需要的宇宙初始能量。");
       delayedPrint("你需要选择其中的一个葫芦探索。如果选择对将会获得能量，否则不会获得。");
       Scanner scanner = new Scanner(System.in);

       Random rand = new Random();
       // 生成一个1（包括）到10（包括）之间的随机整数
       int randomInt = rand.nextInt(10) + 1;
       //玩家输入大或者小
       System.out.println("请输入大或者小");
       String choose = scanner.nextLine();
       String Radius;
       //定义一个范围(1-5为小，6-10为大)
       if (randomInt >= 1 && randomInt <= 5 )
           Radius = "小";
       else
           Radius = "大";

       //测试玩家输入是否与范围匹配
       //匹配成功
       if (choose == Radius) {
           System.out.println("恭喜，你选对了，你将获得宇宙初始能量");
           int score = LoLoVenture.score;
           score += 35;
       }
       //匹配失败
       else{
           System.out.println("抱歉，你猜错了，你不会获得宇宙初始能量");
       }
   }

    // 犹余游戏（约瑟夫）
    // 当骰子数为3时进入该游戏
    /*
    负责人：童鹏飞
    功能
        玩家通过键盘输入数字进入对应的座位
        玩家输入后显示结果并输入Y进入下一个回合（继续投骰子）
        通过键盘输入座位数

          int diceService_number 色子数
          int input; 读取输入的数字或者输入字母是否为Y？？？
          int Seat 座位号

          根据游戏情况，给当前用户score加分
    参数：void
    返回值：void
    */
    public static void joseph() {
        Scanner scanner = new Scanner(System.in);
        delayedPrint("在骰子的力量下，");
        delayedPrint("你来到了一个名为嘟嘟王国的星球，");
        delayedPrint("这里的大魔王嘟嘟可设立了一个登陆游戏：");
        delayedPrint("给你n个蹦蹦炸弹，蹦蹦炸弹们围成一个圈从第一个蹦蹦炸弹开始计数，");
        delayedPrint("计到m的的蹦蹦炸弹将会爆炸,下一个蹦蹦炸弹又从1开始计数，");
        delayedPrint("直到场上剩下最后一个蹦蹦炸弹游戏才结束。");
        delayedPrint("下面给出n和m的值，请输入你最开始选择的蹦蹦炸弹编号吧！");
        Random random = new Random();
        int n = random.nextInt(10) + 5; // 生成 5 到 14 之间的随机数作为人数
        int m = random.nextInt(5) + 1; // 生成 1 到 5 之间的随机数作为起始报数的数字
        System.out.println("n="+n+" "+"m="+ m + "。");
        int result = josephus(n, m);
        delayedPrint("请输入最后留下的蹦蹦炸弹的编号（从1开始编号）：");
        int answer = scanner.nextInt();

        if (answer == result) {
            delayedPrint("恭喜你，通过了嘟嘟大魔王的考验！");
            score+=66;
        } else {
            delayedPrint("很遗憾，您被驱逐出了嘟嘟星球。正确答案是：" + result);
        }
    }
    public static int josephus(int n, int m) {
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = (result + m) % i;
        }
        return result + 1;
    }
    public static void delayedPrint(String text) {
            System.out.print(text);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println();
    }
    // 剪刀石头布
    // 当骰子数为4时进入该游戏
    /*
    负责人:方为嘉
    功能
      玩家通过键盘输入string剪刀/石头/布与电脑随机生成的手势进行对比,根据玩家胜利的情况给玩家加分
      玩家输入后显示结果并输入Y进入下一个回合（继续投骰子）
      固定难度三局两胜
          String shoushi 描述手势
          int gametimes  游戏次数
          int computer/player 电脑与玩家分别胜利次数

       根据游戏情况，给当前用户score加分
    参数: void
    返回值:
    */
    public static void RockPaperScissors(){
        System.out.println("骰子数为7");
        System.out.println("跟随着掷出的骰子的数字指引，这里是被诅咒的镜子星球，在你面前有个巨大的镜子，和许多小镜子谁也不知道镜子另一边有什么魔法，只是有个传说，能与镜子猜拳三局两胜的，将获得宇宙能量值，当你对镜子选择出剪刀石头布中的任何中一个时，你会在镜子中看到回应，请注意这不是一般的镜子，快来挑战吧");


        Scanner scanner = new Scanner(System.in);
        Random random = new Random();//使用一个
        String[] shoushi = {"石头", "剪刀", "布"};
        int playerScore = 0;
        int computerScore = 0;
        //这两个局部变量的含义是胜利局数，不是宇宙能量值的得分

        //三局两胜的话，无论是电脑还是玩家胜利局数小于二就继续循环
        while (playerScore < 2 && computerScore < 2) {
            System.out.println("请输入您的选择（石头/剪刀/布，要与描述的输入一致哦）:");
            String playerChoice = scanner.nextLine();
            String computerChoice = shoushi[random.nextInt(shoushi.length)];
            System.out.println("电脑的选择是：" + computerChoice);

            //游戏规则
            if (playerChoice.equals(computerChoice)) {
                System.out.println("平局！");
            } else if ((playerChoice.equals("石头") && computerChoice.equals("剪刀")) ||
                    (playerChoice.equals("剪刀") && computerChoice.equals("布")) ||
                    (playerChoice.equals("布") && computerChoice.equals("石头"))) {
                System.out.println("玩家胜利！");
                playerScore++;
            } else {
                System.out.println("电脑胜利！");
                computerScore++;
            }

            System.out.println("当前比分：玩家 " + playerScore + " - " + computerScore + " 电脑");
            if (playerScore < 2 && computerScore < 2) {
                System.out.println("输入 'Y' 进入下一个回合：");
                String input = scanner.nextLine();
                while (!input.equalsIgnoreCase("Y")){
                    System.out.println("请不要尝试输入错误的，我很忙滴,没有时间给你加彩蛋，下次一定，ok?[doge]");
                    System.out.println("请输入 'Y' 进入下一个回合：");
                    input = scanner.nextLine();
                }
            }
        }

        //跳成while后，进行宇宙能量值加分
        if (playerScore >= 2) {
            System.out.println("恭喜您赢得了游戏！获得35分。");
            // 更新玩家的分数
            score += 35;
        } else if (computerScore >= 2) {
            System.out.println("很遗憾，您输了游戏。");
        }
    }

    // 猜字谜和脑筋急转弯
    // 当骰子数为5时进入该游戏
    /*
    负责人：伍富鸿
    功能
        在对应题目下输入玩家回答与答案进行比较
        分为两种题型，一种是猜字，一种是脑筋急转弯
        玩家输入后显示结果并输入Y进入下一个回合（继续投骰子）

        固定难度
        由负责该版块的同学出题，以选择题的形式，ABCD四个选项供玩家选择
        char A,B,C,D  选项字母
        char X 设置为正确答案并被赋值（正确答案为A，则X=A）
        string title 描述题目
        int score得分
        int bout_number回合数

        根据游戏情况，给当前用户score加分
     参数：void
     返回值：void
    */
    public static void guessGames() {
        System.out.println("根据骰子的指引，你来到了一个陌生的星球");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("你发现了宇宙初始能量的踪迹，但是他们被封印在一个神秘的阵法中");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("想要获得宇宙初始能量就必须解开阵法");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("解开阵法需要根据提示解开谜语，输入答案");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("注意，你有且只有一次机会");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 题目和答案的映射
        Map<Integer, String[]> questions = new HashMap<>();
        questions.put(1, new String[]{"一点一横长，一撇漂南洋，南洋有个人，只有一寸长，猜一个字", "府"});
        questions.put(2, new String[]{"减少一个，孤独一人", "从"});
        questions.put(3, new String[]{"大雨下在横山上，打一个字", "雪"});
        questions.put(4, new String[]{"半部春秋，打一个字", "秦"});
        questions.put(5, new String[]{"人我不分", "俄"});
        questions.put(6, new String[]{"自讨苦吃的地方是哪?", "药店"});
        questions.put(7, new String[]{"草地上来了一群羊，打一种水果", "草莓"});
        questions.put(8, new String[]{"什么东西愈生气，它便愈大?", "脾气"});
        questions.put(9, new String[]{"盲人都是怎样吃桔子的?", "瞎掰"});

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int questionNumber = random.nextInt(9) + 1; // 生成1到9的随机数
        String[] question = questions.get(questionNumber);

        System.out.println("题目: " + question[0]);
        System.out.print("你的答案: ");
        String input = scanner.nextLine();

        if (input.equals(question[1])) {
            score += 35;
            System.out.println("回答正确! 得分:35 ");
        } else {
            System.out.println("回答错误! 正确答案是: " + question[1]);
        }

        System.out.println("游戏结束! 最终得分: " + score);
    }
    // 数学题
    // 当骰子数为6时进入该游戏
    /*
    负责人：康玲萍
    功能
        玩家通过键盘输入回答问题
        在对应题目下输入玩家回答与答案进行比较
        玩家输入后显示结果并输入Y进入下一个回合（继续投骰子）
        int diceService_number 色子数
        char input 玩家的输入
        char answer 正确的答案
        int score 得分

        根据游戏情况，给当前用户score加分
    参数：void
    返回值：void
    */
    public static void mathTitle() {
        delayedPrint("骰子数为6");
        delayedPrint("你来到了玄奇星球，并发现了被封印的宇宙能量，");
        delayedPrint("当地居民告诉你必须解出谜题才能够打开封印获取能量。");
        delayedPrint("谜题如下：");
        delayedPrint("鸡和兔共有40个头，116只脚，鸡和兔分别有几只？");
        Scanner myscan=new Scanner(System.in);
        delayedPrint("请输入兔子有多少只(只能输入数字，否则game over)");
        int rabbit=myscan.nextInt();
        delayedPrint("请输入鸡有多少只(只能输入数字，否则game over)");
        int chicken=myscan.nextInt();
        int answerRabbit=18;
        int answerChicken=22;
        if(rabbit==answerRabbit&&chicken==answerChicken){
            System.out.println("恭喜你解除谜题，获得宇宙能量35");
            score+=35;
        }else {
            System.out.println("回答错误，将被驱逐出玄奇星球");
        }
    }

    // 陷进
    // 当骰子数为7时进入虫洞
    /*
    负责人：蓝惠芬
    功能
          减少玩家分数
          10-20随机值减少玩家分数
       int diceService_number 色子数
       int Reduce 减少的得分
       int score 得分

      根据游戏情况，给当前用户score加分
     参数： void
     返回值：void
    */
    public static void trap() {
        // 在此处完成代码
        delayedPrint("在星际航行的征途上，洛洛意外触发了隐藏在");
        delayedPrint("虚空中的虫洞陷阱，这是一个古老的宇宙迷宫");
        delayedPrint("它以扭曲时空的方式悄然张开了吞噬之口。为");
        delayedPrint("了逃脱这场突如其来的危机，他必须调动随机");
        delayedPrint("的宇宙初始能量，激活飞船的反物质引擎，沿");
        delayedPrint("着来时的轨迹逆流而上，重返安全的星域");
        Random r = new Random();
        int reduce = r.nextInt(11)+10;
        score -= reduce;
    }
}
