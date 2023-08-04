package it.unibo.puzbob.model;

public class Test {
    public static void main(String[] args) {
        Physics fisica = new PhysicsImpl(new Pair<Double,Double>(50.0, 50.0), 1, new Pair<Double,Double>(25.0, 0.0));

        FlyingBallImpl ball2 = new FlyingBallImpl("WHITE", 3, 5, new Pair<Double,Double>(25.0,0.0));

        Ball[][] matrixBall = new Ball[10][10];

        matrixBall[0][1] = new FlyingBallImpl("WHITE", 3, 5, new Pair<Double,Double>(0.0, 0.0));

        Pair<Integer, Integer> result = null;
        double time = 0;

        while (result == null) {
            Pair<Double, Double> position = fisica.getBallPosition(ball2, 170, time);
            System.out.println(position);
            ball2.setPosition(position);
            result = fisica.isAttached(0, matrixBall, ball2);
            time += 0.3;
        }

        System.out.println("Attached to " + result);
        
    }
}
