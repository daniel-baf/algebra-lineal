from ply import yacc
from Utils.ComplexNumber import ComplexNumber
from Utils.DerivationTree.DerivationNode import DerivationNode
from Parser.ComplexLexer import tokens  # this is not used in this class, but is used in the generated parser


# precedence rules
# precedence = (
#     ('left', 'PLUS', 'MINUS'),
#     ('left', 'TIMES', 'DIVIDE'),
# )


# Production rules
# Parser declaration, non-ambiguous grammar
# E -> E + T
#   | E - T
#   | T
# T -> T * F
#   | T / F
#   | F
# F -> Z
#   | C
#   | ( E )
#
def p_expression_plus_minus(p):
    """
    expression : expression PLUS term
               | expression MINUS term
    """
    if p[2] == '+':  # sum operation
        tmp_node = DerivationNode("+", p[1], p[3])
        p[1].parent_node = p[3].parent_node = tmp_node
        p[0] = tmp_node
    elif p[2] == '-':  # subtraction
        tmp_node = DerivationNode("-", p[1], p[3])
        p[1].parent_node = p[3].parent_node = tmp_node
        p[0] = tmp_node


def p_expression_term(p):
    """
    expression : term
    """
    p[0] = p[1]


def p_term_times_divide(p):
    """
    term : term TIMES factor
         | term DIVIDE factor
    """
    if p[2] == '*':
        tmp_node = DerivationNode("*", p[1], p[3])
        p[1].parent_node = p[3].parent_node = tmp_node
        p[0] = tmp_node
    elif p[2] == '/':
        if p[3] == 0:
            print("Error: No se puede dividir por cero")
            p[0] = DerivationNode(ComplexNumber(0, 0))
        else:
            tmp_node = DerivationNode("/", p[1], p[3])
            p[1].parent_node = p[3].parent_node = tmp_node
            p[0] = tmp_node


def p_term_factor(p):
    """
    term : factor
    """
    p[0] = p[1]


def p_factor_number(p):
    """factor : NUMBER"""
    p[0] = DerivationNode(p[1])
    # p[0] = p[1]


def p_factor_absolute_value(p):
    """factor : COMPLEMENT_VALUE expression COMPLEMENT_VALUE"""
    tmp_node = DerivationNode("||", p[2])
    p[2].parent_node = tmp_node
    p[0] = tmp_node


def p_factor_imaginary_unit(p):
    """factor : IMAGINARY_UNIT"""
    p[0] = DerivationNode(p[1])


def p_factor_parenthesized_expression(p):
    """factor : O_PAREN expression C_PAREN"""
    tmp_node = DerivationNode("()", p[2])
    p[2].parent_node = tmp_node
    p[0] = tmp_node


def p_error(p):
    print(f"Error de sintaxis en la linea {p.lineno}, posicion {find_column(p)}: TOKEN no esprado [INV TKN] '{p.value}'")


def find_column(p):
    last_cr = p.lexer.lexdata.rfind('\n', 0, p.lexpos)
    if last_cr < 0:
        last_cr = 0
    return p.lexpos - last_cr


def make_parser():
    parser = yacc.yacc()
    return parser

# parser = yacc.yacc()

# TODO: no imprime bien cuando elimina parentesis y el signo de arriba es -
