
# parsetab.py
# This file is automatically generated. Do not edit.
# pylint: disable=W,C,R
_tabversion = '3.10'

_lr_method = 'LALR'

_lr_signature = 'COMPLEMENT_VALUE C_PAREN DIVIDE IMAGINARY_UNIT MINUS NUMBER O_PAREN PLUS TIMES\n    expression : expression PLUS term\n               | expression MINUS term\n    \n    expression : term\n    \n    term : term TIMES factor\n         | term DIVIDE factor\n    \n    term : factor\n    factor : NUMBERfactor : COMPLEMENT_VALUE expression COMPLEMENT_VALUEfactor : IMAGINARY_UNITfactor : O_PAREN expression C_PAREN'
    
_lr_action_items = {'NUMBER':([0,5,7,8,9,10,11,],[4,4,4,4,4,4,4,]),'COMPLEMENT_VALUE':([0,2,3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,],[5,-3,-6,-7,5,-9,5,5,5,5,5,18,-1,-2,-4,-5,-8,-10,]),'IMAGINARY_UNIT':([0,5,7,8,9,10,11,],[6,6,6,6,6,6,6,]),'O_PAREN':([0,5,7,8,9,10,11,],[7,7,7,7,7,7,7,]),'$end':([1,2,3,4,6,14,15,16,17,18,19,],[0,-3,-6,-7,-9,-1,-2,-4,-5,-8,-10,]),'PLUS':([1,2,3,4,6,12,13,14,15,16,17,18,19,],[8,-3,-6,-7,-9,8,8,-1,-2,-4,-5,-8,-10,]),'MINUS':([1,2,3,4,6,12,13,14,15,16,17,18,19,],[9,-3,-6,-7,-9,9,9,-1,-2,-4,-5,-8,-10,]),'C_PAREN':([2,3,4,6,13,14,15,16,17,18,19,],[-3,-6,-7,-9,19,-1,-2,-4,-5,-8,-10,]),'TIMES':([2,3,4,6,14,15,16,17,18,19,],[10,-6,-7,-9,10,10,-4,-5,-8,-10,]),'DIVIDE':([2,3,4,6,14,15,16,17,18,19,],[11,-6,-7,-9,11,11,-4,-5,-8,-10,]),}

_lr_action = {}
for _k, _v in _lr_action_items.items():
   for _x,_y in zip(_v[0],_v[1]):
      if not _x in _lr_action:  _lr_action[_x] = {}
      _lr_action[_x][_k] = _y
del _lr_action_items

_lr_goto_items = {'expression':([0,5,7,],[1,12,13,]),'term':([0,5,7,8,9,],[2,2,2,14,15,]),'factor':([0,5,7,8,9,10,11,],[3,3,3,3,3,16,17,]),}

_lr_goto = {}
for _k, _v in _lr_goto_items.items():
   for _x, _y in zip(_v[0], _v[1]):
       if not _x in _lr_goto: _lr_goto[_x] = {}
       _lr_goto[_x][_k] = _y
del _lr_goto_items
_lr_productions = [
  ("S' -> expression","S'",1,None,None,None),
  ('expression -> expression PLUS term','expression',3,'p_expression_plus_minus','ComplexParser.py',29),
  ('expression -> expression MINUS term','expression',3,'p_expression_plus_minus','ComplexParser.py',30),
  ('expression -> term','expression',1,'p_expression_term','ComplexParser.py',45),
  ('term -> term TIMES factor','term',3,'p_term_times_divide','ComplexParser.py',53),
  ('term -> term DIVIDE factor','term',3,'p_term_times_divide','ComplexParser.py',54),
  ('term -> factor','term',1,'p_term_factor','ComplexParser.py',73),
  ('factor -> NUMBER','factor',1,'p_factor_number','ComplexParser.py',80),
  ('factor -> COMPLEMENT_VALUE expression COMPLEMENT_VALUE','factor',3,'p_factor_absolute_value','ComplexParser.py',87),
  ('factor -> IMAGINARY_UNIT','factor',1,'p_factor_imaginary_unit','ComplexParser.py',95),
  ('factor -> O_PAREN expression C_PAREN','factor',3,'p_factor_parenthesized_expression','ComplexParser.py',101),
]
