package com.nbteam;
import com.nbteam.dao.UserDao;
import com.nbteam.entity.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.nbteam.dao.impl.UserDaoImpl;
import static com.nbteam.MiniGames.*;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class LoLoVenture {

    //当前玩家用户id
    public static String id;
    //当前玩家用户密码
    public static String password;
    //当前玩家的分数
    public static int score=100;
    
    //玩家数组，用于登录注册，和排行榜
    public static User[] users ;




    // -------------------- view --------------------

    /*
    负责人：王涛
    功能:
            展示选项, 玩家可以在这里选择进入游戏, 排行榜或退出游戏
            开始游戏: 调用游戏界面函数gameView();
            排行榜: 调用排行榜函数
            退出游戏: 调用exit(0);用户输入0并回车，关闭程序

            菜单界面就包含登录界面，这是一开始打开看到的
            登录（菜单）界面
            1.开始游戏---输入1后会先进入登录页面
            2.排行榜
            3.退出游戏

    参数：void
    返回值：void
    */
    public static void menuView() {

        while (true) {
            System.out.println("----------------洛洛历险记------------------");
            System.out.println("\033[38;5;7m┌────────────────────────────┐\033[m");
            System.out.println("\033[38;5;7m│ \033[38;5;7m \033[38;5;24m 1 \033[38;5;7m.\033[38;5;24m 开始游戏             \033[m");
            System.out.println("\033[38;5;7m│ \033[38;5;7m \033[38;5;24m 2 \033[38;5;7m.\033[38;5;24m 排行榜               　 \033[m");
            System.out.println("\033[38;5;7m│ \033[38;5;7m \033[38;5;24m 3 \033[38;5;7m.\033[38;5;24m 退出游戏             \033[m");
            System.out.println("\033[38;5;7m└────────────────────────────┘\033[m");

            // 提示用户输入
            System.out.print("\033[38;5;5m请选择选项（1-3）: \033[m");
            int choose;
            Scanner sc = new Scanner(System.in);
            choose = sc.nextInt();
            switch (choose) {
                case 1:
                    choiceLoginRegisterView();
                    break;
                case 2:
                    RankingView();
                    break;
                case 3:
                    exit(0);
                default:
                    System.out.println("输入错误,请重新输入");
                    break;
            }
        }
    }

    /*
    负责人：钱风
    功能:
        开始游戏的时候，展示登录与注册的选择界面
        1.登录 调用loginView进入登录界面
        2.注册 调用registerView进入注册界面
        3.返回菜单
        请选择：（输入前方数字）
        添加一个接受选择的数据，然后进入1或2界面
        玩家第一次进入应该是先注册，在后台有了存储注册的数据后就可以进行登录了

    参数：void
    返回值：void
    */
    public static void choiceLoginRegisterView() {
        String filePath = "userInformationC.txt";
        File file = new File(filePath);
        if (file.exists()==false)
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        Scanner sc = new Scanner(System.in);
        System.out.println("1.登录");
        System.out.println("2.注册");
        System.out.println("3.退出");
        System.out.println("请输入选项前的数字选择：");
        int n =sc.nextInt();
        switch (n){
            case 1:
                loginView();
                break;
            case 2:
                registerView();
                break;
            case 3:
                menuView();
                break;
        }
    }

    /*
    负责人：钱风
    功能:
        展示登录界面，将用户输入的用户id和密码设置到静态变量
        提示用户输入用户id和密码，密码错误就提示密码错误请重新输入
        登录成功后，开始游戏进入游戏
        参数: 静态变量：id，password
        返回值: void
     */
    public static void loginView() {
        //if(loginService(id,password)) ...
        Scanner scanner = new Scanner(System.in);
        System.out.println("登录");
        System.out.println("请输入用户名：");
        id = scanner.next();
        System.out.println("请输入密码：");
        String password = scanner.next();
        if (loginService(id,password)){
            System.out.println("登录成功");
            gameView();
        }else {
            System.out.println("登录失败，请重新选择");
            choiceLoginRegisterView();
        }
    }

    /*
    负责人：钱风
    功能:
        展示注册界面
        1.注册成功返回 登录界面/登录与注册的选择界面choiceLoginRegisterView
        2.注册失败，提示重新输入
          参数: void
          返回值: void
    */
    public static void registerView() {
        //if(loginService(id,password)) ...
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        id = scanner.next();
        System.out.println("请输入密码：");
        password = scanner.next();
        User user = new User(id, password);
        int re = registerService(user);
        if (re==0){
            System.out.println("请重新注册");
            registerView();
        } else if (re==2) {
            System.out.println("用户名已存在，请继续注册");
            registerView();
        } else {
            System.out.println("注册成功，请继续登录");
            loginView();
        }
    }

    /*
    负责人：伍富鸿
    功能:
      展示游戏的投色子界面
      登录成功自动调用到这里,
      显示以下内容
      当前游戏进度(P:玩家位置，B:Boss位置)：
      路线（地图）OPO0000000000BO0，只能从左到右
                  string maps[]
      宇宙能量：   int score
      剩余投骰子次数： int times 限制为8个是投色子次数
                   玩家通过掷骰子决定进入相应关卡

      等待玩家输入T，输入后调用掷色子函数，返回这个色子的值
        int score 宇宙能量值
        String maps[] 初始化线性地图
        int times 投色子的次数
    参数：void
    返回值：void
    */
    public static void gameView() {
        //洛洛被一个名为“NBT”的神秘组织选中，获得了八个能够穿梭宇宙的骰子和一本“荒诞宇宙指南”。
        System.out.println("-----------------------------------游戏背景-----------------------------------");
        System.out.println("洛洛被一个名为“NBT”的神秘组织选中，获得了八个能够穿梭宇宙的骰子和一本“荒诞宇宙指南”。");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 洛洛发现“混沌虚空”正在侵蚀各个宇宙，只有收集足够的“宇宙初始能量”才能阻止这一灾难。
        System.out.println("洛洛发现“混沌虚空”正在侵蚀各个宇宙，只有收集足够的“宇宙初始能量”才能阻止这一灾难。");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 洛洛穿梭于不同的宇宙，每个宇宙都有其独特的物理法则和生物，玩家需要解决各种问题，收集“宇宙初始能量”。
        System.out.println("洛洛穿梭于不同的宇宙，每个宇宙都有其独特的物理法则和生物，玩家需要解决各种问题，收集“宇宙初始能量”。");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 在收集了足够的“宇宙初始能量”后，洛洛（玩家）发现“混沌虚空”的始作俑者是一个叫做“东东哥”的宇宙霸主，他因为过于自恋而引发了这场灾难。
        System.out.println("在收集了足够的“宇宙初始能量”后，洛洛（玩家）发现“混沌虚空”的始作俑者是一个叫做“东东哥”的宇宙霸主，他因为过于自恋而引发了这场灾难。");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 洛洛利用收集到的“宇宙初始能量”来阻止东东哥毁灭宇宙你将操控洛洛拯救宇宙
        System.out.println("洛洛利用收集到的“宇宙初始能量”来阻止东东哥毁灭宇宙。");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("你将操控洛洛拯救宇宙，请通过骰子的指引完成小游戏来收集至少150宇宙能量才能打败东东哥");
        try {        //延时1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] maps = {"O", "P", "O", "0", "0", "0", "0", "0", "0", "0", "B", "O", "0"};
        int times = 8;

        Scanner scanner = new Scanner(System.in);

        while (times > 0) {
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println();
            // 显示当前游戏进度和玩家信息
            System.out.println("当前游戏进度(P:玩家位置，B:Boss位置)：");
            for (String map : maps) {
                System.out.print(map);
            }
            System.out.println("\n宇宙能量：" + score);
            System.out.println("剩余投骰子次数：" + times);

            // 等待玩家输入 'T'
            System.out.println("等待玩家输入T：");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("T")) {
                // 调用掷色子函数
                int diceValue = diceService(); // 值类型不同
                System.out.println("骰子的值为：" + diceValue);
                System.out.println();
                System.out.println("----------------------------------------------------");
                System.out.println();
                if (diceValue == 1) guessNumber();
                else if (diceValue == 2) guessSize();
                else if (diceValue == 3) joseph();
                else if (diceValue == 4) RockPaperScissors();
                else if (diceValue == 5) guessGames();
                else if (diceValue == 6) mathTitle();
                else if (diceValue == 7) trap();

                // 更新玩家位置，每次小游戏结束后前进一步
                updatePlayerPosition(maps);

                // 更新投骰子次数
                times--;
            } else {
                System.out.println("无效输入，请输入T掷骰子。");
            }
        }

        isWinService();
    }
    public static void updatePlayerPosition(String[] maps) {
        int playerPosition = -1;

        // 找到玩家当前位置
        for (int i = 0; i < maps.length; i++) {
            if (maps[i].equals("P")) {
                playerPosition = i;
                break;
            }
        }

        // 清除玩家当前位置
        if (playerPosition != -1) {
            maps[playerPosition] = "0";
        }

        // 计算新位置，每次只前进一步
        int newPosition = playerPosition + 1;
        if (newPosition >= maps.length) {
            newPosition = maps.length - 1; // 防止越过boss位置
        }

        // 更新玩家新位置
        maps[newPosition] = "P";
    }
    public static void updatePlayerPosition(String[] maps, int steps) {
        int playerPosition = -1;

        // 找到玩家当前位置
        for (int i = 0; i < maps.length; i++) {
            if (maps[i].equals("P")) {
                playerPosition = i;
                break;
            }
        }

        // 清除玩家当前位置
        if (playerPosition != -1) {
            maps[playerPosition] = "0";
        }

        // 计算新位置
        int newPosition = playerPosition + steps;
        if (newPosition >= maps.length) {
            newPosition = maps.length - 1; // 防止越过boss位置
        }

        // 更新玩家新位置
        maps[newPosition] = "P";
    }

    /*
    负责人：青浩洋
    功能:
    展示排行榜
        对users的maxScore进行从大到小排序，取前五名玩家展示得分记录以及排名
        用户id：id 得分：int energy 排名：int ranking

      询问玩家是否确认退出游戏[1/ 0]?
      输入1：则回到主菜单
      输入0并回车：则返回游戏菜单

    参数：void
    返回值：void
    */
    public static void RankingView() {
        Scanner scan = new Scanner(System.in);
        String filePath = "rank.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        class UserScore {
            String id;
            int score;

            UserScore(String id, int score) {
                this.id = id;
                this.score = score;
            }

            @Override
            public String toString() {
                return id + "," + score;
            }
        }
        // 读取文件内容到List中
        List<UserScore> scoreList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                UserScore userScore = new UserScore(parts[0], Integer.parseInt(parts[1]));
                scoreList.add(userScore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 按分数排序
        Collections.sort(scoreList, new Comparator<UserScore>() {
            @Override
            public int compare(UserScore o1, UserScore o2) {
                return Integer.compare(o2.score, o1.score); // 降序排序
            }
        });

        // 输出排序后的信息
        System.out.println("-----ID,分数--------排行榜前五名----------按分数从高到低排行-");
        int i=1;
        for (UserScore userScore : scoreList) {
            if(i==6)break;
            System.out.print("第"+i+"名:");i++;
            System.out.println(userScore);
        }
        System.out.println("--------------输入1返回主菜单，输入0退出游戏-----------------");
        while(true)
        {
            int x=scan.nextInt();
            if(x==0)
            {
                try {
                    System.out.println("欢迎下次游玩哦");
                    sleep(1000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(x==1)
            {
                menuView();
                break;
            }
            else {
                System.out.println("输入错误，请重新输入，输入1返回主菜单，输入0退出游戏");
            }
        }
    }

    /*
    负责人：蓝惠芬
    功能：
      设计一个好看的胜利界面
      参数: void
      返回值: void
    */
    public static void winView() {
        // 在此处完成代码
        delayedPrint("随着一声无形的宇宙共鸣，洛洛将那股");
        delayedPrint("磅礴的能量化作一道璀璨的光束，穿越");
        delayedPrint("了时空的界限，精准地击中了东东哥的");
        delayedPrint("核心。在这场关乎宇宙命运的对决中，");
        delayedPrint("东东哥的力量被彻底瓦解，他的威胁烟");
        delayedPrint("消云散，宇宙因此重归和谐与宁静.");
        System.out.println("现在你可以选择 输入1 回到菜单  其他输入 退出游戏");
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if(number == 1){
            menuView();
        }else{
            exit(0);
        }
    }

    /*
    负责人：蓝惠芬
    功能：
          设计一个好看的失败界面
          参数: void
          返回值: void
    */
    public static void failView() {
        // 在此处完成代码
        delayedPrint("在这场关乎存亡的较量中，洛洛最终未能逆转");
        delayedPrint("乾坤，他的失败如同宇宙中的一颗超新星爆发");
        delayedPrint("虽然短暂却震撼人心。即使在失利之际，洛洛");
        delayedPrint("的眼中依旧闪烁着不屈的光芒，他的故事将成");
        delayedPrint("为一段传奇，激励着后来者在宇宙的长河中继");
        delayedPrint("续前行,寻找新的希望与可能。");
        System.out.println("现在你可以选择 输入1 回到菜单  其他输入 退出游戏");
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        if(number == 1){
            menuView();
        }else{
            exit(0);
        }
    }

    // -------------------- service --------------------


    /*
        负责人：青浩洋
        功能: 一局游戏结束后，将当前用户的信息，也就是包括当前游戏的分数添加到users中，
        并且更新到文件中。注意：打破当前用户记录分数才设置当前分数
        参数: void
        返回值: void
    */
    public static void addUserInfoService() {
        String filePath = "rank.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        class UserScore {
            String id;
            int score;

            UserScore(String id, int score) {
                this.id = id;
                this.score = score;
            }

            @Override
            public String toString() {
                return id + "," + score;
            }
        }

        // 读取文件内容到List中
        List<UserScore> scoreList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                UserScore userScore = new UserScore(parts[0], Integer.parseInt(parts[1]));
                scoreList.add(userScore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 添加新的ID和分数（如果需要）
        if (scoreList.stream().anyMatch(userScore -> userScore.id.equals(id))) {
            Optional<UserScore> existingUserScore = scoreList.stream()
                    .filter(userScore -> userScore.id.equals(id))
                    .findFirst();
            if (existingUserScore.isPresent() && existingUserScore.get().score >= score) {
                // 不需要更新，保持不变
            } else {
                // 更新分数
                scoreList.removeIf(userScore -> userScore.id.equals(id));
                scoreList.add(new UserScore(id, score));
            }
        } else {
            // 添加新的ID和分数
            scoreList.add(new UserScore(id, score));
        }

        // 按分数排序
        Collections.sort(scoreList, new Comparator<UserScore>() {
            @Override
            public int compare(UserScore o1, UserScore o2) {
                return Integer.compare(o2.score, o1.score); // 降序排序
            }
        });

        // 将更新后的列表写回到文件
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath))) {
            for (UserScore userScore : scoreList) {
                bw.write(userScore.toString());
                bw.newLine(); // 写入换行符
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    负责人：钱风
    功能: 遍历users 匹配id和password是否一致
    参数: String id, String password
    返回值: true 登录成功 false登录失败
     */
    public static boolean loginService(String id, String password) {
        UserDao ud = new UserDaoImpl();
        return ud.isLoginUser(id,password);
    }
    /*
    负责人：钱风
    功能: 将用户名和密码存到users
    参数: String id, String password
    返回值: 0 注册失败  1注册成功 2 账号已经存在
     */
    public static int registerService(User user){
        UserDao ud = new UserDaoImpl();
        return ud.registerUser(user);
    }

    // 投掷色子
    /*
    负责人：蓝惠芬
    功能
        等待玩家输入T，输入T后，调用这里，输入0则退出程序
        生成随机数1-8 进入相对应的游戏

    参数：void
    返回值：void
    */
    public static int diceService() {
        Random r = new Random();
        int dice = r.nextInt(7)+1;
        return dice;
    }

    // 最终结局
    /*
    负责人：蓝惠芬
    功能:
        当骰子投完自动进入结局界面
        根据score得分，比较与boss的分数
        实现判断胜利与否
        显示玩家当前游戏结果
    参数：
          int boss boss的得分
          int score 得分
          int times  用来判断掷色子的次数的剩余
    返回值：
            1胜利，0失败，分别调用进入不同的界面
    */
    public static void isWinService() {
        // 在此处完成代码
        addUserInfoService();
        if(score > 150){
            winView();
        }else{
            failView();
        }
    }


    // -------------------- service --------------------

}
