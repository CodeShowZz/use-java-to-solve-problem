package designpattern.factorymethod;

/**
 * @author junlin_huang
 * @create 2021-03-21 下午7:38
 **/

public interface CalculateFactory {

    Calculate create();

    public static void main(String[] args) {
        CalculateFactory addFactory = new AddFactory();
        Calculate add = addFactory.create();
        System.out.println(add.calculate(5, 2));


        CalculateFactory mulFactory = new MulFactory();
        Calculate mul = mulFactory.create();
        System.out.println(mul.calculate(5, 2));
    }
}

class AddFactory implements CalculateFactory {

    @Override
    public Calculate create() {
        return new Add();
    }
}

class MulFactory implements CalculateFactory {

    @Override
    public Calculate create() {
        return new Mul();
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