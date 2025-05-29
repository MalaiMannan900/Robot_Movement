package com.robot.robot_movement;

import java.util.*;

/**
 * Manages robots on a 2D grid (of size width x height).
 * Handles robot creation and movement commands.
 */
public class RobotManager {
    private final int width;
    private final int height;
    private final Map<String, Robot> robots = new LinkedHashMap<>();

    public RobotManager(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive.");
        }
        this.width = width;
        this.height = height;
    }

    /**
     * Adds a new robot with the given ID at (0,0).
     * Throws if ID is null/empty or already exists.
     */
    public void addRobot(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Robot ID cannot be null or empty.");
        }
        if (robots.containsKey(id)) {
            throw new IllegalArgumentException("Robot ID already exists: " + id);
        }
        // Robot starts at (0,0) by requirement
        Robot robot = new Robot(id, new Position(0, 0));
        robots.put(id, robot);
    }

    /**
     * Moves the robot identified by id in the given direction up to 'steps' times.
     * Stops early if boundary or collision occurs.
     * Returns the final position after movement.
     */
    public Position moveRobot(String id, char dirChar, int steps) {
        Robot robot = robots.get(id);
        if (robot == null) {
            throw new IllegalArgumentException("Robot not found: " + id);
        }
        if (steps < 0) {
            throw new IllegalArgumentException("Steps must be non-negative.");
        }
        if (steps == 0) {
            return robot.getPosition();
        }
        Direction direction = Direction.fromChar(dirChar);
        Position pos = robot.getPosition();

        for (int i = 0; i < steps; i++) {
            int newX = pos.getX() + direction.getDx();
            int newY = pos.getY() + direction.getDy();
            if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
                break;
            }
            Position newPos = new Position(newX, newY);
            boolean occupied = robots.values().stream()
                .anyMatch(r -> !r.getId().equals(id) && r.getPosition().equals(newPos));
            if (occupied) {
                break;
            }
            pos = newPos;
            robot.setPosition(pos);
        }
        return pos;
    }


    /**
     * Returns a list of strings describing each robot's ID and position.
     */
    public List<String> getRobotPositions() {
        List<String> list = new ArrayList<>();
        for (Robot r : robots.values()) {
            list.add(r.getId() + ": " + r.getPosition());
        }
        return list;
    }
}