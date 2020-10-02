import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) {
            int a,b,c;
            boolean tamgiac;
            Scanner sc = new Scanner(System.in);

            System.out.print("Nhap a di: ");
            a = sc.nextInt();
            System.out.print("nhap b nao ");
            b = sc.nextInt();
            System.out.print("Nhap c thoi ");
            c = sc.nextInt();

            if ( (a+b<c) || (a+c<b) || (b+c<a)) {
                System.out.println("Day la mot tam giac hop le");
            }
            else System.out.println("Day khong la mot tam giac hop le");

    }
}
