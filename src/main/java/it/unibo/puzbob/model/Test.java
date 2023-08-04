package it.unibo.puzbob.model;

public class Test {
    public static void main(String[] args) {
        Physics fisica = new PhysicsImpl(new Pair<Double,Double>(15.0, 10.0), 5);

        FlyingBallImpl ball2 = new FlyingBallImpl("WHITE", 3, 0.5, new Pair<Double,Double>(7.5, 0.0));

        System.out.println(fisica.getBallPosition(ball2, 25, 5));
        
    }
}
