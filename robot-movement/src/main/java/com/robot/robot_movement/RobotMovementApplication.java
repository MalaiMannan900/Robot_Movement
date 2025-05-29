package com.robot.robot_movement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class RobotMovementApplication implements CommandLineRunner {

    @Value("${grid.width:10}")
    private int gridWidth;

    @Value("${grid.height:10}")
    private int gridHeight;

    private RobotManager manager;

    public static void main(String[] args) {
        SpringApplication.run(RobotMovementApplication.class, args);
    }

    @Override
    public void run(String... args) {
        manager = new RobotManager(gridWidth, gridHeight);
        printWelcome();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    continue; // Ignore empty lines
                }

                String cmd = input.split("\\s+")[0].toUpperCase();

                switch (cmd) {
                    case "EXIT":
                        if (confirmExit(scanner)) {
                            System.out.println("👋 Exiting Robot Movement Application. Goodbye!");
                            return;
                        }
                        break;
                    case "LIST":
                        printPositions();
                        break;
                    case "CREATE":
                        handleCreate(input);
                        break;
                    case "MOVE":
                        handleMove(input);
                        break;
                    default:
                        System.out.println("❌ Unknown command. Try CREATE, MOVE, LIST, or EXIT.");
                }
            }
        }
    }

    private boolean confirmExit(Scanner scanner) {
        System.out.print("Are you sure you want to exit? (Y/N): ");
        String answer = scanner.nextLine().trim();
        return answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES");
    }

    private void handleCreate(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length != 2 || !parts[1].matches("^[A-Za-z0-9]+$")) {
            System.out.println("❌ Invalid CREATE command. Usage: CREATE <robotId> (alphanumeric only)");
            return;
        }

        String robotId = parts[1];
        try {
            manager.addRobot(robotId);
            System.out.println("✅ Robot " + robotId + " created at (0, 0)");
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️  " + e.getMessage());
        }
    }

    private void handleMove(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length != 3) {
            System.out.println("❌ Invalid MOVE command. Usage: MOVE <robotId> <D><steps> (e.g., MOVE R1 N3)");
            return;
        }

        String robotId = parts[1];
        String directionAndSteps = parts[2].toUpperCase();

        if (!directionAndSteps.matches("^[NSEW]\\d+$")) {
            System.out.println("❌ Invalid direction. Use N, S, E, W followed by steps (e.g., N3).");
            return;
        }

        char direction = directionAndSteps.charAt(0);
        int steps;
        try {
            steps = Integer.parseInt(directionAndSteps.substring(1));
            if (steps <= 0) {
                System.out.println("❌ Steps must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Steps must be a valid integer.");
            return;
        }

        try {
            Position finalPos = manager.moveRobot(robotId, direction, steps);
            System.out.println("✅ " + robotId + " moved to (" + finalPos.getX() + ", " + finalPos.getY() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }

    }

    private void printPositions() {
        List<String> positions = manager.getRobotPositions();
        System.out.println("📍 Robot Positions:");
        if (positions.isEmpty()) {
            System.out.println("  (no robots)");
        } else {
            for (String pos : positions) {
                System.out.println("  • " + pos);
            }
        }
    }

    private void printWelcome() {
        System.out.println("\n  ____       _           _     _           _       _   _             ");
        System.out.println(" |  _ \\ ___ | |__   ___ | |_  | |__   ___ | |_ ___| |_(_) ___  _ __  ");
        System.out.println(" | |_) / _ \\| '_ \\ / _ \\| __| | '_ \\ / _ \\| __/ __| __| |/ _ \\| '_ \\ ");
        System.out.println(" |  _ < (_) | |_) | (_) | |_  | | | | (_) | |_\\__ \\ |_| | (_) | | | |");
        System.out.println(" |_| \\_\\___/|_.__/ \\___/ \\__| |_| |_|\\___/ \\__|___/\\__|_|\\___/|_| |_|");
        System.out.println("\n :: Robot Movement Application ::        (v1.0.0)");
        System.out.println("Grid size: " + gridWidth + "x" + gridHeight + "\n");
        System.out.println("Commands:");
        System.out.println("  CREATE <id>               - Create a new robot with given ID");
        System.out.println("  MOVE <id> <D><steps>      - Move robot (e.g., MOVE R1 N3)");
        System.out.println("  LIST                      - List all robot positions");
        System.out.println("  EXIT                      - Exit the application\n");
    }
}
