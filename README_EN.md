# Oh My Little Multi 🌈

**Oh My Little Multi** is an educational Android app for children that turns learning multiplication tables into an exciting 3D adventure.

## ✨ Project Features

### 🎨 Design and UI
- **3D-Pixel-Perfect UI**: The interface is inspired by classic tactile games. Every button features real volume, shadows, and glossy highlights.
- **Interactive Mascots**:
    - **Wise Owl**: A mentor who provides hints and monitors answer accuracy. Its design is protected and crafted using complex radial gradients.
    - **Drako (The Dragon)**: A loyal companion who cheers the player on throughout the game.
- **Immersive Environment**: Floating clouds, background hills, and smooth element animations create a living game world.

### 🧠 Learning Methodology (Leitner System)
The game implements a scientifically-backed spaced repetition system — the **Leitner Algorithm (5 Boxes)**:
1. New or difficult tasks are placed in **Box 1** and appear frequently.
2. Correct answers promote the task to the next box (Box 2, 3, etc.).
3. Incorrect answers immediately demote the task back to **Box 1**.
4. Progress is saved in `SharedPreferences`, allowing for efficient mastery of specifically challenging equations.

### 🎮 Gameplay
- **Equation Card**: A central card displaying the problem with a "3D slot" for the selected answer.
- **Answer Bank**: A dedicated panel containing answer options, styled as a convenient "tray".
- **Hint System**: Allows players to see the last digit of the answer or get a logical clue at the cost of a small point penalty.
- **Victory Celebration**: Completing a series of 5 questions triggers a confetti blast (using the Konfetti library).

## 🛠 Technical Stack
- **Language**: Kotlin
- **Architecture**: ViewBinding, Fragment-based navigation.
- **UI**: ConstraintLayout, Custom Vector Drawables (3D styling), Material 3.
- **Animations**: ObjectAnimator, XML animations (pulse, float, pop).
- **Data Storage**: SharedPreferences (Leitner system state).

## 🚀 How to Run
1. Clone the repository.
2. Open the project in **Android Studio Ladybug (or newer)**.
3. Run on an emulator or a real device with API 24+.

---
*Created with love for details and mathematics!* 🍎✨
