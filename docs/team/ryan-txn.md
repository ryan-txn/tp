# Project Portfolio Page - ryan-txn

## Overview
HealthMate is a meal and calorie tracking application designed to help users manage their dietary intake, monitor their weight, and track their overall health goals. The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Summary of Contributions

### Code Contributed
[Link to code contributions](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=ryan-txn&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=ryan-txn&tabRepo=AY2425S1-CS2113-W12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented
1. **Implement User Data Format**
    * Created a data structure to store user information including height, weight, gender, age, timestamp, and health goal
    * Enables usage for displaying weight changes over time ([ryryry-3302](ryryry-3302.md)) and calculating ideal daily calories ([DarkDragoon](darkdragoon2002))

2. **Implement Saving and Loading of User Data into CSV File**
    * Developed the `UserHistoryTracker` class with methods for:
        - Printing user data
        - Converting user data into comma-separated strings for saving
        - Parsing saved data and converting it to a `UserEntryList` format for easy retrieval and display

3. **Add Test Cases for Saving and Loading of User Data**
    * Created unit tests to ensure correct functionality for saving and loading user data
    * Validates proper conversion to and from CSV format, ensuring data integrity during file operations

4. **Strengthen User Save File from Manual Changes**
    * Added validation checks for each user attribute to detect tampering with the save file
    * Ensures that loaded data adheres to expected formats for all fields, protecting against erroneous or malicious modifications to user data


### Contributions to the User Guide
* Added contents page
* Added details for add mealEntry command


### Contributions to the Developer Guide
* Added Sequence Diagrams for:
    * loadUserEntries() method
    * createFileIfNotExists() method

### Contributions to Team Tasks
* Handled user data architecture
* Contributed to creation of issues

### Review/Mentoring Contributions
* Reviewed and approved multiple PRs
* Resolved merge conflicts where necessary
