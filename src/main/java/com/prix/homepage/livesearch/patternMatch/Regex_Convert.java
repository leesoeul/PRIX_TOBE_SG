package com.prix.homepage.livesearch.patternMatch;

public class Regex_Convert {
    public Regex_Convert() {
    }

    public static String[] PrositeToPerl(String[] var0) {
        int var1 = var0.length;
        String[] var2 = new String[var1];

        for (int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = var0[var3].trim();
            var2[var3] = var2[var3].replace('x', '.');
            var2[var3] = var2[var3].replace('X', '.');
            var2[var3] = var2[var3].replace("{", "[^");
            var2[var3] = var2[var3].replace('}', ']');
            var2[var3] = var2[var3].replace('(', '{');
            var2[var3] = var2[var3].replace(')', '}');
            var2[var3] = var2[var3].replace('<', '^');
            var2[var3] = var2[var3].replace('>', '$');
            var2[var3] = var2[var3].replace("-", "");
            var2[var3] = var2[var3].replace(" ", "");
        }

        return var2;
    }
}
