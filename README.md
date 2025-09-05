# Minecraft-Dye-Mixing
An exhaustive searcher to find all colors of dyed leather armor that are obtainable through crafting in Minecraft JE.

Results of the search are in the files named Primary.txt, Secondary.txt, Tertiary.txt, etc.

Primary colors are colors that can be made in a single dyeing operation (undyed leather armor + 1-8 dyes)  
Secondary colors require at least 2 dyeing operations (leather armor dyed a primary color + 1-8 dyes)  
Tertiary colors require at least 3 dyeing operations (secondary color + 1-8 dyes), etc.  
Undenary.txt is included for the sake of completeness: it is empty, because there are no undenary colors - All obtainable colors can be made in 10 dyeing operations or less

Data in the files is split into three space-separated columns. The first is the result color, as 0xRRGGBB. The second is the base color that the armor is before dyeing, also as 0xRRGGBB. The third is the dyes used, represented by a string of single-letter color abbreviations (order doesn't matter). Since primary colors have no base color, that column is completely absent from Primary.txt

I made up the color abbreviations, as follows. If you're wondering about the weird color order, blame the wiki.  
White - W  
Orange - O  
Magenta - M  
Light Blue - b  
Yellow - Y  
Lime - L  
Pink - P  
Gray - A  
Light Gray - a  
Cyan - C  
Purple - P  
Blue - B  
Brown - N  
Green - G  
Red - R  
Black - K  

The search algorithm should always find an "optimal" result - they will always be optimized for the fewest number of dyeing operations first, then the number of dyes used in the final dyeing operation, then the number of dyes in the second-to-last dyeing operation, etc. This means it's possible that there are ways to make a color that take fewer dyes overall (honestly probably should have optimized for that, but it would have been more work), and there are likely many equivalently optimal ways to get a color.

The total runtime, across all 11 searches, was about 2-3 days (the vast majority being for secondary and tertiary colors)  
Final results:  
Primary: 606221  
Secondary: 5013556  
Tertiary: 74250  
Quaternary: 12629  
Quinary: 3167  
Senary: 800  
Septenary: 216  
Octonary: 66  
Nonary: 30  
Denary: 5  
Undenary: 0  
Total: 5710940

Final Note: The functions generateComposites and generateAllComposites, which are commented out, were my original method for generating composite (not primary) colors. They gave all the correct resulting colors, but weren't optimized to use the fewest dyes possible. As a nice bonus from the switch, generateComposites2 is actually significantly faster! I decided to leave them in because why not, it's mildly interesting.

Final Final Note: The way the program's set up, if you ran it again and told it to output to the same file, it would just append all the data to the end of the file, so make sure to delete the file or change the name if you want to run it yourself.
