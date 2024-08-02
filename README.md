# ASCII art

## Running the program

Best way to run this program is to use sbt in IntelliJ IDEA.

However, you can run program using `sbt run` command with conversion arguments from terminal.
Mandatory arguments are:

`--image <path to image file>` or `--image-random`

and

`--output-file <path to output file>` or `--output-console`

## Example usage

`sbt run --image-random --output-console`

`sbt run --image src/test/assets/globe.png --output-file out/out.txt`

`sbt run --image src/test/assets/globe.png --output-console`

`sbt run --image src/test/assets/globe.png --invert --output-file out/out.txt`

`sbt run --image src/test/assets/globe.png --scale 0.25 --brightness -10 --output-file out/out.txt`

and so on...

## Defined arguments

`--brightness <value>` -> value must be a number - negative or positive

`--custom-table <chatacters sequence>` -> any non empty sequence

`--image <path to image file>` -> supported file formats are *.png and *.jpg

`--image-random` -> generates random image

`--invert` -> inverts image

`--output-console` -> prints output to console

`--output-file <path to output file>` -> prints output to file that ends with *.txt

`--rotate <angle>` -> angle can be any number in multiples of 90 (can be negative too)

`--scale <value>` -> allowed values are 0.25, 1 and 4

`--table <table name>` -> supported names are `bourke` `bourke-short` and `non-linear`

## Running tests

`sbt test`

## Running tests with coverage

`sbt ; clean ; coverage ; test ; coverageReport`