# Project Portfolio Page - Dri Water

## Overview
HealthMate is a meal and calorie tracking application designed to help users manage their dietary intake, monitor their weight, and track their overall health goals. The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Summary of Contributions

### Code Contributed
[Link to code contributions](Insert your tP Code Dashboard link here)

### Enhancements Implemented
1. **Enhanced Meal Menu System**
   * Implemented the meal saving functionality that allows users to store frequently eaten meals with their calorie counts using the `save meal` command (details in mealSaver class)
   * Added ability to retrieve saved meals when logging new meal entries using `add mealEntry`, improving user experience by eliminating the need to remember and re-enter calorie information
   * Modified mealEntry class to accomodate retrieval of saved meals

2. **Implemented MealSaver Class**
   * This class handles the logic of writing and overwriting the mealOptions
   * This abstracts the logic of saving meals from the main logic of the program, making the code more modular and easier to understand

3. **Implemented History Tracking System** 
   * Added a HistoryTracker class
   * Developed the meal history tracking system that maintains a chronological record of all meal entries that persists beyond the current session
   * Writes to a csv file the current meal_entries and meal_options within the /data folder

4. **Implemented manual mealEntry timestamp** 
   * Added functionality to allow users to add a mealEntry with a custom specified timestamp
   * Modified Parser class to accept timestamp input

### Contributions to the User Guide
  * Added Quick start guide
  * Added details for add mealEntry command


### Contributions to the Developer Guide
* Added implementation details for:
  * Meal Menu feature
  * HistoryTracker class
  * UML diagrams for 'meal menu' command

### Contributions to Team Tasks
* Helped in setting up the initial project structure
* Contributed to creation of issues
* Helped coordinate version control and release management

### Review/Mentoring Contributions
* Reviewed and approved multiple PRs
* Helped troubleshoot integration issues between different components

