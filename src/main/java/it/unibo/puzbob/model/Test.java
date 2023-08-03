package it.unibo.puzbob.model;

public class Test {
    public static void main(String[] args) {
        Physics fisica = new PhysicsImpl(new Pair<Double,Double>(15.0, 10.0), 10);

        FlyingBallImpl ball2 = new FlyingBallImpl("WHITE", 3, 0.5, new Pair<Double,Double>(0.5, 0.0));

        System.out.println(fisica.getBallPosition(ball2, 25, 1));
        System.out.println(fisica.getBallPosition(ball2, 25, 2));
        System.out.println(fisica.getBallPosition(ball2, 25, 3));
        System.out.println(fisica.getBallPosition(ball2, 25, 4));
        System.out.println(fisica.getBallPosition(ball2, 25, 5));
        
    }
}
