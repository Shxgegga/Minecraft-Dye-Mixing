import java.util.*;
import java.io.*;

public class Main {
    //String[] colorNames = {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
    public static char[] colorAbvs = {'W', 'O', 'M', 'b', 'Y', 'L', 'P', 'A', 'a', 'C', 'U', 'B', 'N', 'G', 'R', 'K'};
    // Corresponding color codes in 0xRRGGBB format, as reported by the wiki here: https://minecraft.wiki/w/Dye#Color_values
    public static int[] colorCodes = {0xF9FFFE, 0xF9801D, 0xC74EBD, 0x3AB3DA, 0xFED83D, 0x80C71F, 0xF38BAA, 0x474F52, 0x9D9D97, 0x169C9C, 0x8932B8, 0x3C44AA, 0x835432, 0x5E7C16, 0xB02E26, 0x1D1D21};
    
    // Encoding/Decoding color values as ints using bitwise functions
    public static int getR(int color) {
        return (color >> 16) & 0xFF;
    }
    public static int getG(int color) {
        return (color >> 8) & 0xFF;
    }
    public static int getB(int color) {
        return color & 0xFF;
    }
    public static int toColor(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
    // Mixes colors, as per Minecraft's color mixing algorithm, as described here:
    // https://minecraft.wiki/w/Dye#Dyeing_armor
    // Actual code taken from anonymous Pastebin user here, presumably decompiled from Minecraft: https://pastebin.com/GGjQhyFa
    // More readable version written by me: https://pastebin.com/hbNY99Ff 
    public static int mixColors(int[] colors) {
        int numColors = colors.length;
        int totalR = 0, totalG = 0, totalB = 0, totalMax = 0;
        for (int color : colors) {
            totalR += getR(color);
            totalG += getG(color);
            totalB += getB(color);
            totalMax += Math.max(getR(color), Math.max(getG(color), getB(color)));
        }
        int avgR = totalR / numColors;
        int avgG = totalG / numColors;
        int avgB = totalB / numColors;
        float avgMax = (float) totalMax / (float) numColors;
        float maxAvg = Math.max(avgR, Math.max(avgG, avgB));
        if (maxAvg == 0) return 0;
        avgR = (int) ((float) avgR * (avgMax / maxAvg));
        avgG = (int) ((float) avgG * (avgMax / maxAvg));
        avgB = (int) ((float) avgB * (avgMax / maxAvg));
        return toColor(avgR, avgG, avgB);
    }

    public static void generatePrimaries(String filename) throws Exception {
        Set<Integer> generatedColors = new HashSet<Integer>();
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
        for (int i = 0; i < colorCodes.length; i++) {
            generatedColors.add(colorCodes[i]);
            bw.write(String.format("0x%06X %s", colorCodes[i], colorAbvs[i]));
            bw.newLine();
        }
        System.out.println("1 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                int mixedColor = mixColors(new int[]{colorCodes[i], colorCodes[j]});
                if (!generatedColors.contains(mixedColor)) {
                    generatedColors.add(mixedColor);
                    bw.write(String.format("0x%06X %s%s", mixedColor, colorAbvs[i], colorAbvs[j]));
                    bw.newLine();
                }
            }
        }
        System.out.println("2 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    int mixedColor = mixColors(new int[]{colorCodes[i], colorCodes[j], colorCodes[k]});
                    if (!generatedColors.contains(mixedColor)) {
                        generatedColors.add(mixedColor);
                        bw.write(String.format("0x%06X %s%s%s", mixedColor, colorAbvs[i], colorAbvs[j], colorAbvs[k]));
                        bw.newLine();
                    }
                }
            }
        }
        System.out.println("3 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        int mixedColor = mixColors(new int[]{colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l]});
                        if (!generatedColors.contains(mixedColor)) {
                            generatedColors.add(mixedColor);
                            bw.write(String.format("0x%06X %s%s%s%s", mixedColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l]));
                            bw.newLine();
                        }
                    }
                }
            }
        }
        System.out.println("4 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            int mixedColor = mixColors(new int[]{colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m]});
                            if (!generatedColors.contains(mixedColor)) {
                                generatedColors.add(mixedColor);
                                bw.write(String.format("0x%06X %s%s%s%s%s", mixedColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m]));
                                bw.newLine();
                            }
                        }
                    }
                }
            }
        }
        System.out.println("5 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                int mixedColor = mixColors(new int[]{colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n]});
                                if (!generatedColors.contains(mixedColor)) {
                                    generatedColors.add(mixedColor);
                                    bw.write(String.format("0x%06X %s%s%s%s%s%s", mixedColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n]));
                                    bw.newLine();
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("6 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                for (int o = n; o < colorCodes.length; o++) {
                                    int mixedColor = mixColors(new int[]{colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n], colorCodes[o]});
                                    if (!generatedColors.contains(mixedColor)) {
                                        generatedColors.add(mixedColor);
                                        bw.write(String.format("0x%06X %s%s%s%s%s%s%s", mixedColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n], colorAbvs[o]));
                                        bw.newLine();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("7 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                for (int o = n; o < colorCodes.length; o++) {
                                    for (int p = o; p < colorCodes.length; p++) {
                                        int mixedColor = mixColors(new int[]{colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n], colorCodes[o], colorCodes[p]});
                                        if (!generatedColors.contains(mixedColor)) {
                                            generatedColors.add(mixedColor);
                                            bw.write(String.format("0x%06X %s%s%s%s%s%s%s%s", mixedColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n], colorAbvs[o], colorAbvs[p]));
                                            bw.newLine();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("8 color combinations done.");
        bw.close();
    }
    
    /*
    public static void generateComposites(String filename, Set<Integer> generatedColors, int baseColor) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
        for (int i = 0; i < colorCodes.length; i++) {
            int mixedColor = mixColors(new int[]{baseColor, colorCodes[i]});
            if (!generatedColors.contains(mixedColor)) {
                generatedColors.add(mixedColor);
                bw.write(String.format("0x%06X 0x%06X %s", mixedColor, baseColor, colorAbvs[i]));
                bw.newLine();
            }
        }
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j]});
                if (!generatedColors.contains(mixedColor)) {
                    generatedColors.add(mixedColor);
                    bw.write(String.format("0x%06X 0x%06X %s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j]));
                    bw.newLine();
                }
            }
        }
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k]});
                    if (!generatedColors.contains(mixedColor)) {
                        generatedColors.add(mixedColor);
                        bw.write(String.format("0x%06X 0x%06X %s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k]));
                        bw.newLine();
                    }
                }
            }
        }
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l]});
                        if (!generatedColors.contains(mixedColor)) {
                            generatedColors.add(mixedColor);
                            bw.write(String.format("0x%06X 0x%06X %s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l]));
                            bw.newLine();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m]});
                            if (!generatedColors.contains(mixedColor)) {
                                generatedColors.add(mixedColor);
                                bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m]));
                                bw.newLine();
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n]});
                                if (!generatedColors.contains(mixedColor)) {
                                    generatedColors.add(mixedColor);
                                    bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n]));
                                    bw.newLine();
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                for (int o = n; o < colorCodes.length; o++) {
                                    int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n], colorCodes[o]});
                                    if (!generatedColors.contains(mixedColor)) {
                                        generatedColors.add(mixedColor);
                                        bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n], colorAbvs[o]));
                                        bw.newLine();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                for (int o = n; o < colorCodes.length; o++) {
                                    for (int p = o; p < colorCodes.length; p++) {
                                        int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n], colorCodes[o], colorCodes[p]});
                                        if (!generatedColors.contains(mixedColor)) {
                                            generatedColors.add(mixedColor);
                                            bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n], colorAbvs[o], colorAbvs[p]));
                                            bw.newLine();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        bw.close();
    }
    
    public static void generateAllComposites(String outputFilename, String rootFileName, Set<Integer> generatedColors) throws Exception {
        Set<Integer> baseColors = new LinkedHashSet<Integer>();
        loadFile(rootFileName, baseColors);
        int count = 0;
        for (int baseColor : baseColors) {
            generateComposites(outputFilename, generatedColors, baseColor);
            count++;
            if (count % 1000 == 0) {
                System.out.println(count + " base colors processed.");
            }
        }
    }
    */
    public static void generateComposites2(String outputFilename, String rootFileName, Set<Integer> generatedColors) throws Exception {
        Set<Integer> baseColors = new LinkedHashSet<Integer>();
        loadFile(rootFileName, baseColors);
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilename, true));
        long count = 0l;
        long total = (long) baseColors.size() * (long) (735470); // Total combinations of 1-8 colors from 16 options, times number of base colors
        for (int i = 0; i < colorCodes.length; i++) {
            for (int baseColor : baseColors) {
                int mixedColor = mixColors(new int[]{baseColor, colorCodes[i]});
                if (!generatedColors.contains(mixedColor)) {
                    generatedColors.add(mixedColor);
                    bw.write(String.format("0x%06X 0x%06X %s", mixedColor, baseColor, colorAbvs[i]));
                    bw.newLine();
                }
                count++;
                if (count % 1000000000l == 0l) {
                    System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                }
            } 
        }
        System.out.println("1 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int baseColor : baseColors) {
                    int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j]});
                    if (!generatedColors.contains(mixedColor)) {
                        generatedColors.add(mixedColor);
                        bw.write(String.format("0x%06X 0x%06X %s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j]));
                        bw.newLine();
                    }
                    count++;
                    if (count % 1000000000l == 0l) {
                        System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                    }
                }
            }
        }
        System.out.println("2 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int baseColor : baseColors) {
                        int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k]});
                        if (!generatedColors.contains(mixedColor)) {
                            generatedColors.add(mixedColor);
                            bw.write(String.format("0x%06X 0x%06X %s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k]));
                            bw.newLine();
                        }
                        count++;
                        if (count % 1000000000l == 0l) {
                            System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                        }
                    }
                }
            }
        }
        System.out.println("3 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int baseColor : baseColors) {
                            int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l]});
                            if (!generatedColors.contains(mixedColor)) {
                                generatedColors.add(mixedColor);
                                bw.write(String.format("0x%06X 0x%06X %s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l]));
                                bw.newLine();
                            }
                            count++;
                            if (count % 1000000000l == 0l) {
                                System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                            }
                        }
                    }
                }
            }
        }
        System.out.println("4 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int baseColor : baseColors) {
                                int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m]});
                                if (!generatedColors.contains(mixedColor)) {
                                    generatedColors.add(mixedColor);
                                    bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m]));
                                    bw.newLine();
                                }
                                count++;
                                if (count % 1000000000l == 0l) {
                                    System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("5 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                for (int baseColor : baseColors) {
                                    int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n]});
                                    if (!generatedColors.contains(mixedColor)) {
                                        generatedColors.add(mixedColor);
                                        bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n]));
                                        bw.newLine();
                                    }
                                    count++;
                                    if (count % 1000000000l == 0l) {
                                        System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("6 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                for (int o = n; o < colorCodes.length; o++) {
                                    for (int baseColor : baseColors) {
                                        int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n], colorCodes[o]});
                                        if (!generatedColors.contains(mixedColor)) {
                                            generatedColors.add(mixedColor);
                                            bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n], colorAbvs[o]));
                                            bw.newLine();
                                        }
                                        count++;
                                        if (count % 1000000000l == 0l) {
                                            System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("7 color combinations done.");
        for (int i = 0; i < colorCodes.length; i++) {
            for (int j = i; j < colorCodes.length; j++) {
                for (int k = j; k < colorCodes.length; k++) {
                    for (int l = k; l < colorCodes.length; l++) {
                        for (int m = l; m < colorCodes.length; m++) {
                            for (int n = m; n < colorCodes.length; n++) {
                                for (int o = n; o < colorCodes.length; o++) {
                                    for (int p = o; p < colorCodes.length; p++) {
                                        for (int baseColor : baseColors) {
                                            int mixedColor = mixColors(new int[]{baseColor, colorCodes[i], colorCodes[j], colorCodes[k], colorCodes[l], colorCodes[m], colorCodes[n], colorCodes[o], colorCodes[p]});
                                            if (!generatedColors.contains(mixedColor)) {
                                                generatedColors.add(mixedColor);
                                                bw.write(String.format("0x%06X 0x%06X %s%s%s%s%s%s%s%s", mixedColor, baseColor, colorAbvs[i], colorAbvs[j], colorAbvs[k], colorAbvs[l], colorAbvs[m], colorAbvs[n], colorAbvs[o], colorAbvs[p]));
                                                bw.newLine();
                                            }
                                            count++;
                                            if (count % 1000000000l == 0l) {
                                                System.out.println(String.format("%d (%.2f%%) color combinations processed.", count, (float) count / (float) total * 100.0f));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("8 color combinations done.");
        bw.close();
    }
    
    public static void loadFile(String filename, Set<Integer> colorSet) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length > 0) {
                int color = Integer.parseInt(parts[0].substring(2), 16);
                colorSet.add(color);
            }
        }
        br.close();
    }
    
    public static void main(String[] args) throws Exception {
        Set<Integer> generatedColors = new HashSet<Integer>();

        loadFile("Primary.txt", generatedColors);
        loadFile("Secondary.txt", generatedColors);
        loadFile("Tertiary.txt", generatedColors);
        loadFile("Quaternary.txt", generatedColors);
        loadFile("Quinary.txt", generatedColors);
        loadFile("Senary.txt", generatedColors);
        loadFile("Septenary.txt", generatedColors);
        loadFile("Octonary.txt", generatedColors);
        loadFile("Nonary.txt", generatedColors);
        loadFile("Denary.txt", generatedColors);

        generateComposites2("Undenary.txt", "Denary.txt", generatedColors);
    }
}
