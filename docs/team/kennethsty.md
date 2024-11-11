# Project Portfolio Page - kennethSty

## Overview
HealthMate is a meal and calorie tracking application designed to help users manage their dietary intake, 
monitor their weight, and track their overall health goals. 
The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Summary of Contributions

### Code Contributed
[Link to code contributions](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=Kenneth&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=kennethSty&tabRepo=AY2425S1-CS2113-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

*Created the foundational design (including the packaging) of the application and contributed to the following classes:*
  - the `Meal` and `MealEntry` classes (*1)
  - the `MealList` and `MealEntriesList` classes (*1)
  - the `HistoryTracker` class (*3)
  - the `UI` class (*1)
  - the `Logging` class (*1)
  - the `User` class (*1)
  - the `UserHistoryTracker` class (*2)
  - the `ConsumptionStatistics` class (*1)
  - the `Pair` and `CommandPair` classes (*1)

Legend: 
- *1: implemented the first working version of the class and improved its design over time
- *2: contributed to the class design of another teammate
- *3: bug fixes and other minor contributions

### Enhancements Implemented
1. **Implementing the Meal and MealEntry classes**
* Created the `Meal` and `MealEntry` class and their inheritance relationship.  
* Implemented the usage of the `Optional` context to allow meal entries without a name.
* Added low-level methods for extracting meals and meal entries from user input. 

2. **Implementing the MealList and MealEntriesList class**
* Created the `MealList` and `MealEntriesList` class and their inheritance relationship.
* Used the polymorphism of the `Meal` and `MealEntry` classes to extract meals and mealEntries from text and add them to the respective lists.

3. **Enhancing the User, UserHistoryTracker and HistoryTracker class**
* Created the structure of the `User` class.
* Added the `checkForUserData()` method and implemented the logic of `loadUserEntries()`.  
* Refactored the creation of a new user instance, if no user file exists so far.
* Refactor askForUserData to adhere to SLAP principles, incorporating enhanced exception handling. 
Allows users to re-enter incorrect input without restarting the entire process.
* Helped refactoring the `UserHistoryTracker` to resolve issues encountered when a user performs data corruption. 
* Improved `HistoryTracker` resilience to data corruption, providing options for the user to either revert corrupted data or create a new data file. 
* Added user-specific customization options to the `UI`, allowing compatibility adjustments based on the system's capability to display special characters for the consumption bar (see UI for details).

4. **Contributions to the `ChatParser` class**
* Designed the overall `ChatParser` structure.
* Implemented data-syncing across multiple running versions of the application.
* Implemented the preprocessing of user input into commands.
* Created Exception handling for system recovery after incorrect user input.
* Implemented the command routing via the `run` and `multiCommandParsing` methods (including helper methods).
* Implemented logging the command routing flow for tracing back possible issues.
* Furthermore, I implemented the logic of the following commands:
  - `meal log`
  - `meal menu` (previously list meals. [s here:](https://github.com/nus-cs2113-AY2425S1/tp/commit/e327b5ac406777e57f70f0014db46927f7d51d32#diff-44f81584378b70dc571d0791db08ade270cd1e700ad47c561c9a85bbe10b46ebR18))
  - `save meal`
  - `add mealentry`

5. **Implementing the `UI` class**
* Implemented static methods to be used throughout this project for standardizing output to the user. 
* Added the feature to print the list of previously consumed meals and meal options.
* Added key elements for output structuring and their usage when printing user feedback.
* Implemented the consumption bar as the key visual element to highlight the progress the user makes towards his/her health goal on a daily basis.
* Made the `UI` customizable based on which chars are available to the user's system.
* Implemented the triggers of printing and updating the consumption bar when adding and deleting meal entries.
The following exemplifies these elements: 
```
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

Implemented the `ChatParserTest` class and the foundational methods 
- `setOutputStream()`
- `restoreStream()`
- `compareChatParserOutput()`.

Further, I added the following tests:
- `randomInput_printsError`: Validates handling of invalid inputs.
- `addMealToOptionsWithName_success`: Tests adding a named meal to options.
- `addMealToOptionsNoName_failure`: Confirms proper error handling when saving a meal without a name.
- `trackMealEntryWithCalories_success`: Tests successful tracking of a meal entry with calories.
- `showHistoricCaloriesNoTime_failure`: Validates response when no time is provided in a history command.
- `addPastMealsAndShowHistory_success`: Simulates adding meal entries over past dates 
and tests history retrieval, using a user stub.

Additionally, I implemented the `testGetTimestamp()` unit test, 
which asserts proper responses for several positive and negative inputs.

### Contributions to the User Guide
Added the sections:
- Get an overview on your calorie consumption 
- Track your daily progress
- Create your user profile
- Data Storage and Persistence
- Data Security
- Add Meal Entry for Tracking (both 'shortcut' subsections)

Overall proofreading and improvements.

  
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
  - `Creating a User Profile` and the sequence diagram `userSequenceDiagram.jpg`
  - `ChatParser Input Handling` including the subsections
* Other contributions:
  - Adding the target user profile and personification with the "Martin".
  - Helped define valule propositions

### Contributions to Team Tasks
* Took over responsibility for managing and creating issues for steering team progress.
* Created the first POC application for tracking meal entries and meal options to kickstart the project. 
* Fixed IO Testing issues.
* Left feedback in my PR reviews, resulting in follow-up issues.
* Resolved 18 issues after the Dry-PE run.
* Will help to do the product demo.
* Test the application before submitting.

### Review/Mentoring Contributions
* Reviewed, approved or if necessary requested changes to multiple PRs
* Helped to finish the User profile creation procedure when the previous approach got stuck.
* Helped to SLAP code (e.g. the Feature to print historic consumption bars).
* Initiated calls to help my teammates solve bugs with test cases and create the final Jar.
* Simply had a good time :-) 
