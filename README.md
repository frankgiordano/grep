# Grep    

Implementation of a basic grep linux like command.
  
This program follows GNU grep logic flow. It reads raw data into a large buffer, searches the buffer for a set of matches. 

The program will search in two ways:

- indexOf() search (BasicSearch.java)
- Boyer-Moore-Horspool (AdvanceSearch)  
    
# Usage  
  
    java -jar grep.jar <agr1> <arg2>  
  
    arg1 is file or directory  
    arg2 is search pattern   
    
Examples:   
  
From the project's root directory you can execute the following commands:

    java -jar ./target/grep.jar ./samples/text touched
    and lose them. Religion is philosophy touched with emotion â?? Philosophy
    his ears and cut it half-off, as Peter touched up the servant of the
    touched with the essence of the thing against which we strive.â?? And Bill  

    java -jar ../target/grep.jar . touched
    text:and lose them. Religion is philosophy touched with emotion â?? Philosophy
    text:his ears and cut it half-off, as Peter touched up the servant of the
    text:touched with the essence of the thing against which we strive.â?? And Bill
    text2://*touched
  
# Build  
  
From project's root directory, execute the following command:
  
    mvn clean package    
    
target directory contains grep.jar   
  
## Requirements  
  
    Java 11 and above   
    Maven       
  
