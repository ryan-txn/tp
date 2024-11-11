# Project Portfolio Page - ryryry-3302

## Overview
HealthMate is a meal and calorie tracking application designed to help users manage their dietary intake, monitor 
their  weight, and track their overall health goals. The app enables users to log meals, track calories, and observe 
their progress towards a healthier lifestyle.

## Summary of Contributions
[Link to code contributions](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=ryryry-3302&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=ryryry-3302&tabRepo=AY2425S1-CS2113-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
Given below are my contributions to the project.
1. **Command Class**
   - Abstract class used to serve as the base class child command classes
   - Used to store command info such as, command name, syntax and description
   - Convenient design allowing us to store future command information added later on and for easy retrieval with a 
     common toString() method
2. **CommandMap Class**
   - Used to store all possible commands for use with the `list commands` command
   - Allows users to quickly see a brief overview of all commands possible
   - Hashmap allows fast retrieval of all information of a specific command in O(1) time
3. **Parameter enum**
   - Used to store different parameters we use for different commands instead of magic literals
   - Has methods for retrieving specific parameter values as well as throwing exceptions for bad formatting/ 
     missing values
4. **Enhanced add meal command**
   - Modified extractAndAppendMeal to include optional parameters from the Parameter class
   - Added exception handling for Bad formatting/ Missing values accompanied by a UI reply
5. **Weight timeline command**
   - Made WeightEntryDisplay class to generate a graph of the last 10 weight entries
6. **Meal recommendations command**
   - Made Recipe parent class and child recipe classes
   - Made Recipe map to store recipes to return the right recipes based on user's health goal
6. **Unit Testing**
   - Implemented all tests Parameters
   - Implemented all tests UI
   - Updated ChatParser tests

### Contributions to the User Guide
Wrote documentation for the following commands including description, sample usage and console output
1. `list commands`
2. `save meal`
3. `add mealEntry`
4. `delete meal`
5. `meal menu`
6. `meal log`
7. `meal recommendations`
7. `delete mealEntry`
8. `weight timline`
9. Summary table

### Contributions to the Developer Guide
- Added implementation details of the `CommandMap` class and `listCommands`
- Created UML diagrams for `askForUserData`, `addMeal`, `listCommands`

### Contributions to team based tasks
- Setting up milestones and maintaining issues
- Enable assertions in build gradle
- Reminding of weekly deliverables and deadlines

### Community
- Forum responses [1](https://github.com/nus-cs2113-AY2425S1/forum/issues/2#issuecomment-2294732154)