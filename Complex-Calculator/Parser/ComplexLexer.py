from ply import lex
from Utils.ComplexNumber import ComplexNumber

# Lexer, declare all the tokens needed for this grammar
tokens = (
    'NUMBER',
    'PLUS',
    'MINUS',
    'TIMES',
    'DIVIDE',
    'COMPLEMENT_VALUE',
    'IMAGINARY_UNIT',
    'O_PAREN',
    'C_PAREN'
)

# declare returnable values for tokens, and custom operations
t_PLUS = r'\+'
t_MINUS = r'-'
t_TIMES = r'\*'
t_DIVIDE = r'/'
t_O_PAREN = r'\('
t_C_PAREN = r'\)'
t_COMPLEMENT_VALUE = r'\|\|'

t_ignore = ' \t\n\r'  # all the values I want to ignore for lexer


# I'll make wach number work as a complex, so my class ComplexNumber manage all operations,
# the idea is that C = Z + I , so Z = 0 or i = 0 can be managed by COmplexNUmber class
def t_IMAGINARY_UNIT(t):
    r"""\d+(\.\d*)?i"""
    t.value = ComplexNumber(0, float(t.value[:-1]) if '.' in t.value else int(t.value[:-1]))
    return t


def t_NUMBER(t):
    r"""\d+(\.\d+)?"""
    t.value = ComplexNumber(float(t.value) if '.' in t.value else int(t.value), 0)
    return t


# Error management, TODO manage this error
def t_error(t):
    print(f"Caracter no valido ingresado en el texto [TKN invalido] '{t.value[0]}'")
    t.lexer.skip(1)


def make_lexer():
    lexer = lex.lex()
    return lexer


def test():
    input_string = "2+8i"
    lexer = lex.lex()
    lexer.input(input_string)
    for token in lexer:
        print(token)
