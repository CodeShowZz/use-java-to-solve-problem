package designpattern.decorator;

/**
 * @author junlin_huang
 * @create 2021-03-21 下午7:01
 **/


public class Person implements Show {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("被装饰的" + name);
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("小明");
        TShirt tShirt = new TShirt();
        tShirt.decorate(person);
        Shoes shoes = new Shoes();
        shoes.decorate(tShirt);
        Trousers trousers = new Trousers();
        trousers.decorate(shoes);
        trousers.show();
    }
}

abstract class Clothes implements Show {

    private Show show;

    public void decorate(Show show) {
        this.show = show;
    }

    public void show() {
        if (show != null) {
            show.show();
        }
    }
}

class TShirt extends Clothes implements Show {

    public void show() {
        System.out.println("穿TShirt");
        super.show();
    }
}

class Shoes extends Clothes implements Show {
    public void show() {
        System.out.println("穿鞋子");
        super.show();
    }
}

class Trousers extends Clothes implements Show {
    public void show() {
        System.out.println("穿裤子");
        super.show();
    }
}

interface Show {

    void show();
}

