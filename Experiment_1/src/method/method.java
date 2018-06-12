package method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class method {//展示当前容器中的进程信息，响应输入
    public abstract void show();
    public void goon(){
        System.out.println("请按回车键继续执行...");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String str = in.readLine();
            switch (str){
                default:return;
            }
        }catch (NumberFormatException e){
            System.out.println("异常退出");
            System.exit(0);
        }catch (IOException ie){
            System.out.println("异常退出");
            System.exit(0);
        }
    }
}
