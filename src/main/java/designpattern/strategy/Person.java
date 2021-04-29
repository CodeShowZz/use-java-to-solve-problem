package designpattern.strategy;

/**
 * @author junlin_huang
 * @create 2021-03-21 下午7:19
 **/

public class Person {

    private SuperAbility superAbility;

    public void setSuperAbility(SuperAbility superAbility) {
        this.superAbility = superAbility;
    }

    public void show() {
        superAbility.show();
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setSuperAbility(new Fly());
        person.show();

        person.setSuperAbility(new ThroughWall());
        person.show();
    }
}

interface SuperAbility {

    void show();
}

class Fly implements SuperAbility {

    @Override
    public void show() {
        System.out.println("飞");
    }
}

class ThroughWall implements SuperAbility {

    @Override
    public void show() {
        System.out.println("穿墙");
    }
}
