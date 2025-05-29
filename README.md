# Robot_Movement
🤖 Robot Movement Application
Description
This is a Java command-line application that simulates multiple robots moving on a 2D grid terrain. Each robot has a unique identifier and starts at the origin point (0, 0) on the grid. The robots can be created, moved, and tracked based on user commands entered interactively.

The key feature of this app is managing multiple robots moving independently on a grid while avoiding collisions and staying within the grid boundaries.

Key Functionalities
Create multiple robots: Each robot is associated with a unique ID.

Move robots: Robots move in one of four directions (North, East, South, West) a given number of steps.

Collision avoidance: If a robot’s intended move would cause it to collide with another robot, it stops before the collision.

Boundary enforcement: Robots cannot move outside the grid limits.

Track positions: Display the current location of all robots.

Simple CLI interface: Interactive commands to create, move, list robots, and exit the application.

Input Format
Commands are two-letter or keyword based:

CREATE <robot_id> — create a new robot with the given ID.

MOVE <robot_id> <D><N> — move robot <robot_id> in direction <D> (N/S/E/W) for <N> steps.

LIST — list all robots and their current positions.

EXIT — exit the application.

Assumptions
The terrain is a 10x10 grid (default).

All robots start at (0, 0).

Grid positions are represented by zero-based coordinates (x, y).

No visual representation is needed, positions are shown textually.

Example

> CREATE R1
✅ Robot R1 created at (0, 0)

> MOVE R1 N5
✅ R1 moved to (0, 5)

> CREATE R2
✅ Robot R2 created at (0, 0)

> MOVE R2 E3
✅ R2 moved to (3, 0)

> MOVE R1 S1
✅ R1 moved to (0, 4)

> LIST
📍 Robot Positions:
  • R1: (0, 4)
  • R2: (3, 0)
> CREATE R1
⚠️  Robot R1 already exists.

> MOVE R3 N2
❌ Robot R3 not found.

> MOVE R1 X2
❌ Invalid move command. Use N/S/E/W followed by steps (e.g., N3).

> EXIT
👋 Exiting Robot Movement Application. Goodbye!

Run tests

# mvn test
