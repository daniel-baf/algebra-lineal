from ply import lex

# Lexer, declare all the tokens needed for this grammar
tokens = (
    'MATRIX',
    'GAUSS',  # 'solve' keyword
    'GAUSS_JORDAN',  # 'solve' keyword
    'ID',
    'NUMBER',
    'NEWLINE'
)

# declare returnable values for tokens, and custom operations
t_NEWLINE = r'\n'

t_ignore = ' \t\r'  # all the values I want to ignore for lexer

def t_GAUSS(t):
    r"""gauss"""
    return t


def t_GAUSS_JORDAN(t):
    r"""gjordan"""
    return t


def t_MATRIX(t):
    r"""matrix"""
    return t


# catches a string id for matrix creation
def t_ID(t):
    r"""[a-zA-Z_]+"""
    return t


# catches a number for matrix creation, if it has a dot, it's a float, if not, it's an int
def t_NUMBER(t):
    r"""[+-]?\d+(\.\d+)?"""
    t.value = float(t.value)
    return t

# Error management, TODO manage this error
def t_error(t):
    print(f"Illegal character '{t.value[0]}'")
    t.lexer.skip(1)


def make_lexer():
    lexer = lex.lex()
    return lexer


def test():
    input_string = "matrix A \n1 2 -2\n5 9 12\n-3 4 5\nmatrix B \n 1 2 3\n4 5 6\n7 8 9\n gauss A B C \ngjordan C D E\n\n"
    lexer = lex.lex()
    lexer.input(input_string)
    for token in lexer:
        print(token)

