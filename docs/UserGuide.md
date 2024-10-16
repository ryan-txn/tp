# User Guide for HealthMate

## Introduction

**HealthMate** is a meal and calorie tracking application designed to help users manage their dietary intake, monitor their weight, and track their overall health goals. The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Quick Start Guide
tbd later
## Features

### List Commands
Use the `list commands` command to view all valid commands and their formats.
- Example usage to store a meal of grapes with 400 calories
```
list commands
      _________________________________________________________________________
      Command: list commands
      Format: list commands
      Description: Lists out all available commands
      _________________________________________________________________________
      Command: save meal
      Format: save meal {meal name} /c{number of calories}
      Description: Saves a meal to the meal menu for later use with the add mealEntry command
      _________________________________________________________________________
      Command: log meals
      Format: log meals
      Description: Displays the log of all meal entries along with their Timestamp in Date Time format
      _________________________________________________________________________
      ...
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
#### Delete meal from meal lo: `delete mealEntry {index of meal in the meal log`
- Deletes meal entry from the meal log at the specified index
- Example Usage
```
delete mealEntry 1
      _________________________________________________________________________
      Deleted entry: burger with 300 calories (at: 2024-10-14T15:00)
      _________________________________________________________________________
```
## FAQ
## Command Summary

| Command                             | Syntax                                             | Description                                                       |
|-------------------------------------|----------------------------------------------------|-------------------------------------------------------------------|
| List all commmands                  | `list commands`                                    | Lists all available commands and the proper formatting            
| Save meal to meal options           | `save meal {meal} /c{Number of calories}`          | Prompts for meal name, calories, then confirms saving the meal.   |
| Add meal entry for tracking         | `add mealEntry {meal} /c{Number of calories}`      | Adds meal to daily caloric intake and shows progress toward goal. |
| Show list of available meal options | `meal menu`                                        | Prints all available meals from the saved options list.           |
| Show meal history                   | `log meals`                                        | Displays history of meals with timestamp and calories.            |
| Delete meal from meal menu          | `delete meal {index of meal in meal menu}`         | Deletes meal option from the meal menu at the specified index     |
| Delete meal entry from meal log     | `delete mealEntry {index of meal in the meal log`  | Deletes meal entry from the meal log at the specified index       |

