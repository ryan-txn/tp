# User Guide for HealthMate

## Introduction

**HealthMate** is a meal and calorie tracking application designed to help users manage their dietary intake, monitor their weight, and track their overall health goals. The app enables users to log meals, track calories, and observe their progress towards a healthier lifestyle.

## Contents

- [Quick Start Guide](#quick-start-guide)
- [Features](#features)
  - [Exit](#exit-bye)
  - [List Commands](#list-commands)
  - [Meal Management Commands](#meal-management-commands)
    - [Save Meal to Meal Menu](#save-meal-to-meal-menu-save-meal-name-of-meal-cnumber-of-calories)
    - [Overwrite Saved Meal in Meal Menu](#overwrite-saved-meal-in-meal-menu-save-meal-name-of-existing-meal-cnumber-of-calories)
    - [Add Meal Entry for Tracking](#add-meal-entry-for-trackingadd-mealentry-meal-cnumber-of-calories-pportions-tyyyy-mm-ddor-add-mealentry-meal-from-meal-menu-or)
    - [Delete meal from meal menu](#delete-meal-from-meal-menu-delete-meal-index-of-meal-in-meal-menu)
    - [Show List of Available Meal Options](#show-list-of-available-meal-options-meal-menu)
    - [Weight Timeline](#weight-timeline-weight-timeline)
    - [Meal Recommender](#meal-recommender-meal-recommendations)
- [Meal Log Commands](#meal-log-commands)
  - [Show Meal History](#show-meal-history-log-meals)
  - [Delete meal from meal log](#delete-meal-from-meal-log-delete-mealentry-index-of-meal-in-the-meal-log)
- [Calorie Progress Commands](#calorie-progress-commands)
  - [Show Calorie Progress for Today](#show-calorie-progress-for-today-show-todaycalories)
  - [Show Historic Calorie Progress](#show-historic-calorie-progress-show-historiccalories-number-of-days-inclu-today)
- [Update your data](#update-your-data)
- [FAQ](#faq)
- [Command Summary](#command-summary)
- [Data Storage and Persistence](#data-storage-and-persistence)
  - [Data Security](#data-security)


## Quick Start Guide

- Welcome to HealthMate! Follow these steps to get started quickly:
- Do note all commands are not case-sensitive, except for special parameters (/c, /p, /t)

**Entering the App**
- Open CLI and Navigate to location of `HealthMate.jar`
- Run the following Command: `java -jar HealthMate.jar`

**Create your user profile**
Input data about yourself needed to compute your ideal daily calorie intake. 
This includes:
- Your height in cm (e.g. 180)
- Your weight in kg (e.g. 80)
- Your gender (either male or female)
- Your age in years (e.g. 20)
- Your personal health goal: 
  - `1` WEIGHT_LOSS
  - `2` STEADY_STATE
  - `3` BULKING
- Whether you your system can print the special characters "░" and "█" 
(The UI of the consumption bar feature will be customized based on your answer).

**Log Your First Meal**
- Enter the meal name and calories.
- Example: `add mealEntry burger /c300`
- The printed consumption bar shows you how much more (or less) you should consume to reach your health goal.

```
add mealEntry burger /c300

  _____________________________________________________________________________
  Tracked: burger with 300 calories (at: 2024-10-29T22:00)
  _____________________________________________________________________________
  % of Expected Calorie Intake Consumed:
  ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-29)
  _____________________________________________________________________________
```
**Get an overview on your calorie consumption**
- Use the "meal log" feature to view your meal entries and track your daily caloric intake.
- Example: Add your first mealEntry and then use `meal log` to assess the meals tracked so far. 

  ```
  meal log
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
### Exit: `bye`
- Exits program

```
bye
      Stay healthy!
      _____________________________________________________________________________
```

### List Commands
Use the `list commands [{command}]` command to view all valid commands and their formats.
- Example usage to list all commands

```
list commands
      _____________________________________________________________________________
      Use `list commands <command>` to view a command's syntax
      _____________________________________________________________________________
      update userdata 
       update userdata
      _____________________________________________________________________________
      current userdata 
       current userdata
      _____________________________________________________________________________
      list commands 
       list commands
      _____________________________________________________________________________
      meal log 
       meal log
      _____________________________________________________________________________
      add mealEntry 
       add mealEntry {meal name from menu} OR add mealEntry [{name}] /c{calories} [/p{portions}] [/t{Date in YYYY-MM-DD}]
      _____________________________________________________________________________
      delete mealEntry 
       delete mealEntry {index of meal in the meal log}
      _____________________________________________________________________________
      meal menu 
       meal menu
      _____________________________________________________________________________
      save meal 
       save meal {meal name} /c{number of calories}
      _____________________________________________________________________________
      delete meal 
       delete meal {index of meal in meal menu}
      _____________________________________________________________________________
      show todayCalories 
       show todayCalories
      _____________________________________________________________________________
      show historicCalories 
       show historicCalories {Number of Days inclu. Today}
      _____________________________________________________________________________
      meal recommendations 
       meal recommendations
      _____________________________________________________________________________
      weight timeline 
       weight timeline
      _____________________________________________________________________________
      bye 
       bye
      _____________________________________________________________________________
```

List Commands with command parameter:

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
- Example usage to store a meal of pizza with 300 calories

```
save meal pizza /c300
      _____________________________________________________________________________
      Added to options: pizza with 300 calories
      _____________________________________________________________________________
```

#### Overwrite Saved Meal in Meal Menu: `save meal {Name of existing Meal} /c{Number of calories}`
- Allows user to update an existing meal option with a new calorie number

```
save meal soup /c300
      _____________________________________________________________________________
      Added to options: soup with 300 calories
      _____________________________________________________________________________
save meal soup /c200
      _____________________________________________________________________________
      Duplicate meal found: soup
      Updated existing meal with new meal specifics!
      _____________________________________________________________________________
meal menu
      _____________________________________________________________________________
      1: pizza with 400 calories
      2: soup with 200 calories
      _____________________________________________________________________________
```

#### Add Meal Entry for Tracking:`add mealEntry {meal} /c{Number of calories} /p{portions} /t{YYYY-MM-DD}`or `add mealEntry {meal from meal menu}` or
- Adds a meal from the saved meal options to your daily caloric intake.
- After adding the meal, the app will show how the meal affects your progress towards your daily caloric goal.

Log a meal with calories:

```
add mealEntry grapes /c100
      _____________________________________________________________________________
      Tracked: grapes with 100 calories (at: 2024-11-03)
      _____________________________________________________________________________
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      Current Calories Consumed: 100
      % of Expected Calorie Intake Consumed: 
      █░░░░░░░░░░░░░░░░░░░░░░░░░░░░|   4%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-11-03)
      _____________________________________________________________________________
```

Log a meal for a specific day in the past:

``` 
add mealEntry pizza /c300 /t2024-10-30
      _____________________________________________________________________________
      Tracked: pizza with 300 calories (at: 2024-10-30)
      _____________________________________________________________________________
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      Current Calories Consumed: 300
      % of Expected Calorie Intake Consumed:
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-30)
      _____________________________________________________________________________
```

Shorcut: log a meal without a name and only calories.

```
add mealEntry /c200
      _____________________________________________________________________________
      Tracked: Meal with 200 calories (at: 2024-11-11)
      _____________________________________________________________________________
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      Current Calories Consumed: 1300
      % of Expected Calorie Intake Consumed: 
      █████████████░░░░░░░░░░░░░░░░|  46%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-11-11)
      _____________________________________________________________________________

```

Shortcut: log a presaved meal from the list of meal options (no calories needed).

```
add mealEntry pizza
Getting info from meal options...
      _____________________________________________________________________________
      Tracked: pizza with 300 calories (at: 2024-11-03)
      _____________________________________________________________________________
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      Current Calories Consumed: 400
      % of Expected Calorie Intake Consumed: 
      ████░░░░░░░░░░░░░░░░░░░░░░░░░|  14%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-11-03)
      _____________________________________________________________________________
```


#### Delete meal from meal menu: `delete meal {index of meal in meal menu}`
- Deletes meal option from the meal menu at the specified index
- To identify the right index consider running `meal menu` beforehand
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

#### Weight Timeline: `weight timeline`
- Returns a graph of weight changes over time 
- Requires users to have saved sufficient weight entries of different values using the `update userdata` command
before being able to display a graph of weight in kilograms over time. Graph is normalized to the Max and minimum 
  weight values hence resolution of graph decreases if the gap between minimum and maximum weight is unrealistically 
  large 

```
weight timeline
Weight Timeline
170.0 |                                      *                      
165.5 |                                      *                      
161.0 |                                      *                      
156.5 |                                      *                      
152.0 |                                      *                      
147.5 |                                      *                      
143.0 |                                      *                      
138.5 |                                      *                      
134.0 |                                      *                      
129.5 |                                      *                      
125.0 |                                      *                      
120.5 |                                      *                      
116.0 |                          *           *                      
111.5 |                          *           *                      
107.0 |                          *           *                      
102.5 |                          *           *                      
 98.0 |                    *     *           *                      
 93.5 |                    *     *           *                      
 89.0 |                    *     *           *                      
 84.5 |        *           *     *           *                      
 80.0 |  *     *     *     *     *     *     *     *     *     *    
       ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- 
       11-09 11-09 11-09 11-09 11-09 11-09 11-09 11-09 11-09 11-09 
```


#### Meal Recommender: `meal recommendations`
- Returns recipes that suit your health goal

```
meal recommendations
      _____________________________________________________________________________
      Recommended recipes for your health goal
      Veggie Wrap with Hummus: 361 calories
      Protein: 12g
      Carbs: 50g
      Fat: 14g
      Fiber: 8g
      1 teaspoon extra-virgin olive oil
      ½ small zucchini, sliced
      ½ medium red bell pepper, sliced
      ¼ small red onion, sliced
      ½ teaspoon dried oregano
      Pinch of salt
      2 whole-grain wraps
      ¼ cup hummus
      ½ cup baby spinach
      2 tablespoons crumbled feta cheese
      4 black olives, sliced
```

### Meal Log Commands:

- Meal entries are managed in the meal log. With it user's can view their tracked meals.

#### Show Meal History: `meal log`
- Displays the log of all meal entries along with their Timestamp in Date Time format.
- Example Usage

```
meal log
      _____________________________________________________________________________
      1: pizza with 300 calories (at: 2024-11-01)
      2: late dinner with 300 calories (at: 2024-11-02)
      3: salad with 200 calories (at: 2024-11-03)
      4: supper with 100 calories (at: 2024-11-04)
      _____________________________________________________________________________

```

#### Delete meal from meal log: `delete mealEntry {index of meal in the meal log}`
- Deletes meal entry from the meal log at the specified index
- Note: depending on your system the bars might look different
- Example Usage

```
delete mealEntry 1
      _____________________________________________________________________________
      Deleted entry: Cheeseburger with 900 calories (at: 2024-10-30T11:00)
      _____________________________________________________________________________
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      Current Calories Consumed: 500
      % of Expected Calorie Intake Consumed: 
      █████░░░░░░░░░░░░░░░░░░░░░░░░|  18% |░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-30)
      _____________________________________________________________________________
```
### Calorie Progress Commands:
#### Show Calorie Progress for Today: `show todayCalories`
- Prints a Calorie Progress Bar to represent Today Calorie Progress
- Note: depending on your system the bars might look different
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
- Combines global and local view on eating patterns via the progress bar and details such as the meal with the highest calories.
- Example Usage (Note: depending on your system the bars might look different):

```
show historicCalories 10
      _____________________________________________________________________________
      Ideal Daily Caloric Intake: 2865
      _____________________________________________________________________________
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-25)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-26)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-27)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-28)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-29)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-30)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-10-31)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-11-01)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-11-02)
      ███░░░░░░░░░░░░░░░░░░░░░░░░░░|  11%|░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ (2024-11-03)
      Stats over past 10 days
      Total Calories Consumed: 3000
      Total Ideal Calories: 28650
      Percentage of Total Ideal Calories : 10.0%
      Day With Heaviest Meal: 2024-10-24
      Heaviest Meal Consumed: burger with 300 calories (at: 2024-10-25)
      Meals Consumption's Percentage of Daily Ideal Calories: 10.0%
      _____________________________________________________________________________
```


### Update your data
Your goal or your body weight changed? By running the `update userdata` you can update the specifics of your profile. 

```
_____________________________________________________________________________
update userdata
      Create your profile: please enter...
      Height in cm (e.g. 180):
```

If you want to see the specifics for which your ideal calorie consumption is calculated 
run the `current userdata` command. 

```
current userdata
      _____________________________________________________________________________
      Here is your current user data:
      Height: 182.0cm
      Weight: 80.0kg
      Gender: male
      Age: 20
      Health Goal: BULKING
      Ideal Daily Caloric Intake: 2688.0
      Recorded at: 2024-11-12 09:01:41
      Is able to see special chars: true
      _____________________________________________________________________________

```

## FAQ
## Command Summary

### Legend
[] = optional parameter
{} = parameter

| Command                                    | Syntax                                                                                                 | Description                                                                                                   |
|--------------------------------------------|--------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| List all commmands                         | `list commands` or `list commands [{command}]`                                                         | Lists all available commands and the proper formatting.                                                       
| Save meal to meal options                  | `save meal {meal} /c{Number of calories}`                                                              | Prompts for meal name, calories, then confirms saving the meal.                                               |
| Add meal entry for tracking                | `add mealEntry {meal} [/c{Number of calories}] [/p{Number of portions}] [/t{timestamp in YYYY-MM-DD}]` | Adds meal to daily caloric intake and shows progress toward goal.                                             |
| Show list of available meal options        | `meal menu`                                                                                            | Prints all available meals from the saved options list.                                                       |
| Show past meals                            | `meal log`                                                                                             | Displays history of meals with timestamp and calories.                                                        |
| Delete meal from meal menu                 | `delete meal {index of meal in meal menu}`                                                             | Deletes meal option from the meal menu at the specified index.                                                |
| Delete meal entry from meal log            | `delete mealEntry {index of meal in the meal log}`                                                     | Deletes meal entry from the meal log at the specified index and shows effect on the days progress toward goal. |
| Show Calorie Progress for Today            | `show todayCalories`                                                                                   | Prints a Calorie Progress Bar to represent Today Calorie Progress                                             |
| Show Historic Calorie Progress             | `show historicCalories {Number of Days inclu. Today}`                                                  | Prints Calorie Progress Bars & Various Stats to represent Historical Calorie Progress                         |
| Add and Update new User Entry to Save File | `update userdata`                                                                                      | Asks user for new User data to update in save file.                                                           |
| Show Most Recent User Data Entry           | `current userdata`                                                                                     | Prints the most recent User Data from the save file. Prints an error if none found.                           |
| Exit                                       | `bye`                                                                                                  | Closes program after saving data                                                                              |
| Display weight timeline                    | `weight timeline`                                                                                      | Creates a graph of up to the last  10 weight entries over time if there is significant changes.               |
| Meal recommendation command                | `meal recommendations`                                                                                 | Returns a list of ready recipes for a user based on their HealthGoal                                          |

## Data Storage and Persistence
HealthMate stores your meal logs, meal options, and user profile data in CSV files located in a folder named `data` within the application directory. 
This allows your data to persist between sessions, so you won’t lose your progress when you close the application. 

Here’s how HealthMate manages your data in detail:
1. Meal Entries and Options: Your logged meals and saved meal options are stored in `meal_entries.csv` and `meal_options.csv`. 
2. User Profile: Your profile data, including height, weight, age, gender, and health goal, is saved in `user_data.csv`. 

### Data Security
To ensure no data is lost DO NOT manually modify these files or move them out of the directory.
In case you need to transfer your data, we recommend making a copy instead.