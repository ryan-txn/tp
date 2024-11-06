# Project Portfolio Page - kennethSty

## Overview
HealthMate is a meal and calorie tracking application designed to help users manage their dietary intake, 
monitor their weight, and track their overall health goals. 
The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Summary of Contributions

### Code Contributed
[Link to code contributions](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=Kenneth&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=kennethSty&tabRepo=AY2425S1-CS2113-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
*Created the foundational design (including the packaging) of the application and implemented the following classes:*
- the Meal and MealEntry classes
- the MealList and MealEntriesList classes
- the main logic of the ChatParser class
- the UI class
- the Logging class
- the User class

### Enhancements Implemented
1. **Implementing the Meal and MealEntry classes**
* Created the Meal and MealEntry class and their inheritance relationship. 
* Implemented the usage of the Optional context to allow MealEntries without a name.
* Added low-level methods for extracting meals and mealEntries from user input. 

2. **Implementing the MealList and MealEntriesList class**
* Created the MealList and MealEntriesList class and their inheritance relationship.
* Implemented low-level functionality to extract meals and mealEntries from text and add them to the respective lists.

3. **Enhancing the User and UserHistoryTracker class**
* Created basic structure of the User class.
* Added `checkForUserData` method and implemented the logic of `loadUserEntries`.  
* Refactored the creation of a new user instance (`askForUserData`), if no user file exists so far.

4. **Contributions to the ChatParser class**
* Designed the overall ChatParser structure
* Implemented the preprocessing of user input into commands
* Created Exception handling for system recovery after incorrect user input
* Implemented the command routing in the ChatParser class via the `run` and the `multiCommandParsing` methods.
* Implemented logging the command routing flow
* Furthermore, I implemented the logic of the following commands:
- log meals
- meal menu (previously list meals. [s here:](https://github.com/nus-cs2113-AY2425S1/tp/commit/e327b5ac406777e57f70f0014db46927f7d51d32#diff-44f81584378b70dc571d0791db08ade270cd1e700ad47c561c9a85bbe10b46ebR18))
- save meal
- add mealEntry

5. **Implementing the UI class**
* Implemented static methods to be used throughout this project for standardizing user input. 
* Added the feature to print the list of previously consumed meals and meal options
* Added key elements for output structuring and their usage when printing user feedback
* Implemented the consumption bar as the key visual element to highlight the progress the user makes towards his/her health goal on a daily basis.
* Implemented the triggers of printing and updating the consumption bar when adding and deleting mealEntries.
The following exemplifies these elements: 
```dtd
  add mealEntry burger /c300
  _____________________________________________________________________________
  Tracked: burger with 300 calories (at: 2024-10-29T22:00)
  _____________________________________________________________________________
  % of Expected Calorie Intake Consumed:
  ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-29)
  _____________________________________________________________________________
  log meals
  _____________________________________________________________________________
  1: burger with 300 calories (at: 2024-10-29T22:00)
  _____________________________________________________________________________
```

6. **Unit Testing**
* Implemented the ChatParserTest class and the foundational methods setOutputStream, restoreStream, compareChatParserOutput.
* Added the following tests:
- randomInput_printsError: Validates handling of invalid inputs.
- addMealToOptionsWithName_success: Tests adding a named meal to options.
- addMealToOptionsNoName_failure: Confirms proper error handling when saving a meal without a name.
- trackMealEntryWithCalories_success: Tests successful tracking of a meal entry with calories.
- showHistoricCaloriesNoTime_failure: Validates response when no time is provided in a history command.
- addPastMealsAndShowHistory_success: Simulates adding meal entries over past dates 
and tests history retrieval, using a user stub.
* Additionally, I implemented the testGetTimestamp() unit test, 
  which asserts proper responses for several positive and negative inputs.

### Contributions to the User Guide
Added the sections:
- `Get an overview on your calorie consumption` 
- `Track your daily progress`
- `Create your user profile`
Overall proofreading.

  
### Contributions to the Developer Guide
* In `Design & Implementation` I added the following subsections:
- `High Level Class design` (incl. class diagram)
- `HealthMate`
- `ChatParser`
- `MealList`
- `MealEntriesList`
- `Meal` (including the sequence diagram)
- `MealEntry`
- `User`

* In the `Features` section the subsections I added are:
- `Creating a User Profile` and the sequence diagram userSequenceDiagram.jpg
- `ChatParser Input Handling` including the subsections

### Contributions to Team Tasks
* Took over responsibility for managing and creating issues for steering team progress.
* Created the first POC application for tracking mealEntries and mealOptions to kickstart the project. 
* Fixed IO Testing issues.
* Left feedback in my PR reviews, resulting in follow-up issues.

### Review/Mentoring Contributions
* Reviewed, approved or if necessary requested changes to multiple PRs
* Helped to finish the User profile creation procedure when the previous approach got stuck.
* Helped to reduce repetitive code via polymorphism (e.g. UserHistoryTracker extends HistoryTracker).
* Helped to SLAP code (e.g. the Feature to print historic consumption bars).
* Handled most of the issue tracking and tested code help peers find bugs.
