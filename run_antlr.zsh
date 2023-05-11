#!/usr/bin/zsh

base_pth="${0:a:h}"
infile="${base_pth}/antlr/CML.g4"
outdir="${base_pth}/cml_parser/src/main/java/com/jaytux/cml_parser/"
package="com.jaytux.cml_parser"

antlr4 "$infile" -Dlanguage=Java -visitor -o "$outdir" -package "$package"