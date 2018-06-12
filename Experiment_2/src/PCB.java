
public class PCB {
    private String name= new String();
    private char status;
    private int[] apply = {0,0,0};
    private int[] needmax = {0};
    private int[] occupy = {0};

    PCB(String name,char s,int[] n,int[] o) {
        this.name = name;
        this.status = s;
        this.needmax = n;
        this.occupy = o;
    }

    public void setNeedmax(int[] needmax) {
        this.needmax = needmax;
    }

    public void setApply(int[] apply) {
        this.apply = apply;
    }

    public void setOccupy(int[] occupy) {
        this.occupy = occupy;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getStatus() {
        return status;
    }

    public int[] getApply() {
        return apply;
    }

    public int[] getNeedmax() {
        return needmax;
    }

    public int[] getOccupy() {
        return occupy;
    }

    public void updateOccupy(int[] a) {
        for (int i = 0; i < a.length; i++) {
            this.occupy[i] += a[i];
        }
    }
    public void release(){
        for(int i = 0;i<occupy.length;i++){
            occupy[i] = 0;
        }
    }
}
