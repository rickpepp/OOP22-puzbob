package it.unibo.puzbob.model;

public class Test {
    public static void main(String[] args) {
        

        FlyingBallImpl ball2 = new FlyingBallImpl("WHITE", 3, 1.875, new Pair<Double,Double>(7.5,0.0));

        JSONReader reader = new JSONReaderImpl();

        JSONParser parser = new JSONParserImpl();

        // File separator and the path with colors.json and level1.json
       final String FILE_SEPARATOR = System.getProperty("file.separator");
        final String COLORS_PATH = "levels" + FILE_SEPARATOR + "colors.json";
        final String LEVEL1_PATH = "levels" + FILE_SEPARATOR + "level1.json";

        BallFactory ballFactory = new BallFactoryImpl(
            parser.parserColors(reader.readJSONFromFile(COLORS_PATH)), 1.875);

        Level testLevel = new LevelImpl(ballFactory, new Pair<Integer,Integer>(10, 10));

        Ball[][] matrixBalls = testLevel.getStartBalls(parser.parserStarterBalls(reader.readJSONFromFile(LEVEL1_PATH)));

        matrixBalls[0][1] = new FlyingBallImpl("WHITE", 3, 1.875, new Pair<Double,Double>(7.5, 0.0));

        Physics fisica = new PhysicsImpl(new Pair<Double,Double>(15.0, 50.0), 1, new Pair<Double,Double>(7.5, 0.0), ballFactory.getBallDimension());

        Pair<Integer, Integer> result = null;
        double time = 0;

        while (result == null) {
            Pair<Double, Double> position = fisica.getBallPosition(ball2, 173, time);
            System.out.println(position);
            ball2.setPosition(position);
            result = fisica.isAttached(0, matrixBalls, ball2);
            time += 0.05;
        }



        System.out.println("Attached to " + result);
        
    }
}
