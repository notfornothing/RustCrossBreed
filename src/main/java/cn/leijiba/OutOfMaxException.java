package cn.leijiba;

public class OutOfMaxException extends RuntimeException {


    public OutOfMaxException(String s) {
        super(s);
    }

    public static void main(String[] args) {
//        3917:9563:109

        //TC 6*4=24000
        Integer stone = 9563;
        Integer frag = 3917;
        Integer hqm = 109;
        

//        Integer i = 4000;
//        Integer j = 9600;
//        Integer k = 110;

        int r = 0;

        //最大公因数
        for (int integer = 1; integer < i; integer++) {
            if (i % integer == 0 && j % integer == 0 && k % integer == 0) {
                r = integer;
            }
            if (integer == k) {
                break;
            }
        }
        System.out.println(r);
    }
}
