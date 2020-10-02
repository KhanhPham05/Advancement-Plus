class Pwr {
    double b;
    int e;
    double val;

    Pwr (double base, int exp) {
        b = base;
        e = exp;

        this.val =1;
        if(exp == 0) return;
        for (;exp>0;exp--) this.val = this.val * base;
    }
    double get_pwr() {
        return this.val;
    }
}

class DemoPwr {
    public static void main(String[] args) {
        Pwr x = new Pwr(4.0, 2);
        Pwr y = new Pwr(2.5, 1);
        Pwr z = new Pwr(5.7, 0);

        System.out.println("ajea");
    }
}