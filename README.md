litnet book saver | litnet сохранение книг
=================
## Disclaimer

:warning: ***For research/educational purposes only, the use of this code is your responsibility. Do not violate copyright!***<br>
***I'm not responsible for the use of this program.<br>***
***Before any usage please read the [Litnet rules](https://litnet.com/).***

## Overview
Simple book saver from Litnet to .html

## Getting Started
Google Chrome must be installed
### Run
```
java -jar litnet-book-saver.jar -u url_to_book_first_page
```
Result will be saved to a file resultBook.html
### Command options
|Command short|Command long|Description|
|---|---|---|
|-h|--help|Print help|
|-u|--url <arg>|Litnet book url. First page|
|-f|--file <arg>|File name to save. Should be in html format|
|-w|--wait <arg>| Wait seconds before start parse. On this delay you can login on you Litnet account, for private books|
|-dn|--delayMin <arg>|Min delay (seconds) before parse next page|
|-dx|--delayMax <arg>|Max delay (seconds) before parse next page|

