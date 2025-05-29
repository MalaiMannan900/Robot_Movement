package com.robot.robot_movement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RobotManagerTests {

	@Test
	public void testAddAndMoveRobot() {
	    RobotManager manager = new RobotManager(5, 5);
	    manager.addRobot("A");
	    assertTrue(manager.getRobotPositions().contains("A: (0,0)"));

	    manager.moveRobot("A", 'N', 2);
	    assertTrue(manager.getRobotPositions().contains("A: (0,2)"));

	    manager.moveRobot("A", 'E', 2);
	    assertTrue(manager.getRobotPositions().contains("A: (2,2)"));

	    // Try moving out of bounds
	    manager.moveRobot("A", 'N', 10);
	    assertTrue(manager.getRobotPositions().contains("A: (2,4)")); // max y = 4

	    // Add second robot and block collision
	    manager.addRobot("B");
	    manager.moveRobot("B", 'N', 4);
	    assertTrue(manager.getRobotPositions().contains("B: (0,4)"));

	    // Move A toward B; should stop before collision
	    manager.moveRobot("A", 'W', 2); // Now at (0,4)
	    assertTrue(manager.getRobotPositions().contains("A: (0,4)"));

	    manager.moveRobot("A", 'W', 1); // Should not move - out of bounds
	    assertTrue(manager.getRobotPositions().contains("A: (0,4)"));
	}

    @Test
    public void testMultipleRobotsAndCollision() {
        RobotManager manager = new RobotManager(5, 5);
        manager.addRobot("R1");
        manager.addRobot("R2");
        // Move R1 north 2 steps to (0,2). R2 remains at (0,0).
        manager.moveRobot("R1", 'N', 2);
        assertTrue(manager.getRobotPositions().contains("R1: (0,2)"));
        assertTrue(manager.getRobotPositions().contains("R2: (0,0)"));

        // Now move R1 south 2 steps; R2 at (0,0) should block second step.
        manager.moveRobot("R1", 'S', 2);
        // R1 should stop at (0,1) instead of colliding with R2.
        assertTrue(manager.getRobotPositions().contains("R1: (0,1)"));
        assertTrue(manager.getRobotPositions().contains("R2: (0,0)"));
    }

    @Test
    public void testBoundaryLimits() {
        RobotManager manager = new RobotManager(3, 3);
        manager.addRobot("B");
        // Move beyond north boundary
        manager.moveRobot("B", 'N', 5);
        // Should stop at top edge (0,2).
        assertTrue(manager.getRobotPositions().contains("B: (0,2)"));

        // Try moving west at x=0 (no change expected)
        manager.moveRobot("B", 'W', 1);
        assertTrue(manager.getRobotPositions().contains("B: (0,2)"));

        // Move east beyond boundary; should stop at (2,2).
        manager.moveRobot("B", 'E', 5);
        assertTrue(manager.getRobotPositions().contains("B: (2,2)"));
    }

    @Test
    public void testInvalidCommands() {
        RobotManager manager = new RobotManager(5, 5);
        manager.addRobot("X");
        // Invalid direction letter
        assertThrows(IllegalArgumentException.class, () -> manager.moveRobot("X", 'Q', 1));
        // Negative steps not allowed
        assertThrows(IllegalArgumentException.class, () -> manager.moveRobot("X", 'N', -2));
        // Non-existent robot ID
        assertThrows(IllegalArgumentException.class, () -> manager.moveRobot("Y", 'N', 1));
    }

    @Test
    public void testDuplicateRobotId() {
        RobotManager manager = new RobotManager(5, 5);
        manager.addRobot("ID1");
        // Adding same ID again should fail
        assertThrows(IllegalArgumentException.class, () -> manager.addRobot("ID1"));
    }
}