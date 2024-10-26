### Angry Birds: Space x MCU (Static GUI)

---

#### **Group: ByteMe**
1. Anouska Sahoo (2023096)
2. Vaishnavi Srivastava (2023574)

---

#### **Implementation Overview:**
The **Static GUI** for the Angry Birds game was implemented using the **libGDX framework**. Key components such as the **Home Screen**, **Game Screen**, **Levels** (with their individual designs), and various UI elements (like buttons and menus) have been built.

The design heavily utilizes **Object-Oriented Programming (OOP)** principles such as **inheritance**,  **polymorphism**, **abstraction**, **encapsulation**. This is evident for instance in Bird, Pig, Obstacle classes etc.

The project also follows **Factory Design Pattern**, which helps manage the creation of different game objects efficiently.

---
#### **References:**
- The official **libGDX documentation** for understanding the game framework: [libGDX Documentation](https://libgdx.com/dev/).
- **Angry Birds official website** to familiarize ourselves with the game mechanics: [Angry Birds](https://www.angrybirds.com/).

---

#### **Setup and Run Instructions:**

1. **Clone the Repository from GitHub:**
   ```bash
    git clone https://github.com/vaiiishhh/AngryBirds.git
    cd AngryBirds
   ```

2. **Import the Project in IntelliJ or any other IDEs**

3. **Set Up libGDX in Your Environment:**
    - Ensure you have **Java JDK 11+** installed.

4. **Run the Game:**
    - Run `Lwjgl3Launcher` located in `com/ByteMe/lwjgl3/Lwjgl3Launcher.java`.

5. **Running in Terminal (Optional):**
    - Navigate to the project directory:
      ```bash
      cd core/
      ./gradlew run
      ```

---

#### **How to Navigate the Game**:

1. #### **Login Screen:**
    When you first launch the game, you will see the **Login Screen** where you can enter your player name.

2. #### **Home Screen:**
    After entering your name, you are taken to the **Home Screen**, which contains four buttons:
   - **New Game**: Starts a new game and leads to the level selection screen.
   - **Load Saved Games**: This button is present for future functionality to load previously saved games, current it take you to a static screen.
   - **Leaderboard**: Displays the leaderboard.
   - **Exit**: Ends the game.

3. #### **Level Selection Screen:**
    Clicking on the **New Game** button from the home screen takes you to the **Level Selection Screen**, where you can choose between **Level 1**, **Level 2**, and **Level 3**.

4. #### **In-Game Options:**
    Each level has a few options represented by buttons:
   - **Pause Game**: Pauses the game.
   - **Resume**: Resumes the game after pausing.
   - **Exit**: Exits the level.
   - **Save Game**: A placeholder button to save the current progress (functionality yet to be implemented).

*Note: The current version is purely a Static GUI, so no game logic or dynamic interactions have been added yet.*

(^oo^)

---
