from ply import yacc
from Parser.MatrixLexer import tokens
from Matrix.Matrix import Matrix
import numpy as np

# array of matrices and operations (derivationNode with str or Matrix as node)
_data = {"matrices": [], "operations": []}

def p_code_matrix(p):
    '''code_file : matrix_section solve_section'''
    # save the operations and return final result
    global _data
    _data["matrices"] = p[1]
    _data["operations"] = p[2]
    p[0] = _data

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
    '''solve_section : SOLVE solve_list'''
    p[0] = p[2]

def p_operation_list(p):
    '''solve_list   : ID 
                    | ID  solve_list'''
    # check if len(p) is 3, if this, create a new array with p[1], otherwise, concat p[1] and p[2] as a matrix
    if(len(p) == 2):
        p[0] = [p[1]]
    else:
        p[0] = [p[1]] + p[2]


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
