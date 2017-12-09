# Test Tool Hub

A set of "Test Tools".

Originally created as a set of 'coding exercises' but collated into a 'tool'. Decided to release to the wild in November of 2016.

If you use this code in your own projects, you do so at your own risk, no warranty is provided or implied by release of this source. Do what you will.

https://en.wikipedia.org/wiki/MIT_License


* http://www.eviltester.com
* http://www.seleniumsimplified.com
* http://www.javafortesters.com
* http://www.compendiumdev.co.uk
* Twitter: [@eviltester](https://twitter.com/eviltester)


## Basic Functionality

- CounterStrings - for testing input field lengths
- Strings - generate a string of characters between to character values
- Typer - a Robot which will 'type' using operating system keyboard events, a string in the input box **New in 1.2**
- Canned Text Tree - handy, easy to use data
- Launch URLs - configurable set of URLs to launch in a browser
- HTML Comments - GET a URL, report HTML comments in the page source
- Binary Chop - a support tool for performing binary chops **New in 1.3**
- Config - configure parts of the tool **New in 1.3**

## CounterStrings

Type in the `Length:` of counterstring.

Defaults to `"*"` as the counterstring delimiter but you can change this to whatever you want.
 
and press:

- `Create` to create counterstring in the text view
- `Copy` to copy the text view to the clipboard
- `=>` generate counterstring directly to the clipboard
- `Length?` tells you the length of the text in the text view
- `Clear` - clear the text view
- `Robot` - generates the counterstring as it types it into wherever the cursor is. So when you click this button you see a countdown of 5 seconds which gives you time to click on the field you want the counterstring to be typed into. Can give a different effect than using the clipboard on some systems.

This uses a 'forward' counter string generation algorithm so it is able to generate incredibly long counterstrings for the Robot without pre-generating, i.e. it builds the string as it goes, rather than create the string, then reverse it (reverse counterstring).

Reverse counterstring algorithms need as much memory as the string and then room to reverse it, so for long strings this can be a lot.

Clearly for clipboard, we have to generate, then add to clipboard.

So this only adds value for the 'Robot' at the moment.

 

## Robot Typer

Given a set of text in the memo field.

Robot Typer will attempt to type it into whatever field you put the cursor in.

Just like the counterstring robot you have 5 seconds countdown to put your cursor in the correct place.

Then typing starts.

Press [Robot] button, or close the dialog to stop any typing currently taking place.

- Not all characters can be typed.
    - Any characters which could not be typed are shown in an alert when finished and are added to the clipboard. 
- Currently the Shift mappings are hard coded and might not match your keyboard layout.
   - to amend the shift mappings, use the [Config] dialog
   - shift mappings are a string of character pairs e.g. "!1@2£3"
      - a pair would be "!1"
      - "!" is the character we want to see typed
      - "1" is the key, that when pressed when shift is pressed, will output "!"
      - Upper case alpha chars are handled automatically

## Strings

Generate a string from ascii value `First Value:` to `Second Value:`.

I say 'ascii value' but of course I mean unicode character.

e.g. http://www.ascii-code.com/ , http://unicode-table.com/en/

so 48 to 57 would give "0123456789"

- `Create` generate and add to text view (warning text view might crash with some characters)
- `=>` generate directly to clipboard
- `Copy` copies the text view to clipboard
- `?` link an ascii table web site - currently http://www.ascii-code.com/
- `Clear` clear the text view
  


## Canned Text Tree

A tree of canned text - double click to copy to clipboard.


## Launch URLs

A set of 'handy urls' that you use during testing. Suggested use: environments, system urls, etc.

### How to Configure Launcher URLS

- create a `\config` folder at the root of the app
- create a `\launcher` subfolder
- have a file for each set of launcher urls e.g. `Social_Media_Urls.txt`
- file format is `name = url`

e.g.

`\config\launcher\Testing_URLs.txt`

~~~~~~~~
java_for_testers = http://www.javafortesters.com
eviltester = http://www.eviltester.com
Selenium_Simplified = http://www.seleniumsimplified.com
Compendium_Developments = https://www.compendiumdev.co.uk
~~~~~~~~


## HTML Comments

- Given a URL
- visit page
- extract obvious comments from page and display in text view
- no errors are reported and no progress is shown
 
 
 ## Binary Chopifier
 
 Given a 'from' value and a 'to' value. Create values to try to find a value between them (or below) without trying random values or incrementing by X. A binary chop search.
 
 The Binary Chopifier shows a report with the binary chop values you can use.
 
 The values on the left are for the middle towards the upper value.
 
 The values on the right (in brackets) are for the middle towards 0.
 
 e.g.
 
 ~~~~~~~~
 Start: 1024
 End: 2048
 
 Chop: value (diff)
 ------------------
 1: 1536 (512)
 2: 1792 (256)
 3: 1920 (128)
 4: 1984 (64)
 5: 2016 (32)
 6: 2032 (16)
 7: 2040 (8)
 8: 2044 (4)
 9: 2046 (2)
 10: 2047 (1)
 11: 2048 (0)
 ~~~~~~~~
 
 - above is binary chop from 1024 to 2048
 - start with `1536` which is between `1024` and `2048`
    - if that was too high, then try `512`
       - if `512` was too low then try a binary chop between `512` and 1024`
 - if that was too low, then try `1792`, then try `1920` etc.
    - when you reach a point that is too high, then try binary chopping that, with the last 'low' value you tried.
    
## Config

- Config lets you amend the Shift Modified characters for the Robot Typer. See the instructions in Robot Typer for more information.      

## TODO

- CounterStrings TODO:
    - write to a file
    
- Strings TODO:
    - more configurable ranges
    - canned ranges
    
- Canned Text Tree TODO:
    - configurable via files in config folder
    - re-use BugMagnet config for handy set of strings
    - generate data from spec in the config

- Launch URLs TODO:
    - config browser
        
- General
    - split out data generation into libraries as maven includes
    - more @Test code
    - test it
    - report errors and exceptions to the GUI or a log file (essential for HTTP code which just swallows any errors)
    
    
## Release Notes
      
### 20171209 9th December 2017 - version 1.2

- internal code tidy up
- Robot Typer now reports characters it was unable to type, these are added to the clipboard when typing finishes
- Added a [Config] button, which opens a dialog where the shifted characters uses by the Robot Typer can be configured
- Added a GUI for the Binary Chopper report - this was previously only accessible if you ran the unit tests
         
### 20171208 8th December 2017 - version 1.2

- Reinstated the Robot Typer
- Robot Typer now handles more characters
- All chars may not work on every machine as mapped to my machine
- currently no way to override the shift character mappings
- Robot typer uses same threading model as counterstring
- currently only see typing exceptions if run as `java -jar ...`

      
### 20171015 15th October 2017

- Amended Robot counterstring generator to not have a hanging thread that keeps going
 - used service for JavaFx
- click on 'Robot' button during counterstring generation while typing cancels the generation and typing
- added handlers to prompt for application exit
- added handlers to stop counterstring service when window hidden or application closes
- Added - Y/N Exit App dialog
      
### 20161112
         
- started using this in videos for youtube, so other people saw it
- people asked how to get hold of it
- packaged into a jar and amended package structure
- wrote readme.md to explain a bit
- getting ready to release to github

### prior to 20161112

- private source managed on xp-dev.com for Alan Richardson use only
- originally created as a set of coding exercises and experimentation with Java FX
- code is not intended to be 'good' example of code, this has been hacked together.