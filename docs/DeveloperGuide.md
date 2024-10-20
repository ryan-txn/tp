# Developer Guide

## Acknowledgements
## Design & implementation
### High Level Class Design
The main classes of this implementation are the following:
- HealthMate
- ChatParser
- User
- MealList
- MealEntriesList
- Meal
- MealEntry
- HistoryTracker
- UI
The following diagram illustrates their associations, methods and attributes.
![High Level CD](images/highLevelClassDiagram.jpg)

#### HealthMate
Entry point to the application is the main function of HealthMate.
The HealthMate class contains a private ChatParser attribute. This attribute's run function initiates, 
after an initial greeting to the user, the interaction process. In this process, 
the user enters commands with additional information into his command line application.
The content of these commands is parsed by the ChatParser. 

#### ChatParser
The ChatParser class, which is instantiated exactly once, manages the overall usage flow via its run() method. 
It contains a MealEntriesList object called MealEntries as well as a MealList object called mealOptions.
Thereby MealEntries contain tracked calorie consumptions. mealOptions tracks possible meals that are presaved by the 
user in order to quickly select from a list of meals for which the calories of a portion are already saved. 
Therefore, mealEntries and mealOptions are the primary objects with which the user interacts through his command line. 
The ChatParser class orchestrates the effects of the users command line prompts, ensuring no unintended changes are done.
Thereby, we distinguish between basic command prompts such as "bye" which are handled directly within run() and more
sophisticated commands that require several tokens and are therefore abstracted into the multiCommandParsing() method,
which is called within the run() method. 
To save the changes temporarily the ChatParser object also has a HistoryTracker object which facilitates the process of 
storing User data, mealEntries data and mealOptions data to their corresponding files. Besides storing data, 
loading existing data from the files, once another usage session is initiated is conducted by the HistoryTracker as well.

#### MealList
The MealList class contains a private ArrayList of Meal object.
Further, it encapsulates behaviour to operate on this list of meals. Most notably, 
this adding or deleting a Meal to/from the list. 

#### MealEntriesList
The MealEntriesList class extends the MealList class. It overwrites the extractAndAppendMeal(...) method,
and additionally includes methods specifically tailored to providing helpul user feedback, as the MealEntries stored 
within its instance, signify the users calorie consumption. 
As a MealEntry object differs from a Meal object by the additional timestamp attribute, this includes
computations based on the time dimension. More specifically, the printDaysConsumptionBar() uses the UIs class'
methods in the background to visualize the percentage of a certain days total consumption versus the idaeal consumption
of a User class. 

#### Meal
The Meal class encapsulates the concept of a meal. As the purpose of this application 
is to track calorie consumption, this consists of a mandatory calorie entry. The meal's name attribute, 
is however an Optional<String> allowing a case, where no meaningful label can be attached to a certain consumption.

#### MealEntry
The MealEntry class extends the meal class and contains an additional field timestamp. 
This distinction was made, as objects of the Meal class will represent possible meal options to choose form, 
while a mealEntry is a concrete calorie consumption the user wants to track. The latter makes a timestamp indispensible. 

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
