# Caesar (intelliJ IDEA)
Console turn based game, where you get to play as Gaius 
Julius Caesar. Game starts from his famous Conquest of Gal 
(Gallic Wars). Gameplay consist of simple text inputs and game 
progression is limited by the amount of choices you have 
during each turn. 

There is always a random chance that can influence your choices.
Such as your soldiers getting sick, poor visibility during
foggy weather, low morale due to hunger and etc.

Game is created as a faculty project in Slovak Technical
University Faculty of Informatics and Information
Technologies (STU FIIT). 

Created in Visual Studio Code, further developed in Eclipse IDE 
for Java, currently being developed in IntelliJ IDEA Ultimate.

## Refactoring

This project also served as a Git / Refactoring project for Methods of Engineering
Work subject at faculty.

During development of the game, many refactoring techniques were
applied to improve readability, efficiency and to make code
more idiomatic. Here is a list of them with actual examples 
from this project:

##### 1. Extract method:

**Before:**

```
private String wrapContent(String content) {
 		
        int contentLength = content.length();
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(content);
        
        if (contentLength <= this.maxLength)
            return stringBuilder.toString();
        
        for (int i = this.maxLength; i < contentLength; i += this.maxLength) {
                
            for (int j = i; j >= 0; j--) {
                
                if (Character.isWhitespace(stringBuilder.charAt(j))) {
                    stringBuilder.setCharAt(j, '\n');
                    i += j - i + 1;
                    break;
                }
            }
        }
        
        return stringBuilder.toString();	   
}
```
**After:**

```
private String wrapContent(String content) {
 		
    int contentLength = content.length();
    
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(content);
    
    if (contentLength <= this.maxLength)
        return stringBuilder.toString();
    
    insertLineBreaks(stringBuilder, contentLength);
    
    return stringBuilder.toString();
 }
 	
private void insertLineBreaks(StringBuilder stringBuilder, int contentLength) {
    
    for (int i = this.maxLength; i < contentLength; i += this.maxLength) {
        
        for (int j = i; j >= 0; j--) {
            
            if (Character.isWhitespace(stringBuilder.charAt(j))) {
                stringBuilder.setCharAt(j, '\n');
                i += j - i + 1;
                break;
            }
        }
    }
}
```

##### 2. Remove Middle Man:

**Before**:

```
if (game.getEnemyLocation().equals(game.getPlayerLocation())) {
    ...
}
```
**After**:

```
if (game.getEnemy().isAtPlayer()) {
    ...
}
```
