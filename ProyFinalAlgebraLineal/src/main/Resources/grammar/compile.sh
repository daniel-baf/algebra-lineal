echo "-------------------------------"
echo "GENERATING LEXER"
/opt/jflex/jflex-1.9.1/bin/jflex --verbose src/main/Resources/grammar/MatrixLexer.flex -d src/main/java/Model/Compiler
echo "-------------------------------"
echo "GENERATING PARSER"
java -jar /opt/cup/java-cup-11b.jar -parser MatrixParser -symbols sym -destdir src/main/java/Model/Compiler src/main/Resources/grammar/MatrixSyntax.cup