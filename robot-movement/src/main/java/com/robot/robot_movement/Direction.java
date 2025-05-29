package com.robot.robot_movement;

public enum Direction {
    N(0, 1),
    S(0, -1),
    E(1, 0),
    W(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() { return dx; }
    public int getDy() { return dy; }

    public static Direction fromChar(char c) {
        switch (Character.toUpperCase(c)) {
            case 'N': return N;
            case 'S': return S;
            case 'E': return E;
            case 'W': return W;
            default: throw new IllegalArgumentException("Invalid direction: " + c);
        }
    }
}
