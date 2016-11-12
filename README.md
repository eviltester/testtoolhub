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