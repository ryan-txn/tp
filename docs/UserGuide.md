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
help
      save meal {Name of Meal} /c{Number of calories}
      add mealEntry {meal}
      _________________________________________________________________________
```
### Meal Management Commands:

#### Save Meal to Meal Menu: `save meal {Name of Meal} /c{Number of calories}`
- Allows user to store a meal with its calories to be used with the add mealEntry command
- Example usage to store a meal of grapes with 400 calories
```
save meal grapes /c400

      Added to options: grapes with 400 calories
      _________________________________________________________________________
```

#### Add Meal Entry for Tracking: `add mealEntry {meal} /c{Number of calories}`
- Adds a meal from the saved meal options to your daily caloric intake.
- After adding the meal, the app will show how the meal affects your progress towards your daily caloric goal.

```
add mealEntry grapes /c400

      Tracked: grapes with 400 calories (at: 2024-10-06T19:44:21.662257300)
      _________________________________________________________________________
```

#### Show List of Available Meal Options: `meal menu`
- Lists all the saved meal options for quick selection when logging your meals.
```
meal menu
      0: chicken pie with 400 calories
      1: grapes with 400 calories
```

### Log Commands:
#### Show Meal History: `log meals {timespan}`
- Displays the history of the meals you've eaten over the chosen timespan.
  log meals
Example Usage
```
log meals
      0: chicken soup with 300 calories
      1: chicken spaghetti with 500 calories
      2: Burger with 400 calories
      3: grapes with 400 calories
```
## FAQ
## Command Summary

| Command                             | Syntax                                        | Description                                                                 |
|-------------------------------------|-----------------------------------------------|-----------------------------------------------------------------------------|
| List all commmands                  | `list commands`                               | Lists all available commands and the proper formatting              
| Save meal to meal options           | `save meal {meal} /c{Number of calories}`     | Prompts for meal name, calories, then confirms saving the meal.              |
| Add meal entry for tracking         | `add mealEntry {meal} /c{Number of calories}` | Adds meal to daily caloric intake and shows progress toward goal.            |
| Show list of available meal options | `meal menu`                                   | Prints all available meals from the saved options list.                      |
| Show meal history                   | `log meals {timespan}`                        | Displays history of meals over the specified timespan.                       |

