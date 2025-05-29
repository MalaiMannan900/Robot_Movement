package com.robot.robot_movement;

public class Robot {
    private final String id;
    private Position position;

    public Robot(String id, Position position) {
        this.id = id;
        this.position = position;
    }

    public String getId() { return id; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}
