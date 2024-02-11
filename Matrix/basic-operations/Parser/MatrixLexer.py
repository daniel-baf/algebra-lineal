from ply import lex

# Lexer, declare all the tokens needed for this grammar
tokens = (
    'MATRIX',
    'SOLVE',  # 'solve' keyword
    'ID',
    'NUMBER',
    'PLUS',
    'MINUS',
    'TIMES',
    'DIVIDE',
    'O_PAREN',
    'C_PAREN',
    'NEWLINE'
)

# declare returnable values for tokens, and custom operations
t_PLUS = r'\+'
t_MINUS = r'-'
t_TIMES = r'\*'
t_DIVIDE = r'/'
t_O_PAREN = r'\('
t_C_PAREN = r'\)'
t_NEWLINE = r'\n'

t_ignore = ' \t\r'  # all the values I want to ignore for lexer


def t_MATRIX(t):
    r"""matrix"""
    return t

def t_SOLVE(t):
    r"""solve"""
    return t

# catches a string id for matrix creation
def t_ID(t):
    r"""[a-zA-Z_]"""
    return t


# catches a number for matrix creation, if it has a dot, it's a float, if not, it's an int
def t_NUMBER(t):
    r"""[+-]?\d+(\.\d+)?"""
    t.value = float(t.value)
    return t

# Error management, TODO manage this error
def t_error(t):
    print(f"Caracter invalido para generar token [TKN error] '{t.value[0]}'")
    t.lexer.skip(1)


def make_lexer():
    lexer = lex.lex()
    return lexer


def test():
    input_string = "matrix A \n1 2 -2\n5 9 12\n-3 4 5\nmatrix B \n 1 2 3\n4 5 6\n7 8 9\n solve A*B\nA-B*(A+B) A/(B*B)\n\n"
    lexer = lex.lex()
    lexer.input(input_string)
    for token in lexer:
        print(token)


