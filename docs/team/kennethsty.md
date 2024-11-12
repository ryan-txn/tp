# Project Portfolio Page - kennethSty

## Overview
HealthMate is a meal and calorie tracking application designed to help users manage their dietary intake, 
monitor their weight, and track their overall health goals. 
The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Summary of Contributions

### Code Contributed
[Link to code contributions](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=Kenneth&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=kennethSty&tabRepo=AY2425S1-CS2113-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

*Created foundational application design, including packaging, and contributed to the following classes:*
- `Meal` and `MealEntry` - initial version and design improvements
- `MealList` and `MealEntriesList` - initial version and refinements
- `HistoryTracker` - bug fixes and minor contributions
- `UI`, `Logging`, `User`, `UserHistoryTracker`, and `ConsumptionStatistics` - initial implementations and design enhancements
- `Pair` and `CommandPair` - initial implementations

### Enhancements Implemented

1. **`Meal` and `MealEntry` Classes**
  - Established inheritance structure for `Meal` and `MealEntry`.
  - Added `Optional` handling for unnamed entries and low-level input parsing.

2. **`MealList` and `MealEntriesList` Classes**
  - Set up classes with polymorphism for `Meal` and `MealEntry` to enable efficient list handling.

3. **User- & Storage-Related Enhancements (`User`, `UserHistoryTracker`, `HistoryTracker`)**
  - Created `User` class structure, added `checkForUserData()` and `loadUserEntries()` for user initialization.
  - Refactored `askForUserData` to improve exception handling and support re-entry without restarting.
  - Assisted in refactoring `UserHistoryTracker` to handle data corruption.
  - Enhanced `HistoryTracker` to allow users to revert corrupted data.

4. **`ChatParser` Class Contributions**
  - Designed `ChatParser` and implemented command preprocessing, command routing (via `run` and `multiCommandParsing`) and command logging.
  - Developed command logic for `meal log`, `meal menu`, `save meal`, and `add mealentry`.

5. **`UI` Class Implementation**
  - Built static methods for standardized output, structured feedback, and added methods for displaying logged meals.
  - Implemented the consumption bar and its applications to visualize user progress toward health goals, with system-dependent customization for character support.
  - Customized `UI` for system compatibility with special characters for a visual consumption bar.

6. **Unit Testing**
  - Created `ChatParserTest` with foundational test methods (`setOutputStream`, `restoreStream`, `compareChatParserOutput`).
  - Added 7 tests for input handling, meal tracking, historical data retrieval, and timestamp validation.


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
  - Adding the target user profile and personification.
  - Added non-functional requirements.
  - Helped define value propositions.

### Contributions to Team Tasks
* Took over responsibility for managing and creating issues for steering team progress (opened 30 issues, closed 46 myself).
* Created the first POC application for tracking meal entries and meal options to kickstart the project. 
* Fixed IO Testing issues.
* Resolved 18 issues after the Dry-PE run.
* Will help to do the product demo.
* Performed testing. 

### Review/Mentoring Contributions
* Reviewed, approved or if necessary requested changes to several PRs every week (authored 42 PRs, reviewed 30)
* Helped to finish the user profile creation procedure when the previous approach got stuck.
* Helped to SLAP code (e.g. the feature to print historic consumption bars).
* Initiated calls to help my teammates solve final bugs before creating the final Jar.
* Simply had a good time :-) 
