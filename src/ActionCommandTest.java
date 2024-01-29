import Component.CityCrew;
import Component.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActionCommandTest {
    public static void main(String[] args) throws IOException {
        // Create a sample player and city crew
        List<Player> palyers = new ArrayList<>();
        Player player = new Player("sdsd", 1);
        Player player2 = new Player("s33", 2);
        player.getCityCrew().setCurrentRow(3);
        player.getCityCrew().setCurrentCol(2);


        land l = new land(palyers);
        l.buyCity(2,2,player2,1000);
        // Create a sample ActionCommand instance (you need to replace this with your specific implementation)
        System.out.println("current loc 3 , 2");
        ActionCommands actionCommand = new ActionCommands() {

        };

        // Test moving in different directions
        testMoveDirection(actionCommand, player, Direction.UP,l);
//        testMoveDirection(actionCommand, player, Direction.UPLEFT,l);
//        testMoveDirection(actionCommand, player, Direction.UPRIGHT,l);
//
//        testMoveDirection(actionCommand, player, Direction.DOWNRIGHT,l);
//        testMoveDirection(actionCommand, player, Direction.DOWN,l);
//        testMoveDirection(actionCommand, player, Direction.DOWNLEFT,l);
    }

    private static void testMoveDirection(ActionCommands actionCommand, Player p, Direction direction,land l) {
        System.out.println("Moving " + direction.name().toLowerCase());
        actionCommand.move(direction, p, l);
        System.out.println("New position: Row " + p.getCityCrew().getCurrentRow() + ", Col " + p.getCityCrew().getCurrentCol());

        System.out.println();
    }
}
