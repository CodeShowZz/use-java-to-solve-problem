package designpattern.simplefactory;

/**
 * @author junlin_huang
 * @create 2021-03-21 下午7:28
 **/

public class CalculateFactory {

    public static Calculate create(String type) {
        switch (type) {
            case "add":
                return new Add();
            case "mul":
                return new Mul();
        }
        return null;
    }

    public static void main(String[] args) {
        Calculate calculate = CalculateFactory.create("add");
        System.out.println(calculate.calculate(5,2));
        calculate = CalculateFactory.create("mul");
        System.out.println(calculate.calculate(5,8));
    }
}

interface Calculate {

    int calculate(int operand1, int operand2);

}

class Add implements Calculate {


    @Override
    public int calculate(int operand1, int operand2) {
        return operand1 + operand2;
    }
}

class Mul implements Calculate {

    @Override
    public int calculate(int operand1, int operand2) {
        return operand1 * operand2;
    }
}