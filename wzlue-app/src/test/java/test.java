public class test {
    public static void main(String[] args) {
        String smsCode = String.valueOf((Math.random() * 9 + 1) * 1000).substring(0,4);//验证码
        System.out.println(smsCode);
    }
}
