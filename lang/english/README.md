# litnet-book-saver

<p align="center">
  <a href="https://github.com/Romancha/litnet-book-saver/tree/master/#litnet-book-saver">Русский</a>
  <span>English</span> |
</p>

## Disclaimer

:warning: ***For research/educational purposes only, the use of this code is your responsibility. Do not violate copyright!***<br>
***I'm not responsible for the use of this program.<br>***
***Before any usage please read the [Litnet rules](https://litnet.com/).***

## Overview
A simple litnet.com parser to save books in html

## Getting Started
Java, Google Chrome must be installed
### Run
1. Download litnet-book-save.jar from [last release](https://github.com/Romancha/litnet-book-saver/releases). <br>
2. Run the command with the specified url to the book first page
```
java -jar litnet-book-saver-{version}.jar -u url_to_book_first_page
```
3. Wait for all pages to be saved, and the browser to close
4. Check result file *resultBook.html*

If you need to save a private book, you can use the <i> -w </i> flag to specify the number of seconds to wait before parsing, at this time log in your account.
### Command options
|Command short|Command long|Description|
|---|---|---|
|-h|--help|Print help|
|-u|--url <arg>|Litnet book url. First page|
|-f|--file <arg>|File name to save. Should be in html format|
|-w|--wait <arg>| Wait seconds before start parse. On this delay you can login on you Litnet account, for private books|
|-dn|--delayMin <arg>|Min delay (seconds) before parse next page|
|-dx|--delayMax <arg>|Max delay (seconds) before parse next page|

