import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class system extends T0status {
    private int[] source;
    private int[][] allocation;
    private int[][] need;
    private int[] available;
    private int[] work;
    private int currready;
    private List<PCB> process;

    system(String T0){
        this.source = super.source;
        this.allocation = super.allocation;
        this.need = super.need;
        this.available =super.available;
        this.process = super.process;
    }

    public void show() {
        System.out.println("---------------------初始资源分配情况--------------------------");
        System.out.println("          Max        Allocation      Need        Available");
        System.out.println("       A   B   C     A   B   C     A   B   C     A   B   C");
        int f = 0;
        for (int i = 0; i < process.size(); i++) {
            System.out.print("P[" + i + "]   ");
            for (int j = 0; j < 3; j++)
                System.out.print(process.get(i).getNeedmax()[j] + "   ");
            System.out.print("  ");
            for (int j = 0; j < 3; j++)
                System.out.print(process.get(i).getOccupy()[j] + "   ");
            System.out.print("  ");
            for (int j = 0; j < 3; j++)
                System.out.print(need[i][j] + "   ");
            System.out.print("  ");
            if(f == 0){
                for (int j = 0; j < 3; j++)
                    System.out.print(available[j] + "   ");
                f++;
            }
            System.out.println();
        }
    }

    private int name2index(@NotNull String s){
        String str = s.substring(s.length()-1,s.length());
        return Integer.valueOf(str);
    }

    public void opwPCB(String name,char c){//更改状态，哪个进程，状态改为什么
        this.process.get(name2index(name)).setStatus(c);
    }

    public void opwPCB(int n,String s,int[] a){
        switch (n){
            case 1:
                this.process.get(name2index(s)).setApply(a);
                break;
            case 2:
                this.process.get(name2index(s)).setNeedmax(a);
                break;
            case 3:
                this.process.get(name2index(s)).setOccupy(a);
                break;
        }
    }

    public boolean check(int[] a){
        if(a.length!=this.available.length)return false;
        for(int i = 0;i<this.available.length;i++){
            if(a[i]>this.available[i])return false;
        }
        return true;
    }

    private boolean can(PCB p){
        for(int i = 0;i<this.work.length;i++){
            if(p.getApply()[i]>this.work[i]){
                return false;
            }
        }
        return true;
    }

    private boolean checkSecurity(){
        List<PCB> tmp = new ArrayList<>();
        this.work = this.available;//初始化工作向量
        for(int i = 0;i<this.process.size();i++){
            if(this.process.get(i).getStatus() == 'r'){
                tmp.add(this.process.get(i));//找到要申请资源的进程将其加入列表
            }
        }
        boolean[] finish = new boolean[tmp.size()];
        for(int i = 0;i<tmp.size();i++){
            finish[i]=false;
        }
        for(int i = 0;i<tmp.size();i++){
            if(finish[i]==false && can(tmp.get(i))){//对于可加入安全序列的进程
                for(int j = 0;j<work.length;j++){
                    this.work[j] += tmp.get(i).getApply()[j];//假设它很快完成任务，把资源归还
                }
                finish[i] = true;//让它可完成
            }
        }
        for(int i = 0;i<finish.length;i++){
            if(finish[i]==false){//只要有不能完成的，即不满足安全序列
                return false;
            }
        }
        //能执行到这说明都可完成
        return true;
    }

    private void apply(){
        for(int i = 0;i<this.available.length;i++){
            this.available[i] -= this.process.get(this.currready).getApply()[i];//系统现存减申请
            this.allocation[currready][i] += this.process.get(this.currready).getApply()[i];//系统占用加申请
        }
        this.orderApply();//将申请清零
    }

    private void giveback(){
        //将进程占据资源全部归还
        for(int i = 0;i<this.process.get(this.currready).getOccupy().length;i++){
            this.available[i] += this.process.get(this.currready).getOccupy()[i];
        }
        //将该进程系统占用清零
        for(int i = 0;i<this.process.get(this.currready).getOccupy().length;i++){
            this.allocation[this.currready][i] = 0;
        }
        this.process.get(this.currready).release();//更新占据资源信息
        this.process.get(this.currready).setStatus('e');//更改状态为“完成”
    }

    public boolean BankerAlgorithm(){
        while (this.checkReady()){//检查就绪进程
            if(this.check(this.process.get(this.currready).getApply()) && checkSecurity()){//现存可满足申请并进行安全性检查
                this.apply();
                if(!this.checkFulfil() && (process.get(currready).getApply()[0]!=0&&process.get(currready).getApply()[1]!=0&&process.get(currready).getApply()[2]!=0)){//没有得到全部所需资源
                    continue;
                }else {//得到全部所需资源
                    this.giveback();
                }
                for(int i = 0;i<this.process.size();i++){
                    if(this.process.get(i).getStatus() == 'w'){//检查等待中的进程
                        if(check(this.need[i])){//如果系统现存能满足之前的申请
                            this.process.get(i).setStatus('r');//设置为“就绪”状态
                        }
                    }
                }
            }else{
                this.process.get(this.currready).setStatus('w');//不能满足申请置为“等待”状态
                this.need[this.currready] = this.process.get(this.currready).getApply();//向系统登记申请
                continue;
            }
        }
        for(int i = 0;i<this.process.size();i++){//检查是否有进程未处于完成态
            if(this.process.get(i).getStatus() != 'e')return false;
        }
        //执行到这说明全部完成
        return true;
    }

    private void orderApply(){//允许申请
        int[] a = new int[this.process.get(this.currready).getApply().length];
        for(int i = 0;i<this.process.get(this.currready).getApply().length;i++){
            a[i] = 0;//将进程申请全部清零
        }
        this.process.get(this.currready).setApply(a);//完成清零
    }

    private boolean checkReady(){//检查当前进程列表中是否有状态为“就绪”的进程
        for(int i = 0; i < this.process.size(); i++){
            if(this.process.get(i).getStatus()=='r'){
                this.currready = i;//如果有就把它的序号交给currready
                return true;
            }
        }
        return false;
    }

    private boolean checkFulfil(){//检查是否满足全部需求
        boolean result = true;
        for(int i = 0;i<process.get(currready).getNeedmax().length;i++){
            if(process.get(currready).getNeedmax()[i] != process.get(currready).getOccupy()[i]){
                result = false;
            }
        }
        return result;
    }

    public boolean RandomAlgorithm(){
        while (this.checkReady()){//检查就绪进程
            if(this.check(this.process.get(this.currready).getApply())){//现存可满足申请
                this.apply();
                if(!this.checkFulfil() && (process.get(currready).getApply()[0]!=0&&process.get(currready).getApply()[1]!=0&&process.get(currready).getApply()[2]!=0)){//没有得到全部所需资源
                    continue;
                }else {//得到全部所需资源
                   this.giveback();
                }
                for(int i = 0;i<this.process.size();i++){//检查等待中的进程
                    if(this.process.get(i).getStatus() == 'w'){
                        if(check(this.need[i])){//如果系统现存能满足之前的申请
                            this.process.get(i).setStatus('r');//设置为“就绪”状态
                        }
                    }
                }
            }else{
                this.process.get(this.currready).setStatus('w');//不能满足申请置为“等待”状态
                this.need[this.currready] = this.process.get(this.currready).getApply();//向系统登记申请
                continue;
            }
        }
        for(int i = 0;i<this.process.size();i++){//检查是否有进程未处于完成态
            if(this.process.get(i).getStatus() != 'e')return false;
        }
        //执行到这说明全部完成
        return true;
    }
}
