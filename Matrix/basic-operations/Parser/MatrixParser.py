from ply import yacc
from Parser.MatrixLexer import tokens
from Utils.Matrix import Matrix
from Utils.DerivationTree.DerivationNode import DerivationNode
import numpy as np

# 
"""
SOLVES GRAMMAR TO EXECUTE OPERATIONS WITH MATRIXES
EJ:
matrix A
    1 2 3
    4 5 6
solve
    A + A


GRAMMAR

code_file : middleware_section solve_section
middleware_section : matrix_section
matrix_section : matrix_declaration
                | matrix_declaration matrix_section
matrix_declaration : MATRIX ID NEWLINE matrix_content
matrix_content : matrix_row NEWLINE
                | matrix_row NEWLINE matrix_content
matrix_row : NUMBER
            | NUMBER matrix_row
solve_section : SOLVE NEWLINE solve_list
solve_list : expression NEWLINE
            | expression NEWLINE solve_list
expression : expression PLUS term
            | expression MINUS term
            | term
term : term TIMES factor
        | term DIVIDE factor
        | factor    
factor : ID
        | O_PAREN expression C_PAREN
        | NUMBER
"""

# array of matrices and operations (derivationNode with str or Matrix as node)
_data = {"matrices": [], "operations": []}

def p_code_matrix(p):
    '''code_file : middleware_section solve_section'''
    # save the operations and return final result
    _data["matrices"] = p[1]
    _data["operations"] = p[2]
    p[0] = _data

# this acts as a middleware to save _data["matrices"]
def p_middleware_section(p):
    '''middleware_section : matrix_section'''
    p[0] = p[1]
    global _data
    _data["matrices"] = p[0]

# Define the grammar rules
def p_matrix_dec_section(p):
    '''matrix_section   : matrix_declaration
                        | matrix_declaration matrix_section'''
    # check if len(p) is 2, if this, create a new array with p[1], otherwise, concat p[1] and p[2] as a matrix
    if(len(p) == 2):
        p[0] = [p[1]]
    else:
        p[0] = [p[1]] + p[2]

def p_matrix_declaration(p):
    '''matrix_declaration : MATRIX ID NEWLINE matrix_content '''
    p[0] = Matrix(p[2], np.array(p[4]))

def p_matrix_definition(p):
    '''matrix_content   : matrix_row NEWLINE
                        | matrix_row NEWLINE matrix_content'''
    # check if len(p) is 3, if this, create a new matrix with p[1] as content, otherwise, concat p[1] and p[2] as a matrix
    if(len(p) == 3):
        p[0] = [p[1]]
    else:
        p[0] = [p[1]] + p[3]

def p_matrix_definition_line(p):
    '''matrix_row   : NUMBER
                    | NUMBER matrix_row'''
    # check production type, if is first then send p[0] as number, otherwise, concat
    if(len(p) == 2):
        p[0] = [float(p[1])]
    else:
        # create a new array and append p[1] as array and p[2] as array
        p[0] = [p[1]] + p[2]

def p_solve_section(p):
    '''solve_section : SOLVE NEWLINE solve_list'''
    p[0] = p[3]
    # global _data

def p_operation_list(p):
    '''solve_list   : expression NEWLINE
                    | expression NEWLINE solve_list'''
    # check if len(p) is 3, if this, create a new array with p[1], otherwise, concat p[1] and p[2] as a matrix
    if(len(p) == 3):
        p[0] = [p[1]]
    else:
        p[0] = [p[1]] + p[3]

# define a list of production rules to operate any arithmetical rule but make it no ambiguous
def p_expression_plus_minu(p):
    '''expression : expression PLUS term
                  | expression MINUS term'''
    if p[2] == '+':  # sum operation
        tmp_root_node = DerivationNode("+", p[1], p[3])
        p[1].parent_node = p[3].parent_node = tmp_root_node
        p[0] = tmp_root_node
    elif p[2] == '-':  # subtraction
        tmp_root_node = DerivationNode("-", p[1], p[3])
        p[1].parent_node = p[3].parent_node = tmp_root_node
        p[0] = tmp_root_node

def p_expression_term(p):
    '''expression : term'''
    p[0] = p[1]

def p_term_times_div(p):
    '''term : term TIMES factor
            | term DIVIDE factor'''
    if p[2] == '*':
        tmp_node = DerivationNode("*", p[1], p[3])
        p[1].parent_node = p[3].parent_node = tmp_node
        p[0] = tmp_node
    elif p[2] == '/':
        tmp_node = DerivationNode("/", p[1], p[3])
        p[1].parent_node = p[3].parent_node = tmp_node
        p[0] = tmp_node

def p_term_factor(p):
    '''term : factor'''
    p[0] = p[1]

def p_factor_num(p):
    '''factor : ID'''
    # createa a Matrix by calling method find_matrix_by_id with p[1] as parameter
    p[0] = DerivationNode(find_matrix_by_id(p[1]))

def p_factor_parenthesis_exp(p):
    '''factor : O_PAREN expression C_PAREN'''
    tmp_node = DerivationNode("()", p[2])
    p[2].parent_node = tmp_node
    p[0] = tmp_node

def p_error(p):
    print(f"Syntax error at '{p.value}'")


def find_column(p):
    last_cr = p.lexer.lexdata.rfind('\n', 0, p.lexpos)
    if last_cr < 0:
        last_cr = 0
    return p.lexpos - last_cr

def make_parser():
    parser = yacc.yacc()
    return parser

# find matrix by ID name
def find_matrix_by_id(id: str):
    global _data
    for item in _data["matrices"]:
        if item.name == id:
            return item.matrix
    print(f'cannot find matrix with id {id} in matrices')
    return None
