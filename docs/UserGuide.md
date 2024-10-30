# User Guide for HealthMate

## Introduction

**HealthMate** is a meal and calorie tracking application designed to help users manage their dietary intake, monitor their weight, and track their overall health goals. The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Quick Start Guide

Welcome to HealthMate! Follow these steps to get started quickly:

**Entering the App**
- Open CLI and Navigate to location of `HealthMate.jar`
- Run the following Command: `java -jar HealthMate.jar`
- 
**Create you user profile**
Input data about yourself needed to compute your ideal daily calorie intake. 
This includes:
- Your height in cm (e.g. 180)
- Your weight in kg (e.g. 80)
- Your gender (either male or female)
- Your age in years (e.g. 20)
- Your personal ealth goal (either WEIGHT_LOSS, STEADY_STATE, BULKING)

**Log Your First Meal**
- Enter the meal name and calories.
- Example: `add mealEntry grapes /c400`
- The printed consumption bar shows you how much more (or less) you should consume to reach your health goal.

**Get an overview on your calorie consumption**
- Use the "Log Meals" feature to view your meal entries and track your daily caloric intake.
- Example: Add your first mealEntry and then use `log meals` to assess the meals tracked so far. 
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
  
**Track your daily progress**
- Based on your user data and your health goal we computed your ideal daily calorie intake. 
- When you add (or delete) a mealEntry, we print a consumption bar showing how much more/less you should consume.
- Interpreting the consumption bar:
  - The percentage in the middle shows how much percent of your ideal calorie consumption you have consumed so far.
  - The date on the right shows you the date for which this percentage is calculated.
- Example: On the 29th of October 2024 you have consumed 11% of your ideal calorie intake. 
```
  % of Expected Calorie Intake Consumed:
  ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-29)
```
  
  
  
**Store Common Meals**
- Save frequently eaten meals to your meal menu for quick access
- Example: `save meal pizza /c800` saves pizza with 800 calories to your meal menu
- Add meals from the meal menu without specifying the calories
- Example: `add mealEntry pizza`

**Track Meal Menu**
- Use the `meal menu` command to view your saved meal options
- Example: `meal menu`



Enjoy your journey towards a healthier lifestyle with HealthMate!
## Features
### Legend
[] = optional parameter
{} = parameter
### List Commands
Use the `list commands [{command}]` command to view all valid commands and their formats.
- Example usage to list all commands
```
list commands
      _____________________________________________________________________________
      Use `list commands <command>` to view a command's syntax
      _____________________________________________________________________________
      list commands
      _____________________________________________________________________________
      show todayCalories
      _____________________________________________________________________________
      update userdata
      _____________________________________________________________________________
      show historicCalories
      _____________________________________________________________________________
      save meal
      _____________________________________________________________________________
      log meals
      _____________________________________________________________________________
      meal menu
      _____________________________________________________________________________
      delete mealEntry
      _____________________________________________________________________________
      add mealEntry
      _____________________________________________________________________________
      delete meal
      _____________________________________________________________________________
```
List Commands with command parameter
```
list commands delete meal
      _____________________________________________________________________________
      Command: delete meal
      Format: delete meal {index of meal in meal menu}
      Description: Deletes meal option at the specified index from the meal menu
```

### Meal Management Commands:

#### Save Meal to Meal Menu: `save meal {Name of Meal} /c{Number of calories}`
- Allows user to store a meal with its calories to be used with the add mealEntry command
- Example usage to store a meal of grapes with 400 calories
```
save meal grapes /c400
      _________________________________________________________________________
      Added to options: grapes with 400 calories
      _________________________________________________________________________
```

#### Add Meal Entry for Tracking: `add mealEntry {meal} /c{Number of calories}` or `add mealEntry {meal from meal menu}`
- Adds a meal from the saved meal options to your daily caloric intake.
- After adding the meal, the app will show how the meal affects your progress towards your daily caloric goal.

```
add mealEntry grapes /c400

      Tracked: grapes with 400 calories (at: 2024-10-06T19:44:21.662257300)
      _________________________________________________________________________
```
```
add mealEntry pizza
Getting info from meal options...
      _________________________________________________________________________
      Tracked: pizza with 400 calories (at: 2024-10-14T18:00)
      _________________________________________________________________________
```

#### Delete meal from meal menu: `delete meal {index of meal in meal menu}`
-  Deletes meal option from the meal menu at the specified index
- Example usage
```
delete meal 1
      _________________________________________________________________________
      Deleted option: pizza with 400 calories
      _________________________________________________________________________
```
#### Show List of Available Meal Options: `meal menu`
- Lists all the saved meal options for quick selection when logging your meals.
```
meal menu
      _________________________________________________________________________
      1: pizza with 400 calories
      2: ciffbar with 300 calories
      _________________________________________________________________________
```

### Log Commands:
#### Show Meal History: `log meals`
- Displays the history of the meals you've eaten over the chosen timespan.
  log meals
- Example Usage
```
log meals
      _________________________________________________________________________
      1: burger with 300 calories (at: 2024-10-14T15:00)
      2: pizza with 400 calories (at: 2024-10-14T15:00)
      _________________________________________________________________________
```
#### Delete meal from meal log: `delete mealEntry {index of meal in the meal log`
- Deletes meal entry from the meal log at the specified index
- Example Usage
```
delete mealEntry 1
      _________________________________________________________________________
      Deleted entry: burger with 300 calories (at: 2024-10-14T15:00)
      _________________________________________________________________________
```
### Calorie Progress Commands:
#### Show Calorie Progress for Today: `show todayCalories`
- Prints a Calorie Progress Bar to represent Today Calorie Progress
- Example Usage:
```
show todayCalories
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      Current Calories Consumed: 900
      % of Expected Calorie Intake Consumed: 
      █████████░░░░░░░░░░░░░░░░░░░░|  32% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-29)
      _____________________________________________________________________________

```
#### Show Historic Calorie Progress: `show historicCalories {Number of Days inclu. Today}`
- Prints Calorie Progress Bars & Various Stats to represent Historical Calorie Progress
- Example Usage:
```
show historicCalories 3
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░|   0% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-27)
      ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░|   0% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-28)
      █████████░░░░░░░░░░░░░░░░░░░░|  32% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-29)
      Stats over past 3 days
      Total Calories Consumed: 900
      Total Ideal Calories: 8595
      Percent Target Consumed: 10%
      Day with Max Calories Consumed: 2024-10-29
      Calories Consumed: 900
      Percent Target Consumed: 31%
      _____________________________________________________________________________
```

## FAQ
## Command Summary

### Legend
[] = optional parameter
{} = parameter

| Command                             | Syntax                                                                   | Description                                                                                                    |
|-------------------------------------|--------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| List all commmands                  | `list commands` or `list commands [{command}]`                           | Lists all available commands and the proper formatting.                                                         
| Save meal to meal options           | `save meal {meal} /c{Number of calories}`                                | Prompts for meal name, calories, then confirms saving the meal.                                                |
| Add meal entry for tracking         | `add mealEntry {meal} [/c{Number of calories}] [/p{Number of portions}]` | Adds meal to daily caloric intake and shows progress toward goal.                                              |
| Show list of available meal options | `meal menu`                                                              | Prints all available meals from the saved options list.                                                        |
| Show meal history                   | `log meals`                                                              | Displays history of meals with timestamp and calories.                                                         |
| Delete meal from meal menu          | `delete meal {index of meal in meal menu}`                               | Deletes meal option from the meal menu at the specified index.                                                 |
| Delete meal entry from meal log     | `delete mealEntry {index of meal in the meal log`                        | Deletes meal entry from the meal log at the specified index and shows effect on the days progress toward goal. |
| Show Calorie Progress for Today     | `show todayCalories`                                                     | Prints a Calorie Progress Bar to represent Today Calorie Progress                                              |
| Show Historic Calorie Progress      | `show historicCalories {Number of Days inclu. Today}`                    | Prints Calorie Progress Bars & Various Stats to represent Historical Calorie Progress                          |


